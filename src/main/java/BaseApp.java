import java.sql.*;
import java.util.Scanner;

public class BaseApp {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stat = con.createStatement();
        stat.execute("CREATE TABLE PERSON(id INT PRIMARY KEY, name VARCHAR(20) NOT NULL,  surename VARCHAR (30) NOT NULL)");
        int count = stat.executeUpdate("INSERT INTO PERSON VALUES (1, 'ADAM', 'NOWAK')");
        if (count == 1){
            System.out.println("ROW ADDED");
        }
        count = stat.executeUpdate("INSERT INTO PERSON VALUES (2, 'EWA', 'BOSKA')");
        if (count == 1){
            System.out.println("ROW ADDED");
        }

        ResultSet set = con.prepareStatement("Select * from person where id = 1 or 1 = 1").executeQuery();
        while (set.next()){
            System.out.println(set.getString("id")+" "+set.getString("name") + " " + set.getString("surename"));
        }
        stat.close();
        con.close();
    }
}
