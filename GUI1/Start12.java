
/**
 * Beschreiben Sie hier die Klasse Start1.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Start12 implements ITuWas
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Zeichnung window;
    private Eingabefeld eingabeA;

    /**
     * Konstruktor für Objekte der Klasse Start1
     */
    public Start12()
    {
        window = new Zeichnung();
        window.resize(600, 400);
        window.show();
        window.setAlwaysOnTop(true);
        window.setTitle("Loading");
        
        // Instanzvariable initialisieren
        eingabeA = new Eingabefeld();
        eingabeA.setzePosition(100, 100);
        eingabeA.setzeGroesse(400, 50);
        eingabeA.setzeHintergrundfarbe("gelb");
        eingabeA.setzeAusgabetext("Info Text");
        eingabeA.setReadonly();
        

    }

    public void tuWas(int ID) { // Diese Methode wird von den Tasten aufgerufen        
        if (ID == 0) 
        {
            this.eingabeLoeschen(); // Die Taste rot  
        } 
        else if (ID == 10) 
        {
            this.eingabeAuslesen(); // Die Taste grün
        }
    }

    public void eingabeAuslesen()
    {
        System.out.println("Du heißt " + eingabeA.leseText());
    }

    public void eingabeLoeschen()
    {
        eingabeA.setzeAusgabetext("Dein Name");
        System.out.println("~~Die Eingabe wurde gelöscht.~~");
    }
}
