import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Hello World!");
        try {
            //serversocket
            ServerSocket server = new ServerSocket(12345);
            Socket socket = server.accept();
            //get input
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuffer sb = new StringBuffer();
            while((len = inputStream.read(bytes))!=-1){
                sb.append(new String(bytes,0,len,"UTF-8"));
            }
            System.out.println(sb);
            //exec command
            OsmOperator osmOperator = new OsmOperator(sb.toString());
            FutureTask<String> future = new FutureTask<String>(osmOperator);
            new Thread(future).start();
            String result = future.get();

            System.out.println(result);
            System.out.println("Hello World!");
            OutputStream socketOut = socket.getOutputStream();
            System.out.println("Hello World!");

            DataOutputStream outstream = new DataOutputStream(socketOut);
            outstream.writeUTF("server ");
            inputStream.close();
            socket.close();
            server.close();
            outstream.close();
            socketOut.close();
//

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
