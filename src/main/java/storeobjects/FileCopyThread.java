package storeobjects;

import javax.swing.*;
import java.io.*;

public class FileCopyThread extends Thread {
    private String sourceLocation;
    private String destinationLocation;
    private JProgressBar fileCopyProgressBar;

    public FileCopyThread(String sourceLocation, String destinationLocation, JProgressBar fileCopyProgressBar) {
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.fileCopyProgressBar = fileCopyProgressBar;
    }


    @Override
    public void run() {

        fileCopyProgressBar.setVisible(true);

        File src = new File(sourceLocation);
        File dst = new File(destinationLocation);

        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst);) {

            dst.createNewFile();
            long expectedBytes = src.length();
            long totalBytesCopied = 0;
            byte[] buf = new byte[256];
            int len = 0;
            fileCopyProgressBar.setVisible(true);

            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);
                totalBytesCopied += len;
                showProgress(totalBytesCopied, expectedBytes);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void showProgress(long size, long totalSize) {

        int sizeofProgressBar = (int) ((size / totalSize) * 100);

        fileCopyProgressBar.setVisible(true);

        fileCopyProgressBar.setValue(sizeofProgressBar);
    }


}
