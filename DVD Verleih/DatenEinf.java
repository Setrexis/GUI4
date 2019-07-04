import java.sql.*;
/**
 * Beschreiben Sie hier die Klasse DatenEinf.
 * Eine SQL-Anweisung (Insert) wird an die SQLite Datenbank test.db gesendet 
 * und eine Bestätigung wird auf der Konsole ausgegeben. 
 * https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class DatenEinf
{   
    public void registrieren(String Name, String Vorname, int BenutzerAlter, String Benutzername, byte[] Passwort, String Email, String Lieblingsgenre, byte[] salt) {
        Connection c = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Filme.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            
            String sql = "INSERT INTO Benutzer (Name,Vorname,BenutzerAlter,Benutzername,Passwort,Email,Lieblingsgenre,salt) " +
                "VALUES ( '" + Name +"', '" + Vorname +"', " + BenutzerAlter +", '" + Benutzername +"', ?, '" + Email +"', '" + Lieblingsgenre + "',? );"; 
            stmt = c.prepareStatement(sql);
            System.out.println(sql);    
            stmt.setBytes(1, Passwort);
            stmt.setBytes(2, salt);
            stmt.executeUpdate();
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException("E-mail wird bereits genutzt!");
        }
        System.out.println("Records created successfully");
    }
    public void FilmAusleihen(String Filmtitel, int BenutzerID) {
        Connection c = null;
        Statement stmt = null;
        java.util.Date time = new java.util.Date();
        double d = 1.21e+9;
        long i = time.getTime() + (long)d;
        String Datum = Long.toString(i);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Filme.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE Filme SET Ausgeliehen = true, VerliehenAn = '" + BenutzerID + "', VerliehenBis = '" + Datum +"' WHERE Titel = '" + Filmtitel + "';";
            
            System.out.println(sql);    
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        System.out.println("Records created successfully");
    }
    public void FilmZurückgeben(String Filmtitel) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Filme.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE Filme SET Ausgeliehen = false, VerliehenAn = null, VerliehenBis = null WHERE Titel = '" + Filmtitel + "';";
            
            System.out.println(sql);    
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        System.out.println("Records created successfully");
    }
}
