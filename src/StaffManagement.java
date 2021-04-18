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

    public void execute(){
        while(true) {
            String in = scanner.next();
            if (in.equals("back")) {
                return;
            } else if (in.equals("1")) {
                System.out.println("--new staff--");
            }
            else{
                System.out.println("invalid input");
            }
        }
    }
}
