package by.bsuir.diplom.logic;

import by.bsuir.diplom.utils.ImageHelper;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class NumberDetector {

    public static String detectNumber(BufferedImage img) {
        Mat image = ImageHelper.bufferedImageToMat(img);



        return "S";
    }


}
