
package coderLib;

import com.sun.javafx.image.impl.ByteArgb;

/**
 *
 * @author leonid
 */
public class MD5 extends CoderAdapter<byte[], String> {
    
    private final static int MOD = 512;
    private final static int TARGET_REST = 448;
    private final static byte ONE_BIT = (byte) 0x80;
    
    private int A, B, C, D;
    private int[] M;
    private final int[] X = new int[16];
    
    private final static int[] T = new int[64];
    
    static {        
        for (int i = 0; i < T.length;) {
            T[i] = (int) (4294967296l * Math.abs(Math.sin(++i)));
        }
    }
    
    @Override
    public String code(byte[] source) throws SourceFormatException {
        return this.initBlocks(MD5.stepTwo(MD5.stepOne(source), source.length)).stepThree().stepFour().stepFive();
    }
    
    private static byte[] stepOne (byte[] initialStream) {
        int length = initialStream.length;
        int newLenght = length + 1;
        int rest = newLenght % MD5.MOD;
        newLenght += (MD5.TARGET_REST - rest);
        
        byte[] lStream = new byte[newLenght];
        
        int i;
        for (i = 0; i < length; ++i) {
            lStream[i] = initialStream[i];
        }
        lStream[i] = MD5.ONE_BIT;
        
        return lStream;
    }

    private static byte[] stepTwo (byte[] stream, long length) {
        byte [] finalStream = new byte[stream.length + 8];
        
        int young = (int) (length & -1);
        int old = (int) ((length >>> 32) & -1);
            
        for (int i = finalStream.length - 1; i >= 0; --i) {
            if (i >= stream.length + 4) {
                finalStream[i] = (byte) old;
                old >>>= 8;
            } else if (i >= stream.length) {
                finalStream[i] = (byte) young;
                length >>>= 8;
            } else {
                finalStream[i] = stream[i];
            }
        }
        
        return finalStream;
    }
    
    private MD5 stepThree () {
        this.A = 0x67452301;
        this.B = 0xEFCDAB89;
        this.C = 0x98BADCFE;
        this.D = 0x10325476;
        
        return this;
    }
    
    private MD5 stepFour () {
        int AA, BB, CC, DD;
        
        for (int i = 0; i < M.length / 16; ++i) {
            
            for (int j = 0; j < 16; ++j) {
                this.X[j] = this.M[i * 16 + j];
            }
            
            AA = this.A;
            BB = this.B;
            CC = this.C;
            DD = this.D;
            
            this.roundOne().roundTwo().roundThree().roundFour();
            
            this.A += AA;
            this.B += BB;
            this.C += CC;
            this.D += DD;
        }
        return this;
    }
    
    private String stepFive () {        
        return new StringBuilder(Integer.toHexString(this.D)).append(Integer.toHexString(this.C)).append(Integer.toHexString(this.B)).append(Integer.toHexString(this.A)).toString();
    }
    
    private MD5 initBlocks (byte[] byteArr) {
        int [] intArr = new int[byteArr.length / 4];
        int block = 0;
        
        for (int i = 0; i < byteArr.length; ++i) {
            block = i % 4 == 0 ? 0 : (block << 8) | (((int)(byteArr[i])) & 0xff);
            
            if (i % 4 == 3) {
                intArr[i / 4] = block;
            }
        }
        
        this.M = intArr;
        
        return this;
    }
    
    private static int F(int x, int y, int z) {
        return (x & y) | (~x & z);
    }
    
    private static int G(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }
    
    private static int H(int x, int y, int z) {
        return x ^ y ^ z;
    }
    
    private static int I(int x, int y, int z) {
        return y ^ (x | ~z);
    }
    
    private int roundOneOperator (int a, int b, int c, int d, int k, int s, int i) {
        return (int) (b + MD5.loopShift((F(b, c, d) + this.X[k] + MD5.T[--i]), s));
    }
    
    private int roundTwoOperator (int a, int b, int c, int d, int k, int s, int i) {
        return (int) (b + MD5.loopShift((G(b, c, d) + this.X[k] + MD5.T[--i]), s));
    }
    
     private int roundThreeOperator (int a, int b, int c, int d, int k, int s, int i) {
        return (int) (b + MD5.loopShift((H(b, c, d) + this.X[k] + MD5.T[--i]), s));
    }
    
    private int roundFourOperator (int a, int b, int c, int d, int k, int s, int i) {
        return (int) (b + MD5.loopShift((I(b, c, d) + this.X[k] + MD5.T[--i]), s));
    }
    
    private MD5 roundOne () { 
        for (int k = 0; k < 16; ++k) {
            int rest = k % 4;
            int s = 7 + 5 * rest;
            int i = k + 1;
            
            switch (rest) {
                case 0:
                    this.A = roundOneOperator(this.A, this.B, this.C, this.D, k, s, i);
                    break;
                case 1:
                    this.D = roundOneOperator(this.D, this.A, this.B, this.C, k, s, i);
                    break;
                case 2:
                    this.C = roundOneOperator(this.C, this.D, this.A, this.B, k, s, i);
                    break;
                case 3:
                    this.B = roundOneOperator(this.B, this.C, this.D, this.A, k, s, i);
                    break;
            }
        }
        
        return this;
    }
    
    private MD5 roundTwo () {
        int k = 12;
        int s;
        for (int j = 0; j < 16; ++j) {
            k = k > 10 ? k - 11 : k + 5;
            int rest = j % 4;
            int i = j + 17;
  
            switch (rest) {
                case 0:
                    s = 5;
                    this.A = roundTwoOperator(this.A, this.B, this.C, this.D, k, s, i);
                    break;
                case 1:
                    s = 9;
                    this.D = roundTwoOperator(this.D, this.A, this.B, this.C, k, s, i);
                    break;
                case 2:
                    s = 14;
                    this.C = roundTwoOperator(this.C, this.D, this.A, this.B, k, s, i);
                    break;
                case 3:
                    s = 20;
                    this.B = roundTwoOperator(this.B, this.C, this.D, this.A, k, s, i);
                    break;
            }
        }
        return this;
    }
    
    private MD5 roundThree () {
        int k = 18;
        int s;
        for (int j = 0; j < 16; ++j) {
            k = k > 12 ? k - 13 : k + 3;
            int rest = j % 4;
            int i = j + 33;
  
            switch (rest) {
                case 0:
                    s = 4;
                    this.A = roundThreeOperator(this.A, this.B, this.C, this.D, k, s, i);
                    break;
                case 1:
                    s = 11;
                    this.D = roundThreeOperator(this.D, this.A, this.B, this.C, k, s, i);
                    break;
                case 2:
                    s = 16;
                    this.C = roundThreeOperator(this.C, this.D, this.A, this.B, k, s, i);
                    break;
                case 3:
                    s = 23;
                    this.B = roundThreeOperator(this.B, this.C, this.D, this.A, k, s, i);
                    break;
            }
        }
        return this;
    }
    
    private MD5 roundFour () {
        int k = 9;
        int s;
        for (int j = 0; j < 16; ++j) {
            k = k > 8 ? k - 9 : k + 7;
            int rest = j % 4;
            int i = j + 49;
  
            switch (rest) {
                case 0:
                    s = 6;
                    this.A = roundFourOperator(this.A, this.B, this.C, this.D, k, s, i);
                    break;
                case 1:
                    s = 10;
                    this.D = roundFourOperator(this.D, this.A, this.B, this.C, k, s, i);
                    break;
                case 2:
                    s = 15;
                    this.C = roundFourOperator(this.C, this.D, this.A, this.B, k, s, i);
                    break;
                case 3:
                    s = 21;
                    this.B = roundFourOperator(this.B, this.C, this.D, this.A, k, s, i);
                    break;
            }
        }
        return this;
    }
    
    private static int loopShift (int x, int y) {
        int mask = -1 << y;
        
        return (x << y) | ((x & mask) >>> (32 - y));
    }
}
