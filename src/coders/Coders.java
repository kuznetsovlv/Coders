package coders;

import coderLib.*;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * @author leonid
 */
public class Coders {
    
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(codeFile(args[0]));
        } else {
            byte[] byteList = createRandomByteList();
            
            printByteArray(byteList);
            
            String base64 = codeBytes(byteList);
            System.out.println(base64);
            
            printByteArray(decodeString(base64));
            
            try {
              
                // 1bc29b36f623ba82aaf6724fd3b16718;
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("md5".getBytes()));
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("abc".getBytes()));
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("acc".getBytes()));
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("abd".getBytes()));
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("abcd".getBytes()));
                System.out.println(CoderFactory.get(CoderFactory.CoderTypes.MD5).code("a".getBytes()));

            } catch (SourceFormatException e) {
                System.err.println(e);
                System.exit(3);
            }
        }
    }
    
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
    
    private static String codeFile (String fileName) {
        File file = new File(fileName);
        
        try(FileInputStream input = new FileInputStream(file)) {  
            byte[] byteList = new byte[(int)file.length()];  
            input.read(byteList);
            return Coders.codeBytes(byteList);
        } catch(Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        
        return null;
    }
    
    private static byte[] decodeString (String str) {
        try {
            return (byte[]) CoderFactory.get(CoderFactory.CoderTypes.BASE64).decode(str);
        } catch (SourceFormatException e) {
            System.err.println(e);
            System.exit(3);
        }
        
        return null;
    }
    
    private static String codeBytes (byte[] bytes) {
        try {
            return (String) CoderFactory.get(CoderFactory.CoderTypes.BASE64).code(bytes);
        } catch (SourceFormatException e) {
            System.err.println(e);
            System.exit(2);
        }
        
        return null;
    }
    
    private static byte[] createRandomByteList () {
        int length = 1 + (int) (10 * Math.random());
        
        byte[] bytes = new byte[length];
        
        for (int i = 0; i < length; ++i) {
            bytes[i] = (byte)(256 * Math.random() - 128);
        }
        
        return bytes;
    }
}