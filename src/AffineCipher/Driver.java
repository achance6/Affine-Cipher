package AffineCipher;

import java.io.IOException;

public final class Driver {
    private Driver() {}

    public static void main(String[] args) throws IOException {
        Affine cipher = new Affine("test", "enable1.txt");
    }


}
