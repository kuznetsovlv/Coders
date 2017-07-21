package coderLib;

/**
 *
 * @author leonid
 */
abstract class CoderAdapter<T, S> implements Coder<T, S> {

    @Override
    final public T decode(S code) throws SourceFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
