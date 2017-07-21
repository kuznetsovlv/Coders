package coderLib;

import java.util.HashMap;

/**
 *
 * @author leonid
 */
public  class CoderFactory {
    final static private int BASE64 = 0;
    final static private int MD5 = 1;
    
    public static enum CoderTypes {
        BASE64(CoderFactory.BASE64),
        MD5(CoderFactory.MD5);
        
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
                case BASE64:
                    coders.put(type, new Base64());
                    break;
                case MD5:
                    coders.put(type, new MD5());
                    break;
                default: return null;
            }
        }
        
        return coders.get(type);
    }
}
