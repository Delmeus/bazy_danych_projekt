package Forms.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel receiverLabel;
    private JTextField receiverTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performButton;
    private JButton quitButton;
    private JComboBox<String> transactionTypeBox;
    private JLabel transactionTypeLabel;

    private final Client parent;

    public TransferForm(Client parent) {
        this.parent = parent;

        performButton.addActionListener(this);
        quitButton.addActionListener(this);

        transactionTypeBox.addItem("Standardowy");
        transactionTypeBox.addItem("Natychmiastowy");

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.setVisible(true);
            }
        });

        setContentPane(mainPanel);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == performButton){
            double amount = -1;

            try{
                amount = Double.parseDouble(amountTextField.getText());
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this, "Niepoprawna kwota.");
                return;
            }

            if(amount < 0){
                JOptionPane.showMessageDialog(this, "Niepoprawna kwota.");
                return;
            }

            if(parent.getBalance() - amount < 0 || (parent.getBalance() - amount - 5 < 0 && transactionTypeBox.getSelectedIndex() == 1)){
                JOptionPane.showMessageDialog(this, "Brak wystarczającej ilości pieniędzy do wykonania przelewu");
                return;
            }

            if(parent.getAccountNumber().equalsIgnoreCase(receiverTextField.getText())){
                JOptionPane.showMessageDialog(this, "Nie można przelać pieniędzy na własne konto");
                return;
            }

            boolean success = parent.makeTransaction(transactionTypeBox.getSelectedIndex() == 0, amount, receiverTextField.getText());

            if(!success){
                JOptionPane.showMessageDialog(this, "Przelew nie powiódł się");
            }

            JOptionPane.showMessageDialog(this,"Przelew wykonany poprawnie.");
            dispose();
            parent.setVisible(true);
        }
        if(e.getSource() == quitButton){
            dispose();
            parent.setVisible(true);
        }
    }


}
