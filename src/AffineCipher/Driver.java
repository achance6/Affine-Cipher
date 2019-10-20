package AffineCipher;

import java.io.IOException;

public final class Driver {
    private Driver() {}

    public static void main(String[] args) throws IOException {
        Affine cipher = new Affine("enable1.txt");
        cipher.setPhrase("YEQ LKCV BDK XCGK EZ BDK UEXLVM QPLQGWSKMB");

        //cipher.setPhrase(cipher.encode(5, 8));
        System.out.println(cipher.decode());
    }


}
