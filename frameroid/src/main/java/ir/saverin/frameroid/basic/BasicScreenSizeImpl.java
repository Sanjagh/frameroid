package ir.saverin.frameroid.basic;


public class BasicScreenSizeImpl implements ir.saverin.frameroid.api.ScreenSize {

    double scale = 1;
    float targetW = 1000;
    float targetH = 600;
    float originalW = 0;
    float originalH = 0;
    float xOffset = 0;
    float yOffset = 0;

    @Override
    public float getYOffset() {
        return yOffset;
    }

    @Override
    public float getXOffset() {
        return xOffset;
    }

    @Override
    public float getTargetH() {
        return targetH;
    }

    @Override
    public float getTargetW() {
        return targetW;
    }

    @Override
    public double getScale() {
        return scale;
    }

    public void init(float screenW, float screenH, float tw, float th) {
        originalW = screenW;
        originalH = screenH;
        targetW = tw;
        targetH = th;

        double rateW = ((double) this.originalW) / ((double) this.targetW);
        double rateH = ((double) this.originalH) / ((double) this.targetH);
        scale = rateW < rateH ? rateW : rateH;

        targetW = (float) (((double) this.targetW) * scale);
        targetH = (float) (((double) this.targetH) * scale);


        xOffset = (float) ((this.originalW - targetW) / 2.0);
        yOffset = (float) ((this.originalH - targetH) / 2.0);

    }

}
