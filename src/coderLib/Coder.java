package coderLib;

/**
 *
 * @author leonid
 * @param <T>
 * @param <S>
 */
public interface Coder <T, S> {
    S code (T source) throws SourceFormatException;
    T decode (S code) throws SourceFormatException;
}
