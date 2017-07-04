package coders;

/**
 *
 * @author leonid
 * @param <T>
 * @param <S>
 */
public interface Coder <T, S> {
    S code (T source);
    T decode (S code);
}
