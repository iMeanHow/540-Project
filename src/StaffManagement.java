import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class StaffManagement {

    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    //the helper function shows hint for operation code
    public static void helper(){
        System.out.println("\n0: Search Staff");
        System.out.println("1: New Staff");
        System.out.println("2: Create Manager");
        System.out.println("3: Create Cashier");
        System.out.println("4: Create Billing Staff");
        System.out.println("5: Create Warehouse Checker");
        System.out.println("6: Delete Manager Role");
        System.out.println("7: Delete Cashier Role");
        System.out.println("8: Delete Billing Staff Role");
        System.out.println("9: Delete Warehouse Checker Role");
        System.out.println("10: Delete Staff");
        System.out.println("11: Update Staff");
        System.out.println("back: return last menu");
    }

    public StaffManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner){
        this.connection=connection;
        this.statement=statement;
        this.result=result;
        this.scanner=scanner;
    }

    // query staff by condition search
    public void findStaff(){
        System.out.println("-----find staff by condition-----");
        System.out.println("--press enter to skip the input--");

        //for every query input, do not format it into sql if it is null
        String sql = "select * from staff where 1=1 ";
        System.out.print("staffId: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();

        //if id is given, no need for other information
        if(!StringUtils.isNullOrEmpty(id)){
            sql+=(" and StaffID="+id);
        }
        else{
            System.out.print("name: ");
            String name=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(name)){
                sql+=(" and Name like'%"+name+"%'");
            }
            System.out.print("age: ");
            String age=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(age)){
                sql+=(" and Age="+age);
            }
            System.out.print("address: ");
            String address=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(address)){
                sql+=(" and Address like'%"+address+"%'");
            }
            System.out.print("phone: ");
            String phone=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(phone)){
                sql+=(" and Phone='"+phone+"'");
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
                System.out.println("staffid: "+result.getInt("StaffID"));
                System.out.println("name: "+result.getString("Name"));
                System.out.println("age: "+result.getInt("Age"));
                System.out.println("address: "+result.getString("Address"));
                System.out.println("phone: "+result.getString("Phone"));
                System.out.println("employmentTime: "+result.getDate("EmploymentTime").toString()+"\n");
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //new staff
    public void createStaff(){
        System.out.println("--------create new staff---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into staff (Name,Age,Address,Phone,EmploymentTime) values (";
        System.out.print("name: ");
        sql += ("'"+scanner.next()+"'"+",");
        System.out.print("age: ");
        sql += scanner.next()+",";
        System.out.print("address: ");
        sql += ("'"+scanner.next()+"'"+",");
        System.out.print("phone: ");
        sql += ("'"+scanner.next()+"'"+",");
        sql += "now() )";

        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //new manager with staff id reference
    public void associateManager(){
        System.out.println("--------associate manager---------");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "Insert into AssistantManager Values ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }
    }

    //new billing staff with staff id reference
    public void billingStaff(){
        System.out.println("---associate billing staff---");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "Insert into BillingStaff Values ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }
    }

    //new cashier with staff id reference
    public void cashier(){
        System.out.println("--------associate cashier---------");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "Insert into Cashier Values ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }
    }

    //new warehouse checker with staff id
    public void warehouseChecker(){
        System.out.println("--associate warehouse checker--");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "Insert into WarehouseChecker Values ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }
    }

    //delete manager role by id
    public void deleteAssistantManager(){
        System.out.println("-----delete manager-----");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from AssistantManager where StaffID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //delete billing staff role by id
    public void deleteBillingStaff(){
        System.out.println("--delete billing staff--");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from BillingStaff where StaffID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //delete cashier role by id
    public void deleteCashier(){
        System.out.println("--delete cashier--");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from Cashier where StaffID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //delete warehouse checker by id
    public void deleteWarehouseChecker(){
        System.out.println("--delete warehouse checker--");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from WarehouseChecker where StaffID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //delete staff by id (not recommended)
    public void deleteStaff(){
        System.out.println("--delete staff (not recommended)--");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql = "DELETE from staff where StaffID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }
    }

    //update staff info by id
    public void updateStaff(){
        System.out.println("-----update staff-----");
        System.out.print("staff id: ");
        String id=scanner.next();
        int staffid;
        try{
            staffid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
        }
        String sql1 = "select * from staff where StaffID = ("+id+")";

        //print original info first
        try{
            result = statement.executeQuery(sql1);
            if(result.next()){
                System.out.println("staffid: "+result.getInt("StaffID"));
                System.out.println("name: "+result.getString("Name"));
                System.out.println("age: "+result.getInt("Age"));
                System.out.println("address: "+result.getString("Address"));
                System.out.println("phone: "+result.getString("Phone"));
                System.out.println("employmentTime: "+result.getDate("EmploymentTime").toString()+"\n");
            } else {
                System.out.println("Staff Not Exist");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Staff Not Exist");
        }

        //load updated information from input
        String sql2 = "Update staff set ";
        System.out.print("name: ");
        String name=scanner.next();
        sql2+=(" Name='"+name+"'");
        System.out.print("age: ");
        String age=scanner.next();
        sql2+=(" ,Age="+age);
        System.out.print("address: ");
        String address=scanner.next();
        sql2+=(" ,Address='"+address+"'");
        System.out.print("phone: ");
        String phone=scanner.next();
        sql2+=(" ,Phone='"+phone+"'");

        sql2+=(" where StaffID="+id);
        try{
            int res = statement.executeUpdate(sql2);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //entry of staff management functions
    public void execute(){
        while(true) {
            helper();
            System.out.print("enter operation code: ");
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("--search staff--");
                findStaff();
            } else if (in.equals("1")) {
                System.out.println("--new staff--");
                createStaff();
            } else if (in.equals("2")) {
                System.out.println("--associate staff with manager role--");
                associateManager();
            }
            else if (in.equals("3")) {
                System.out.println("--associate staff with cashier role--");
                cashier();
            }
            else if (in.equals("4")) {
                System.out.println("--associate staff with billing staff role--");
                billingStaff();
            }
            else if (in.equals("5")) {
                System.out.println("--associate staff with warehouse checker role--");
                warehouseChecker();
            }
            else if (in.equals("6")) {
                System.out.println("--delete manager role of staff--");
                deleteAssistantManager();
            }
            else if (in.equals("7")) {
                System.out.println("--delete cashier role of staff--");
            }
            else if (in.equals("8")) {
                System.out.println("--delete billing staff role of staff--");
                deleteBillingStaff();
            }
            else if (in.equals("9")) {
                System.out.println("--delete warehouse checker role of staff--");
                deleteWarehouseChecker();
            }
            else if (in.equals("10")) {
                System.out.println("--delete staff--");
                deleteStaff();
            }
            else if (in.equals("11")) {
                System.out.println("--update staff info by id--");
                updateStaff();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
