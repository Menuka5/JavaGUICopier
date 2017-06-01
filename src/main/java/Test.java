import javax.swing.*;
import java.io.*;

/**
 * Created by hsenid on 5/31/17.
 */
public class Test {
    public static void main(String[] args) {

        File src = new File("/home/hsenid/Copier/src/main/java/test");
        File dst = new File("/home/hsenid/Documents/Movefiles/copy");

        try {

            dst.createNewFile();
            InputStream in = new FileInputStream(src);


            OutputStream out = new FileOutputStream(dst);
            long expectedBytes = src.length();
//            System.out.println("length="+src.length());


            long totalBytesCopied = 0;
            byte[] buf = new byte[1024];
            int len = 0;
            int progress = 10;

            while ((len = in.read(buf)) > 0) {
                System.out.println(in.read(buf));
                out.write(buf, 0, len);
                totalBytesCopied += len;
//                progress += (int) Math.round(((double) totalBytesCopied / (double) expectedBytes) * 100);
//                progressBar.setValue(progress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startCopingx(String from, String to, String copyOrMove, JProgressBar progressBar) {
        progressBar.setValue(0);
        progressBar.setVisible(true);
        File src = new File(from);
        File dst = new File(to);
        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);
            long expectedBytes = src.length();
            long totalBytesCopied = 0;
            byte[] buf = new byte[1024];
            int len = 0;
            int progress = 10;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
                totalBytesCopied += len;
                progress += (int) Math.round(((double) totalBytesCopied / (double) expectedBytes) * 100);
                progressBar.setValue(progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
