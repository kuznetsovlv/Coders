package coderLib;

import java.util.HashMap;

/**
 *
 * @author leonid
 */
public  class CoderFactory {
    public static enum CoderTypes {
        BASE64(0),
        MD5(1);
        
        private final int value;

        private CoderTypes(int value) {
            this.value = value;
        }
        
        protected final int getValue () {
            return value;
        }
    };
    
    private static final HashMap<CoderTypes, Coder> coders = new HashMap<>();
    
    public static Coder get (CoderTypes type) {
        if (!coders.containsKey(type)) {
            switch (type.getValue()) {
                case 0:
                    coders.put(type, new Base64());
                    break;
                case 1:
                    coders.put(type, new MD5());
                    break;
                default: return null;
            }
        }
        
        return coders.get(type);
    }
}
