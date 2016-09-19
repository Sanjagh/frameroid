package ir.saverin.frameroid.util;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public class PersianUtil {

    public static final int ZERO = 1776;

    public static String getPersianNumber(int number) {
        StringBuffer buffer = new StringBuffer();
        do {
            char current = (char) (number % 10 + ZERO);
            buffer.insert(0, current);
            number = number / 10;
        } while (number != 0);
        return buffer.toString();
    }
}
