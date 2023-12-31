package Forms.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreditCardsForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel cardsLabel;
    private JButton orderCardButton;
    private JButton quitButton;
    private JList<String> list1;
    private JButton deleteButton;

    private final Client parent;

    public CreditCardsForm(Client parent){
        this.parent = parent;

        //TODO low priority : change list to JTable
        ArrayList<String> dataList = new ArrayList<>();
        for(CreditCard card : parent.getCreditCards()){
            dataList.add(card.toString());
        }
        String[] data = new String[dataList.size()];
        dataList.toArray(data);

        list1.setListData(data);

        orderCardButton.addActionListener(this);
        quitButton.addActionListener(this);
        deleteButton.addActionListener(this);

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
            if(parent.getCreditCards().size() >= 3){
                JOptionPane.showMessageDialog(this, "Nie można posiadać więcej niż 3 karty kredytowe");
                return;
            }
            new OrderCreditCardForm(parent, this);
        }
        else if(e.getSource() == deleteButton){
            parent.deleteCard(list1.getSelectedIndex());
            ArrayList<String> dataList = new ArrayList<>();
            for(CreditCard card : parent.getCreditCards()){
                dataList.add(card.toString());
            }
            String[] data = new String[dataList.size()];
            dataList.toArray(data);

            list1.setListData(data);
            repaint();
        }
    }

    public JList<String> getList1() {
        return list1;
    }
}
