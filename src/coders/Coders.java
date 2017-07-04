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
                    coders.put(type, new Coder() {
                        @Override
                        public Object code(Object source) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public Object decode(Object code) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
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
