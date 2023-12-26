package Forms.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditCardsForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel cardsLabel;
    private JTable table1;
    private JButton orderCardButton;
    private JButton quitButton;

    private final Client parent;

    public CreditCardsForm(Client parent){
        this.parent = parent;

        orderCardButton.addActionListener(this);
        quitButton.addActionListener(this);

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
        if(e.getSource() == quitButton){
            dispose();
            parent.setVisible(true);
        }
        else if(e.getSource() == orderCardButton){
            new OrderCreditCardForm(parent);
        }
    }
}
