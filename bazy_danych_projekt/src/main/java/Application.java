import Forms.Client.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Application {
    public static void main(String[] args) {
        //ResultSet set = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from clients");
            set.next();
            Client client = new Client(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5));
            while(set.next()){
                System.out.println(set.getInt(1)+ " " + set.getString(2) + " " + set.getString(3));
            }


            //set = resultSet;
        }
        catch (Exception e){
            System.out.println(e);
        }
//        JFrame frame = new JFrame("Aplikacja Klienta");
//        try {
//            Client client = new Client(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5));
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//        frame.setContentPane(clientMainForm.getjPanel());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
}
