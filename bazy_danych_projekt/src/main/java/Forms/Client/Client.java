package Forms.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    private CardLayout cardLayout;
    private JPanel cardPanel;



    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private final int id;
    private final String accountNumber;
    private double balance;


    public Client(int id, String firstName, String lastName, String address, String city, Double balance, String accountNumber) {
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

        nameFillLabel.setText(firstName + " " + lastName);
        balanceFillLabel.setText(String.valueOf(balance));
        accountFillLabel.setText(accountNumber);

        //transferFrame = new JFrame();

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
}
