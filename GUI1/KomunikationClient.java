import java.net.*;
import java.io.*;
/**
 * Write a description of class KomunikationClient here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class KomunikationClient
{
    private Socket client;
    private int port;
    private String host;
    /**
     * Constructor for objects of class KomunikationClient
     */
    KomunikationClient (int port)
    {
        this.port = port;
    }
    
    public Liste querry(String command){
        String ans = "";
        Liste list = null;
        try {
            client = new Socket(host,port);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(command);
            
            DataInputStream input = new DataInputStream(client.getInputStream());
            ans = input.readUTF();
            //System.out.println(ans);
            if(ans.equals("~Start~")){
                list = new Liste();
            }
            while(true){
                ans = input.readUTF();
                //System.out.println(ans);
                if(ans.equals("~End~")){
                    break;
                }
                Datenelement e = new Datenelement(ans);
                list.einfügen(e);
            }
            client.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public String send(String data){
        String ans = "";
        try{
            client = new Socket(host,port);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(data);
                
            DataInputStream input = new DataInputStream(client.getInputStream());
            ans = input.readUTF();
        }catch(IOException e){
            e.printStackTrace();
            ans = "ERROR "+ e.getClass().getName() + ": " + e.getMessage();
        }
        return ans;
    }
}
