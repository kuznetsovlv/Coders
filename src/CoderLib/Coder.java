package CoderLib;

/**
 *
 * @author leonid
 * @param <T>
 * @param <S>
 */
public interface Coder <T, S> extends CoderInterface {
    S code (T source) throws SourceFormatException;
}
