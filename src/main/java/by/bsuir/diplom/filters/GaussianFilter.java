package by.bsuir.diplom.filters;


import by.bsuir.diplom.utils.ColorRGB;

public class GaussianFilter implements Filter {

    private static int getPixRepeat(int x, int y, int w, int h, int[] pix) {
        int x2 = x % w;
        if (w == 1) x2 = 0;
        int y2 = y % h;
        if (h == 1) x2 = 0;
        if (x2 < 0) x2 += w;
        if (y2 < 0) y2 += h;
        return pix[y2 * w + x2];
    }

    @Override
    public int[] transform(int width, int height, int[] pixels) {
        final int[] result = new int[width * height];
        final int[] tmppix = new int[width * height + 1];

        for (int y = 0; y < height; ++y) {
            int pos = y * width;
            for (int x = 0; x < width; ++x) {
                int r = 0;
                for (int c = 0; c < 3; c++) {
                    // [1 4 6 4 1] filter
                    r += ColorRGB.channelShift((
                            (ColorRGB.getChannel(getPixRepeat(x - 2, y, width, height, pixels), c) +
                                    ColorRGB.getChannel(getPixRepeat(x - 1, y, width, height, pixels), c) * 4 +
                                    ColorRGB.getChannel(getPixRepeat(x, y, width, height, pixels), c) * 6 +
                                    ColorRGB.getChannel(getPixRepeat(x + 1, y, width, height, pixels), c) * 4 +
                                    ColorRGB.getChannel(getPixRepeat(x + 2, y, width, height, pixels), c)) / 16
                    ), c);
                }
                tmppix[pos + x] = r;
            }
        }

        // vertical filtering
        for (int x = 0; x < width; ++x) {
            int pos = x;
            for (int y = 0; y < height; y++) {
                int r = 0;
                for (int c = 0; c < 3; c++) {
                    r += ColorRGB.channelShift((
                            (ColorRGB.getChannel(getPixRepeat(x, y - 2, width, height, tmppix), c) +
                                    ColorRGB.getChannel(getPixRepeat(x, y - 1, width, height, tmppix), c) * 4 +
                                    ColorRGB.getChannel(getPixRepeat(x, y, width, height, tmppix), c) * 6 +
                                    ColorRGB.getChannel(getPixRepeat(x, y + 1, width, height, tmppix), c) * 4 +
                                    ColorRGB.getChannel(getPixRepeat(x, y + 2, width, height, tmppix), c)) / 16
                    ), c);
                }
                result[pos] = r + (0xff << 24);
                pos += width;
            }
        }
        return result;
    }
}
