import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class OsmOperator implements Callable<String> {
    public BufferedReader br = null;
    String command;

    public OsmOperator(String command){

        this.command = command;
    }
    @Override
    public String call() {
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine())!=null){
                sb.append(line + "\n");

            }
            System.out.println(sb.toString());
            return sb.toString();
        }catch ( Exception e){
            e.printStackTrace();
        }
        finally {
            if(br !=null){
                try{
                    br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
//    public String  exeCmd(String commanStr){
//        BufferedReader br = null ;
//        try {
//            Process p = Runtime.getRuntime().exec(commanStr);
//            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = null;
//            StringBuilder sb = new StringBuilder();
//            while ((line = br.readLine())!=null){
//                sb.append(line + "\n");
//
//            }
//            System.out.println(sb.toString());
//            return sb.toString();
//        }catch ( Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            if(br !=null){
//                try{
//                    br.close();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//
//    }


}
