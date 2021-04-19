import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public TransactionManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    public void queryTransactions(){
        System.out.println("-----find transaction by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from transactionrecords join ClubMembers on transactionrecords.CustomerID = ClubMembers.CustomerID where 1=1";
        System.out.print("TransactionID: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(id)){
            sql+=(" and TransactionID="+id);
        }else{
            System.out.print("cashier id: ");
            String cashierid=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(cashierid)){
                sql+=(" and CashierID="+cashierid);
            }
            System.out.print("store id: ");
            String storeid=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(storeid)){
                sql+=(" and StoreID="+storeid);
            }
            System.out.print("customer id: ");
            String customerid=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(customerid)){
                sql+=(" and CustomerID="+customerid);
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("transaction id: "+result.getInt("TransactionID"));
                System.out.println("cashier id: "+result.getInt("CashierID"));
                System.out.println("store id: "+result.getInt("StoreID"));
                System.out.println("customer id: "+result.getInt("CustomerID"));
                System.out.println("customer first name: "+result.getString("FirstName"));
                System.out.println("customer last name: "+result.getString("LastName"));
                System.out.println("total price: "+result.getDouble("TotalPrice"));
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void newTransaction(){
        try {
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void queryTransactionDetail(){
        System.out.println("-----find transaction detail by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from TransactionContains join Merchandise on TransactionContains.ProductID = Merchandise.ProductID where TransactionID=";
        System.out.print("TransactionID: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();
        sql+=id;
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("transaction id: "+result.getInt("TransactionID"));
                System.out.println("product id: "+result.getInt("ProductID"));
                System.out.println("product name: "+result.getInt("ProductName"));
                System.out.println("customer id: "+result.getInt("CustomerID"));
                System.out.println("total price: "+result.getDouble("TotalPrice"));
                System.out.println("total price: "+result.getDate("Date").toString());
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(){
        while(true) {
            System.out.print("enter operation code: ");
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("--search transactions--");
                queryTransactions();
            } else if (in.equals("1")) {
                System.out.println("--new transaction--");
                newTransaction();
            }
            else if (in.equals("2")) {
                System.out.println("--query transaction detail--");
                queryTransactionDetail();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
