package coderLib;

import java.util.regex.Pattern;

/**
 *
 * @author leonid
 */
class Base64 implements Coder<byte[], String> {
    
    private final char[] chars; // Symbols
    
    // Constants    
    private final int BYTE_BLOCK_SIZE = 3;
    private final int BITS_IN_SYMBOL = 6;
    private final int SYMBOLS_IN_BLOCK = 4;
    private final int SYMBOL_BITS_IN_BLOCK = SYMBOLS_IN_BLOCK * BITS_IN_SYMBOL;
    
    private Pattern pattern = null;
    private int MASK = 0;
    
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
            if (i < this.sum(sets, 1)) {
                chars[i] = (char) ('A' + i);
            } else if (i < this.sum(sets, 2)) {
                chars[i] = (char) ('a' + i - sets[0]);
            } else if (i < this.sum(sets, 3)) {
                chars[i] = (char) ('0' + i - sets[0] - sets[1]);
            } else if (i < this.sum(sets, 4)) {
                chars[i] = '+';
            } else {
                chars[i] = '/';
            }
        }
    }
    
    private int sum (int[] arr, int n) {
        int s = 0;
        
        if (n > arr.length) {
            n = arr.length;
        }
        
        for (int i = 0; i < n; ++i) {
            s += arr[i];
        }
        return s;
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
    
    private boolean isBase64Correct (String s) {    
        return s.matches("^[\\w+\\/]+={0,2}$");
    }
    
    private int getIndex (char c) {
        for (int index = 0; index < this.chars.length; ++index) {
            if (c == this.chars[index]) {
                return index;
            }
        }
        
        return -1;
    }
    
    private int getMask () {
        if (this.MASK == 0) {
            this.MASK = (1 << 24) - (1 << 16);
        }
        
        return this.MASK;
    }
    
    private int strSetToInt (String s) {
        int r = 0;    
        char[] charSet = s.toCharArray();
        
        for (int i = 0; i < charSet.length; ++i) {
            r <<= 6;
            if (charSet[i] != '=') {
                r |= this.getIndex(charSet[i]);
            }            
        }
        
        return r;
    }

    @Override
    public byte[] decode (String code) throws SourceFormatException {
        int length;
        
        if (code == null || (length = code.length()) == 0) {
            throw new SourceFormatException("No Base64 string.", code);
        }
        if (length % 4 != 0) {
            throw new SourceFormatException("Base64 string length must be dividable by 4.", code);
        }
        
        if (!this.isBase64Correct(code)) {
            throw new SourceFormatException("Base64 string contains forbiden symbols.", code);
        }
        
        byte[] result = new byte[code.length() / 4 * 3 - (code.endsWith("==") ? 2 : code.endsWith("=") ? 1 : 0)];
        
        int k = 0;
        for (int i = 0, j = 4; i < length; i = j, j += 4) {
            int num = this.strSetToInt(code.substring(i, j));
            int mask = this.getMask();
            
            while (mask != 0 && k < result.length) {
                result[k] = (byte) ((num & mask) >>> ((2 - k % 3) * 8));
                mask >>>= 8;
                ++k;
            }
        }
        
        return result;
    }
}
