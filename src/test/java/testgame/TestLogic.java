package testgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestLogic {

    public static void main(String[] args) throws IOException {
//        String testText = "This is random text";
//        File testfile = new File("testfile.txt");
//        byte[] encodedText = Base64.getEncoder().encode(testText.getBytes(StandardCharsets.UTF_8) );
//
//        try (OutputStream stream = new FileOutputStream(testfile)) {
//            stream.write(encodedText);
//        }
//
//


        File testfile = new File("testfile.txt");
        InputStream stream = new FileInputStream(testfile);
        byte[] bytes = stream.readAllBytes();
        bytes = Base64.getDecoder().decode(bytes);
        String readIn = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(readIn);


    }


}
