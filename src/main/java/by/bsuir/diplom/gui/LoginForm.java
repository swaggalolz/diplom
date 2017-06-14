package by.bsuir.diplom.gui;

import by.bsuir.diplom.dao.WorkerDaoImpl;
import by.bsuir.diplom.dao.interfaces.WorkerDaoInterface;
import by.bsuir.diplom.entity.Worker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.*;

public class LoginForm {

    private final JFrame frame;
    private WorkerDaoInterface workerDao = new WorkerDaoImpl();

    public LoginForm() {
        frame = new JFrame();
        frame.setSize(300, 200);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Фамилия");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Пароль");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);
        JLabel error = new JLabel("Неверное имя пользователя или пароль!");
        error.setBounds(10, 80, 250, 25);
        error.setVisible(false);
        panel.add(error);
        JButton loginButton = new JButton("Войти");
        loginButton.setBounds(10, 120, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Worker worker = auth(userText.getText(), passwordText.getText());
                if (worker != null) {
                    startApp(worker);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                } else {
                    error.setVisible(true);
                }
            }


        });
    }

    private Worker auth(String surname, String pass) {
        java.util.List<Worker> workers = workerDao.getAll();
        for (Worker w : workers) {
            if (w.getSurname().equals(surname) && w.getPassword().equals(pass)) {
                return w;
            }
        }
        return null;
    }

    private void startApp(Worker worker) {
        MainForm application = null;
        try {
            application = new MainForm();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        MainForm.worker = worker;
        application.setSize(new Dimension(1024, 768));
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        application.setLocation(dim.width / 2 - application.getSize().width / 2, dim.height / 2 - application.getSize().height / 2);
    }
}
