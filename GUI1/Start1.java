import java.util.regex.Pattern;
import java.awt.Color;


public class Start1 implements ITuWas
{
    private String key;
    private KomunikationClient suche;
    private boolean didex;
    private int width;
    private int status;

    // zeichnung
    private Zeichnung window;
    private float scale;
    
    //style
    private String priemereFarbe;
    private String secundereFarbe;
    private String dritteFarbe;
    private String loginFarbe;
    private String loginZweitFarbe;
    private String loginDrittFarbe;
    private Color bgFarbe;

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
    private Taste z;
    private Taste p;

    //register
    private Eingabefeld eingabeB;
    private Bild b;
    private Listbox e;
    private Eingabefeld d;
    private Eingabefeld f;
    private Eingabefeld g;
    private Passwortfeld h;
    private Taste i;
    private Taste y;


    //Login
    private Eingabefeld eingabeC;
    private Listbox eLogin;
    private Eingabefeld gLogin;
    private Passwortfeld hLogin;
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
       priemereFarbe = "weiss";
       secundereFarbe = "blau";
       dritteFarbe = "weiss";
       loginFarbe = "weiss";
       loginZweitFarbe = "blau";
       loginDrittFarbe = "hellgrau";
       bgFarbe = new Color(122,122,122);
       infohandler = new Infotext(this,bgFarbe,priemereFarbe,secundereFarbe);
       window = new Zeichnung();
       window.maximiere();
       width = window.getWidth();
       scale = width/1920;
       System.out.println(scale);
       window.setBackground(bgFarbe.LIGHT_GRAY);
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
        else if (ID >= 300  && ID <= 300 + länge)
        {
            this.zurückgeben(ID - 300); // Die grünen Tasten
        }
        else if (ID == 5)
        {
            this.delReg();
            this.login();
        }else if (ID == 6){
            this.ausleihinfo();
        }else if (ID == 7){
            this.logout();
        }
    }

    public void suche(String titel)
    {
        String st = "";
        status = 1;
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
    
    public void ausleihinfo(){
        String st = "AUSGELIEHEN~~+~~" + key;
        status = 2;
        data = suche.querry(st);
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
            infonachricht("Ausleihinformation", st[i].leseText() + "\n"+"wurde erfolgreich ausgeliehen");
        }else{
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(ans);
            infonachricht("Ausleihinformation", "Fehler: " + data[1] + ".");
        }
        suche("");
    }
    
    public void zurückgeben(int i)
    {
        System.out.println("ZURÜCKGEBEN " + st[i].leseText() + " Mit " + key);
        String info = "ZURÜCKGEBEN~~+~~" + key + "~~+~~" +st[i].leseText();
        String ans = suche.send(info);
        System.out.print(ans);
        if(ans.equals("ERFOLG")){
            infonachricht("Ausleihinformation", st[i].leseText() + "\n"+"wurde erfolgreich zurückgegeben.");
        }else{
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(ans);
            infonachricht("Ausleihinformation", "Fehler: " + data[1] + ".");
        }
        ausleihinfo();
    }

    public void entfernenListe(){

        eingabeA.entfernen();
        tasteRot.entfernen();
        z.entfernen();
        p.entfernen();

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
        scale = (float)window.getWidth()/(float)1920;
        System.out.println(scale);
        
        // Instanzvariable initialisieren
        eingabeA = new Eingabefeld();
        eingabeA.setzePosition((int)(1350 * scale), 100);
        eingabeA.setzeGroesse(400, 50);
        eingabeA.setzeHintergrundfarbe(secundereFarbe);
        eingabeA.setzeSchriftfarbe(priemereFarbe);
        eingabeA.setzeAusgabetext("Film suchen ...");
        eingabeA.setOnClickDelete();

        tasteRot = new Taste();
        tasteRot.setzeHintergrundfarbe(secundereFarbe);
        tasteRot.setzeSchriftfarbe(priemereFarbe);
        tasteRot.setzePosition((int)(1750 * scale), 100);
        tasteRot.setzeAusgabetext("Los!");
        
        z = new Taste();
        if (status == 1){
            z.setzeAusgabetext("Meine Mediathek");
            z.setzeLink(this, 6);
        }else{
            z.setzeAusgabetext("Zurück");
            z.setzeLink(this, 0);
        }
        z.setzeHintergrundfarbe(priemereFarbe);
        z.setzeSchriftfarbe(secundereFarbe);
        z.setzeGroesse(300, 50);
        z.setzePosition((int)(150 * scale), 100);
        
        p = new Taste();
        p.setzeAusgabetext("Logout");
        p.setzeHintergrundfarbe(priemereFarbe);
        p.setzeSchriftfarbe(secundereFarbe);
        p.setzePosition((int)(1350 * scale), 150);
        p.setzeGroesse(400, 30);
        p.setzeID(7);
        p.setzeLink(this);


        rr = new RechteckMitRundenEcken[länge];
        st = new Eingabefeld[länge];
        bt = new Eingabefeld[länge];
        et = new Eingabefeld[länge];
        taste3 = new Taste[länge];
        for(int i = 0;i < länge;i++){
           rr[i] = new RechteckMitRundenEcken(1600,200,50);
           rr[i].setzePosition((int)(150 * scale), 200+i*300);
           rr[i].setzeFarbe(dritteFarbe);
           
           st[i] = new Eingabefeld();
           st[i].setzePosition((int)(200 * scale), 225+i*300);
           st[i].setzeGroesse(500, 40);
           st[i].setzeHintergrundfarbe(priemereFarbe);
           st[i].setReadonly();
           st[i].setzeAusgabetext(l.get(i).getTitle());
           st[i].setzeSchriftgroesse(18);
           st[i].setzeSchriftfarbe(secundereFarbe);
           
           bt[i] = new Eingabefeld();
           bt[i].setzePosition((int)(200 * scale), 275+i*300);
           bt[i].setzeGroesse(500, 30);
           bt[i].setzeHintergrundfarbe(priemereFarbe);
           bt[i].setReadonly();
           bt[i].setzeAusgabetext(l.get(i).Genre + "      " + l.get(i).Länge + " min");
           bt[i].setzeSchriftgroesse(11);
           bt[i].setzeSchriftfarbe(secundereFarbe);
           
           et[i] = new Eingabefeld();
           et[i].setzePosition((int)(200 * scale), 315+i*300);
           et[i].setzeGroesse(500, 30);
           et[i].setzeHintergrundfarbe(priemereFarbe);
           et[i].setReadonly();
           et[i].setzeAusgabetext("FSK : " + l.get(i).Altersbeschränkung + "      " + l.get(i).Erscheinungsjahr);
           et[i].setzeSchriftgroesse(11);
           et[i].setzeSchriftfarbe(secundereFarbe);
           
           taste3[i] = new Taste();
           taste3[i].setzeGroesse(200, 50);
           taste3[i].setzeHintergrundfarbe(secundereFarbe);
           taste3[i].setzeSchriftfarbe(priemereFarbe);
           taste3[i].setzePosition((int)(1520 * scale), 210+i*300);
           if (status == 1){
               taste3[i].setzeAusgabetext("Ausleihen");
               taste3[i].setzeID(200+i);
           }else {
               taste3[i].setzeAusgabetext("Zurückgeben");
               taste3[i].setzeID(300+i);
           }
           taste3[i].setzeLink(this);
        }
        tasteRot.setzeLink(this, 0);
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
        eLogin.setzeHintergrundfarbe(loginDrittFarbe);

        eingabeC = new Eingabefeld();
        eingabeC.setzePosition(mini_box, 250); // 700
        eingabeC.setzeGroesse(400, 50);
        eingabeC.setzeHintergrundfarbe(loginFarbe);
        eingabeC.setzeAusgabetext("Anmeldung");
        eingabeC.zentrieren();
        eingabeC.setReadonly();
        eingabeC.setzeSchriftgroesse(45);

        gLogin = new Eingabefeld();
        gLogin.setzePosition(mini_box, 350);
        gLogin.setzeGroesse(400, 50);
        gLogin.setzeHintergrundfarbe(loginFarbe);
        gLogin.setzeAusgabetext("E-Mail");
        gLogin.setzeSchriftgroesse(40);
        gLogin.setzeSchriftStilKursiv();
        gLogin.setzeSchriftfarbe(loginZweitFarbe);
        gLogin.setOnClickDelete();
        gLogin.setzeLink(this, 7);

        hLogin = new Passwortfeld();
        hLogin.setzePosition(mini_box, 450);
        hLogin.setzeGroesse(400, 50);
        hLogin.setzeHintergrundfarbe(loginFarbe);
        hLogin.setzeAusgabetext("**Kennwort**");
        hLogin.setzeSchriftgroesse(40);
        hLogin.setzeSchriftStilKursiv();
        hLogin.setzeSchriftfarbe(loginZweitFarbe);
        hLogin.setzeLink(this, 2);

        dLogin = new Taste();
        dLogin.setzeAusgabetext("Noch nicht registriert? Hier klicken zum Registrieren");
        dLogin.setzeGroesse(400,40);
        dLogin.setzePosition(mini_box, 550);
        dLogin.setzeHintergrundfarbe(priemereFarbe);
        dLogin.setzeSchriftgroesse(11);
        dLogin.setzeSchriftfarbe(secundereFarbe);
        dLogin.setzeLink(this);
        dLogin.setzeID(3);

        iLogin = new Taste();
        iLogin.setzeAusgabetext("Anmelden");
        iLogin.setzeGroesse(400,50);
        iLogin.setzePosition(mini_box, 680);
        iLogin.setzeHintergrundfarbe(priemereFarbe);
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
        e.setzeHintergrundfarbe(loginDrittFarbe);

        eingabeB = new Eingabefeld();
        eingabeB.setzePosition(mini_box, 250);
        eingabeB.setzeGroesse(400, 50);
        eingabeB.setzeHintergrundfarbe(loginFarbe);
        eingabeB.setzeAusgabetext("Registrierung");
        eingabeB.zentrieren();
        eingabeB.setReadonly();
        eingabeB.setzeSchriftgroesse(45);

        d = new Eingabefeld();
        d.setzePosition(mini_box, 340);
        d.setzeGroesse(400, 50);
        d.setzeHintergrundfarbe(loginFarbe);
        d.setzeAusgabetext("Vorname");
        d.setzeSchriftgroesse(40);
        d.setzeSchriftStilKursiv();
        d.setzeSchriftfarbe(loginZweitFarbe);
        d.setOnClickDelete();

        f = new Eingabefeld();
        f.setzePosition(mini_box, 410);
        f.setzeGroesse(400, 50);
        f.setzeHintergrundfarbe(loginFarbe);
        f.setzeAusgabetext("Nachname");
        f.setzeSchriftgroesse(40);
        f.setzeSchriftStilKursiv();
        f.setzeSchriftfarbe(loginZweitFarbe);
        f.setOnClickDelete();

        g = new Eingabefeld();
        g.setzePosition(mini_box, 480);
        g.setzeGroesse(400, 50);
        g.setzeHintergrundfarbe(loginFarbe);
        g.setzeAusgabetext("E-Mail");
        g.setzeSchriftgroesse(40);
        g.setzeSchriftStilKursiv();
        g.setzeSchriftfarbe(loginZweitFarbe);
        g.setOnClickDelete();

        h = new Passwortfeld();
        h.setzePosition(mini_box, 550);
        h.setzeGroesse(400, 50);
        h.setzeHintergrundfarbe(loginFarbe);
        h.setzeAusgabetext("**Kennwort**");
        h.setzeSchriftgroesse(40);
        h.setzeSchriftStilKursiv();
        h.setzeSchriftfarbe(loginZweitFarbe);
        h.setzeLink(this, 1);

        i = new Taste();
        i.setzeAusgabetext("Registrieren");
        i.setzeGroesse(400,50);
        i.setzePosition(mini_box, 680);
        i.setzeHintergrundfarbe(priemereFarbe);
        i.setzeLink(this);
        i.setzeID(1);
        
        y = new Taste();
        y.setzeAusgabetext("Zurück zur Anmeldung");
        y.setzeGroesse(400,40);
        y.setzePosition(mini_box,780);
        y.setzeHintergrundfarbe(priemereFarbe);
        y.setzeSchriftgroesse(11);
        y.setzeSchriftfarbe(secundereFarbe);
        y.setzeLink(this);
        y.setzeID(5);
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
        y.entfernen();
    }

    public void reg()
    {
        key = suche.send("REGISTER~~+~~" + d.leseText()+"~~+~~"+f.leseText()+"~~+~~"+h.leseText()+"~~+~~"+g.leseText());
        System.out.println("Reg Key " + key);
        if(key.startsWith("ERROR") || key.equals("")){
            System.out.println(key);
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(key);
            infonachricht("Registrationsinformation", "Fehler: " + data[1] + ".");
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
            infonachricht("Logininformation", "Fehler: " + data[1] + ".");
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
    
    public void logout(){
        String l = "LOGOUT~~+~~"+ key;
        String ans = suche.send(l);
        if(ans.startsWith("ERFOLG")){
            entfernenListe();
            login();
            key = "";
        }else{
            System.out.println(key);
            Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
            String[] data = pattern.split(key);
            infonachricht("Logoutinformation", "Fehler: " + data[1] + ".");
        }
        
    }
}