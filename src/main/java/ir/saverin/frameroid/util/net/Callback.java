package ir.saverin.frameroid.util.net;

/**
 * Created by fahim on 2/28/15.
 *
 * @author <a href="mailto:fahim.ayat@gmail.com">Fahim Ayat</a>
 */
public interface Callback<T> {

    void call(T t);
}
