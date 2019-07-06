import java.sql.*;
import java.util.Scanner;

/**
 * 1. Utwórz połączenie z bazą w mem
 * 2. Utwórz tabele z min 3 kolumnami
 * 3. Wyświetl menu: DODAJ, POKAZ, ZNAJDZ
 * 3. Na podstawie danych z klawiatury zrealizuje
 * polecenia z menu
 */
public class MyBase {

    private static void printMenu(){
        System.out.println("Wpisz polecenie'");
        System.out.println("DODAJ, POKAŻ, ZNAJDŹ, KONIEC");
    }

    static private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:testdb","sa","");
    }

    static private void prepareBase(Connection connection) throws SQLException {
        connection.createStatement().execute("Create table cat(id int primary key, name varchar(10), sex varchar(6))");

    }

    static private void insertCat(Connection con, int id, String name, String sex) throws SQLException {
        PreparedStatement stat = con.prepareStatement("INSERT INTO CAT VALUES (?, ?, ?)");
        stat.setInt(1, id);
        stat.setString(2, name);
        stat.setString(3, sex);
        stat.executeUpdate();
    }

    static private void showAll(Connection connection) throws SQLException {
        ResultSet set = connection.createStatement().executeQuery("SELECT * FROM CAT");
        while(set.next()){
            System.out.println(set.getString("name") + " " + set.getString("sex"));
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int id = 0;
        printMenu();
        Scanner scanner = new Scanner(System.in);
        Connection con = getConnection();
        prepareBase(con);
        boolean exit = false;
        while(!exit) {
            String com = scanner.next();
            if ("DODAJ".equals(com)){
                System.out.println("Wpisz imię:");
                String name = scanner.next();
                System.out.println("Wpisz płeć (kot, kotka):");
                String sex = scanner.next();
                insertCat(con, id++, name, sex);
            }
            if ("POKAŻ".equals(com)){
                showAll(con);
            }
            if ("NAJDŹ".equals(com)){

            }
            if ("KONIEC".equals(com)){
                exit = true;
            }
        }
    }
}
