package coders;

import CoderLib.*;
import java.io.*;

/**
 *
 * @author leonid
 */
public class Coders {  
    public static void main(String[] args) {
        byte[] byteList = {-128, 1, 1, 0, -1};
        
        File file = new File(args[0]);
        try(FileInputStream input = new FileInputStream(file)) {  
            byteList = new byte[(int)file.length()];
            
            input.read(byteList);
        } catch(Exception e) {             
            System.err.println("e");
            System.exit(1);
        } finally {
            System.out.println(CoderFactory.get(CoderFactory.CoderTypes.BASE64).code(byteList));
        }
    }
}