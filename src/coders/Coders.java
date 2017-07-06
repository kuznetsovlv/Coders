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
    
    private static HashMap<CoderTypes, Coder> coders = new HashMap<>();
    
    public static Coder get (CoderTypes type) {
        if (!coders.containsKey(type)) {
            switch (type.getValue()) {
                case 0:
                    coders.put(type, new Base64());
                    break;
                default: return null;
            }
        }
        
        return coders.get(type);
    }
    
    public static void main(String[] args) {
        byte[] byteList = {-128, 1, 1, 0, -1};
        
        System.out.println(get(CoderTypes.BASE64).code(byteList));
    }
}