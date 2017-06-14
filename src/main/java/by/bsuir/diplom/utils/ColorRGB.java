package by.bsuir.diplom.utils;

import java.util.Random;

public class ColorRGB {

    private static final Random RANDOM = new Random();
    private int red;
    private int green;
    private int blue;

    public ColorRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static int getRed(int color) {
        return (color & 0x00ff0000) >> 16;
    }

    public static int getGreen(int color) {
        return (color & 0x0000ff00) >> 8;
    }

    public static int getBlue(int color) {
        return color & 0x000000ff;
    }

    public static int getLuminance(int pixel){
        int red = (pixel & 0xff0000) >> 16;
        int green = (pixel & 0xff00) >> 8;
        int blue = pixel & 0xff;
        return Math.round(0.299f * red + 0.587f * green + 0.114f * blue);
    }

    public static int getMixColor(int red, int green, int blue) {
        return red << 16 | green << 8 | blue;
    }

    public static int getGray(int pixel) {
        int red = (pixel & 0xff0000) >> 16;
        int green = (pixel & 0xff00) >> 8;
        int blue = pixel & 0xff;
        int gray = (int)(0.2126 * red + 0.7152 * green + 0.0722 * blue);
        return getMixColor(gray, gray, gray);
    }

    public static int getBlackColor() {
        return getMixColor(0, 0, 0);
    }

    public static int getWhiteColor() {
        return getMixColor(255, 255, 255);
    }

    // extract a channel value from a RGB 'int' packed color
    public static int getChannel(int color, int channel) {
        return (color >> (8 * channel)) & 0xFF;
    }

    // shift a color value of the corresponding channel offset
    public static int channelShift(int color, int channel) {
        return (color & 0xFF) << (8 * channel);
    }

    public static ColorRGB getRandomColorRGB() {
        return new ColorRGB(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255));
    }

    public int getMixColor() {
        return red << 16 | green << 8 | blue;
    }


}
