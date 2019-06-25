
/**
 * Write a description of class Abschluss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class Abschluss extends Listenelement
{

    /**
     * Constructor for objects of class Abschluss
     */
    Abschluss ()
    {
        
    }
    public  Listenelement naechsterGeben(){
         return null; 
    }
    public Datenelement inhaltGeben(){
        return null;
    }
    public int längeGeben(){
        return 0;
    }
    public void indexSet(int d){
        System.out.println(d);
    }
    public Datenelement get(int index){
        System.err.println("Index out of range!");
        return null;
    }
}
