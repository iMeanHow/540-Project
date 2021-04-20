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

    //the helper function shows hint for operation code
    public void helper(){
        System.out.println("\n0: Customer Growth of Store");
        System.out.println("1: Customer Expense");
        System.out.println("2: Store Sales");
        System.out.println("3: Product Sale Num");
        System.out.println("back: return last menu");
    }

    public ReportGenerator(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    //calculate customer growth of store within period of time
    public void customerGrowth(){
        System.out.println("==customer growth report==");
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
                System.out.println("\n===New Customer Growth: "+result.getInt("num")+"===\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //calculate customer expense within period of time
    public void customerExpense(){
        System.out.println("==customer expense report==");
        System.out.print("customer id: ");
        String cid = scanner.nextLine();
        System.out.print("start date: ");
        String sdate = scanner.nextLine();
        System.out.print("end date: ");
        String edate = scanner.nextLine();
        String sql = "select sum(TotalPrice) as num from TransactionRecords where CustomerID="+cid+" and Date > '"+sdate+"' and Date < '"+edate+"'";
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("\n===Total Expense: "+result.getDouble("num")+"===\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //calculate store sale within period of time
    public void storeSale(){
        System.out.println("==store sale report==");
        System.out.print("store id: ");
        String sid = scanner.nextLine();
        System.out.print("start date: ");
        String sdate = scanner.nextLine();
        System.out.print("end date: ");
        String edate = scanner.nextLine();
        String sql = "select sum(TotalPrice) as num from TransactionRecords where StoreID="+sid+" and Date > '"+sdate+"' and Date < '"+edate+"'";
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("\n===Total Sale: "+result.getDouble("num")+"===\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //calculate product sale num growth of store within period of time
    public void productSale(){
        System.out.println("==product sale num report==");
        System.out.print("product id: ");
        String pid = scanner.nextLine();
        System.out.print("start date: ");
        String sdate = scanner.nextLine();
        System.out.print("end date: ");
        String edate = scanner.nextLine();

        //join to get product information which not includes in TransactionContains
        String sql = "select sum(Count) as num from TransactionContains join TransactionRecords on TransactionContains.TransactionID = TransactionRecords.TransactionID where ProductID="+pid+" and Date > '"+sdate+"' and Date < '"+edate+"'";
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("\n===Total Sale Num: "+result.getInt("num")+"===\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //entry of report function
    public void execute(){
        String unuse = scanner.nextLine();
        while(true) {
            //while loop
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
