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
        System.out.println("Usuwanie wiersza");
        set.beforeFirst();
        while(set.next()){
            String name = set.getString("first_name");
            if (name.equals("ADAM")){
                set.deleteRow();
            }
        }
        set.beforeFirst();
        set = stat.executeQuery();
        System.out.println("BAZA PO USUNIECIU WIERSZA");
        StatementDemo.printResults(set);
    }
}
