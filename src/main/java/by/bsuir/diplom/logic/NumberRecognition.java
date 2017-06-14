package by.bsuir.diplom.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberRecognition {

    private static final String IMG_SUFIX = ".png";

    public NumberRecognition() throws IOException {
        //First load the patterns
        loadPatterns();
    }

    /**
     * Return as BufferedImage the file specified
     *
     * @param file
     * @return
     * @throws IOException
     */
    private BufferedImage readImage(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        return img;
    }

    /**
     * Convert image into list of pixels (Assuming all the images have the same width&height)
     *
     * @param img
     * @return
     */
    private int[] obtainPixelsList(BufferedImage img) {

        /**
         * Like we are using only pixels totally white, or totally black, we store the entire RGB, don't worry about colours
         */
        int[] pixelsValues = new int[img.getWidth() * img.getHeight()];

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {

                int rgb = img.getRGB(i, j);
                pixelsValues[j * img.getWidth() + i] = rgb;

            }
        }

        return pixelsValues;
    }

    /**
     * Instead of using a file, store in a Hashmap the list of the array of pixels, for each pattern
     * Example:
     * 0 -> [[0_pattern_1],[0_pattern_2]...]
     * 1 -> [[1_pattern_1],[1_pattern_2]...]
     * ....
     */
    private HashMap<Integer, List<int[]>> patterns = new HashMap<>();

    /**
     * Load the patterns into the Hashmap
     * It proccess each image pixel by pixel storing as an Array of pixels
     *
     * @throws IOException
     */
    private void loadPatterns() throws IOException {
        for (int i = 0; i < 10; i++) {
            List<int[]> patternsForCurrentNumber = new ArrayList<>();
            File patternsFolder = new File(getClass().getResource("/patterns/").getFile());
            for (File file : patternsFolder.listFiles()) {
                if (file.getName().startsWith(Integer.toString(i))) {
                    BufferedImage pattern = readImage(file);

                    //Store the pattern
                    patternsForCurrentNumber.add(obtainPixelsList(pattern));
                }
            }
            //Store the list of patterns for the current number
            patterns.put(i, patternsForCurrentNumber);
        }
    }

    /**
     * Compare the pixels of the pattern of the number [patternNumber] with the pixels of the image provided
     *
     * @param patternNumber
     * @param imagePixels
     * @return
     */
    private int numberOfCoincidences(int patternNumber, int[] imagePixels) {
        int coincidences = 0;

        //Retrieve from the patterns preread the pixels of the patterns of number i
        List<int[]> patternImages = patterns.get(patternNumber);

        int maxCoincidences = 0;
        for (int[] patternPixels : patternImages) {
            int len = (patternPixels.length > imagePixels.length) ?
                    imagePixels.length : patternPixels.length;
            coincidences = 0;
            for (int i = 0; i < len; i++) {
                if (patternPixels[i] == imagePixels[i]) {
                    coincidences++;
                }
            }

            if (maxCoincidences < coincidences) {
                maxCoincidences = coincidences;
            }
        }

        System.out.println("Number of coincidences for number:" + patternNumber + " = " + coincidences);
        return maxCoincidences;
    }

    /**
     * Recognize the number of the image provided comparing with the patterns
     * The number with more coincidences is the winner
     *
     * @param f
     * @return
     * @throws IOException
     */
    public int recognize(BufferedImage img) throws IOException {
//        BufferedImage img = readImage(f);

        //Get the entire list of pixels for img
        int[] imgPixels = obtainPixelsList(img);

        int[] results = new int[10];

        //Then compare each number and store the result
        for (int i = 0; i < 10; i++) {
            results[i] = numberOfCoincidences(i, imgPixels);
        }

        //Identify the winner
        int winner = -1;
        int maxCoincidences = -1;
        for (int i = 0; i < 10; i++) {
            if (results[i] > maxCoincidences) {
                winner = i;
                maxCoincidences = results[i];
            }
        }

        return winner;
    }

    /**
     * Main entry
     *
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//        NumberRecognition r = new NumberRecognition();
//        File test = new File("E:\\dip\\res\\1\\1-TRANSFORMED14.png"); //Change it for your image
//        int winner = r.recognize(test);
//        System.out.println("And the winner is the number: " + winner);
//    }

}
