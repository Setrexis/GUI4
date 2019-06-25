
/**
 * Beschreiben Sie hier die Klasse Info.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Info
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    public int id;
    public byte[] passwort;
    public byte[] salt;

    /**
     * Konstruktor für Objekte der Klasse Info
     */
    public Info(int id, byte[] passwort, byte[] salt)
    {
        this.id = id;
        this.passwort = passwort;
        this.salt = salt;
    }
}
