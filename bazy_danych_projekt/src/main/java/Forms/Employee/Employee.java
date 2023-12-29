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
        this.address = position;
        this.position = this.position;
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
        }
        else if(e.getSource() == changeDataButton){
            setVisible(false);
            new ManageAcc(this);
        }
        else if(e.getSource() == closeAccButton){
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

            protected void updateCredentials (String firstName, String lastName, String address, String city){
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

            protected void deleteAccount () {
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

            protected boolean makeTransaction ( boolean standard, double amount, String receiver, TransferForm form){
                PreparedStatement insertTransaction = null;
                PreparedStatement subtractExpressCost = null;
                java.util.Date javaDate = new java.util.Date();
                java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
                int transactionTypeId;
                try {
                    insertTransaction = connection.prepareStatement("INSERT INTO transactions(amount, type_id, account_id, transaction_date) VALUES (?, ?, ?, ?)");
                    insertTransaction.setDouble(1, amount);
//                    insertTransaction.setInt(3, departmentId);

                    if (standard) {
                        transactionTypeId = 2;
                        insertTransaction.setInt(2, transactionTypeId);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(mySQLDate);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);

                        insertTransaction.setDate(4, new java.sql.Date(calendar.getTimeInMillis()));
                    } else {
                        transactionTypeId = 8;
                        insertTransaction.setInt(2, transactionTypeId);
                        insertTransaction.setDate(4, mySQLDate);

                        subtractExpressCost = connection.prepareStatement("UPDATE accounts SET balance = balance - 5 WHERE client_id = ?");
                        subtractExpressCost.setInt(1, employeeId);

                        subtractExpressCost.executeUpdate();
                    }

                    insertTransaction.executeUpdate();
                } catch (Exception e) {
                    System.out.println(e + " TTT");
                    return false;
                }

                PreparedStatement transferMoneyToReceiver = null;
                PreparedStatement getAccountID = null;
                PreparedStatement insertIncoming = null;
                try {
//            getAccountID = connection.prepareStatement("SELECT clients_info_view.`ID konta` FROM clients_info_view WHERE `Numer konta` = ?");
                    getAccountID.setString(1, receiver);
                    ResultSet resultSet = getAccountID.executeQuery();
                    resultSet.next();

                    insertIncoming = connection.prepareStatement("INSERT INTO transactions (amount, type_id, account_id, transaction_date) VALUES (?, ?, ?, ?)");
                    insertIncoming.setDouble(1, amount);
                    insertIncoming.setInt(2, transactionTypeId - 1);
                    insertIncoming.setInt(3, resultSet.getInt(1));
                    insertIncoming.setDate(4, mySQLDate);
                    insertIncoming.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(form, "Przelano do klienta innego banku");
                }

                updateClientInfo();
                return true;
            }


            private void updateClientInfo () {
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

            public String getFirstName () {
                return firstName;
            }

            public String getLastName () {
                return lastName;
            }

            public String getAddress () {
                return address;
            }

            public String getPosition () {
                return position;
            }

            public double getBalance () {
                return balance;
            }


            // History of transactions
            private class transactionsFrame extends JFrame implements ActionListener {
                private final Client parent;
                private final JButton quitButton;

                private transactionsFrame(Client parent) {
                    this.parent = parent;

                    JPanel jPanel = new JPanel(new GridLayout(3, 1));
                    jPanel.setBackground(new Color(24, 26, 48));

                    Font font = new Font("Cooper Black", Font.BOLD | Font.ITALIC, 22);
                    JLabel label = new JLabel();
                    label.setFont(font);
                    label.setForeground(new Color(255, 255, 255));
                    label.setText("Bank Bilardzistów");
                    label.setHorizontalAlignment(JLabel.CENTER);
                    jPanel.add(label);

                    setTitle("Historia transakcji");
                    setContentPane(jPanel);

                    quitButton = new JButton();
                    quitButton.setText("Powrót");
                    quitButton.addActionListener(this);

                    ArrayList<Object[]> dataList = new ArrayList<>();

                    try {
                        PreparedStatement preparedStatement = Employee.this.connection.prepareStatement("SELECT `Rodzaj transakcji`, Data, Kwota FROM transactions_view WHERE `Numer konta` = ?");
//                preparedStatement.setString(1, Employee.this.accountNumber);
                        ResultSet set = preparedStatement.executeQuery();
                        while (set.next()) {
                            Object[] row = new Object[]{set.getString(1), set.getDate(2), set.getDouble(3)};
                            dataList.add(row);
                        }
                        Object[][] data = new Object[dataList.size()][];
                        dataList.toArray(data);
                        String[] columns = {"Rodzaj transakcji", "Data", "Kwota"};
                        DefaultTableModel tableModel = new DefaultTableModel(data, columns);

                        JTable transactions = new JTable(tableModel);
                        transactions.getColumnModel().getColumn(0).setMinWidth(220);
                        JScrollPane scrollPane = new JScrollPane(transactions);
                        jPanel.add(scrollPane);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "Brak transakcji do pokazania");
                    }

                    jPanel.add(quitButton);

                    addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            parent.setVisible(true);
                        }
                    });

                    setSize(400, 300);
                    setVisible(true);

                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == quitButton) {
                        parent.setVisible(true);
                        dispose();
                    }
                }
            }
        }



