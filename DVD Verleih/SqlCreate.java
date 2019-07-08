import java.sql.*;

public class SqlCreate
{
    private Statement stmt;
    private Connection c;
    
    public ResultSet querry(String abfrage){
        ResultSet rs = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Filme.db");

            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            rs = stmt.executeQuery(abfrage);
        }catch(ClassNotFoundException | SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() + " SqlCreate");
        }
        return rs;
    }

    public void close(){
        try{
            stmt.close();
            c.close();
        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
