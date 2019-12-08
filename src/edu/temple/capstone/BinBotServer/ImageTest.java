//package edu.temple.capstone.BinBotServer;
//
//import org.apache.commons.io.FileUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.Base64;
//
//
//public class ImageTest {
//
//    private static final int PORT = 7001;
//
//    public static void main(String[] args) throws IOException {
//
//        String jpg_file1 = "res/jpg_b64.txt";
//        String jpg_file3 = "res/test.jpg";
//        String jpg_file4 = "res/stream.txt";
//
////        String jpg_str = readFileAsString(jpg_file1);
////        byte[] jpg_b64 = readFileAsByteArray(jpg_file1);
//
//        byte[] stream = FileUtils.readFileToByteArray(new File(jpg_file4));
//        byte[] b64_stream = Base64.getEncoder().encode(stream);
//        String encoded = Base64.getEncoder().encodeToString(b64_stream);
//
//        BufferedImage bufferedImage = base64ToBufferedImage(jpg_file3);
////
////        try {
////            File outputFile = new File("image.jpg");
////            ImageIO.write(bufferedImage, "jpg", outputFile);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
//        System.out.println("Breakpoint");
//    }
//
//    public static String readFileAsString(String filename) {
//        InputStream is = null;
//        String fileAsString = null;
//        try {
//            is = new FileInputStream(filename);
//
//            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
//
//            String line = buf.readLine();
//            StringBuilder sb = new StringBuilder();
//
//            while (line != null) {
//                sb.append(line).append("\n");
//                line = buf.readLine();
//            }
//
//            fileAsString = sb.toString();
//            System.out.println("Contents : " + fileAsString);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return fileAsString;
//    }
//
//    public static byte[] readFileAsByteArray(String filename) {
//
//        File file = new File(filename);
//        byte[] arr = new byte[(int) file.length()];
//
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//            fis.read(arr);
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return arr;
//    }
//
//    public static BufferedImage base64ToBufferedImage (String filename) {
//        byte[] fileContent = new byte[0];
//        BufferedImage image = null;
//        try {
//
//            fileContent = FileUtils.readFileToByteArray(new File(filename));
//            String encodedString = Base64.getEncoder().encodeToString(fileContent);
//
//            ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
//            image = ImageIO.read(bis);
//            bis.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return image;
//    }
//}
