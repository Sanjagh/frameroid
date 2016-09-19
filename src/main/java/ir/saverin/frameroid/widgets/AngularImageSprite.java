package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 5/7/16.
 */
public class AngularImageSprite extends AnimatedButtonSprite {

    protected float degree;
    protected boolean noAngleClick;

    public AngularImageSprite(FileResource resource, float degree) {
        super(resource);
        this.degree = degree;
    }

    public AngularImageSprite(FileResource resource, float degree, float strechtWidth, float strechtHeight) {
        super(resource, strechtWidth, strechtHeight);
        this.degree = degree;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            if (pressed) {
                if (noAngleClick) {
                    g.drawImage(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2);
                } else {
                    g.drawImage(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2, degree);
                }
            } else {
                g.drawImage(bitmap, x, y, degree);
            }
        }
    }

    public boolean isNoAngleClick() {
        return noAngleClick;
    }

    public void setNoAngleClick(boolean noAngleClick) {
        this.noAngleClick = noAngleClick;
    }
}
