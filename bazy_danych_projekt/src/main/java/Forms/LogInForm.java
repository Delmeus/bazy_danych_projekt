package Forms;

import Forms.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LogInForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel loginLabel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JButton loginButton;

    private String userType;
    private Connection connection = null;

    public LogInForm(){

        userType = "";

        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "okon");
        }catch (Exception e){
            System.out.println(e);
        }

        setTitle("Aplikacja Klienta");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(this);

        setContentPane(mainPanel);

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            if(loginField.getText().equalsIgnoreCase("ADMIN") && String.valueOf(passwordField.getPassword()).equalsIgnoreCase("ADMIN")){
                userType = "ADMIN";
            }
            else if(loginField.getText().equalsIgnoreCase("PRACOWNIK") && String.valueOf(passwordField.getPassword()).equalsIgnoreCase("PRACOWNIK")){
                userType = "EMPLOYEE";
            }
            else if(String.valueOf(passwordField.getPassword()).equalsIgnoreCase("KLIENT")){
                try{
                    Statement statement = connection.createStatement();
                    ResultSet set = statement.executeQuery("SELECT * FROM client_credentials_view");
                    while(set.next()){
                        if(set.getString(1).equalsIgnoreCase(loginField.getText())){
                            userType = "CLIENT";
                            break;
                        }
                    }
                }catch (SQLException exception){
                    System.out.println(exception);
                }
            }

            if(userType.isEmpty()){
                JOptionPane.showMessageDialog(this,"Niepoprawne dane logowania");
            }
            else{
                setVisible(false);
            }
        }
    }

    public String getUserType() {
        return userType;
    }
}
