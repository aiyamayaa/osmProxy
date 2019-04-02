import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class client  {
    public static void main(String args[]){
        try {
            System.out.println("Hello World!");
            Socket socket = new Socket("10.112.197.221",12345);
            OutputStream outputStream = socket.getOutputStream();
            String message = "osm vnfd-list";
            socket.getOutputStream().write(message.getBytes("UTF-8"));

//            InputStream inputStream = socket.getInputStream();
//            DataInputStream din = new DataInputStream(inputStream);
//            String back = din.readUTF();
//            System.out.println(back);

            outputStream.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
