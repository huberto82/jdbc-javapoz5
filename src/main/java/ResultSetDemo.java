import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection con = ConnectDemo.getConnection();
        ConnectDemo.prepareBase(con);
        PreparedStatement stat = con.prepareStatement(
                "SELECT * FROM PERSON",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        ResultSet set = stat.executeQuery();
        System.out.println("UPDATABLE: " + (stat.getResultSetConcurrency() == ResultSet.CONCUR_UPDATABLE));
        System.out.println("SENSITIVE: " + (stat.getResultSetType() == ResultSet.TYPE_SCROLL_SENSITIVE));
        StatementDemo.printResults(set);
        set.beforeFirst();
        StatementDemo.printResults(set);
        set.beforeFirst();
        System.out.println("Modyfikacja wiersza");
        while(set.next()){
            String name = set.getString("first_name");
            System.out.println("------"+name);
            if (set.wasNull()) {
                System.out.println("UWAGA!!! W BAZIE first_name jest NULL");
            } else {
                if (name.equals("ADAM")) {
                    set.updateString("email", "adam@gmail.com");
                    set.updateRow();
                }
            }
        }
        set.beforeFirst();
        System.out.println("BAZA PO AKTUALIZACJI");
        StatementDemo.printResults(set);

        System.out.println("Usuwanie wiersza");
        set.beforeFirst();
        while(set.next()){
            String name = set.getString("first_name");
            if ("ADAM".equals(name)){
                set.deleteRow();
            }
        }
        set.beforeFirst();
        set = stat.executeQuery();
        System.out.println("BAZA PO USUNIECIU WIERSZA");
        StatementDemo.printResults(set);

        set.beforeFirst();
        System.out.println("Dodanie nowej osoby");
        while(set.next()){
            if (set.isLast()){
                set.moveToInsertRow();
                set.updateString("first_name","KAROL");
                set.updateString("last_name","KOROLCZYK");
                set.updateString("email","karolus@koral.pl");
                set.updateInt("id",5);
                set.insertRow();
            }
        }
        set.beforeFirst();
        set = stat.executeQuery();
        System.out.println("BAZA PO WSTAWIENIU WIERSZA");
        StatementDemo.printResults(set);

    }
}
