/**
 * Beschreiben Sie hier die Klasse Knoten.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Knoten extends Listenelement
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Datenelement inhalt;
    private int index;
    private Listenelement naechster;

    /**
     * Konstruktor für Objekte der Klasse Knoten
     */
    public Knoten(Datenelement neu)
    {
        // Instanzvariable initialisieren
        inhalt = neu;
        naechster = null;
        index = -1;
    }

    public Datenelement inhaltGeben()
    {
        return inhalt;
    }
    
    public void inhaltSetzen(Datenelement neuerInhalt)
    {
        inhalt = neuerInhalt;
    }
    
    public Listenelement naechsterGeben()
    {
        return naechster;
    }
    
    public void naechsterSetzen(Listenelement naechsterKnoten)
    {
        naechster = naechsterKnoten;
    }
    
    public int längeGeben(){
        return 1 + naechster.längeGeben();
    }
    
    public void indexSet(int in){
        index = in + 1;
        naechster.indexSet(index);
    }
    
    public Datenelement get(int index){
        if(index == this.index){
            return inhalt;
        }
        return naechster.get(index);
    }
}
