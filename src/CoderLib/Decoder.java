package CoderLib;

/**
 *
 * @author leonid
 * @param <T>
 * @param <S>
 */
public interface Decoder<T, S> extends CoderInterface {
    T decode (S code) throws SourceFormatException;
}
