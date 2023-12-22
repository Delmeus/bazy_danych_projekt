package Forms.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanForm extends JFrame implements ActionListener {
    private JLabel mainLabel;
    private JPanel mainPanel;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performButton;
    private JButton quitButton;
    private JLabel titleLabel;

    private final Client parent;

    public LoanForm(Client client) {
        parent = client;

        performButton.addActionListener(this);
        quitButton.addActionListener(this);

        setContentPane(mainPanel);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == quitButton){
            dispose();
            parent.setVisible(true);
        }
    }
}
