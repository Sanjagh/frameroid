package ir.saverin.frameroid.widgets.keyboard;

/**
 * @author S.Hosein Ayat
 */
public class LayoutKey extends Key.ControlKey {

    public final KeyboardLayout layout;

    public LayoutKey(KeyboardLayout layout) {
        super(KeyType.CHANGE_LAYOUT);
        this.layout = layout;
    }

    public enum KeyboardLayout {
        PERSIAN,
        PERSIAN_SYM,
        ENGLISH_CAP,
        ENGLISH_SML,
        ENGLISH_SYM;
    }
}
