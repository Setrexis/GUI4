

public class Infotext implements ITuWas

{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Eingabefeld eingabeA;
    private Taste A;
    private Zeichnung window;
    /**
     * Konstruktor für Objekte der Klasse Infotext
     */
    public Infotext(String Text, String title)
    {
        window = new Zeichnung();
        window.resize(600, 400);
        window.show();
        window.setAlwaysOnTop(true);
        window.setTitle(title);
        
        eingabeA = new Eingabefeld();
        eingabeA.setzeGroesse(580,200);
        eingabeA.setzeAusgabetext(Text);
        eingabeA.setReadonly();
        eingabeA.zentrieren();
        
        A = new Taste();
        A.setzeGroesse(80, 50);
        A.setzePosition(260,260);
        A.setzeAusgabetext("OK");
       
    }

    public void tuWas(int ID) { // Diese Methode wird von den Tasten aufgerufen        
        
    }
}
