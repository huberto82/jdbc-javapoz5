import java.sql.*;
import java.util.Scanner;

public class StatementDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection con = ConnectDemo.getConnection();
        ConnectDemo.prepareBase(con);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz id osoby: ");
        Statement stat = con.createStatement();
        ResultSet all = stat.executeQuery("SELECT * FROM PERSON");
        printResults(all);
        String id = scanner.nextLine();
        String query = "SELECT * FROM PERSON WHERE id = "+id+";";
        System.out.println(query);
        ResultSet set = stat.executeQuery(query);

        printResults(set);
        String name = scanner.nextLine();
        set = stat.executeQuery("SELECT * FROM PERSON WHERE first_name = '" + name+ "'");
        printResults(set);

        System.out.println("Przekazywanie parametrów zapytań w PreparedStatement");
        PreparedStatement findByFirstName = con.prepareStatement("" +
                "SELECT * FROM PERSON WHERE first_name = ?");
        findByFirstName.setString(1,name);
        set = findByFirstName.executeQuery();
        printResults(set);

        stat.close();
        con.close();
    }

    public static void printResults(ResultSet set) throws SQLException {
        while(set.next()){
            String firstName = set.getString("first_name");
            String lastName = set.getString("last_name");
            String email = set.getString("email");
            System.out.println(firstName +" " +lastName+" "+email);
        }
    }
}
