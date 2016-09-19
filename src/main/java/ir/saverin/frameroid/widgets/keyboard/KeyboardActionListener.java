package ir.saverin.frameroid.widgets.keyboard;

/**
 * This interface is added to any keyboard to monitor it's behaviour.
 *
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public interface KeyboardActionListener {

    void doneCalled();

    void layoutChanged(LayoutKey.KeyboardLayout layout);
}
