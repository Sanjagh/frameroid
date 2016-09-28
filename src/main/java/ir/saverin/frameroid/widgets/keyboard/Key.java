package ir.saverin.frameroid.widgets.keyboard;

/**
 * @author S.Hosein Ayat
 */
public interface Key {
    KeyType getKeyType();

    public static class ControlKey implements Key {
        private final KeyType type;

        public ControlKey(KeyType type) {
            this.type = type;
        }

        @Override
        public KeyType getKeyType() {
            return type;
        }
    }


}
