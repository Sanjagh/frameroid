package ir.saverin.frameroid.widgets;

import ir.saverin.frameroid.api.media.Graphics;
import ir.saverin.frameroid.api.widget.Sprite;
import ir.saverin.frameroid.util.sound.SoundPlayer;

/**
 * @author <a mailto:fahim.ayat@gmail.com>Fahim.Ayat@gmail.com</a>
 */
public abstract class SoundPlayerSprite extends AbstractSprite implements Sprite {

    protected SoundPlayer.SoundType soundType = SoundPlayer.SoundType.CLICK_DEFAULT;
    protected boolean playSound = false;

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public void setSoundType(SoundPlayer.SoundType soundType) {
        this.soundType = soundType;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void down(float x, float y) {

    }

    @Override
    public void up(boolean inside, float x, float y) {
        if (inside && playSound) {
            SoundPlayer.getInstance().play(soundType);
        }
    }

}
