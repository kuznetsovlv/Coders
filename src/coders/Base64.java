package coders;

/**
 *
 * @author leonid
 */
class Base64 implements Coder<byte[], String>, Decoder<byte[], String> {
    
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
    
    private int[] glue (byte[] bytes) {
        int[] blocks = new int[(int)Math.floor(bytes.length / 3.0)];
        
        for (int i = 0, blockIndex = 0; i < bytes.length; ++i, ++blockIndex) {
            int rest = bytes.length - i - 1;
            int block = rest < 3 ? 3 - rest : 0;
            
            for (int j = 0; j < 3; ++j) {
                i += j;
                
                byte item = i < bytes.length ? bytes[i] : 0;
                
                block <<= 8;
                block |= item;
            }           
            blocks[blockIndex] = block;
        }
        
        return blocks;
    }

    @Override
    public String code(byte[] source) {  
        int [] blocks = this.glue(source);
        final int mask = 1 << 7 - 1;
        
        StringBuilder str = new StringBuilder();
        
        for (int i = 0; i < blocks.length; ++i) {
            int ends = blocks[i] >>> 24;
            StringBuilder symbols = new StringBuilder();
            for (int j = 0; j < 24; j += 6) {
                symbols.append(ends-- > 0 ? '=' : this.chars[(blocks[i] >>> j) & mask]);
            }
            
            str.append(symbols.reverse());
        }        
        return str.toString();
    }

    @Override
    public byte[] decode(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
