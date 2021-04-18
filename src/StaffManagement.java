import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public void createStaff(){

    }

    public void execute(){
        while(true) {
            System.out.print("enter opeartion code: ");
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if (in.equals("0")) {
                System.out.println("--search staff--");
            } else if (in.equals("1")) {
                System.out.println("--new staff--");
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
