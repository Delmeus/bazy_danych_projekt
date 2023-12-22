package Forms.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderCreditCardForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel cardsLabel;
    private JComboBox cardsComboBox;
    private JButton performButton;
    private JButton quitButton;

    private final Client parent;

    public OrderCreditCardForm(Client parent){
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
        }
        if(e.getSource() == quitButton){
            dispose();
        }
    }
}
