package CoderLib;

import java.util.zip.DataFormatException;

/**
 *
 * @author leonid
 */
public class SourceFormatException extends DataFormatException {

    private Object wrongData;
    
    public SourceFormatException(String s, Object wrongData) {
        super(s);
        this.wrongData = wrongData;
    }

    public SourceFormatException(Object wrongData) {
        this(wrongData == null ? "Incorrect data format." : "Incorrect data format of " + wrongData + ".", wrongData);
    }

    public SourceFormatException(String s) {
        this(s, null);
    }
    
    public SourceFormatException() {
        this(null);
    }
    
    Object getData () {
        return this.wrongData;
    }
}
