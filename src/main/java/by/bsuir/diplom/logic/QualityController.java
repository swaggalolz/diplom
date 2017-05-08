package by.bsuir.diplom.logic;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import by.bsuir.diplom.filters.BinaryFilter;
import by.bsuir.diplom.filters.Filter;
import by.bsuir.diplom.filters.MedianFilter;
import by.bsuir.diplom.utils.ImageHelper;

public class QualityController {


    final static List<Filter> filters = new LinkedList<Filter>();

    static {
        filters.add(new MedianFilter());
        filters.add(new BinaryFilter());
    }

    public boolean control(BufferedImage image) throws IOException {


        BufferedImage filteredImage = applyFilters(image, filters);

        ImageHelper.saveImage(filteredImage, "E:/dip/res.jpg");

        return false;
    }




    private BufferedImage applyFilters(BufferedImage image, List<Filter> filters) throws IOException {
        final int width = image.getWidth();
        final int height = image.getHeight();

        int[] pixels = ImageHelper.getPixels(image);
        for (Filter filter : filters) {
            pixels = filter.transform(width, height, pixels);
            ImageHelper.saveImage(ImageHelper.getImageFromPixels(pixels, width, height,BufferedImage.TYPE_INT_RGB), "E:/dip/res.jpg");
        }
        return ImageHelper.getImageFromPixels(pixels, width, height,BufferedImage.TYPE_BYTE_BINARY);
    }




    public static void main(String[] args) throws Exception {
        QualityController controller = new QualityController();
        controller.control(ImageHelper.readImage("E:/dip/2.jpg"));
    }

}
