
/**
 * Write a description of class Liste here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class Liste
{
    private Listenelement erst;
    /**
     * Constructor for objects of class Liste
     */
    Liste ()
    {
        erst = new Abschluss();
    }
    
    public void einfügen(Datenelement e){
        Knoten k = new Knoten(e);
        k.naechsterSetzen(erst);
        erst = k;
        indexSet();
    }
    
    public void indexSet(){
        erst.indexSet(-1);
    }
    
    public int length(){
        return erst.längeGeben();
    }
    
    public Datenelement get(int index){
        if(index >= 0 && index <= length()){
            return erst.get(index);
        }
        //System.err.println("Null");
        return null;
    }
}
