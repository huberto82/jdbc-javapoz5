import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.h2.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa","");
        Statement stat = con.createStatement();
        String create = "CREATE TABLE PERSON(" +
                "id int primary key," +
                "first_name varchar(20) not null," +
                "last_name varchar(30) not null," +
                "email varchar(20))";
        stat.executeUpdate(create);
    }
}
