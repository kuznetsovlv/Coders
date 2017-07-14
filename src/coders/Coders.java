package coders;

import CoderLib.*;
import java.io.File;

/**
 *
 * @author leonid
 */
public class Coders {  
    public static void main(String[] args) {        
        try {            
            File file = new File(args[0]);
        } catch(Exception e) {
            byte[] byteList = {-128, 1, 1, 0, -1};
            System.out.println(CoderFactory.get(CoderFactory.CoderTypes.BASE64).code(byteList));
             
            System.err.println("e");
            System.exit(1);
        }
    }
}