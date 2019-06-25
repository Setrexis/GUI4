import java.sql.*;
/*
 * Wir benötigen alle SQL Klassen von der Java Bibliothek, um auf die SQLite
 * Datei zuzugreifen.
 * Die Klassen hat der Hersteller von Java, Oracle bzw. Sun für
 * uns programmiert.
 */
/**
 * Beschreiben Sie hier die Klasse SQLAbfrage.
 * Eine SQL-Abfrage wird an die SQLite Datenbank test.db gesendet und das
 * Ergebnis ausgegeben
 * https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 *
 */
public class SQLAbfrage
{
    /**
     * Wir benötigen eine Verbindung c zur SQLite Datenbankdatei, über die
     * wir kommunizieren, d.h. Daten hin und her senden. Bei uns ist die
     * Datenbankdatei im selben Ordner, wie das BlueJProjekt
     */
    private Connection c;
    /**
     * Unsere SQL-Abfragen, SELECT * FROM COMPANY; ,ist ein String. Java muss
     * den String in eine Abfrage umwandeln, die SQLite versteht und abarbeiten
     * kann. Hierfür gibt das Objekt stmt der Klasse Statement.
     */
    private Statement stmt;
    /**
     * Unsere SQL-Abfragen liefert eine Ergebnistabelle als Antwort zurück.
     * Java muss die Ergebnistabelle in eine ResultSet umwandeln. Anschließend
     * können wir mit Java jede Zeile einzeln auslesen, bestimmte Daten herausholen
     * und danach zur nächsten Zeile springen.
     */
    private ResultSet rs;

    private Liste list;

    public Liste SQLAbfrage(String abfrage) {
        c = null;
        stmt = null;
        list = new Liste();
        try {
            /**
             * Java sucht den aktuellen SQLiteTreiber für den Zugriff auf die
             * Datenbankdatei. Bei BlueJ ist das die Datei sqlite-jdbc...
             * Die muss immer im Ordner +libs in unserem BlueJ Projekt sein.
             * Den aktuellen Treiber gibt es kostenlos unter
             * https://bitbucket.org/xerial/sqlite-jdbc/downloads/
             */
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Filme.db");
            /**
             * Stelle Auto Commit aus. Eine Erklärung zu SQL Transaktionen
             * erfolgt in einigen Stunden.
             */
            c.setAutoCommit(false);
            /**
             * Tritt bisher kein Fehler auf, konnte die Datenbankdatei
             * geöffnet werden.
             */
            System.out.println("Opened database successfully");
            /**
             * Erstelle eine neue SQL-Abfrage-Anweisung, die Java und SQLite
             * verstehen.
             */
            stmt = c.createStatement();
            /**
             * Rufe die Methode AbfrageA auf, führe sie aus und zeige das
             * Ergebnis dem Benutzer auf der Konsole an. Das ist der einfachste
             * Fall.
             */
            this.AbfrageA(abfrage);

            /**
             * Beende die SQL-Abfrage.
             */
            stmt.close();

            /**
             * Schließe die Verbindung zur SQLite Datenbankdatei.
             */
            c.close();

        } catch ( Exception e )
        {
            /**
             * Treten Fehler auf, werden die Fehler gefangen, angezeigt
             */
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            /**
             * Java beendet unser Programm mit gewalt und schont den PC.
             */
            System.exit(0);
        }

        /**
         * Falls alles ohne Fehler klappt, informiere den Benutzer auf
         * der Konsole
         */
        System.out.println("Operation done successfully");
        return list;
    }

    private void AbfrageA(String abfrage)
    {
        try
        {
            /**
             * Sende die SQL-Abfrage an die Datenbank und merke die die
             * Ergebnistabelle mit allen Datensätzen.
             */
            rs = stmt.executeQuery(abfrage);

            /**
             * Wir gehen Zeile für Zeile von oben nach unten durch die Ergebnis-
             * tabelle
             */
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

                /*
                System.out.println( "Titel = " +Titel);
                System.out.println( "Erscheinungsjahr = " +Erscheinungsjahr);
                System.out.println( "Länge = " +Länge);
                System.out.println( "Altersbeschränkung = "+Altersbeschränkung);
                System.out.println( "Genre = " +Genre);
                System.out.println( "ID = " +ID);
                System.out.println( "Ausgeliehen = " +Ausgeliehen);
                System.out.println( "VerliehenAn = " +VerliehenAn);
                System.out.println();
                */
            }

            /**
             * Gibt es keine weitere Zeile mehr in unserer Ergebnistabelle,
             * wird die SQL-Abfrage geschlossen. Danach kann man nicht mehr
             * auf die Daten der Ergebistablle zugreifen. Der Speicherplatz
             * wird freigegeben.
             */
            rs.close();
        }
        catch ( Exception e ) {
            /**
             * Treten Fehler auf, werden die Fehler gefangen, angezeigt und
             * unsere Programm mit Zwang beendet.
             */
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    private void Abfrage1Spalte(String abfrage)
    {
        try
        {
            /**
             * Sende die SQL-Abfrage an die Datenbank und merke die die
             * Ergebnistabelle mit allen Datensätzen.
             */
            rs = stmt.executeQuery(abfrage);

            /**
             * Wir gehen Zeile für Zeile von oben nach unten durch die Ergebnis-
             * tabelle
             */
            while ( rs.next() ) {
                String Wert = rs.getString(1);





                System.out.println( "Wert = " +Wert);
            }

            /**
             * Gibt es keine weitere Zeile mehr in unserer Ergebnistabelle,
             * wird die SQL-Abfrage geschlossen. Danach kann man nicht mehr
             * auf die Daten der Ergebistablle zugreifen. Der Speicherplatz
             * wird freigegeben.
             */
            rs.close();
        }
        catch ( Exception e ) {
            /**
             * Treten Fehler auf, werden die Fehler gefangen, angezeigt und
             * unsere Programm mit Zwang beendet.
             */
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public Info LoginInfo(String email){
        SqlCreate c = new SqlCreate();

        int id = -1;
        byte[] pas = null;
        byte[] salt = null;

        String sql = "SELECT KundenID, Passwort, salt FROM Benutzer WHERE Email='" + email + "';";
        rs = c.querry(sql);

        try{
            id = rs.getInt("KundenID");
            pas = rs.getBytes("Passwort");
            salt = rs.getBytes("salt");
            c.close()
            rs.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        Info s = new Info(id,pas,salt);
        return s;
    }

    public int LoginAbfrage(String mail, String password){
        int id = -1;
        rs = SqlCreate.querry("SELECT KundenID FROM Benutzer WHERE Email='" + mail + "' and Passwort = '" + password + "';");
        try
        {
            while(rs.next()){
                id = rs.getInt("KundenID");
            }
            rs.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return id;
    }

    public boolean PrüfenObAusgeliehen(String Filmtitel) {
        Connection c = null;
        Statement stmt = null;
        boolean a = true;
        try{

            String sql = "SELECT Verliehen FROM Filme WHERE Filmtitel ='" + Filmtitel + "'";
            rs = SqlCreate.querry(sql);
            a = rs.getBoolean("Verliehen");
            rs.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return a;
    }
}
