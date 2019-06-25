import java.net.*;
import java.io.*;
/**
 * Write a description of class KomunikationServer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class KomunikationServer 
{
    private ServerSocket server;
    private int port;
    private String host;
    private Socket client;
    /**
     * Constructor for objects of class KomunikationServer
     */
    KomunikationServer (int port)
    {
        this.port = port;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000000);
        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public String recv(){
        while(true){
            try{
                System.out.println("Wating for client at " + server.getLocalPort());
                client = server.accept();
                DataInputStream input = new DataInputStream(client.getInputStream());
                String st = input.readUTF();
                System.out.println(client.getRemoteSocketAddress());
                System.out.println("Befehl " + st);
                return st;
            }catch(Exception e)
            {
                try{
                    e.printStackTrace();
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());
                    output.writeUTF("Error");
                    client.close(); 
                    client = null;
                    return "Error";
                }catch (IOException ex){
                    ex.printStackTrace();
                    return "Error";
                }
            }
        }
    }
    
    public void sendString(String info){
        try{
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(info);
            client.close();
            client = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void send(Liste info){
        try{
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("~Start~");
            for(int i = 0; i < info.length();i++){
                Datenelement d = info.get(i);
                String data = d.getData();
                output.writeUTF(data);
            }
            output.writeUTF("~End~");
            client.close();
            client = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
