package Forms.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientCredentialsForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel mainLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel addressLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JButton acceptButton;
    private JLabel cardsLabel;
    private JList cardList;
    private JLabel titleLabel;
    private JTextField cityTextField;
    private JButton quitButton;
    private JLabel cityLabel;


    private final Client parent;
    public ClientCredentialsForm(Client client) {
        parent = client;
        firstNameField.setText(client.getFirstName());
        lastNameField.setText(client.getLastName());
        addressField.setText(client.getAddress());
        cityTextField.setText(client.getCity());

        acceptButton.addActionListener(this);
        quitButton.addActionListener(this);

        setContentPane(mainPanel);
        setVisible(true);
        pack();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == acceptButton){

            dispose();
            parent.setVisible(true);
        }
        if(e.getSource() == quitButton){
            dispose();
            parent.setVisible(true);
        }
    }

}
