package by.bsuir.diplom;

import by.bsuir.diplom.gui.MainForm;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String args[]) throws Exception {
        MainForm application = new MainForm();
        application.setSize(new Dimension(1024, 768));
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        application.setLocation(dim.width / 2 - application.getSize().width / 2, dim.height / 2 - application.getSize().height / 2);
    }

}
