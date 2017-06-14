package by.bsuir.diplom.gui;


import by.bsuir.diplom.buisness.QualityController;
import by.bsuir.diplom.dao.SealDaoImpl;
import by.bsuir.diplom.dao.interfaces.SealDaoInterface;
import by.bsuir.diplom.entity.Role;
import by.bsuir.diplom.entity.Seal;
import by.bsuir.diplom.entity.Worker;
import by.bsuir.diplom.utils.ImageHelper;
import org.hibernate.jdbc.Work;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class MainForm extends JFrame {

    private final JSplitPane splitPane;

    // Left control tab components
    private final JTabbedPane leftTabPane = new JTabbedPane();

    private final JTextField fileInput = new JTextField(12);
    private final JButton fileBrowse = new JButton("Обзор");

    private final JComboBox cameraComboBox = new JComboBox();
    private final JButton cameraBtn = new JButton("Вкл. камеру");

    private final JButton controlBtn = new JButton("Проверить");

    private final JPanel rightPane = new JPanel();
    private final JLabel label = new JLabel();

    private ScannerThread cameraScanner;
    final JFileChooser fc = new JFileChooser("E://dip//ORIG//");

    private final QualityController qualityController = new QualityController();

    private final JTextField serialInput = new JTextField(10);

    private final JLabel result = new JLabel();
    private final JLabel resultImage = new JLabel();

    public MainForm() throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Dimension minimumSize = new Dimension(250, 50);
        label.setAutoscrolls(true);
        leftTabPane.setMinimumSize(minimumSize);
        rightPane.setMinimumSize(minimumSize);
        rightPane.add(label);
        rightPane.setAutoscrolls(true);

        leftTabPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rightPane.removeAll();
                rightPane.updateUI();
            }
        });
        //Массив содержащий заголоки таблицы
        Object[] headers = { "Месяц", "Количество пломб" };




        JPanel controlTab = new JPanel();
        controlTab.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.1;


        c.gridx = 0;
        c.gridy = 0;
        controlTab.add(new Label("Серийный номер: "), c);
        c.gridx = 1;
        c.gridy = 0;
        controlTab.add(serialInput, c);

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Image Filter", "jpeg", "jpg", "png", "gif", "bmp", "tiff", "tif"));

        fileBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rightPane.add(label);

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
                rightPane.updateUI();
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        controlTab.add(fileInput, c);
        c.gridx = 1;
        c.gridy = 1;
        controlTab.add(fileBrowse, c);

        int cameraCount = getCameraCount(5);
        for (int i = 0; i < cameraCount; i++) {
            cameraComboBox.addItem("Камера №" + i);
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

        c.gridx = 0;
        c.gridy = 2;
        controlTab.add(cameraComboBox, c);
        c.gridx = 1;
        c.gridy = 2;
        controlTab.add(cameraBtn, c);

        controlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!serialInput.getText().isEmpty()) {
                        controlSingle();
                    } else {
                        control();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        c.gridx = 0;
        c.gridy = 3;
        controlTab.add(controlBtn, c);
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 5.0;
        controlTab.add(result, c);
        c.gridx = 1;
        c.gridy = 4;
        controlTab.add(resultImage, c);

        leftTabPane.addTab("Контроль", controlTab);

        // ТАБ ПОИСКА
        JPanel search = new JPanel();
        search.add(new JLabel("Серийный номер"), BorderLayout.LINE_START);
        JTextField searchText = new JTextField(10);
        search.add(searchText, BorderLayout.LINE_END);
        JButton searchBtn = new JButton("Поиск");
        searchBtn.setBounds(10, 80, 80, 25);
        search.add(searchBtn);
        JTextArea area = new JTextArea();
        area.setColumns(5);
        area.setBackground(search.getBackground());
        area.setRows(15);
        search.add(area, BorderLayout.NORTH);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                List<Seal> res = sealDao.getAll();
                for (Seal s : res) {
                    if (s.getSerialNumber().equals(searchText.getText())) {
                        area.setText("Пломбу #" +  searchText.getText() + " произвел\r\n" +
                                "Сотрудник " + s.getWorker().getSurname() + " " + s.getWorker().getName() +"\r\n" +
                                "Дата: " + s.getCreationDate());
                    }
                }
                if (area.getText().isEmpty()) {
                    area.setText("Пломба #" +  searchText.getText() + " не существует\r\n" );
                }
            }
        });
        leftTabPane.addTab("Поиск", search);

        // ТАБ СТАТИСТИКИ
        JPanel stats = new JPanel();
        stats.add(new JLabel("Фамилия сотрудника"), BorderLayout.LINE_START);
        JTextField employ = new JTextField(9);
        stats.add(employ, BorderLayout.LINE_END);

        ButtonGroup group = new ButtonGroup();
        final JRadioButton statEmploy = new JRadioButton("По сотруднику", false);
        final JRadioButton statSummary = new JRadioButton("Общая", true);
        group.add(statEmploy);
        group.add(statSummary);
        stats.add(statEmploy);
        stats.add(statSummary);

        JButton statsBtn = new JButton("Отобразить");
        statsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Seal> seals = sealDao.getAll();
                rightPane.removeAll();
                if (statEmploy.isSelected()) {
                    Iterator<Seal> iterator = seals.iterator();
                    while (iterator.hasNext()) {
                        Seal seal = iterator.next();
                        if (!seal.getWorker().getSurname().toLowerCase().equals(employ.getText().toLowerCase())) {
                            iterator.remove();
                        }
                    }
                }
                Map<String, List<Seal>> sortedSeals = new HashMap<>();
                for (Seal s : seals) {
                    Date date = s.getCreationDate();
                    String key = getMonth(date.getMonth()) + " " + (date.getYear() + 1900);
                    if (!sortedSeals.containsKey(key)) {
                        sortedSeals.put(key, new LinkedList<>());
                    }
                    sortedSeals.get(key).add(s);
                }
                Object[][] data = new Object[sortedSeals.size()][2];
                int i = 0;
                for (String key : sortedSeals.keySet()) {
                    data[i][0] = key;
                    data[i][1] = sortedSeals.get(key).size();
                }

                //Объект таблицы
                JTable jTabPeople = new JTable(data, headers);
                JScrollPane jscrlp = new JScrollPane(jTabPeople);
                jTabPeople.setPreferredScrollableViewportSize(new Dimension(500, 500));
                rightPane.add(jscrlp);
                rightPane.updateUI();
//                rightPane.repaint();
//                System.out.println("sadasd");
            }
        });
        statsBtn.setBounds(10, 80, 80, 25);
        stats.add(statsBtn);


        leftTabPane.addTab("Статистика", stats);


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

    private void controlSingle() throws Exception{
        List<String> list = new ArrayList<>();
        list.add(serialInput.getText());

        boolean checkResult = checkList(list);
        ImageIcon imageIcon = new ImageIcon();
        imageIcon.setImage(ImageHelper.readImage(
                checkResult ?
                        getClass().getResource("/ok.png").getFile() :
                        getClass().getResource("/fail.png").getFile()
        ));
        resultImage.setIcon(imageIcon);
        if (checkResult) {
            result.setText("<html>Сериный номер " + serialInput.getText() + " уникален.");
        } else {
            result.setText("<html>Сериный номер " + serialInput.getText() + " дублируется.");
        }
        serialInput.setText("");
    }

    private String getMonth(int n) {
        switch (n) {
            case 0:
                return "Январь";
            case 1:
                return "Февраль";
            case 2:
                return "Март";
            case 3:
                return "Апрель";
            case 4:
                return "Май";
            case 5:
                return "Июнь";
            case 6:
                return "Июль";
            case 7:
                return "Август";
            case 8:
                return "Сентябрь";
            case 9:
                return "Октябрь";
            case 10:
                return "Ноябрь";
            case 11:
                return "Декабрь";
        }
        return "";
    }

    private void control() throws Exception {
        Icon i = label.getIcon();
        if (i instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) i;
//            saveImage((BufferedImage) icon.getImage());
            java.util.List<String> serialNumbers = qualityController.recognizeSerialNumbers((BufferedImage) icon.getImage());
            if (serialNumbers.size() != 0) {
                String text = "<html>                                                              <br><br>Серийные номера:";
                for (String num : serialNumbers) {
                    text += "<br>" + num;
                }
                result.setText(text);
                boolean checkResult = checkList(serialNumbers);
                ImageIcon imageIcon = new ImageIcon();
                imageIcon.setImage(ImageHelper.readImage(
                        checkResult ?
                                getClass().getResource("/ok.png").getFile() :
                                getClass().getResource("/fail.png").getFile()
                ));
                resultImage.setIcon(imageIcon);
            } else {
                result.setText("<html>Сериные номера<br> не распознаны!</html>");
                ImageIcon imageIcon = new ImageIcon();
                imageIcon.setImage(ImageHelper.readImage(
                                getClass().getResource("/fail.png").getFile()
                ));
                resultImage.setIcon(imageIcon);
            }
        }
    }

    private SealDaoInterface sealDao = new SealDaoImpl();
    public static Worker worker;

    private boolean checkList(List<String> serialNumbers) {
        for (String number : serialNumbers) {
            List<Seal> seals = sealDao.getAll();
            if (seals.size() != 0) {
                for (Seal s : seals) {
                    if (number.equals(s.getSerialNumber())){
                        result.setText(result.getText() + "<br><br>Номер " + number + " существует!");
                        return false;
                    }
                }
            }
            sealDao.save(new Seal(number, new Date(), worker));
        }
        return true;
    }


    private void setScannerThread(final int selectedIndex) {
        if (cameraScanner != null) {
            cameraScanner.interrupt();
        }
        rightPane.add(label);
        cameraScanner = new ScannerThread(label, selectedIndex);
        cameraScanner.start();
        rightPane.updateUI();
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
