import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class MerchandiseManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public void helper(){

        System.out.println("\n0: Search Merchandise");
        System.out.println("1: New Merchandise");
        System.out.println("2: Delete merchandise");
        System.out.println("3: Update Merchandise");
        System.out.println("back: return last menu");
    }


    public MerchandiseManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner){
        this.connection=connection;
        this.statement=statement;
        this.result=result;
        this.scanner=scanner;
    }

    public void findMerchandise(){
        System.out.println("-----find merchandise by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from merchandise where 1=1";
        System.out.print("merchandiseId: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(id)){
            sql+=(" and ProductID="+id);
        }
        else{
            System.out.print("product name: ");
            String productName=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(productName)){
                sql+=(" and ProductName='"+productName+"'");
            }
            System.out.print("buy price: ");
            String buyPrice=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(buyPrice)){
                sql+=(" and BuyPrice="+buyPrice);
            }
            System.out.print("market price: ");
            String marketPrice=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(marketPrice)){
                sql+=(" and MarketPrice="+marketPrice);
            }
            System.out.print("product date: ");
            String productDate=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(productDate)){
                sql+=(" and ProductDate='"+productDate+"'");
            }
            System.out.print("expiration date: ");
            String expirationDate=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(expirationDate)){
                sql+=(" and ExpirationDate='"+expirationDate+"'");
            }
            System.out.print("quantity: ");
            String quantity=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(quantity)){
                sql+=(" and Quantity="+quantity);
            }
            System.out.print("storeid: ");
            String storeID=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(storeID)){
                sql+=(" and StoreID="+storeID);
            }
            System.out.print("supplierid: ");
            String supplierID=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(supplierID)){
                sql+=(" and SupplierID="+supplierID);
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("productid: "+result.getInt("ProductID"));
                System.out.println("product name: "+result.getString("ProductName"));
                System.out.println("buy price: "+result.getDouble("BuyPrice"));
                System.out.println("market price: "+result.getDouble("MarketPrice"));
                System.out.println("product date: "+result.getDate("ProductDate").toString());
                System.out.println("expiration date: "+result.getDate("ExpirationDate").toString());
                System.out.println("quantity: "+result.getInt("Quantity"));
                System.out.println("storeid: "+result.getInt("StoreID"));
                System.out.println("supplierid: "+result.getInt("SupplierID")+"\n");
            }
            System.out.println("\nTotal rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createMerchandise(){
        System.out.println("--------create new merchandise---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into merchandise (ProductName,BuyPrice,MarketPrice,ProductDate,ExpirationDate, Quantity, StoreID, SupplierID) values (";
        System.out.print("product name: ");
        sql += ("'"+scanner.next()+"'"+",");
        System.out.print("buy price: ");
        sql += scanner.next()+",";
        System.out.print("market price: ");
        sql += (scanner.next()+",");
        System.out.print("product date: ");
        String unuse=scanner.nextLine();
        sql += ("'"+scanner.nextLine()+"'"+",");
        System.out.print("expire date: ");
        sql += ("'"+scanner.nextLine()+"'"+",");
        System.out.print("quantity: ");
        sql += (scanner.next()+",");
        System.out.print("storeid: ");
        sql += (scanner.next()+",");
        System.out.print("supplierid: ");
        sql += (scanner.next());
        sql+=")";
        try {
            System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMerchandise(){
        System.out.println("--delete merchandise (not recommended)--");
        System.out.print("merchandise id: ");
        String id=scanner.next();
        int productid;
        try{
            productid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql = "DELETE from merchandise where ProductID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Merchandise Not Exist");
        }
    }

    public void updateMerchandise(){
        System.out.println("-----update merchandise-----");
        System.out.print("merchandise id: ");
        String id=scanner.next();
        int productid;
        try{
            productid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql1 = "select * from merchandise where ProductID = ("+id+")";
        try{
            result = statement.executeQuery(sql1);
            if(result.next()){
                System.out.println("productid: "+result.getInt("ProductID"));
                System.out.println("product name: "+result.getString("ProductName"));
                System.out.println("buy price: "+result.getDouble("BuyPrice"));
                System.out.println("market price: "+result.getDouble("MarketPrice"));
                System.out.println("product date: "+result.getDate("ProductDate").toString());
                System.out.println("expiration date: "+result.getDate("ExpirationDate").toString());
                System.out.println("quantity: "+result.getInt("Quantity"));
                System.out.println("storeid: "+result.getInt("StoreID"));
                System.out.println("supplierid: "+result.getInt("SupplierID")+"\n");
            } else {
                System.out.println("Merchandise Not Exist");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Merchandise Not Exist");
        }
        String sql2 = "Update merchandise set ";
        System.out.print("product name: ");
        String name=scanner.next();
        sql2+=(" ProductName='"+name+"'");
        System.out.print("buy price: ");
        String buyPrice=scanner.next();
        sql2+=(" ,BuyPrice="+buyPrice);
        System.out.print("market price: ");
        String marketPrice=scanner.next();
        sql2+=(" ,MarketPrice="+marketPrice);
        System.out.print("quantity: ");
        String quantity=scanner.next();
        sql2+=(" ,Quantity="+quantity); // other attributes cannot be updated

        sql2+=(" where ProductID="+id);
        try{
            int res = statement.executeUpdate(sql2);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // update and delete of onsale production is not necessary
    public void findOnSaleProduction() {
        System.out.println("-----find OnSaleProduction by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from OnSaleProductions where 1=1";
        System.out.print("productid: ");
        // String unuse=scanner.nextLine();
        // two pk, so
        String productid=scanner.nextLine();
        System.out.print("storeid: ");
        String storeid=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(productid) && !StringUtils.isNullOrEmpty(storeid)){
            sql+=(" and ProductID="+productid+" and StoreID="+storeid);
        }
        else{
            System.out.print("discount: ");
            String discount=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(discount)){
                sql+=(" and Discount="+discount);
            }
            System.out.print("valid date: ");
            String validDate=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(validDate)){
                sql+=(" and ValidDate="+validDate);
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("productid: "+result.getInt("ProductID"));
                System.out.println("storeid: "+result.getInt("StoreID"));
                System.out.println("discount: "+result.getDouble("Discount"));
                System.out.println("valid date: "+result.getDate("ValidDate")+"\n");
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createOnSaleProduction(){
        System.out.println("--------create new onsale production---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into onsale production (ProductID,StoreID,Discount,ValidDate) values (";
        System.out.print("product id: ");
        sql += (scanner.next()+",");
        System.out.print("store id: ");
        sql += (scanner.next()+",");
        System.out.print("dsicount: ");
        sql += (scanner.next()+",");
        System.out.print("valid date: ");
        sql += ("'"+scanner.next()+"'"+",");

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
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
                System.out.println("--search merchandise--");
                findMerchandise();
            } else if (in.equals("1")) {
                System.out.println("--new merchandise--");
                createMerchandise();
            }
            else if (in.equals("2")) {
                System.out.println("--delete merchandise--");
                deleteMerchandise();
            }
            else if (in.equals("3")) {
                System.out.println("--update merchandise info by id--");
                updateMerchandise();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
