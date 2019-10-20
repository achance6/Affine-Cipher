package AffineCipher;

import java.util.ArrayList;

public class Affine {
    private String string;
    private int len;
    public Affine(String input) {
        this.string = input;
        this.len = string.length();
    }

    public String encode(int a, int b) {
        int[] inputAsInts = new int[this.len];
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.len; i++) {
            inputAsInts[i] = (this.string.charAt(i) - 'a');
            inputAsInts[i] *= a;
            inputAsInts[i] += b;
            inputAsInts[i] %= 26;
            output.append((char) (inputAsInts[i] + 'a'));
        }
        return output.toString();
    }

    public String decode(String string) {
        int[] aVals = {3, 5, 7, 11, 15, 17, 19, 21, 23, 25};
        ArrayList<String> possibilites = new ArrayList<>();
        for (int i = 0; i < aVals.length; i++) {
            for (int j = 0; j < 26; j++) {
                possibilites.add(decoderHelper(string, aVals[i], j));
                //System.out.println("a: " + aVals[i] + " " + "b: " + j + " " + possibilites.get(possibilites.size() - 1));
            }
        }
        for (String word: possibilites) {
            System.out.println(word);
            //TODO: check likelihood
        }
        return "";
    }

    private String decoderHelper(String input, int a, int b) {
        ArrayList<Integer> ints = new ArrayList<>();
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            ints.add((int) (input.charAt(i) - 'a'));
            if (input.charAt(i) <= 'z' && input.charAt(i) >= 'a') {
                ints.set(i, ints.get(i) - b);
                ints.set(i, ints.get(i) * a);
                ints.set(i, Math.floorMod(ints.get(i), 26));
            }
            decoded.append((char) (ints.get(i) + 'a'));
        }
        return decoded.toString();
    }
}
