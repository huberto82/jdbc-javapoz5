import java.sql.*;

public class MetaDataDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection con = ConnectDemo.getConnection();
        ConnectDemo.prepareBase(con);
        PreparedStatement stat = con.prepareStatement("SELECT * FROM PERSON");
        ResultSetMetaData meta = stat.getMetaData();
        System.out.println("Kolumny");
        for(int i=0; i< meta.getColumnCount(); i++){
            System.out.print(meta.getColumnName(i+1)+ " ");
            System.out.print(meta.getColumnTypeName(i+1)+" ");
            System.out.print(meta.getColumnType(i+1)+" ");
            System.out.println(meta.getColumnClassName(i+1));
        }
        System.out.println("Skrolowanie ResultSet");
        System.out.println(stat.getResultSetType() == ResultSet.TYPE_FORWARD_ONLY);
    }
}
