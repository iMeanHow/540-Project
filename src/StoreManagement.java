import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StoreManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public StoreManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    public void findStore(){
        System.out.println("-----find staff by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from Store where 1=1";
        System.out.print("store id: ");
        String unuse=scanner.nextLine();
        String storeid=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(storeid)){
            sql+=(" and StoreID="+storeid);
        }else{
            System.out.print("manager id: ");
            String name=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(name)){
                sql+=(" and ManagerID='"+name+"'");
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
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("store id: "+result.getInt("StoreID"));
                System.out.println("manager id: "+result.getInt("MangerID"));
                System.out.println("address: "+result.getString("Address"));
                System.out.println("phone: "+result.getString("Phone"));
            }
            System.out.println("Total rows: "+cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void newStore(){
        System.out.println("--------create new store---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into store (ManagerID,Address,Phone) values (";
        System.out.print("manager id: ");
        String mid=scanner.next();
        int managerid;
        try{
            managerid=Integer.valueOf(mid);
        }catch (Exception e){
            System.out.println("invalid manager id");
            return;
        }
        String managerSQL = "select * from AssistantManager where StaffID = "+managerid;
        try {
            result = statement.executeQuery(managerSQL);
            if(result.next()==false){
                System.out.println("this id is not a manager!");
                return;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql += (managerid+",");
        System.out.print("address: ");
        sql += ("'"+scanner.next()+"'"+",");
        System.out.print("phone: ");
        sql += ("'"+scanner.next()+"'"+" )");
        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStore(){
        System.out.println("-----delete store-----");
        System.out.print("store id: ");
        String id=scanner.next();
        int storeid;
        try{
            storeid=Integer.valueOf(id);
        }catch (Exception e){
            System.out.println("invalid input!");
            return;
        }
        String sql = "DELETE from Store where StoreID = ("+id+")";
        try{
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void employStaff(){
        System.out.println("--------employ staff to store---------");
        System.out.println("-----no null value permitted-----");
        String sql = "Insert into Employ (StoreID,StaffID) values (";
        System.out.print("Store id: ");
        String storeid=scanner.next();
        sql+=(storeid+", ");
        System.out.print("Staff id: ");
        String staffid=scanner.next();
        sql+=(staffid+")");
        String sql1="select * from Employ where StaffID="+staffid;
        try {
            result = statement.executeQuery(sql1);
            if(result.next()==true){
                System.out.println("this staff is already employed!");
                return;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("staff or store not exists!");
        }
    }

    public void unemployStaff(){
        System.out.println("--------un-employ staff from store---------");
        System.out.println("-----no null value permitted-----");
        String sql = "delete from Employ where ";
        System.out.print("Store id: ");
        String storeid=scanner.next();
        sql+=(" StoreID="+storeid);
        System.out.print("Staff id: ");
        String staffid=scanner.next();
        sql+=(" and StaffID="+staffid);
        try {
            //System.out.println(sql);
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("staff or store not exists!");
        }
    }

    public void queryEmploy(){
        System.out.println("-----find employment by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from employ where 1=1";
        System.out.print("store id: ");
        String unuse=scanner.nextLine();
        String storeid=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(storeid)){
            sql+=(" and StoreID="+storeid);
        }
        String staffid=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(staffid)){
            sql+=(" and StaffID="+staffid);
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("store id: "+result.getInt("StoreID"));
                System.out.println("staff id: "+result.getInt("StaffID"));
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
            } else if(in.equals("0")){
                System.out.println("--search store--");
                findStore();
            } else if(in.equals("1")){
                System.out.println("--new store--");
                newStore();
            } else if(in.equals("2")){
                System.out.println("--delete store--");
                deleteStore();
            } else if(in.equals("3")){
                System.out.println("--search employment--");
                queryEmploy();
            } else if(in.equals("4")){
                System.out.println("--new employment--");
                employStaff();
            } else if(in.equals("5")){
                System.out.println("--delete employment--");
                unemployStaff();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
