package osmProxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class reportMessage {
     String ip = "";
     int port = 0;
    public reportMessage(){

    }
    public reportMessage(String ip ,int port){
        this.ip = ip;
        this.port = port;
    }

    public void sendMessage(String message){
        Socket socket = null;
        try {
            socket = new Socket(ip,port);
            OutputStream out = socket.getOutputStream();
            out.write(message.getBytes());
            out.flush();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
