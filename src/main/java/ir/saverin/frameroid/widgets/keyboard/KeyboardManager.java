package ir.saverin.frameroid.widgets.keyboard;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.util.TouchEventListener;
import ir.saverin.frameroid.widgets.LoadableImage;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim Ayat</a>
 */
public interface KeyboardManager extends LoadableImage, TouchEventListener {

    void toggle(boolean show);

    void setBuffer(StringBuffer buffer);

    StringBuffer getBuffer();

    void changeLayout(LayoutKey.KeyboardLayout layout);

    void setKeyboardListener(KeyboardActionListener listener);

    void draw(Graphics g);

    /**
     * Sets maximum valid length of keyboard buffer for a specific layout
     *
     * @param layout    target layout
     * @param maxLength max valid length. Values below 0 (inclusive) won't limit buffer
     */
    void setMaxLength(LayoutKey.KeyboardLayout layout, int maxLength);


    /**
     * Sets maximum valid length of keyboard buffer for all layouts that are already added.
     *
     * @param maxLength max valid length. Values below 0 (inclusive) won't limit buffer
     */
    void setMaxLength(int maxLength);
}
