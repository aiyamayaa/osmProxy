import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Socket socket = null;
    InetAddress clientAddress = null;
    volatile String result = null;
    volatile String command = null;
    public Server(Socket socket,InetAddress clientAddress){
        this.socket = socket;
        this.clientAddress = clientAddress;

    }
    public Server(){

    }
    /*
    * exec Osm command ,return value to String result
    * */
    public  void execOsm(){
        System.out.println("exec OSm start!");
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line+"\n");
            }
            System.out.println(sb.toString());
            this.result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           // System.out.println("Osm result"+this.result);
            System.out.println("exec OSm end!");
        }
    }

    public  void getResult(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            String clientCommand = null;
            StringBuilder sb = new StringBuilder();
            while((clientCommand = br.readLine())!=null){
                sb.append(clientCommand+"\n");
            }
            socket.shutdownInput();
            this.command = sb.toString();
            //System.out.println("get-result: comman:"+this.command);
            Thread execThread =  new Thread(()->this.execOsm());
            execThread.start();
            try {
                execThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          //  System.out.println("get-result: comman:"+this.command);
            System.out.println("get-result: result:"+this.result);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if(br!=null){
                    br.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("get-result: finally result"+this.result);
            System.out.println("get-Result: end");

        }

    }



    public static void main(String args[]){

        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            Socket socket = serverSocket.accept();
            InetAddress address = socket.getInetAddress();
            Server ser = new Server(socket,address);
            new Thread(()->ser.getResult(),"t1").start();
           // new Thread(()->ser.execOsm(),"t2").start();
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ser.getResult();
//                }
//            });
//            thread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("main stop");
        }


    }

}
