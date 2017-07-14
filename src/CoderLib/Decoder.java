package CoderLib;

/**
 *
 * @author leonid
 */
public interface Decoder<T, S> {
    T decode (S code);
}
