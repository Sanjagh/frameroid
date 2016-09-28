package ir.saverin.frameroid.widgets.keyboard;

/**
 * @author S.Hosein Ayat
 */
public enum PersianLetter implements CodeKey {

    Alef(1575),
    Be(1576),
    Pe(1662),
    Te(1578),
    Se(1579),
    Jim(1580),
    Che(1670),
    He(1581),
    Khe(1582),
    Dal(1583),
    Zal(1584),
    Re(1585),
    Ze(1586),
    Je(1688),
    Sin(1587),
    Shin(1588),
    Sad(1589),
    Zad(1590),
    Ta(1591),
    Za(1592),
    Ein(1593),
    Ghein(1594),
    Fe(1601),
    Ghaf(1602),
    Kaf(1705),
    Gaf(1711),
    Lam(1604),
    Mim(1605),
    Nun(1606),
    Vav(1608),
    He2(1607),
    Ye(1740);
    private final int code;

    private PersianLetter(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }


    @Override
    public KeyType getKeyType() {
        return KeyType.CODE;
    }

}
