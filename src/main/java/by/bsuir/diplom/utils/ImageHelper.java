package by.bsuir.diplom.utils;

import org.apache.commons.io.FilenameUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

    private static final String DOT = ".";
    private static final String JPG_EXTENSION = "jpg";
    private static final String TRANSFORMED_PATH_SUFFIX = "-TRANSFORMED";
    private static volatile int counter = 0;

    private ImageHelper() {
    }

    public static BufferedImage readImage(String path) throws IOException {
        return path != null ? ImageIO.read(new File(path)) : null;
    }

    public static BufferedImage readImage(File file) throws IOException {
        return file != null ? ImageIO.read(file) : null;
    }

    public static void saveImage(BufferedImage image, String path) throws IOException {
        if (image != null && path != null)
            ImageIO.write(image, JPG_EXTENSION, new File(getTransformedPath(path)));
    }

//    public static void saveImage(BufferedImage image, Content content, String path) throws IOException {
//        if (image != null && path != null)
//            ImageIO.write(image.getSubimage(content.x, content.y, content.width, content.height), JPG_EXTENSION, new File(getTransformedPath(path)));
//    }

    public static BufferedImage getSubImage(final BufferedImage sourceImage, int x, int y, int width, int height) {
        BufferedImage img;
        try {
            img = sourceImage.getSubimage(x, y, width, height);
        } catch (RasterFormatException e) {
            System.err.printf("RasterFormatException: BufferedImage#getSubimage(%d, %d, %d, %d)\r\n", x, y, width, height);
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public static BufferedImage getImageFromPixels(int[] pixels, int width, int height, int imageType) {
        BufferedImage image = new BufferedImage(width, height, imageType);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

    public static int[] getPixels(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int[] pixels = new int[width * height];
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[index++] = image.getRGB(x, y);
            }
        }
        return pixels;
    }

    private static String getTransformedPath(String imagePath) {
        String path = FilenameUtils.removeExtension(imagePath);
        String extension = FilenameUtils.getExtension(imagePath);
        return path + TRANSFORMED_PATH_SUFFIX + ++counter + DOT + extension;
    }


    public static BufferedImage rotate(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x0 = 0.5 * (width - 1);     // point to rotate about
        double y0 = 0.5 * (height - 1);     // center of image

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // rotation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * cos - b * sin + x0);
                int yy = (int) (+a * sin + b * cos + y0);

                // plot pixel (x, y) the same color as (xx, yy) if it's in bounds
                result.setRGB(x, y, (xx >= 0 && xx < width && yy >= 0 && yy < height) ? image.getRGB(xx, yy) : ColorRGB.getWhiteColor());
            }
        }

        return result;
    }

    public static BufferedImage rotateCw( BufferedImage img ){
        int         width  = img.getWidth();
        int         height = img.getHeight();
        BufferedImage   newImage = new BufferedImage(height, width, img.getType() );

        for( int i=0 ; i < width ; i++ )
            for( int j=0 ; j < height ; j++ )
                newImage.setRGB( height-1-j, i, img.getRGB(i,j) );

        return newImage;
    }

    public static BufferedImage rotateTo90Degrees(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();

        Graphics2D g2 = image.createGraphics();
        g2.rotate(Math.toRadians(90), 0, 0);
        g2.drawImage(image, null, 0, 0);
        return image;
    }

    // Convert image to Mat
    public static Mat bufferedImageToMat(BufferedImage im) {
        // Convert INT to BYTE
        //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
        byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer())
                .getData();

        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
        // Fill Matrix with image values
        image.put(0, 0, pixels);

        return image;

    }

    public static BufferedImage matToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
}
