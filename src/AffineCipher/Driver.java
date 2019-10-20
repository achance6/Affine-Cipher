package AffineCipher;

import AffineCipher.Affine;

public final class Driver {
    private Driver() {}

    public static void main(String[] args) {
        Affine cipher = new Affine("affinecipher");
        System.out.println(cipher.encode(5, 8));
        //System.out.println(AffineCipher.Affine.decoderHelper((AffineCipher.Affine.affineEncoder("affinecipher", 5, 8)), 21, 8));
        //System.out.println(AffineCipher.Affine.affineDecoder("Yeq lkcv bdk xcgk ez bdk uexlv'm qplqgwskmb."));
    }


}
