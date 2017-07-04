package coders;

import java.util.HashMap;


/**
 *
 * @author leonid
 */
public class Coders {
   
    public static enum CoderTypes {
        BASE64(0);
        
        private final int value;

        private CoderTypes(int value) {
            this.value = value;
        }
        
        protected final int getValue () {
            return value;
        }
    };
    
    private static HashMap<CoderTypes, Coder> coders;
    
    public static Coder <?, ?> get (CoderTypes type) {
        
        if (!coders.containsKey(type)) {
            switch (type.getValue()) {
                case 0:
                    coders.put(type, new Base64());
                default: return null;
            }
        }
        
        return coders.get(type);
    }
    
    public static void main(String[] args) {
        System.out.println("Test coders.");
        
        CoderTypes type = CoderTypes.BASE64;
        
        switch (type.getValue()) {
            case 0: System.out.println("base64");
        }
    }
    
}
