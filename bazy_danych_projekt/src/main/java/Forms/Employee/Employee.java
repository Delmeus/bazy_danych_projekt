package Forms.Employee;

import Forms.Client.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Employee extends JFrame implements ActionListener {

    private JButton closeAccButton;
    private JButton loanButton;
    private JPanel jPanel;
    private JLabel mainLabel;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel accountNumberLabel;
    private JLabel nameFillLabel;
    private JLabel positionFillLabel;
    private JLabel branchNameFillLabel;
    //    private JLabel branchAdressFillLabel;
    private JLabel titleLabel;
    private JButton changeDataButton;
    private JLabel adresOddziałuLabel;
    private JLabel label1;


    private String firstName;
    private String lastName;
    private String address;
    private String position;
    private final int employeeId;
    private double balance;

    private Connection connection = null;


    public Employee(int employeeId, String firstName, String lastName, String position, String branchName, String branchAdress) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = branchAdress;
        this.position = position;
        this.employeeId = employeeId;
        setTitle("Aplikacja Pracownika");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(jPanel);
        closeAccButton.addActionListener(this);
        loanButton.addActionListener(this);
        changeDataButton.addActionListener(this);

        nameFillLabel.setText(firstName + " " + lastName);
        positionFillLabel.setText(position);
        branchNameFillLabel.setText(branchName);
        label1.setText(branchAdress);

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loanButton) {
            setVisible(false);
            new LoanAva(this);
        } else if (e.getSource() == changeDataButton) {
            setVisible(false);
            new ManageAcc(this);
        } else if (e.getSource() == closeAccButton) {
            setVisible(false);
            new CloseAcc(this);
        }
//        else if(e.getSource() == manageCardsButton){
//            setVisible(false);
//            new CreditCardsForm(this);
//        }
//        else if(e.getSource() == transactionsHistoryButton){
//            setVisible(false);
//            new Client.transactionsFrame(this);
//        }

    }

    protected void updateCredentials(String firstName, String lastName, String address, String city) {
        // TODO check if given strings are logical (e.g return if firstName contains a digit)

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE clients SET first_name = ?, last_name = ?, address = ?, city = ? WHERE id = ?");

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setInt(5, this.employeeId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return;
        }

        updateClientInfo();
    }

    protected void deleteAccount() {
        PreparedStatement deleteCards = null;
        PreparedStatement deleteAccount = null;
        PreparedStatement deleteClient = null;
        try {
            deleteCards = connection.prepareStatement("DELETE FROM credit_card WHERE client_id = ?");
            deleteAccount = connection.prepareStatement("DELETE FROM accounts WHERE client_id = ?");
            deleteClient = connection.prepareStatement("DELETE FROM clients WHERE id = ?");

            deleteCards.setInt(1, this.employeeId);
            deleteAccount.setInt(1, this.employeeId);
            deleteClient.setInt(1, this.employeeId);

            deleteCards.executeUpdate();
            deleteAccount.executeUpdate();
            deleteClient.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return;
        }

        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }




    private void updateClientInfo() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `Imię`, `Nazwisko`, `Adres`, `Miasto`, `Saldo` FROM clients_info_view WHERE ID = ?");
            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            firstName = resultSet.getString(1);
            lastName = resultSet.getString(2);
            address = resultSet.getString(3);
            position = resultSet.getString(4);
            balance = resultSet.getDouble(5);

            repaint();

        } catch (SQLException e) {
            System.out.println(e);
            return;
        }

        nameFillLabel.setText(firstName + " " + lastName);
        positionFillLabel.setText(String.valueOf(balance));
    }

    protected String[] getLoans(){
        String[] loans = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, `Imie i nazwisko` FROM loan_info_view WHERE Zatwierdzono IS NULL");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<String> loansList = new ArrayList<>();

            while (resultSet.next()){
                loansList.add(resultSet.getInt(1) + ", " + resultSet.getString(2));
            }

            loans = loansList.toArray(new String[0]);

        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return loans;
    }

    protected String[] getLoanInfo(int id){
        String[] loans = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Kwota, `Imie i nazwisko`, `Adres zamieszkania`, `Saldo klienta`, `Obrót na koncie` FROM loan_info_view WHERE ID = ? AND Zatwierdzono IS NULL");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            ArrayList<String> loansList = new ArrayList<>();

            loansList.add(String.valueOf(resultSet.getInt(1)));
            loansList.add(resultSet.getString(2));
            loansList.add(resultSet.getString(3));
            loansList.add(String.valueOf(resultSet.getDouble(4)));
            loansList.add(String.valueOf(resultSet.getDouble(5)));

            loans = loansList.toArray(new String[0]);

        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        return loans;
    }

    protected void reviewLoan(boolean approved, int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE loans SET approved = ? WHERE id = ?");
            preparedStatement.setBoolean(1, approved);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPosition() {
        return position;
    }

    public double getBalance() {
        return balance;
    }

}



