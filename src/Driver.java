public final class Driver {
    private Driver() {}

    public static void main(String[] args) {
        System.out.println(affineEncoder("foobar", 3, 2));
    }

    public static String affineEncoder(String string, int a, int b) {
        int[] inputAsInts = new int[string.length()];
        String output = "";
        for (int i = 0; i < string.length(); i++) {
            inputAsInts[i] = (string.charAt(i) - 'a');
            inputAsInts[i] *= a;
            inputAsInts[i] %= 25;
            inputAsInts[i] += b;
            output += (char) (inputAsInts[i] + 'a');
        }
        return output;
    }

    public static String affineDecoder(String string) {
        
    }
}
