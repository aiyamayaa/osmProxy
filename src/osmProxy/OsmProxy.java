package osmProxy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class OsmProxy {

    ServerSocket server = null;
    Socket socket =  null;
    String result = null;
    int port;
    public OsmProxy(){}
    public OsmProxy(int port){
        this.port = port;

    }

    public void execCommand(String command){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            System.out.println("exec command");
            Process  p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = br.readLine())!=null){
                sb.append(line+"\n");
            }
            System.out.println(sb.toString());
            this.result  = sb.toString();
             p.destroy();
              System.out.println("exec command end");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void startServer(){
        try {
            server = new ServerSocket(port);
            Socket socket = null;
            BufferedReader br = null;
            while(true){
                System.out.println("server start");
                socket = server.accept();

                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String info = null;
                StringBuilder sb = new StringBuilder();
                while ((info= br.readLine())!=null) {
                    sb.append(info);
                }
                br.close();
                System.out.println(sb.toString());
                Thread t = new Thread(()->this.execCommand(sb.toString()));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("server return result"+this.result);

       //         socket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
/**
 * test
 * */
    public static void main(String[] args){
        OsmProxy op = new OsmProxy(23456);
        Thread t =  new Thread(()->{
            op.startServer();
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
