import java.sql.*;
import java.util.Scanner;

public class WolfStore {

    static final String jdbcURL = "jdbc:mysql://localhost:3306/wolfwr";
    static final String user = "root";
    static final String password = "345678";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
    }
    private static BillManagement billManagement;
    private static StaffManagement staffManagement;

    public static void helper(){
        System.out.println("1: Staff Management");
        System.out.println("2: Store Management");
    }


    public static void main(String[] args) {
        System.out.println("===Welcome to WolfStore System===");
        try {
            connectToDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        helper();
        Scanner scanner = new Scanner(System.in);
        while(true){
            String in =scanner.next();

            if(in.equals("1")){
                System.out.println("=====Staff Management=====");
                staffManagement=new StaffManagement(connection,statement,result,scanner);
                staffManagement.execute();
            }
            else if(in.equals("2")){
                System.out.println("=====Store Management=====");

            }


            else if(in.equals("exist")){
                System.out.println("Thank you for using WolfStore System!");
                return;
            }
            else{
                System.out.println("Invalid input!");
                helper();
            }

        }

    }
}
