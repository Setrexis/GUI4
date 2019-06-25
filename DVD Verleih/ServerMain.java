import java.util.regex.Pattern;
import java.util.UUID;
import java.util.*;
import java.util.Arrays;
/**
 * Write a description of class ServerMain here.
 * 
 * @author (No Name) 
 * @version (-1)
 */
class ServerMain
{
    private KomunikationServer con;
    private SQLAbfrage sql;
    private DatenEinf sqlE;
    private HashMap<String,String[]> authIDKeyPair;
    private Date time;
    
    /**
     * Constructor for objects of class ServerMain
     */
    ServerMain ()
    {
        con = new KomunikationServer(2329);
        sql = new SQLAbfrage();
        sqlE = new DatenEinf();
        authIDKeyPair = new HashMap<String,String[]>();
        time  = new Date();
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
            System.out.println(a);
        }
        if (q.startsWith("QUERY")){
            String st = "";
            System.out.println(data[0]);
            if(data[1].equals("*")){
                st = "SELECT * FROM Filme WHERE Ausgeliehen = 0";
                System.out.println("Keine Where");
            }else{
                st = "SELECT * FROM Filme WHERE Ausgeliehen = 0 and (Titel = '" + data[1]+ "' or Genre ='" + data[1]+ "' or Erscheinungsjahr='"+ data[1]+ "' or Länge='"+ data[1]+ "')";
                System.out.println("Keine Where23");
            }
            Liste l = sql.SQLAbfrage(st);
            con.send(l);
        }else if (q.startsWith("LOGIN")){
            byte[] salt =  null;
            byte[] pas = null;
            int i = -1;
            
            // Überprüfen ob benutzername und passwort richtig sind und dann einen AusleihID Sting zurückschicken.
            // index 1 email 2 passwort
            
            // Soll die ID das passwort und den salt returnen als Object Array
            Info dt = sql.LoginInfo(data[1]);
            
            i = dt.id;
            pas = dt.passwort;
            salt = dt.salt;
           
            if (i == -1 || !Arrays.equals(Hash.HashPasswort(data[2], salt), pas)){
                send("ERROR falsches passswort/email!");
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
            
            try{
                sqlE.registrieren(data[1], data[2], 0, data[1] + " " + data[2], pas, data[4], "", salt);
            }catch(Exception e){
                send("ERROR " + e);
                return;
            }
            int i = sql.LoginAbfrage(data[4], data[3]); 
            
            String key = key();
            String[] a = new String[2];
            a[0] = String.valueOf(i);
            a[1] = stempel();
            authIDKeyPair.put(key,a);
            
            send(key);
        }else if (q.startsWith("AUSLEIHEN")){
            // AusleihID überprüfen und dann ausleih bestetigung schicken.
            
            
            String key = data[1];
            try{
                String[] info = authIDKeyPair.get(key);
                
                if(abgelaufen(info[1]) == false){
                    sqlE.FilmAusleihen(data[2], Integer.parseInt(info[0]));
                    send("Erfolg");
                }else {
                    //Error
                    authIDKeyPair.remove(key);
                    send("ERROR" + " Key abgelaufen");
                }
            }catch(Exception e){
                send("ERROR " + e);
            }
        }else{
            send("ERROR1");
            
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
