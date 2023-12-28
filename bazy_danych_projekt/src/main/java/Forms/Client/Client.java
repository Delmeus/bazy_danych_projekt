package Forms.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;

public class Client extends JFrame implements ActionListener {
    private JButton manageCardsButton;
    private JButton changeAccountDetailsButton;
    private JButton transferMoneyButton;
    private JButton loanButton;
    private JPanel jPanel;
    private JLabel mainLabel;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel accountNumberLabel;
    private JLabel nameFillLabel;
    private JLabel balanceFillLabel;
    private JLabel accountFillLabel;
    private JLabel titleLabel;
    private JButton transactionsHistoryButton;


    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private final int id;
    private final String accountNumber;
    private double balance;

    private Connection connection = null;

    public Client(int id, String firstName, String lastName, String address, String city, Double balance, String accountNumber) {

        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "okon");
        }catch (Exception e){
            System.out.println(e);
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.id = id;
        this.balance = balance;
        this.accountNumber = accountNumber;
        setTitle("Aplikacja Klienta");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(jPanel);

        manageCardsButton.addActionListener(this);
        changeAccountDetailsButton.addActionListener(this);
        transferMoneyButton.addActionListener(this);
        loanButton.addActionListener(this);
        transactionsHistoryButton.addActionListener(this);

        nameFillLabel.setText(firstName + " " + lastName);
        balanceFillLabel.setText(String.valueOf(balance));
        accountFillLabel.setText(accountNumber);

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changeAccountDetailsButton){
            setVisible(false);
            new ClientCredentialsForm(this);
        }
        else if(e.getSource() == transferMoneyButton){
            setVisible(false);
            new TransferForm(this);
        }
        else if(e.getSource() == loanButton){
            setVisible(false);
            new LoanForm(this);
        }
        else if(e.getSource() == manageCardsButton){
            setVisible(false);
            new CreditCardsForm(this);
        }
        else if(e.getSource() == transactionsHistoryButton){
            setVisible(false);
            new transactionsFrame(this);
        }

    }

    protected void updateCredentials(String firstName, String lastName, String address, String city){
        // TODO check if given strings are logical (e.g return if firstName contains a digit)

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE clients SET first_name = ?, last_name = ?, address = ?, city = ? WHERE id = ?");

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setInt(5, this.id);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
            return;
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;

        nameFillLabel.setText(firstName + " " + lastName);

    }

    protected void deleteAccount(){
        PreparedStatement deleteCards = null;
        PreparedStatement deleteAccount = null;
        PreparedStatement deleteClient = null;
        try {
            deleteCards = connection.prepareStatement("DELETE FROM credit_card WHERE client_id = ?");
            deleteAccount = connection.prepareStatement("DELETE FROM accounts WHERE client_id = ?");
            deleteClient = connection.prepareStatement("DELETE FROM clients WHERE id = ?");

            deleteCards.setInt(1, this.id);
            deleteAccount.setInt(1, this.id);
            deleteClient.setInt(1, this.id);

            deleteCards.executeUpdate();
            deleteAccount.executeUpdate();
            deleteClient.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
            return;
        }

        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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

    public String getCity() {
        return city;
    }

    private class transactionsFrame extends JFrame implements ActionListener{
        private final Client parent;
        private final JButton quitButton;
        private transactionsFrame(Client parent){
            this.parent = parent;

            JPanel jPanel = new JPanel(new GridLayout(3,1));
            jPanel.setBackground(new Color(24, 26, 48));

            Font font = new Font("Cooper Black", Font.BOLD | Font.ITALIC, 22);
            JLabel label = new JLabel();
            label.setFont(font);
            label.setForeground(new Color(255,255,255));
            label.setText("Bank Bilardzistów");
            label.setHorizontalAlignment(JLabel.CENTER);
            jPanel.add(label);

            setTitle("Historia transakcji");
            setContentPane(jPanel);

            quitButton = new JButton();
            quitButton.setText("Powrót");
            quitButton.addActionListener(this);

            ArrayList<Object[]> dataList = new ArrayList<>();

            try{
                PreparedStatement preparedStatement = Client.this.connection.prepareStatement("SELECT `Rodzaj transakcji`, Data, Kwota FROM transactions_view WHERE `Numer konta` = ?");
                preparedStatement.setString(1, Client.this.accountNumber);
                ResultSet set = preparedStatement.executeQuery();
                while(set.next()){
                    Object[] row = new Object[]{set.getString(1), set.getDate(2),set.getDouble(3)};
                    dataList.add(row);
                }
                Object[][] data = new Object[dataList.size()][];
                dataList.toArray(data);
                String[] columns = {"Rodzaj transakcji", "Data","Kwota"};
                DefaultTableModel tableModel = new DefaultTableModel(data, columns);

                JTable transactions = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(transactions);
                jPanel.add(scrollPane);
            }catch (SQLException e){
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
            if(e.getSource() == quitButton){
                parent.setVisible(true);
                dispose();
            }
        }
    }
}
