import java.awt.Color;

public class Infotext

{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Eingabefeld eingabeA;
    private Taste A;
    private Zeichnung window;
    /**
     * Konstruktor für Objekte der Klasse Infotext
     */
    public Infotext(Start1 comp, Color bgColor,Color pc, Color sc)
    {
        window = new Zeichnung();
        window.resize(600, 400);
        window.setDefaultLookAndFeelDecorated(false);
        window.setLocation(400, 400);
        window.setResizable(false);

        window.setAlwaysOnTop(true);
        window.setTitle("no");
        window.hintergrundFarbe(Color.WHITE);
        
        
        eingabeA = new Eingabefeld();
        eingabeA.setzeGroesse(580,200);
        eingabeA.setzeAusgabetext("no");
        eingabeA.setReadonly();
        eingabeA.zentrieren();
        eingabeA.setzeHintergrundfarbe(pc);
        eingabeA.setzeSchriftfarbe(sc);
        
        A = new Taste();
        A.setzeGroesse(80, 50);
        A.setzePosition(260,260);
        A.setzeAusgabetext("OK");
        A.setzeID(4);
        A.setzeLink(comp);
        A.setzeHintergrundfarbe(pc);
        A.setzeSchriftfarbe(sc);
        
        window.setTransferHandler(null);
        window.hide();
    }
    
    public void hide(){
        window.hide();
    }
    
    public void show(String Text, String title){
        eingabeA.setzeAusgabetext(Text);
        eingabeA.zentrieren();
        window.setTitle(title);
        window.show();
    }
}
