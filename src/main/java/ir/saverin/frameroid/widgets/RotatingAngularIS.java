package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * @author AliReza
 * @since 5/8/16.
 */
public class RotatingAngularIS extends AngularImageSprite {
    protected float maxRotationDegree;
    protected boolean goForward;
    protected float speed;

    public RotatingAngularIS(FileResource resource, float maxRotationDegree, float speed) {
        super(resource, 0);
        this.maxRotationDegree = maxRotationDegree;
        this.speed = speed;
    }

    public RotatingAngularIS(FileResource resource, float maxRotationDegree, float speed, float strechtWidth, float strechtHeight) {
        super(resource, 0, strechtWidth, strechtHeight);
        this.speed = speed;
        this.maxRotationDegree = maxRotationDegree;
    }

    @Override
    public void draw(Graphics g) {
        if (isVisible()) {
            if (pressed) {
                if (noAngleClick) {
                    g.drawImage(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2);
                } else {
                    rotate();
                    g.drawImage(bitmap2, x - strechtWidth / 2, y - strechtHeight / 2, degree);
                }
            } else {
                rotate();
                g.drawImage(bitmap, x, y, degree);
            }
        }
    }

    private void rotate() {
        if (goForward) {
            if (degree < maxRotationDegree) {
                degree += speed;
            } else {
                goForward = false;
                degree -= speed;
            }
        } else {
            if (degree > -maxRotationDegree) {
                degree -= speed;
            } else {
                goForward = true;
                degree += speed;
            }
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxRotationDegree() {
        return maxRotationDegree;
    }

    public void setMaxRotationDegree(float maxRotationDegree) {
        this.maxRotationDegree = maxRotationDegree;
    }
}
