import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Connection.*;

public class ConnectDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = getConnection();
        Statement stat = prepareBase(con);
        if (stat.execute("SELECT * FROM PERSON")){
            System.out.println("SELECT SUCCESS");
        }
        con.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
        int isolation = con.getTransactionIsolation();
        switch(isolation){
            case TRANSACTION_NONE:
                System.out.println("BRAK");
                break;
            case TRANSACTION_READ_UNCOMMITTED:
                System.out.println("READ UNCOMMITTED");
                break;
            case TRANSACTION_READ_COMMITTED:
                System.out.println("READ COMMITTED");
                break;
            case TRANSACTION_REPEATABLE_READ:
                System.out.println("REPEATABLE");
                break;
            case TRANSACTION_SERIALIZABLE:
                System.out.println("SERIALIZABLE");
                break;
        }
    }

    public static Statement prepareBase(Connection con) throws SQLException {
        Statement stat = con.createStatement();
        String create = "CREATE TABLE PERSON(" +
                "id int primary key," +
                "first_name varchar(20) not null," +
                "last_name varchar(30) not null," +
                "email varchar(20))";
        stat.executeUpdate(create);
        int count = stat.executeUpdate(
                "INSERT INTO PERSON VALUES (1, 'ADAM', 'NOWAK','adam@op/pl')");
        if (count == 1){
            System.out.println("ROW ADDED");
        }
        return stat;
    }

    public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver").newInstance();
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa","");
    }
}
