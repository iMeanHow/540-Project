

import com.mysql.cj.util.StringUtils;

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

    public MemberManagement(Connection connection, Statement statement, ResultSet result, Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    public void signUpNewMember() {
        System.out.println("Sign up new club member");
        System.out.println("No null value permitted");
        String sql = "insert into ClubMembers(Email,FirstName,LastName,Phone,Address,Level,ActiveStatus) values(";
        System.out.print("Email: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("FirstName: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("LastName: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("Phone: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("Address: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("Level: ");
        sql += ("'" + scanner.next() + "'" + ",");
        System.out.print("ActiveStatus: ");
        sql += ( scanner.next() );
        sql +=")";
        System.out.print("staff id: ");
        String staffid=scanner.next();
        int customerid=0;
        try {
            //begin transaction
            connection.setSavepoint();
            connection.setAutoCommit(false);
            try {
                //first insert clubmemer
                int res = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    //get generatedKeys for insert registration record
                    customerid=generatedKeys.getInt("GENERATED_KEY");
                }
            } catch (SQLException e) {
                //rollback when failed
                System.out.println(e.getMessage());
                connection.rollback();
                return;
            }
            try {
                //first insert clubmemer

                sql = "insert into registrationrecords values("+customerid+", now(), "+staffid+", (select storeid from employ where staffid="+staffid+"))";
                //System.out.println(sql);
                int res = statement.executeUpdate(sql);
                System.out.println("Success");
            } catch (SQLException e) {
                //rollback when failed
                System.out.println("failed! make sure staff is employed!");
                connection.rollback();
            }
            connection.commit();

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

    public void findMember() {
        System.out.println("Find club member by condition");
        System.out.println("Press enter to skip the input");
        String sql = "select * from ClubMembers where 1=1";
        System.out.print("memberId: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        if (!StringUtils.isNullOrEmpty(id)) {
            sql += (" and MemberID=" + id);
        } else {
            System.out.print("email: ");
            String email = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(email)) {
                sql += (" and email='" + email + "'");
            }
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(firstName)) {
                sql += (" and First Name='" + firstName + "'");
            }
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(lastName)) {
                sql += (" and Last Name='" + lastName + "'");
            }
            System.out.print("phone: ");
            String phone = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(phone)) {
                sql += (" and Phone='" + phone + "'");
            }
            System.out.print("address: ");
            String address = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(address)) {
                sql += (" and Address='" + address + "'");
            }
            System.out.print("level: ");
            String level = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(level)) {
                sql += (" and level='" + level + "'");
            }
            System.out.print("active status: ");
            String activeStatus = scanner.nextLine();
            if (!StringUtils.isNullOrEmpty(activeStatus)) {
                sql += (" and activeStatus=" + activeStatus);
            }
        }
        try {
            result = statement.executeQuery(sql);
            int cnt = 0;
            while (result.next()) {
                cnt++;
                System.out.println("\n=== No." + cnt + " ===");
                System.out.println("member id: " + result.getInt("MemberID"));
                System.out.println("email: " + result.getString("email"));
                System.out.println("first name: " + result.getString("firstName"));
                System.out.println("last name: " + result.getString("lastName"));
                System.out.println("phone: " + result.getString("Phone"));
                System.out.println("address: " + result.getString("Address"));
                System.out.println("level: " + result.getString("level"));
                System.out.println("active status: " + result.getString("activeStatus"));
            }
            System.out.println("Total rows: " + cnt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateMember() {
        System.out.println("Update club member");
        System.out.print("member id: ");
        String id = scanner.next();
        int memberid;
        try {
            memberid = Integer.valueOf(id);
        } catch (Exception e) {
            System.out.println("invalid input!");
        }
        String sql1 = "select * from ClubMembers where MemberID = (" + id + ")";
        try {
            result = statement.executeQuery(sql1);
            if (result.next()) {
                System.out.println("member id: " + result.getInt("MemberID"));
                System.out.println("email: " + result.getString("email"));
                System.out.println("first name: " + result.getString("firstName"));
                System.out.println("last name: " + result.getString("lastName"));
                System.out.println("phone: " + result.getString("Phone"));
                System.out.println("address: " + result.getString("Address"));
                System.out.println("level: " + result.getString("level"));
                System.out.println("active status: " + result.getInt("activeStatus"));
            } else {
                System.out.println("Club member Not Exist");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Club member Not Exist");
        }
        String sql2 = "Update clubmembers set ";

        System.out.print("First Name: ");
        String firstName = scanner.next();
        sql2 += (" First Name='" + firstName + "'");
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        sql2 += (" Last Name='" + lastName + "'");
        System.out.print("phone: ");
        String phone = scanner.next();
        sql2 += (" ,Phone='" + phone + "'");
        System.out.print("address: ");
        String address = scanner.next();
        sql2 += (" ,Address='" + address + "'");
        System.out.print("level: ");
        String level = scanner.next();
        sql2 += (" ,level=" + level);
        System.out.print("active status: ");
        String activeStatus = scanner.next();
        sql2 += (" ,activeStatus=" + activeStatus);

        sql2 += (" where MemberID=" + id);
        try {
            int res = statement.executeUpdate(sql2);
            System.out.println("success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMember() {
        System.out.println("Delete club member");
        System.out.print("member id: ");
        String id = scanner.next();
        int memberid;
        try {
            memberid = Integer.valueOf(id);
        } catch (Exception e) {
            System.out.println("invalid input!");
        }
        String sql = "DELETE from ClubMembers where CustomerID = (" + id + ")";
        try {
            int res = statement.executeUpdate(sql);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("Member Not Exist");
        }
    }

    public void execute() {
        while (true) {
            System.out.print("enter operation code: ");
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("sign up club member");
                signUpNewMember();
            } else if (in.equals("1")) {
                System.out.println("find club member");
                findMember();
            } else if (in.equals("2")) {
                System.out.println("update member");
                updateMember();
            } else if (in.equals("3")) {
                System.out.println("delete member");
                deleteMember();
            }
//            } else if (in.equals("4")) {
//                System.out.println("----");
//                foo();

        }
    }
}