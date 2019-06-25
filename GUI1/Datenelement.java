import java.util.regex.Pattern;
/**
 * Write a description of class Datenelement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class Datenelement
{
    public int Erscheinungsjahr;
    public int Länge;
    public int ID;
    public String Title;
    public int Altersbeschränkung;
    public String Genre;
    public boolean Ausgeliehen;
    public String VerliehenAn;
    /**
     * Constructor for objects of class Datenelement
     */
    Datenelement (int Erscheinungsjahr, int Länge, int ID, String Title, int Altersbeschränkung, String Genre, boolean Ausgeliehen, String VerliehenAn)
    {
        this.Erscheinungsjahr = Erscheinungsjahr;
        this.Länge = Länge;
        this.ID = ID;
        this.Title = Title;
        this.Altersbeschränkung = Altersbeschränkung;
        this.Genre = Genre;
        this.Ausgeliehen = Ausgeliehen;
        this.VerliehenAn = VerliehenAn;
    }
    
    Datenelement (String qurry){
        Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
        String[] q = pattern.split(qurry);

        this.Erscheinungsjahr = Integer.parseInt(q[0]);
        this.Länge = Integer.parseInt(q[1]);
        this.ID = Integer.parseInt(q[2]);
        this.Title = q[3];
        this.Altersbeschränkung = Integer.parseInt(q[4]);
        this.Genre = q[5];
        this.Ausgeliehen = Boolean.parseBoolean(q[6]);
        this.VerliehenAn = q[7];
    }
    
    public String getData(){
        return Erscheinungsjahr + "~~+~~" + Länge + "~~+~~" + ID + "~~+~~" + Title + "~~+~~" + Altersbeschränkung + "~~+~~" + Genre + "~~+~~" + Ausgeliehen + "~~+~~" + VerliehenAn;
    }
    
    public String getTitle(){
        return this.Title;
    }
}
