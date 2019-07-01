import java.util.regex.Pattern;

public class Start1 implements ITuWas
{
    private String key;
    private KomunikationClient suche;
    private boolean didex;
    private int width;

    // zeichnung
    private Zeichnung window;

    // Info
    private Infotext infohandler;

    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Taste tasteRot;
    private Taste tasteGruen;
    private Taste[] taste3;
    private Eingabefeld eingabeA;
    private Schieberegler test;
    private Listbox test2;
    private RechteckMitRundenEcken Objekt;
    private RechteckMitRundenEcken[] rr;
    private Eingabefeld[] st;
    private Eingabefeld[] bt;
    private Eingabefeld[] et;
    private Liste data;
    private int länge;

    //register
    private Eingabefeld eingabeB;
    private Bild b;
    private Listbox e;
    private Eingabefeld d;
    private Eingabefeld f;
    private Eingabefeld g;
    private Eingabefeld h;
    private Taste i;


    //Login
    private Eingabefeld eingabeC;
    private Listbox eLogin;
    private Eingabefeld gLogin;
    private Eingabefeld hLogin;
    private Taste dLogin;
    private Taste iLogin;

    /**
     * Konstruktor für Objekte der Klasse Start1
     */
    public Start1()
    {
       suche = new KomunikationClient(2526);
       key = "";
       didex = false;
       infohandler = new Infotext(this);
       window = new Zeichnung();
       window.maximiere();
       width = window.WIDTH;
       login();
    }

    public void tuWas(int ID) { // Diese Methode wird von den Tasten aufgerufen

        if (ID == 0)
        {
            this.suche(eingabeA.leseText()); // Die Taste rot
        }
        else if(ID == 1){
            this.reg();
        }
        else if(ID == 2){
            this.logn();
        }
        else if (ID == 3){
            this.delLogin();
            this.registrieren();
        }else if (ID == 4){
            if(infohandler == null){

            }else{
                infohandler.hide();
            }
        }
        else if (ID >= 200  && ID <= 200 + länge)
        {
            this.ausleihen(ID - 200); // Die grünen Tasten
        }
    }

    public void suche(String titel)
    {
        String st = "";

        if(titel.equals("") || titel.equals("Film suchen ...")){
            st = "QUERY~~+~~*";
        }else{
            st = "QUERY~~+~~"+ titel;
        }
        data = suche.querry(st);
        System.out.println(data);
        if(didex){
            entfernenListe();
        }
        displayListe(data);
    }

    public void ausleihen(int i)
    {
        System.out.println("Ausleihen " + st[i].leseText() + " Mit " + key);
        String info = "AUSLEIHEN~~+~~" + key + "~~+~~" +st[i].leseText();
        String ans = suche.send(info);
        System.out.print(ans);
        if(ans.equals("ERFOLG")){
            infonachricht("ERFOLG!", st[i].leseText() + "\n"+"wurde Efolgreich ausgeliehen");
        }else{
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(ans);
            infonachricht("Fehler!", "Fehler " + data[1]);
        }
        suche("");
    }

    public void entfernenListe(){

        eingabeA.entfernen();
        tasteRot.entfernen();

        for(int i = 0;i < länge;i++){
            rr[i].entfernen();
            st[i].entfernen();
            bt[i].entfernen();
            et[i].entfernen();
            taste3[i].entfernen();
        }
        didex = false;
    }

    public void displayListe(Liste l){
        window.setzeScrollbar(true);
        didex = true;
        länge = l.length();
        
        // Instanzvariable initialisieren
        eingabeA = new Eingabefeld();
        eingabeA.setzePosition(1350, 100);
        eingabeA.setzeGroesse(400, 50);
        eingabeA.setzeHintergrundfarbe("gruen");
        eingabeA.setzeAusgabetext("Film suchen ...");
        //eingabeA.uuu

        tasteRot = new Taste();
        tasteRot.setzeHintergrundfarbe("gruen");
        tasteRot.setzePosition(1750, 100);
        tasteRot.setzeAusgabetext("Los!");


        rr = new RechteckMitRundenEcken[länge];
        for(int i = 0;i < länge;i++){
           rr[i] = new RechteckMitRundenEcken(1600,200,50);
           rr[i].setzePosition(150, 200+i*300);
           rr[i].setzeFarbe("weiss");
        }

        st = new Eingabefeld[länge];
        for(int i = 0;i < länge;i++){
           st[i] = new Eingabefeld();
           st[i].setzePosition(200, 225+i*300);
           st[i].setzeGroesse(500, 40);
           st[i].setzeHintergrundfarbe("gruen");
           st[i].setReadonly();
           st[i].setzeAusgabetext(l.get(i).getTitle());
           st[i].setzeSchriftgroesse(18);
        }

        bt = new Eingabefeld[länge];
        for(int i = 0;i < länge;i++){
           bt[i] = new Eingabefeld();
           bt[i].setzePosition(200, 275+i*300);
           bt[i].setzeGroesse(500, 30);
           bt[i].setzeHintergrundfarbe("gruen");
           bt[i].setReadonly();
           bt[i].setzeAusgabetext(l.get(i).Genre + "      " + l.get(i).Länge + " min");
           bt[i].setzeSchriftgroesse(11);
        }

        et = new Eingabefeld[länge];
        for(int i = 0;i < länge;i++){
           et[i] = new Eingabefeld();
           et[i].setzePosition(200, 315+i*300);
           et[i].setzeGroesse(500, 30);
           et[i].setzeHintergrundfarbe("gruen");
           et[i].setReadonly();
           et[i].setzeAusgabetext("FSK : " + l.get(i).Altersbeschränkung + "      " + l.get(i).Erscheinungsjahr);
           et[i].setzeSchriftgroesse(11);
        }

        taste3 = new Taste[länge];


        for(int i = 0; i<länge;i++)
        {
            taste3[i] = new Taste();
            taste3[i].setzeGroesse(200, 50);
            taste3[i].setzeHintergrundfarbe("gelb");
            taste3[i].setzePosition(1520, 210+i*300);
            taste3[i].setzeAusgabetext("Ausleihen");
            taste3[i].setzeID(200+i);
            taste3[i].setzeLink(this);
        }

        tasteRot.setzeLink(this);

        // Die Taste Rot meldet sich mit der ID 0
        tasteRot.setzeID(0);

    }

    public void login(){
        window.setzeScrollbar(false);
        b = new Bild("back.png" );
        b.setzeGroesse(4000,3800);
        
        width = window.getWidth() / 2;
        int box = width - 250;
        int mini_box = box + 50;

        eLogin = new Listbox();
        eLogin.setzeGroesse(500, 650); 
        eLogin.setzePosition(box,200); // 650
        eLogin.setzeHintergrundfarbe("weiss");

        eingabeC = new Eingabefeld();
        eingabeC.setzePosition(mini_box, 250); // 700
        eingabeC.setzeGroesse(400, 50);
        eingabeC.setzeHintergrundfarbe("orange");
        eingabeC.setzeAusgabetext("Anmeldung");
        eingabeC.zentrieren();
        eingabeC.setReadonly();
        eingabeC.setzeSchriftgroesse(45);

        gLogin = new Eingabefeld();
        gLogin.setzePosition(mini_box, 350);
        gLogin.setzeGroesse(400, 50);
        gLogin.setzeHintergrundfarbe("orange");
        gLogin.setzeAusgabetext("E-Mail");
        gLogin.setzeSchriftgroesse(40);
        gLogin.setzeSchriftStilKursiv();
        gLogin.setzeSchriftfarbe("grau");

        hLogin = new Eingabefeld();
        hLogin.setzePosition(mini_box, 450);
        hLogin.setzeGroesse(400, 50);
        hLogin.setzeHintergrundfarbe("orange");
        hLogin.setzeAusgabetext("**Kennwort**");
        hLogin.setzeSchriftgroesse(40);
        hLogin.setzeSchriftStilKursiv();
        hLogin.setzeSchriftfarbe("grau");

        dLogin = new Taste();
        dLogin.setzeAusgabetext("Noch nicht registriert? Hier klicken zum Registrieren");
        dLogin.setzeGroesse(400,40);
        dLogin.setzePosition(mini_box, 550);
        dLogin.setzeHintergrundfarbe("weiss");
        dLogin.setzeSchriftgroesse(11);
        dLogin.setzeSchriftfarbe("blau");
        dLogin.setzeLink(this);
        dLogin.setzeID(3);

        iLogin = new Taste();
        iLogin.setzeAusgabetext("Anmelden");
        iLogin.setzeGroesse(400,50);
        iLogin.setzePosition(mini_box, 680);
        iLogin.setzeHintergrundfarbe("weiss");
        iLogin.setzeID(2);
        iLogin.setzeLink(this);
    }

    public void delLogin(){
        b.entfernen();
        eLogin.entfernen();
        eingabeC.entfernen();
        gLogin.entfernen();
        hLogin.entfernen();
        dLogin.entfernen();
        iLogin.entfernen();
    }

    public void registrieren(){
        window.setzeScrollbar(false);
        b = new Bild("back.png" );
        b.setzeGroesse(4000,3800);
        
        width = window.getWidth() / 2;
        int box = width - 250;
        int mini_box = box + 50;

        e = new Listbox();
        e.setzeGroesse(500, 650);
        e.setzePosition(box,200);
        e.setzeHintergrundfarbe("weiss");

        eingabeB = new Eingabefeld();
        eingabeB.setzePosition(mini_box, 250);
        eingabeB.setzeGroesse(400, 50);
        eingabeB.setzeHintergrundfarbe("orange");
        eingabeB.setzeAusgabetext("Registrierung");
        eingabeB.zentrieren();
        eingabeB.setReadonly();
        eingabeB.setzeSchriftgroesse(45);

        d = new Eingabefeld();
        d.setzePosition(mini_box, 340);
        d.setzeGroesse(400, 50);
        d.setzeHintergrundfarbe("orange");
        d.setzeAusgabetext("Vorname");
        d.setzeSchriftgroesse(40);
        d.setzeSchriftStilKursiv();
        d.setzeSchriftfarbe("grau");

        f = new Eingabefeld();
        f.setzePosition(mini_box, 410);
        f.setzeGroesse(400, 50);
        f.setzeHintergrundfarbe("orange");
        f.setzeAusgabetext("Nachname");
        f.setzeSchriftgroesse(40);
        f.setzeSchriftStilKursiv();
        f.setzeSchriftfarbe("grau");

        g = new Eingabefeld();
        g.setzePosition(mini_box, 480);
        g.setzeGroesse(400, 50);
        g.setzeHintergrundfarbe("orange");
        g.setzeAusgabetext("E-Mail");
        g.setzeSchriftgroesse(40);
        g.setzeSchriftStilKursiv();
        g.setzeSchriftfarbe("grau");

        h = new Eingabefeld();
        h.setzePosition(mini_box, 550);
        h.setzeGroesse(400, 50);
        h.setzeHintergrundfarbe("orange");
        h.setzeAusgabetext("**Kennwort**");
        h.setzeSchriftgroesse(40);
        h.setzeSchriftStilKursiv();
        h.setzeSchriftfarbe("grau");

        i = new Taste();
        i.setzeAusgabetext("Registrieren");
        i.setzeGroesse(400,50);
        i.setzePosition(mini_box, 680);
        i.setzeHintergrundfarbe("weiss");
        i.setzeLink(this);
        i.setzeID(1);
    }

    public void delReg(){
        b.entfernen();
        e.entfernen();
        eingabeB.entfernen();
        d.entfernen();
        f.entfernen();
        g.entfernen();
        h.entfernen();
        i.entfernen();
    }

    public void reg()
    {
        key = suche.send("REGISTER~~+~~" + d.leseText()+"~~+~~"+f.leseText()+"~~+~~"+h.leseText()+"~~+~~"+g.leseText());
        System.out.println("Reg Key " + key);
        if(key.startsWith("ERROR") || key.equals("")){
            System.out.println(key);
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(key);
            infonachricht("Fehler!", "Fehler " + data[1]);
            key = "";
        }else{
            System.out.println("Del reg");
            delReg();
            System.out.println("Fertig");
            suche("");
        }
    }

    public void logn(){
        key  = suche.send("LOGIN~~+~~" + gLogin.leseText()+ "~~+~~"+ hLogin.leseText());
        System.out.println("Login Key " + key);
        if(key.startsWith("ERROR") || key.equals("")){
            System.out.println(key);
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(key);
            infonachricht("Fehler!", "Fehler " + data[1]);
            key = "";
        }else{
            System.out.println("login löschen");
            delLogin();
            System.out.println("Fertig");
            suche("");
        }
    }

    public void infonachricht(String titel, String nachricht){
        infohandler.show(nachricht,titel);
    }
}