package CoderLib;

/**
 *
 * @author leonid
 */
class Base64 implements Coder<byte[], String>, Decoder<byte[], String> {
    
    private final char[] chars; // Symbols
    
    // Constants
    
    private final int BYTE_BLOCK_SIZE = 3;
    private final int BITS_IN_SYMBOL = 6;
    private final int SYMBOLS_IN_BLOCK = 4;
    private final int SYMBOL_BITS_IN_BLOCK = SYMBOLS_IN_BLOCK * BITS_IN_SYMBOL;
    
    
    Base64 () {        
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
        int[] blocks = new int[(int)Math.ceil(bytes.length / (double)BYTE_BLOCK_SIZE)];
        
        for (int i = 0, blockIndex = 0; i < bytes.length; i += BYTE_BLOCK_SIZE, ++blockIndex) {
            int rest = bytes.length - i;
            int block = rest < BYTE_BLOCK_SIZE && rest > 0 ? BYTE_BLOCK_SIZE - rest : 0;
            for (int j = 0; j < 3; ++j) {
                int k = i + j;
                int item = k < bytes.length ? bytes[k] : 0;
                
                block <<= 8;
                block |= (item & 0xff);
            }
            blocks[blockIndex] = block;
        }
        
        return blocks;
    }

    @Override
    public String code(byte[] source) {  
        int [] blocks = this.glue(source);
        final int mask = (1 << BITS_IN_SYMBOL) - 1;
        
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < blocks.length; ++i) {
            int ends = blocks[i] >>> SYMBOL_BITS_IN_BLOCK;
            StringBuilder symbols = new StringBuilder();
            for (int j = 0; j < SYMBOL_BITS_IN_BLOCK; j += BITS_IN_SYMBOL) {
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