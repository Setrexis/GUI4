import java.sql.*;
/**
 * Abstrakte Klasse SqlCreate - beschreiben Sie hier die Klasse
 * 
 * @author (Ihr Name)
 * @version (eine Version-Nummer oder ein Datum)
 */
public class SqlCreate
{
    public static ResultSet querry(String abfrage){
        ResultSet rs = null;
        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:Filme.db");
    
            c.setAutoCommit(false);
    
            System.out.println("Opened database successfully");
    
            Statement stmt = c.createStatement();
    
            rs = stmt.executeQuery(abfrage);
    
            stmt.close();
    
            c.close();
        }catch(ClassNotFoundException | SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return rs;
    }
}
