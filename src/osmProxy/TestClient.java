package osmProxy;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * this class simulates the controller
 * */
public class TestClient {
    int port = 23456;
    String ip = "127.0.0.1";
    Socket socket = null;
    public TestClient(){}
    public TestClient(String ip,int port){
        this.ip = ip;
        this.port = port;
    }
    public void getRequest(String request){
        try {
            socket = new Socket(ip,port);
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes());
            out.flush();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] rags){
        TestClient tc = new TestClient("127.0.0.1",23456);
        Thread t = new Thread(()->{
            tc.getRequest("osm vnfd-list");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tc.getRequest("osm nsd-list");
        });
        t.start();
    }
}
