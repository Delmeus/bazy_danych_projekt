package Forms.Employee;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanAva extends JFrame implements ActionListener {
    private JLabel mainLabel;
    private JPanel mainPanel;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton performButton;
    private JButton quitButton;
    private JLabel titleLabel;
    private JLabel saldoNaKoncieKlientaLabel;
    private JTextField textField1;
    private JLabel łącznyObrótNaKoncieLabel;
    private JTextField textField2;
    private JLabel imięINazwiskoKlientaLabel;
    private JTextField textField3;
    private JLabel adresZamieszkaniaKlientaLabel;
    private JTextField textField4;

    private final Employee parent;

    public LoanAva(Employee employee) {
        parent = employee;

        performButton.addActionListener(this);
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
    }
}
