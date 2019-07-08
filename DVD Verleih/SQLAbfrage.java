import java.sql.*;

public class SQLAbfrage
{
    public Liste SQLAbfrage(String abfrage) {
        SqlCreate c = new SqlCreate();
        ResultSet rs = c.querry(abfrage);
        Liste list = new Liste();
        try
        {
            while ( rs.next() ) {
                String Titel = rs.getString("Titel");
                int Erscheinungsjahr = rs.getInt("Erscheinungsjahr");
                int Länge = rs.getInt("Länge");
                int  Altersbeschränkung = rs.getInt("Altersbeschränkung");
                String  Genre = rs.getString("Genre");
                int ID = rs.getInt("ID");
                boolean Ausgeliehen = rs.getBoolean("Ausgeliehen");
                String VerliehenAn = rs.getString("VerliehenAn");

                Datenelement e = new Datenelement(Erscheinungsjahr, Länge, ID, Titel, Altersbeschränkung, Genre, Ausgeliehen, VerliehenAn);

                list.einfügen(e);
            }
            rs.close();
            c.close();
        }
        catch (Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        System.out.println("Operation done successfully");
        return list;
    }

    public Info LoginInfo(String email){
        SqlCreate c = new SqlCreate();

        int id = -1;
        byte[] pas = null;
        byte[] salt = null;

        String sql = "SELECT KundenID, Passwort, salt FROM Benutzer WHERE Email='" + email + "';";
        ResultSet rs = c.querry(sql);

        try{
            id = rs.getInt("KundenID");
            pas = rs.getBytes("Passwort");
            salt = rs.getBytes("salt");
            c.close();
            rs.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }

        Info s = new Info(id,pas,salt);
        return s;
    }
    
    public int LoginAbfrage(String mail, String password){
        SqlCreate c = new SqlCreate();
        int id = -1;
        ResultSet rs = c.querry("SELECT KundenID FROM Benutzer WHERE Email='" + mail + "' and Passwort = '" + password + "';");
        try
        {
            while(rs.next()){
                id = rs.getInt("KundenID");
            }
            c.close();
            rs.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        return id;
    }
    
    public String[] Benutzerabfrage(int id){
        SqlCreate c = new SqlCreate();
        String[] daten = new String[3];
        ResultSet rs = c.querry("SELECT Vorname,Name,Email FROM Benutzer WHERE KundenID=" + id + ";");
        try
        {
            while(rs.next()){
                daten[0] = rs.getString("Vorname");
                daten[1] = rs.getString("Name");
                daten[2] = rs.getString("Email");
            }
            c.close();
            rs.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        return daten;
    }
    
    public boolean PrüfenObAusgeliehen(String Filmtitel) {
        SqlCreate c = new SqlCreate();
        boolean a = true;
        String sql = "SELECT Ausgeliehen FROM Filme WHERE Titel ='" + Filmtitel + "'";
        ResultSet rs = c.querry(sql);
        try{ 
            a = rs.getBoolean("Ausgeliehen");
            c.close();
            rs.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            throw new IllegalArgumentException(e.getMessage());
        }
        return a;
    }
    
    public Liste AusgelieheneFilmeAnzeigen(int BenutzerID) {
        String a = "SELECT* FROM Filme WHERE VerliehenAn = '"+ BenutzerID + "'";
        SqlCreate c = new SqlCreate();
        ResultSet rs = c.querry(a);
        Liste list = new Liste();
        try
        {
            while ( rs.next() ) {
                String Titel = rs.getString("Titel");
                int Erscheinungsjahr = rs.getInt("Erscheinungsjahr");
                int Länge = rs.getInt("Länge");
                int  Altersbeschränkung = rs.getInt("Altersbeschränkung");
                String  Genre = rs.getString("Genre");
                int ID = rs.getInt("ID");
                boolean Ausgeliehen = rs.getBoolean("Ausgeliehen");
                String VerliehenAn = rs.getString("VerliehenAn");

                Datenelement e = new Datenelement(Erscheinungsjahr, Länge, ID, Titel, Altersbeschränkung, Genre, Ausgeliehen, VerliehenAn);

                list.einfügen(e);
            }
            rs.close();
            c.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return list;
    }
}
