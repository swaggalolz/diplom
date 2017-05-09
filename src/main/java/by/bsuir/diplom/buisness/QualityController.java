package by.bsuir.diplom.buisness;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.bsuir.diplom.Debugger;
import by.bsuir.diplom.draw.Content;
import by.bsuir.diplom.draw.ContentLine;
import by.bsuir.diplom.filters.BinaryFilter;
import by.bsuir.diplom.filters.Filter;
import by.bsuir.diplom.filters.MedianFilter;
import by.bsuir.diplom.logic.ContentAnalyzer;
import by.bsuir.diplom.logic.NumberDetector;
import by.bsuir.diplom.utils.ImageHelper;

public class QualityController {


    final static List<Filter> filters = new LinkedList<Filter>();

    static {
        filters.add(new MedianFilter());
        filters.add(new BinaryFilter());
    }

    public boolean control(BufferedImage image) throws Exception {

        final int width = image.getWidth();
        final int height = image.getHeight();
        int[] filteredPixels = applyFilters(image, filters);

        List<ContentLine> lines = filterContentLines(ContentAnalyzer.getContentLines(width, height, filteredPixels));
        ImageHelper.saveImage(ImageHelper.getImageFromPixels(filteredPixels, width, height, BufferedImage.TYPE_INT_RGB), "E:/dip/res.jpg");

        Debugger.debugContentLines(ImageHelper.getImageFromPixels(filteredPixels, width, height, BufferedImage.TYPE_INT_RGB), lines, "E:/dip/res/");

        List<String> serialNumbers = indicateSerialNumbers(ImageHelper.getImageFromPixels(filteredPixels, width, height, BufferedImage.TYPE_INT_RGB), lines);


        return false;
    }

    private List<String> indicateSerialNumbers(BufferedImage image, List<ContentLine> lines) {
        List<String> resultNumbers = new ArrayList<>();

        for (ContentLine contentLine : lines) {
            String number = "";

            for (Content content : contentLine.getLine()) {
                number += NumberDetector.detectNumber(ImageHelper.getSubImage(image, content.x, content.y, content.width, content.height));
            }
            System.out.println(number);
            resultNumbers.add(number);
        }

        return resultNumbers;
    }

    private List<ContentLine> filterContentLines(List<ContentLine> lines) {
        final List<ContentLine> result = new ArrayList<>();
        for (ContentLine line : lines) {
            if (line.size() == 7) {
                result.add(line);
            }
        }
        return result;
    }


    private int[] applyFilters(BufferedImage image, List<Filter> filters) throws IOException {
        final int width = image.getWidth();
        final int height = image.getHeight();

        int[] pixels = ImageHelper.getPixels(image);
        for (Filter filter : filters) {
            pixels = filter.transform(width, height, pixels);
        }

        return pixels;
    }


    public static void main(String[] args) throws Exception {
        QualityController controller = new QualityController();
        controller.control(ImageHelper.readImage("E:/dip/ORIG/G1.jpg"));
    }

}
