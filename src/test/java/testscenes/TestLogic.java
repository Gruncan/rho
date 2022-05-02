package testscenes;

import java.io.IOException;
import java.util.Arrays;

public class TestLogic {

    private static TestLogic testLogic;

    public static void main(String[] args) throws IOException {
//        String testText = "This is random text";
//        File testfile = new File("testfile.txt");
//        byte[] encodedText = Base64.getEncoder().encode(testText.getBytes(StandardCharsets.UTF_8) );
//
//        try (OutputStream stream = new FileOutputStream(testfile)) {
//            stream.write(encodedText);
//        }


//        File testfile = new File("testfile.txt");
//        InputStream stream = new FileInputStream(testfile);
//        byte[] bytes = stream.readAllBytes();
//        bytes = Base64.getDecoder().decode(bytes);
//        String readIn = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(readIn);


        int[] A = new int[]{1, 5, 7, 9, 3, 4};
        System.out.println(Arrays.toString(A));
        f(A, 0, 6);
        System.out.println(Arrays.toString(A));

    }


    private static void f(int[] A, int p, int r) {
        if (p > r) {
            int x = A[p];
            A[p] = A[r];
            A[r] = x;
            f(A, p + 1, r - 1);
        }
    }


}
