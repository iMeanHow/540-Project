
import com.mysql.cj.util.StringUtils;
import javax.transaction.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class MemberManagement {

    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public MemberManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner){
        this.connection=connection;
        this.statement=statement;
        this.result=result;
        this.scanner=scanner;
    }

    public void signUpMember(){
        

    }

    public void updateMember(){

    }

    public void searchMember(){
        System.out.println("-----find staff by condition-----");
        System.out.println("--press enter to skip the input--");
        String sql = "select * from Store where 1=1";
        System.out.print("customer id: ");
        String unuse=scanner.nextLine();
        String customerid=scanner.nextLine();
        if(!StringUtils.isNullOrEmpty(customerid)){
            sql+=(" and CustomerID="+customerid);
        } else {
            System.out.print("first name: ");
            String fname=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(fname)){
                sql+=(" and FirstName='"+fname+"'");
            }
            System.out.print("last name: ");
            String lname=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(lname)){
                sql+=(" and LastName='"+lname+"'");
            }
            System.out.print("phone: ");
            String phone=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(phone)){
                sql+=(" and Phone='"+phone+"'");
            }
            System.out.print("email: ");
            String email=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(email)){
                sql+=(" and Email='"+email+"'");
            }
            System.out.print("address: ");
            String address=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(address)){
                sql+=(" and Address like'%"+address+"%'");
            }
            System.out.print("level: ");
            String level=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(level)){
                sql+=(" and Level='"+level+"'");
            }
            System.out.print("activate status: ");
            String status=scanner.nextLine();
            if(!StringUtils.isNullOrEmpty(status)){
                sql+=(" and ActivateStatus="+status);
            }
        }
        try {
            //System.out.println(sql);
            result = statement.executeQuery(sql);
            int cnt=0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No."+cnt+" ===");
                System.out.println("customerId: "+result.getInt("CustomerID"));
                System.out.println("first name: "+result.getString("FirstName"));
                System.out.println("last name: "+result.getString("LastName"));
                System.out.println("address: "+result.getString("Address"));
                System.out.println("phone: "+result.getString("Phone"));
                System.out.println("email: "+result.getString("email"));
                System.out.println("level: "+result.getString("email"));
                System.out.println("status (0:inactivate, 1: activate): "+result.getInt("ActivateStatus"));
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
                System.out.println("--search club member--");
                searchMember();
            } else if(in.equals("1")){
                System.out.println("--sign up club member--");
                signUpMember();
            } else if(in.equals("2")){
                System.out.println("--update club member--");
                updateMember();
            } else if(in.equals("3")){
                System.out.println("--inactivate club member--");
                updateMember();
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
