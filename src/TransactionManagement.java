import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TransactionManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    //the helper function shows hint for operation code
    public static void helper(){
        System.out.println("\n0: Search Transaction");
        System.out.println("1: New Transaction");
        System.out.println("2: Query Transaction Detail");
        System.out.println("back: return last menu");
    }

    public TransactionManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    // query transaction by condition
    public void queryTransactions(){
        System.out.println("-----find transaction by condition-----");
        System.out.println("--press enter to skip the input--");
        //for every query input, do not format it into sql if it is null
        String sql = "select * from transactionrecords join ClubMembers on transactionrecords.CustomerID = ClubMembers.CustomerID where 1=1";
        System.out.print("TransactionID: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();

        //if id is given, no need for other information
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

            //print all qualified result one by one
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

    /*
    * new transaction
    * 1. load discount information
    * 2. reduce stock & judge whether the product expired or not
    * 3. insert transaction record which include general information
    * 4. insert transaction contains which include product list
    * roll back if any step fails
    * */

    public void newTransaction(){
        System.out.println("-----new transaction -----");
        System.out.println("--no null value--");
        List<Integer> idList =new LinkedList<>();
        List<Integer> cntList =new LinkedList<>();
        List<Double> discountList = new LinkedList<>();
        List<Double> actualPrice = new LinkedList<>();
        double totalPrice=0.0;
        System.out.print("cashier id: ");
        int cashierid=scanner.nextInt();
        System.out.print("store id: ");
        int storeid=scanner.nextInt();
        System.out.print("customer id: ");
        int customerid=scanner.nextInt();
        while(true){
            System.out.print("product id: ");
            idList.add(scanner.nextInt());
            System.out.print("count: ");
            cntList.add(scanner.nextInt());

            //support multiple product
            System.out.print("type y to continue(others would end): ");
            if(!scanner.next().equals('y')){
                break;
            }
        }

        try {
            //begin transaction
            connection.setSavepoint();
            connection.setAutoCommit(false);
            for(int i=0;i<idList.size();i++){
                //Step 1 load discount information
                String sql0="select * from onsaleproductions where ProductID="+idList.get(i)+" and ValidDate > now()";
                try {
                    //first insert clubmemer
                    result = statement.executeQuery(sql0);
                    if(result.next()){
                        discountList.add(result.getDouble("Discount"));
                    }else{
                        discountList.add(1.0);
                    }
                } catch (SQLException e) {
                    //rollback when failed
                    System.out.println(e.getMessage());
                    connection.rollback();
                    return;
                }

                // Step 2 reduce stock & judge whether the product expired or not
                String sql1="update merchandise set Quantity=Quantity-"+cntList.get(i)+" where ProductID="+idList.get(i)+" and ExpirationDate > now()";
                try {
                    //first insert clubmemer
                    int res = statement.executeUpdate(sql1);
                    if(res==0){
                        System.out.println("Transaction failed! No valid product found (may expired)");
                        connection.rollback();
                        return;
                    }
                } catch (SQLException e) {
                    //rollback when failed
                    System.out.println("Transaction failed! check product stock!");
                    connection.rollback();
                    return;
                }

                String sql2="select * from merchandise where ProductID="+idList.get(i);
                try {
                    //first insert clubmemer
                    result = statement.executeQuery(sql2);
                    double mprice=0.0;
                    if(result.next()){
                        mprice=result.getDouble("MarketPrice");
                    }
                    actualPrice.add(mprice*discountList.get(i));
                    totalPrice+=(cntList.get(i)*actualPrice.get(i));
                } catch (SQLException e) {
                    //rollback when failed
                    System.out.println(e.getMessage());
                    connection.rollback();
                    return;
                }
            }

            // Step 3 insert transaction record which include general information
            String sql3="insert into transactionrecords(cashierid,storeid,totalprice,date,customerid) values("+cashierid+","+storeid+","+totalPrice+",now(), "+customerid+")";
            int tid=0;
            try {
                //first insert clubmemer
                int res = statement.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    //get generatedKeys for insert registration record
                    tid=generatedKeys.getInt("GENERATED_KEY");
                }
            } catch (SQLException e) {
                //rollback when failed
                System.out.println(e.getMessage());
                connection.rollback();
                return;
            }

            // Step 4 insert transaction contains which include product list
            for(int i=0;i<idList.size();i++){
                String sql4="insert into transactionContains(transactionid,productid,count,actualprice) values(";
                sql4+=tid;
                sql4+=", ";
                sql4+=idList.get(i);
                sql4+=", ";
                sql4+=cntList.get(i);
                sql4+=", ";
                sql4+=actualPrice.get(i);
                sql4+=")";
                try {
                    //System.out.println(sql);
                    int res = statement.executeUpdate(sql4);
                } catch (SQLException e) {
                    //rollback when failed
                    System.out.println(e.getMessage());
                    connection.rollback();
                }

            }
            System.out.println("Success");
            connection.commit();
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //query product list with their name, actual-price...
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
                System.out.println("product name: "+result.getString("ProductName"));
                System.out.println("actual price: "+result.getDouble("ActualPrice"));
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(){
        while(true) {
            helper();
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
