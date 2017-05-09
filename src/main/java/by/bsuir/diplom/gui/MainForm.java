package by.bsuir.diplom.gui;


import by.bsuir.diplom.buisness.QualityController;
import by.bsuir.diplom.utils.ImageHelper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

public class MainForm extends JFrame {

    private final JSplitPane splitPane;

    // Left control tab components
    private final JTabbedPane leftTabPane = new JTabbedPane();

    private final JTextField fileInput = new JTextField(12);
    private final JButton fileBrowse = new JButton("Browse");

    private final JComboBox cameraComboBox = new JComboBox();
    private final JButton cameraBtn = new JButton("Use camera");

    private final JButton controlBtn = new JButton("Check");

    private final JPanel rightPane = new JPanel();
    private final JLabel label = new JLabel();

    private ScannerThread cameraScanner;
    final JFileChooser fc = new JFileChooser();

    private final QualityController qualityController = new QualityController();

    public MainForm() throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Dimension minimumSize = new Dimension(250, 50);
        leftTabPane.setMinimumSize(minimumSize);
        rightPane.setMinimumSize(minimumSize);
        rightPane.add(label);
        JPanel controlTab = new JPanel();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Image Filter", "jpeg", "jpg", "png", "gif", "bmp", "tiff", "tif"));
        fileBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cameraScanner != null) {
                    cameraScanner.interrupt();
                }

                int returnVal = fc.showOpenDialog(fileBrowse);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    fileInput.setText(file.getAbsolutePath());
                    BufferedImage image = loadImage(file);
                    label.setIcon(new ImageIcon(image));
                } else if (!fileInput.getText().isEmpty()) {
                    File file = new File(fileInput.getText());
                    if (file.exists()) {
                        BufferedImage image = loadImage(file);
                        label.setIcon(new ImageIcon(image));
                    }
                }
            }
        });
        controlTab.add(fileInput, BorderLayout.LINE_START);
        controlTab.add(fileBrowse, BorderLayout.LINE_START);

        int cameraCount = getCameraCount(5);
        for (int i = 0; i < cameraCount; i++) {
            cameraComboBox.addItem("Camera " + i);
        }
        cameraBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setScannerThread(cameraComboBox.getSelectedIndex());
            }
        });

        cameraComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setScannerThread(cameraComboBox.getSelectedIndex());
            }
        });
        controlTab.add(cameraComboBox, BorderLayout.CENTER);
        controlTab.add(cameraBtn, BorderLayout.CENTER);

        controlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    control();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        controlTab.add(controlBtn, BorderLayout.PAGE_END);

        leftTabPane.addTab("Control", controlTab);


        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftTabPane, rightPane){
            private final int location = 250;
            {
                setDividerLocation( location );
            }
            @Override
            public int getDividerLocation() {
                return location ;
            }
            @Override
            public int getLastDividerLocation() {
                return location ;
            }
        };
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane);
    }



    private void control() throws Exception {
        Icon i = label.getIcon();
        if (i instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) i;
//            saveImage((BufferedImage) icon.getImage());
            qualityController.control((BufferedImage) icon.getImage());
        }
    }


    private void setScannerThread(final int selectedIndex) {
        if (cameraScanner != null) {
            cameraScanner.interrupt();
        }

        cameraScanner = new ScannerThread(label, selectedIndex);
        cameraScanner.start();
    }



    protected int getCameraCount(int max) {
//        final Mat frame = new Mat();
        int count = 0;
        for (int i = 0; i < max; i++) {
            final VideoCapture camera = new VideoCapture(i);
            if (camera.isOpened()) {
                count++;
            } else {
                break;
            }
            camera.release();
        }
        return count;
    }


    //Show image on window
    public void window(BufferedImage img, String text, int x, int y) throws Exception {
//        rightPane.set
        JFrame frame0 = new JFrame();
        frame0.getContentPane().add(new MainForm());
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setTitle(text);
        frame0.setSize(img.getWidth(), img.getHeight() + 30);
        frame0.setLocation(x, y);
        frame0.setVisible(true);
    }

    //Load an image
    public BufferedImage loadImage(File input) {
        BufferedImage img;

        try {
            img = ImageIO.read(input);

            return img;
        } catch (Exception e) {
            System.out.println("erro");
        }

        return null;
    }

    //Save an image
    public void saveImage(BufferedImage img) {
        try {
            File outputfile = new File("e:/dip/new.png");
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    //Grayscale filter
    public BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor =
                        new Color(
                                red + green + blue,
                                red + green + blue,
                                red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }


    protected static class ScannerThread extends Thread {
        public final VideoCapture camera;
        private final JLabel label;

        protected ScannerThread(JLabel label, int selectedIndex) {
            this.camera = new VideoCapture(selectedIndex);
            this.label = label;
        }

        @Override
        public void run() {
            while (true) {
                final Mat frame = new Mat();
                camera.read(frame);
                if (camera.read(frame)) {
                    BufferedImage image = ImageHelper.matToBufferedImage(frame);
                    label.setIcon(new ImageIcon(image));
                }
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted.");
                }
            }
        }

        @Override
        public void interrupt() {
            camera.release();
            super.interrupt();
        }
    }
}
