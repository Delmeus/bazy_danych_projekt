package Forms.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class TransferForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel receiverLabel;
    private JTextField receiverTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performButton;
    private JButton quitButton;

    private final Client parent;

    public TransferForm(Client parent) {
        this.parent = parent;

        performButton.addActionListener(this);
        quitButton.addActionListener(this);

        setContentPane(mainPanel);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == performButton){
            dispose();
            parent.setVisible(true);
        }
        if(e.getSource() == quitButton){
            dispose();
            parent.setVisible(true);
        }
    }


}
