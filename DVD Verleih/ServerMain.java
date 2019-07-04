import java.util.regex.Pattern;
import java.util.UUID;
import java.util.*;
import java.util.Arrays;
import java.sql.*;


class ServerMain
{
    private KomunikationServer con;
    private SQLAbfrage sql;
    private DatenEinf sqlE;
    private HashMap<String,String[]> authIDKeyPair;
    private java.util.Date time;
    
    /**
     * Constructor for objects of class ServerMain
     */
    ServerMain ()
    {
        con = new KomunikationServer(2526);
        sql = new SQLAbfrage();
        sqlE = new DatenEinf();
        authIDKeyPair = new HashMap<String,String[]>();
        time  = new java.util.Date();
        /*
         * // http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
        MultiThreadedServer server = new MultiThreadedServer(9000);
        new Thread(server).start();
        
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
        */
    }
    
    public void run(){
        // Optional Multi-Thrading
        while(1==1){
            recv();
        }
    }
    
    public void recv(){
        
        String q = con.recv();
        // Decode data
        Pattern pattern = Pattern.compile(Pattern.quote("~~+~~"));
        String[] data = pattern.split(q);
        for(String a : data){
            System.out.print(a + " ");
        }
        System.out.println("");
        if (q.startsWith("QUERY")){
            String st = "";
            System.out.println(data[0]);
            if(data[1].equals("*")){
                st = "SELECT * FROM Filme WHERE Ausgeliehen = 0";
            }else{
                st = "SELECT * FROM Filme WHERE Ausgeliehen = 0 and (Titel = '" + data[1]+ "' or Genre ='" + data[1]+ "' or Erscheinungsjahr='"+ data[1]+ "' or Länge='"+ data[1]+ "')";
            }
            Liste l = sql.SQLAbfrage(st);
            send(l);
        }else if (q.startsWith("LOGIN")){
            byte[] salt =  null;
            byte[] pas = null;
            int i = -1;
            
            // Überprüfen ob benutzername und passwort richtig sind und dann einen AusleihID Sting zurückschicken.
            // index 1 email 2 passwort
            
            // Soll die ID das passwort und den salt returnen als Object Array
            Info dt;
            try{
                dt = sql.LoginInfo(data[1]);
            }catch(Exception e){
                send("ERROR~~+~~" + e);
                return;
            }
            
            i = dt.id;
            pas = dt.passwort;
            salt = dt.salt;
           
            if (i == -1 || !Arrays.equals(Hash.HashPasswort(data[2], salt), pas)){
                send("ERROR~~+~~Falsches Passwort/Email!");
            }else{
                String key = key();
                String[] a = new String[2];
                a[0] = String.valueOf(i);
                a[1] = stempel();
                authIDKeyPair.put(key,a);
                
                send(key);
            }
        }else if (q.startsWith("REGISTER")){
            // Regestrieren und dann einen AusleihID Sting zurückschicken.
            
            //Überprüfung ob der benutzer schon existiert
            
            byte[] salt = Hash.getSalt();
            byte[] pas = Hash.HashPasswort(data[3], salt);
            int i = -1;
            
            try{
                sqlE.registrieren(data[1], data[2], 0, data[1] + " " + data[2], pas, data[4], "", salt);
                i = sql.LoginAbfrage(data[4], data[3]); 
            }catch(Exception e){
                send("ERROR~~+~~" + e);
                return;
            }
            
            
            String key = key();
            String[] a = new String[2];
            a[0] = String.valueOf(i);
            a[1] = stempel();
            authIDKeyPair.put(key,a);
            
            send(key);
        }else if (q.startsWith("AUSLEIHEN")){
            String key = data[1];
            try{
                String[] info = authIDKeyPair.get(key);
                
                if(abgelaufen(info[1]) == false){
                    if(sql.PrüfenObAusgeliehen(data[2])){
                        send("ERROR~~+~~wurde leider schon ausgeliehen!");
                    }else{
                        sqlE.FilmAusleihen(data[2], Integer.parseInt(info[0]));
                        send("ERFOLG");
                    }
                }else {
                    //Error
                    authIDKeyPair.remove(key);
                    send("ERROR~~+~~Login abgelaufen");
                }
            }catch(Exception e){
                send("ERROR " + e);
            }
        }else if (q.startsWith("ZURÜCKGEBEN")){
            String key = data[1];
            try{
                String[] info = authIDKeyPair.get(key);
                
                if(abgelaufen(info[1]) == false){
                    if(sql.PrüfenObAusgeliehen(data[2])){
                        sqlE.FilmZurückgeben(data[2]);
                        send("ERFOLG");
                    }else{
                        send("ERROR~~+~~Du hast den Film nicht!");
                    }
                }else {
                    //Error
                    authIDKeyPair.remove(key);
                    send("ERROR~~+~~Login abgelaufen!");
                }
            }catch(Exception e){
                send("ERROR " + e);
            }
        }else if (q.startsWith("AUSGELIEHEN")){
            String key = data[1];
            try{
                String[] info = authIDKeyPair.get(key);
                
                if(abgelaufen(info[1]) == false){
                    Liste l = sql.AusgelieheneFilmeAnzeigen(Integer.parseInt(info[0]));
                    send(l);
                }else {
                    //Error
                    authIDKeyPair.remove(key);
                    send("ERROR~~+~~Login abgelaufen!");
                }
            }catch(Exception e){
                send("ERROR " + e);
            }
        }else if(q.startsWith("LOGOUT")){
            String key = data[1];
            try{
                authIDKeyPair.remove(key);
                send("ERFOLG");
            }catch(Exception e){
                send("ERROR " + e);
            }
        }else{
            send("ERROR~~+~~1");
        }
    }
    
    public void send(Liste s){
        con.send(s);
    }
    
    public void send(String s){
        con.sendString(s);
    }
    
    public String key(){
        // pseudo random Code : https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public boolean abgelaufen(String timeStamp){
        return time.getTime() > Long.valueOf(timeStamp);
    }
    
    public String stempel(){
        double d = 7.2e+6;
        long i = time.getTime() + (long)d;
        return Long.toString(i);
    }
    
    public boolean test(){
        return abgelaufen(stempel());
    }
}
