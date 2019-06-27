public class Infotext

{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Eingabefeld eingabeA;
    private Taste A;
    private Zeichnung window;
    /**
     * Konstruktor für Objekte der Klasse Infotext
     */
    public Infotext(Start1 comp)
    {
        window = new Zeichnung();
        window.resize(600, 400);
        window.setDefaultLookAndFeelDecorated(false);
        window.setLocation(400, 400);
        window.setResizable(false);

        window.setAlwaysOnTop(true);
        window.setTitle("no");
        
        
        eingabeA = new Eingabefeld();
        eingabeA.setzeGroesse(580,200);
        eingabeA.setzeAusgabetext("no");
        eingabeA.setReadonly();
        eingabeA.zentrieren();
        
        A = new Taste();
        A.setzeGroesse(80, 50);
        A.setzePosition(260,260);
        A.setzeAusgabetext("OK");
        A.setzeID(4);
        A.setzeLink(comp);
        
        window.setTransferHandler(null);
        window.hide();
    }
    
    public void hide(){
        window.hide();
    }
    
    public void show(String Text, String title){
        eingabeA.setzeAusgabetext(Text);
        window.setTitle(title);
        window.show();
    }
}
