package cz.muni.fi.helpers;

/**
 * Guard class.
 *
 * @author Martin Hořelka (469003)
 */
public class Guard {

    public static void requireNotNull(Object obj, String exceptionMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void requireNull(Object obj, String exceptionMessage) {
        if (obj != null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
