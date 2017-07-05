package coders;

/**
 *
 * @author leonid
 */
class Base64 implements Coder<Byte[], String>, Decoder<Byte[], String> {
    
    private final char[] chars;
    
    Base64 () {
        int length = 0;
        
        chars = new char[64];
        
        int[] sets = {
            'Z' - 'A' + 1, // 'A' - 'Z'
            'z' - 'a' + 1, // 'a' - 'z'
            10, // '0' - '9',
            1, // '+'
            1 // '/'
        };
        
        for (int i = 0; i < chars.length; ++i) {
            if (i < sets[0]) {
                chars[i] = (char) ('A' + i);
            } else if (i < sets[0] + sets[1]) {
                chars[i] = (char) ('a' + i - sets[0]);
            } else if (i < sets[0] + sets[1] + sets[2]) {
                chars[i] = (char) ('0' + i - sets[0] - sets[1]);
            } else if (i < sets[0] + sets[1] + sets[2] - sets[3]) {
                chars[i] = '+';
            } else {
                chars[i] = '/';
            }
        }
    }    

    @Override
    public String code(Byte[] source) {
        byte [] byteSet = new byte[3];
        
        StringBuilder str = new StringBuilder();
        
        int rest = source.length % 3;
        final int ends = rest == 0 ? 0 : 3 - rest;
        
        while (ends > 0) {
            str.append('=');
        }
        
        return str.toString();
    }

    @Override
    public Byte[] decode(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
