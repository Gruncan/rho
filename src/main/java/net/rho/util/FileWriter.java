package net.rho.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FileWriter {


    public static boolean write(String text, String filePath) {
        File file = new File(filePath);
        byte[] encodedText = Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8));

        try (OutputStream stream = new FileOutputStream(file)) {
            stream.write(encodedText);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public static String read(String filePath) {
//        File testfile = new File(filePath);
////        String s = null;
////        try (InputStream stream = new FileInputStream(testfile)) {
////            byte[] bytes = stream.readAllBytes();
////            bytes = Base64.getDecoder().decode(bytes);
////            s = new String(bytes, StandardCharsets.UTF_8);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
        return "";
    }


}
