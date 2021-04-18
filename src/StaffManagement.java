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

    public StaffManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner){
        this.connection=connection;
        this.statement=statement;
        this.result=result;
        this.scanner=scanner;
    }

    public void findStaff(){
        System.out.println("-----find staff by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from staff where 1=1";
        System.out.print("staffId: ");
        String unuse=scanner.nextLine();
        String id=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(id)){
            sql+=(" and StaffID="+id);
        }
        else{
            System.out.print("name: ");
            String name=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(name)){
                sql+=(" and Name='"+name+"'");
            }
            System.out.print("age: ");
            String age=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(age)){
                sql+=(" and Age="+age);
            }
            System.out.print("address: ");
            String address=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(address)){
                sql+=(" and Address='"+address+"'");
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


    public void execute(){
        while(true) {
            System.out.print("enter opeartion code: ");
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
            }
            else if (in.equals("3")) {
                System.out.println("--associate staff with cashier role--");
            }
            else if (in.equals("4")) {
                System.out.println("--associate staff with billing staff role--");
            }
            else if (in.equals("5")) {
                System.out.println("--associate staff with warehouse checker role--");
            }
            else if (in.equals("6")) {
                System.out.println("--update staff info--");
            }
            else if (in.equals("7")) {
                System.out.println("--delete manager role of staff--");
            }
            else if (in.equals("8")) {
                System.out.println("--delete cashier role of staff--");
            }
            else if (in.equals("9")) {
                System.out.println("--delete billing staff role of staff--");
            }
            else if (in.equals("10")) {
                System.out.println("--delete warehouse checker role of staff--");
            }
            else if (in.equals("11")) {
                System.out.println("--delete staff--");
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
