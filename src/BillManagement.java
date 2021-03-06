import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BillManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    // the helper function shows hint for operation code
    public static void helper(){
        System.out.println("\n0:--search bill--");
        System.out.println("1: --new bill--");
        System.out.println("2:--delete bill--");
        System.out.println("3:--update bill status--");
        System.out.println("4:--Insert into OweTo--");
        System.out.println("5:--Insert into PayFor--");
        System.out.println("6:--Insert into ReturnReward--");
        System.out.println("back: return last menu");
    }

    public BillManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    //query bill by condition search
    public void findBill(){
        System.out.println("-----find bill by condition-----");
        System.out.println("--press enter to skip the input--");

        //for every query input, do not format it into sql if it is null
        String sql = "select * from Bills where 1=1";
        System.out.print("bill id: ");
        //String unuse=scanner.nextLine();
        String billid=scanner.nextLine();

        //if id is given, no need for other information
        if(!StringUtils.isNullOrEmpty(billid)){
            sql+=(" and BillID="+billid);
        }else{
            System.out.print("amount: ");
            String amount=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(amount)){
                sql+=(" and Amount="+amount);
            }
            System.out.print("payment status: ");
            String paymentStatus=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(paymentStatus)){
                sql+=(" and paymentStatus="+paymentStatus);
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            //print all qualified result one by one
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("bill id: "+result.getInt("BillID"));
                System.out.println("amount: "+result.getDouble("Amount"));
                System.out.println("payment status: "+result.getString("PaymentStatus"));
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // create bill
    // the bill id will be check before assign to store
    public void createBill(){
        System.out.println("--------create new bill---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into Bills (Amount,PaymentStatus) values (";
        System.out.print("amount: ");
        sql += scanner.next()+",";
        System.out.print("payment status: ");
        sql += scanner.next()+")";

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    }

    //delete bill by id
    public void deleteBill(){
        System.out.println("-----delete bill-----");
        System.out.print("bill id: ");
        String id=scanner.next();
        int billid;
        try{
            billid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from Bills where BillID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // update bill status, only payment status can change
    public void updateBillStatus() {
        System.out.println("-----update bill-----");
        System.out.print("bill id: ");
        String id=scanner.next();
        int billid;
        try{
            billid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql1 = "select * from Bills where BillID = ("+id+")";
        try{
            result = statement.executeQuery(sql1);
            if(result.next()){
                System.out.println("billid: "+result.getInt("BillID"));
                System.out.println("amount: "+result.getInt("Amount"));
                System.out.println("payment status: "+result.getInt("PaymentStatus"+"\n"));
            } else {
                System.out.println("Bill Not Exist");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Bill Not Exist");
        }
        String sql2 = "Update bill set ";
        System.out.print("payment status: ");
        String paymentStatus=scanner.next();
        sql2+=(" ,PaymentStatus="+paymentStatus);

        sql2+=(" where BillID="+id);
        try{
            int res = statement.executeUpdate(sql2);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // create oweto relation to supplier
    public void OwedTo() {
        System.out.println("--------Owed To Supplier---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into OwedTo (BillID,SupplierID) values (";
        System.out.print("Bill id: ");
        String billid = scanner.next();
        sql += (billid + ", ");
        System.out.print("Supplier id: ");
        String supplierid = scanner.next();
        sql += (supplierid + ")");

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("bill or supplier not exists!");
        }
    }

    // create payfor relation to staff
    public void PayFor() {
        System.out.println("--------Pay For Staff---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into PayFor (BillID,StaffID) values (";
        System.out.print("Bill id: ");
        String billid = scanner.next();
        sql += (billid + ", ");
        System.out.print("Staff id: ");
        String staffid = scanner.next();
        sql += (staffid + ")");

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("bill or staff not exists!");
        }
    }

    // create return reward relation to customer
    public void ReturnReward() {
        System.out.println("--------Return Reward Customer---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into ReturnReward (BillID,CustomerID) values (";
        System.out.print("Bill id: ");
        String billid = scanner.next();
        sql += (billid + ", ");
        System.out.print("Customer id: ");
        String customerid = scanner.next();
        sql += (customerid + ")");

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("bill or customer not exists!");
        }
    }

    // entry of bill management
    public void execute(){
        while(true) {
            System.out.print("enter operation code: ");
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if(in.equals("0")){
                System.out.println("--search bill--");
                findBill();
            } else if(in.equals("1")){
                System.out.println("--new bill--");
                createBill();
            } else if(in.equals("2")){
                System.out.println("--delete bill--");
                deleteBill();
            } else if(in.equals("3")){
                System.out.println("--update bill status--");
                updateBillStatus();
            } else if(in.equals("4")){
                System.out.println("--Insert into OweTo--");
                OwedTo();
            } else if(in.equals("5")){
                System.out.println("--Insert into PayFor--");
                PayFor();
            } else if(in.equals("6")){
                System.out.println("--Insert into ReturnReward--");
                ReturnReward();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
