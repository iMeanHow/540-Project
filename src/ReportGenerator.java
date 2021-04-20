import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ReportGenerator {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public void helper(){

    }

    public ReportGenerator(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    public void customerGrowth(){
        System.out.println("Find club member by condition");
        System.out.println("Press enter to skip the input");
        System.out.print("store id: ");
        String sid = scanner.nextLine();
        System.out.print("start date: ");
        String sdate = scanner.nextLine();
        System.out.print("end date: ");
        String edate = scanner.nextLine();
        String sql = "select count(*) as num from RegistrationRecords where StoreID="+sid+" and SignUpDate > '"+sdate+"' and SignUpDate < '"+edate+"'";
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("New Customer Growth: "+result.getInt("num")+"\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void customerExpense(){

    }

    public void storeSale(){

    }

    public void productSale(){

    }

    public void execute(){
        while(true) {
            helper();
            System.out.print("enter operation code: ");
            String in = scanner.nextLine();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("--customer growth of store--");
                customerGrowth();
            } else if (in.equals("1")) {
                System.out.println("--customer expense report--");
                customerExpense();
            }
            else if (in.equals("2")) {
                System.out.println("--store sale report--");
                storeSale();
            }
            else if (in.equals("3")) {
                System.out.println("--product sale number report--");
                productSale();
            }
            else{
                System.out.println("invalid input");
            }


        }

    }
}
