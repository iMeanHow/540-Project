import java.sql.*;
import java.util.Scanner;

public class WolfStore {

    static final String jdbcURL = "jdbc:mysql://localhost:3306/wolfwr";
    static final String user = "root";
    static final String password = "345678";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    //connect to db when initiating
    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
    }
    private static BillManagement billManagement;
    private static StaffManagement staffManagement;
    private static StoreManagement storeManagement;
    private static MemberManagement memberManagement;
    private static MerchandiseManagement merchandiseManagement;
    private static SupplierManagement supplierManagement;
    private static ReportGenerator reportGenerator;
    private static TransactionManagement transactionManagement;

    //the helper function shows hint for operation code
    public static void helper(){
        System.out.println("1: Staff Management");
        System.out.println("2: Store Management");
        System.out.println("3: Supplier Management");
        System.out.println("4: Club Member Management");
        System.out.println("5: Merchandise Management");
        System.out.println("6: Billing Management");
        System.out.println("7: Transaction Management");
        System.out.println("8: Report Generator");
        System.out.print("\nChoose Domain: ");
    }


    public static void main(String[] args) {
        //entry of the program, call other function from here
        System.out.println("===Welcome to WolfStore System===\n");
        try {
            connectToDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        while(true){
            //while loop to perform operation selection and execution
            //the helper function shows hint for operation code
            helper();
            String in =scanner.next();

            //execute function of each object is the entry of specific operation choose
            if(in.equals("1")){
                System.out.println("=====Staff Management=====");
                staffManagement=new StaffManagement(connection,statement,result,scanner);
                staffManagement.execute();
            }
            else if(in.equals("2")){
                System.out.println("=====Store Management=====");
                storeManagement=new StoreManagement(connection,statement,result,scanner);
                storeManagement.execute();
            }
            else if(in.equals("3")){
                System.out.println("=====Supplier Management=====");
                supplierManagement=new SupplierManagement(connection,statement,result,scanner);
                supplierManagement.execute();
            }
            else if(in.equals("4")){
                System.out.println("=====Club Member Management=====");
                memberManagement=new MemberManagement(connection,statement,result,scanner);
                memberManagement.execute();
            }
            else if(in.equals("5")){
                System.out.println("=====Merchandise Management=====");
                merchandiseManagement=new MerchandiseManagement(connection,statement,result,scanner);
                merchandiseManagement.execute();
            }
            else if(in.equals("6")){
                System.out.println("=====Billing Management=====");
                billManagement=new BillManagement(connection,statement,result,scanner);
                //billManagement.execute();
            }
            else if(in.equals("7")){
                System.out.println("=====Transaction Management=====");
                transactionManagement=new TransactionManagement(connection,statement,result,scanner);
                transactionManagement.execute();
            }
            else if(in.equals("8")){
                System.out.println("=====Report Management=====");
                reportGenerator=new ReportGenerator(connection,statement,result,scanner);
                reportGenerator.execute();
            }

            else if(in.equals("exit")){
                System.out.println("Thank you for using WolfStore System!");
                return;
            }
            else{
                //if operation code does not match any, show helper again
                System.out.println("Invalid input!");
                helper();
            }

        }

    }
}
