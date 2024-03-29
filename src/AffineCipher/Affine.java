package AffineCipher;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/** Class for producing and decoding affine ciphers.
 *
 */
public class Affine {
    private String phrase;
    private int len;
    private Scanner scnr;
    private ArrayList<String> dictArray;
    private String error = "Error opening dictionary file";
    private String errorStateMsg = "Please specify a dictionary";
    private boolean errorState;

    /** Constructor of Affine object with phrase and dictionary name provided
     *
     * @param input Phrase to be used
     * @param dictionaryName Dictionary to be used (.txt file)
     */
    public Affine(String input, String dictionaryName) {
        setPhrase(input);
        try {
            setDictionary(dictionaryName);
            this.errorState = false;
        }
        catch (IOException io) {
            this.errorState = true;
            System.err.println(error);
        }
    }

    /** Constructor of Affine object with dictionary name provided and default phrase
     *
     * @param dictionaryName Dictionary to be used (.txt file)
     */
    public Affine(String dictionaryName) {
        setPhrase("affine cipher");
        try {
            setDictionary(dictionaryName);
            this.errorState = false;
        }
        catch (IOException io) {
            this.errorState = true;
            System.err.println(error);
        }
    }

    /** Constructor of Affine object with default dictionary and phrase
     *
     */
    public Affine() {
        try {
            setPhrase("affine cipher");
            setDictionary("enable1.txt");
            this.errorState = false;
        }
        catch (IOException io) {
            this.errorState = true;
            System.err.println(error);
        }
    }

    /** Sets phrase to use
     *
     * @param string Phrase to use
     */
    public void setPhrase(String string) {
        this.phrase = string;
        this.len = phrase.length();
    }

    /** Sets dictionary to use (.txt)
     *
     * @param name dictionary text file
     * @throws IOException Error opening file
     */
    public void setDictionary(String name) throws IOException {
        FileReader dictionary = new FileReader(name);
        this.scnr = new Scanner(dictionary);
    }

    /** Encodes current phrase using an affine cipher (C = aP + b).
     *
     * @param a a value to be used
     * @param b b value to be used
     * @return Encoded phrase
     */
    public String encode(int a, int b) {
        if (errorState) {
            System.err.println(errorStateMsg);
            return "";
        }
        int[] inputAsInts = new int[this.len];
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.len; i++) {
            if (this.phrase.charAt(i) >= 'a' && this.phrase.charAt(i) <= 'z') {
                inputAsInts[i] = (this.phrase.charAt(i) - 'a');
                inputAsInts[i] *= a;
                inputAsInts[i] += b;
                inputAsInts[i] %= 26;
                output.append((char) (inputAsInts[i] + 'a'));
            }
            else if (this.phrase.charAt(i) >= 'A' && this.phrase.charAt(i) <= 'Z') {
                inputAsInts[i] = (this.phrase.charAt(i) - 'A');
                inputAsInts[i] *= a;
                inputAsInts[i] += b;
                inputAsInts[i] %= 26;
                output.append((char) (inputAsInts[i] + 'A'));
            }
            else {
                output.append((char) (inputAsInts[i] + 0));
            }
        }
        return output.toString();
    }

    /** Encodes given phrase using an affine cipher (C = aP + b).
     *
     * @param phrase Phrase to be used
     * @param a a value to be used
     * @param b b value to be used
     * @return Encoded phrase
     */
    public String encode(String phrase, int a, int b) {
        if (errorState) {
            System.err.println(errorStateMsg);
            return "";
        }
        int[] inputAsInts = new int[phrase.length()];
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) >= 'a' && phrase.charAt(i) <= 'z') {
                inputAsInts[i] = (phrase.charAt(i) - 'a');
                inputAsInts[i] *= a;
                inputAsInts[i] += b;
                inputAsInts[i] %= 26;
                output.append((char) (inputAsInts[i] + 'a'));
            }
            else if (phrase.charAt(i) >= 'A' && phrase.charAt(i) <= 'Z') {
                inputAsInts[i] = (phrase.charAt(i) - 'A');
                inputAsInts[i] *= a;
                inputAsInts[i] += b;
                inputAsInts[i] %= 26;
                output.append((char) (inputAsInts[i] + 'A'));
            }
            else {
                output.append((char) (inputAsInts[i] + 0));
            }
        }
        return output.toString();
    }

    /** Decodes given phrase created using an affine cipher
     *
     * @param phrase Phrase to be decoded
     * @return Most likely phrase
     */
    public String decode(String phrase) {
        if (errorState) {
            System.err.println(errorStateMsg);
            return "";
        }
        dictInit();
        TreeMap<Double, String> likelihoods = new TreeMap<>();
        int[] aVals = {3, 5, 7, 11, 15, 17, 19, 21, 23, 25};
        ArrayList<String> possibilities = new ArrayList<>();
        for (int i = 0; i < aVals.length; i++) {
            for (int j = 0; j < 26; j++) {
                possibilities.add(decoderHelper(phrase, aVals[i], j));
            }
        }
        for (String possiblePhrase: possibilities) {
            likelihoods.put(likelihood(possiblePhrase), possiblePhrase);
        }
        return likelihoods.lastEntry().getValue();
    }

    /** Decodes current phrase created using an affine cipher
     *
     * @return Most likely phrase
     */
    public String decode() {
        if (errorState) {
            System.err.println(errorStateMsg);
            return "";
        }
        dictInit();
        TreeMap<Double, String> likelihoods = new TreeMap<>();
        int[] aVals = {3, 5, 7, 11, 15, 17, 19, 21, 23, 25};
        ArrayList<String> possibilities = new ArrayList<>();
        for (int i = 0; i < aVals.length; i++) {
            for (int j = 0; j < 26; j++) {
                possibilities.add(decoderHelper(this.phrase, aVals[i], j));
            }
        }
        for (String phrase: possibilities) {
            likelihoods.put(likelihood(phrase), phrase);
        }
        return likelihoods.lastEntry().getValue();
    }

    /**
     *
     * @param input phrase to be decoded using equation P = a(C - b)
     * @param a value to use
     * @param b value to use
     * @return Decoded phrase
     */
    private String decoderHelper(String input, int a, int b) {
        ArrayList<Integer> ints = new ArrayList<>();
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            ints.add((int) input.charAt(i));
            if (input.charAt(i) <= 'z' && input.charAt(i) >= 'a') {
                ints.set(i, input.charAt(i) - 'a');
                ints.set(i, ints.get(i) - b);
                ints.set(i, ints.get(i) * a);
                ints.set(i, Math.floorMod(ints.get(i), 26));
                decoded.append((char) (ints.get(i) + 'a'));
            }
            else if (input.charAt(i) <= 'Z' && input.charAt(i) >= 'A') {
                ints.set(i, input.charAt(i) - 'A');
                ints.set(i, ints.get(i) - b);
                ints.set(i, ints.get(i) * a);
                ints.set(i, Math.floorMod(ints.get(i), 26));
                decoded.append((char) (ints.get(i) + 'A'));
            }
            else {
                decoded.append((char) (ints.get(i) + 0)); //TODO: fix
            }

        }
        return decoded.toString();
    }

    /** Checks the likelihood of a phrase being correct by checking how many real words it contains.
     *
     * @param phrase phrase to check
     * @return Likelihood of phrase being correct on a 0-1.0 scale (1.0 being every word is real)
     */
    private double likelihood(String phrase) {
        String[] words;
        if (phrase.contains(" ")) {
            words = phrase.split(" ");
        }
        else {
            words = phrase.split("\\x00+"); //If phrase separated by null values (thanks StringBuilder)
        }
        int realWords = 0;
        int totalWords = words.length;
        for (String word: words) {
            if (dictionaryChecker(word)) {
                realWords++;
            }
        }

        return (double) realWords / (double) totalWords;
    }

    /** Checks dictionary for a word
     * @param word to be checked
     * @return true if found
     */
    private boolean dictionaryChecker(String word) {
        word = word.toLowerCase();
        int start = 0;
        int end = dictArray.size();
        int mid;
        for (int i = 0; i < this.dictArray.size(); i++) {
            mid = (start + end) / 2;
            if (start == end) {
                return false;
            }
            if (dictArray.get(mid).compareTo(word) > 0) {
                end = mid;
            }
            else if (dictArray.get(mid).compareTo(word) < 0) {
                start = mid;
            }
            else {
                return true;
            }
        }
        return false;
    }

    /** Puts dictionary into an ArrayList
     *
     */
    private void dictInit() {
        this.dictArray = new ArrayList<>();
        while (scnr.hasNext()) {
            dictArray.add(scnr.next());
        }
    }
}
