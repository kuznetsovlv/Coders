package coders;

/**
 *
 * @author leonid
 * @param <T>
 * @param <S>
 */
interface Coder <T, S> {
    S code (T source);
}
