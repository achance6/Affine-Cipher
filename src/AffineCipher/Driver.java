package AffineCipher;

import java.io.IOException;

public final class Driver {
    private Driver() {}

    public static void main(String[] args) throws IOException {
        Affine cipher = new Affine("Yeq lkcv bdk xcgk ez bdk uexlv'm qplqgwskmb.", "enable1.txt");
        System.out.println(cipher.decode());
    }


}
