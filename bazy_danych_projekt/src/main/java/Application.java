import Forms.Client.Client;
import Forms.Employee.Employee;
import Forms.LogInForm;
import com.mysql.cj.log.Log;

import javax.swing.*;
import java.sql.*;

public class Application {
    public static void main(String[] args) {
        /*
        parameters - id first_name last_name type_of_user (c - client, e - employee, a - admin)
         */
        ResultSet set = null;
        Connection connection = null;
        Statement statement = null;

        new LogInForm();

        // TODO: get the login form to work correctly

//        if(args[3].equals("C")) {
//            try {
//                //Class.forName("com.mysql.cj.jdbc.Driver");
//                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "root");
//                statement = connection.createStatement();
//
//                PreparedStatement preparedStatement = connection.prepareStatement("select * from clients_info_view where id = ?");
//                preparedStatement.setInt(1, Integer.parseInt(args[0]));
//                set = preparedStatement.executeQuery();
//
//                if(set == null){
//                    System.out.println("No such client");
//                    return;
//                }
//
//                set.next();
//                new Client(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getDouble(6), set.getString(7), set.getInt(8));
//                connection.close();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }    if(args[3].equals("E")) {
//            try {
//                //Class.forName("com.mysql.cj.jdbc.Driver");
//                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projekt_banku", "root", "root");
//                statement = connection.createStatement();
//
//                PreparedStatement preparedStatement = connection.prepareStatement("select * from employees_info_view where id = ?");
//                preparedStatement.setInt(1, Integer.parseInt(args[0]));
//                set = preparedStatement.executeQuery();
//
//                if(set == null){
//                    System.out.println("No such client");
//                    return;
//                }
//
//                set.next();
//                new Employee(set.getInt(1), set.getString(2), set.getString(3), set.getString(4),   set.getString(5),  set.getString(6));
//                connection.close();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
    }

}
