import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionManagement {
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private Scanner scanner;

    public TransactionManagement(Connection connection,Statement statement,ResultSet result,Scanner scanner) {
        this.connection = connection;
        this.statement = statement;
        this.result = result;
        this.scanner = scanner;
    }

    public void execute(){

    }
}
