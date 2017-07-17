package coders;

import CoderLib.*;
import java.io.*;

/**
 *
 * @author leonid
 */
public class Coders {
    
    private static void printByteArray (byte[] arr) {
        System.out.printf("[");
        int num = arr.length - 1;
        int i;
        
        for(i = 0; i < arr.length; ++i) {
            System.out.printf("%d", arr[i]);
            
            if (i < num) {
                System.out.printf(", ");
            }
        }
        
        System.out.printf("]\n");
    }
    
    public static void main(String[] args) {
        byte[] byteList = {-128, 1, 1, 0, -1, 67, 2};
        
        try {
            File file = new File(args[0]);
            try(FileInputStream input = new FileInputStream(file)) {  
                byteList = new byte[(int)file.length()];  
                input.read(byteList);
            } catch(Exception e) {
                throw e;
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                String result = (String) ((Coder) CoderFactory.get(CoderFactory.CoderTypes.BASE64)).code(byteList);
                System.out.println(result);
                printByteArray(byteList);
                printByteArray((byte[]) ((Decoder) CoderFactory.get(CoderFactory.CoderTypes.BASE64)).decode(result));
            } catch (SourceFormatException e) {
                System.err.println(e);
            }
        }
    }
}