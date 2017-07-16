package CoderLib;

import java.util.HashMap;

/**
 *
 * @author leonid
 */
public  class CoderFactory {
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
    
    private static final HashMap<CoderTypes, CoderInterface> coders = new HashMap<>();
    
    public static CoderInterface get (CoderTypes type) {
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
}
