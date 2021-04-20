import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class SupplierManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public SupplierManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner){
        this.connection=connection;
        this.statement=statement;
        this.result=result;
        this.scanner=scanner;
    }

    public void findSupplier(){
        System.out.println("-----find supplier by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from Suppliers where 1=1";
        System.out.print("SupplierId: ");
        //String unuse=scanner.nextLine();
        String id=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(id)){
            sql+=(" and SupplierID="+id);
        }
        else{
            System.out.print("SupplierName: ");
            String name=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(name)){
                sql+=(" and SupplierName='"+name+"'");
            }
            System.out.print("Email: ");
            String email=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(email)){
                sql+=(" and Email="+email);
            }
            System.out.print("Location: ");
            String Location=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(Location)){
                sql+=(" and Location='"+Location+"'");
            }
            System.out.print("Phone: ");
            String phone=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(phone)){
                sql+=(" and Phone='"+phone+"'");
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("Supplierid: "+result.getInt("SupplierID"));
                System.out.println("SupplierName: "+result.getString("SupplierName"));
                System.out.println("Email: "+result.getString("Email"));
                System.out.println("Location: "+result.getString("Location"));
                System.out.println("phone: "+result.getString("Phone")+"\n");
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createSupplier(){
        System.out.println("--------create new supplier---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into suppliers (SupplierName,Email,Location,Phone) values (";
        System.out.print("name: ");
        sql += ("'"+scanner.nextLine()+"'"+",");
        System.out.print("email: ");
        sql += ("'"+scanner.nextLine()+"'"+",");
        System.out.print("location: ");
        sql += ("'"+scanner.nextLine()+"'"+",");
        System.out.print("phone: ");
        sql += ("'"+scanner.nextLine()+"'");
        sql+=")";
        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteSupplier(){
        System.out.println("--delete Supplier (not recommended)--");
        System.out.print("Supplier id: ");
        String id=scanner.nextLine();
        int Supplierid;
        try{
            Supplierid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql = "DELETE from Suppliers where SupplierID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Supplier Not Exist");
        }
    }

    public void updateSupplier(){
        System.out.println("-----update Supplier-----");
        System.out.print("Supplier id: ");
        String id=scanner.nextLine();
        int Supplierid;
        try{
            Supplierid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql1 = "select * from Supplier where SupplierID = ("+id+")";
        try{
            result = statement.executeQuery(sql1);
            if(result.next()){
                System.out.println("Supplierid: "+result.getInt("SupplierID"));
                System.out.println("Name: "+result.getString("SupplierName"));
                System.out.println("Email: "+result.getString("Email"));
                System.out.println("Location: "+result.getString("Location"));
                System.out.println("phone: "+result.getString("Phone")+"\n");
            } else {
                System.out.println("Supplier Not Exist");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Supplier Not Exist");
        }
        String sql2 = "Update Supplier set ";
        System.out.print("name: ");
        String name=scanner.nextLine();
        sql2+=(" Name='"+name+"'");
        System.out.print("email: ");
        String email=scanner.nextLine();
        sql2+=(" ,email="+email);
        System.out.print("location: ");
        String location=scanner.nextLine();
        sql2+=(" ,location='"+location+"'");
        System.out.print("phone: ");
        String phone=scanner.nextLine();
        sql2+=(" ,Phone='"+phone+"'");

        sql2+=(" where SupplierID="+id);
        try{
            int res = statement.executeUpdate(sql2);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(){
        while(true) {
            System.out.print("enter operation code: ");
            String in = scanner.nextLine();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("--search supplier--");
                findSupplier();
            } else if (in.equals("1")) {
                System.out.println("--new supplier--");
                createSupplier();
            }
            else if (in.equals("2")) {
                System.out.println("--delete supplier--");
                deleteSupplier();
            }
            else if (in.equals("3")) {
                System.out.println("--update supplier info by id--");
                updateSupplier();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
