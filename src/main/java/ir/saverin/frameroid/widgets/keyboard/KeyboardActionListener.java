package ir.saverin.frameroid.widgets.keyboard;

/**
 * This interface is added to any keyboard to monitor it's behaviour.
 *
 * @author S.Hosein Ayat
 */
public interface KeyboardActionListener {

    void doneCalled();

    void layoutChanged(LayoutKey.KeyboardLayout layout);
}
