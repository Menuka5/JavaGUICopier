import storeobjects.FileCopyThread;
import storeobjects.ThreadObjectStore;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Copier {

    ThreadObjectStore threadObjectStore = new ThreadObjectStore();
    private Path pathOfSource = null;
    private Path pathOfDestination = null;
    private JCheckBox moveCheckBox;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton startButton;
    private JPanel mainPanel;
    private JTextField toField;
    private JTextField fromField;
    private JButton sourceButton;
    private JButton destinationButton;
    private JProgressBar fileCopyProgressBar;
    private JFileChooser selectFile;
    private String sourceLocation;
    private String destinationLocation;

    public Copier() {
        fileCopyProgressBar.setVisible(false);

        startButton.addActionListener(e -> {

            String fromFieldContent = fromField.getText();
            String toFieldContent = toField.getText();

            if (fromFieldContent.equals("") || toFieldContent.equals("")) {

                String messageForNull = (fromFieldContent.equals("") && toFieldContent.equals("") ? "Both paths are empty" : (fromFieldContent.equals("") ? "From path is Empty" : "To path empty"));
                JOptionPane.showMessageDialog(null, messageForNull);

            } else {
                pathOfSource = Paths.get(sourceLocation);
                pathOfDestination = Paths.get(destinationLocation);

                if (moveCheckBox.isSelected()) {

                    try {
                        Files.move(pathOfSource, pathOfDestination, StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "File move Successful!!!");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "File move failed!!!");
                    }
                } else {

                    if (threadObjectStore.getStoreThread() == null) {
                        FileCopyThread fileCopyThread = new FileCopyThread(sourceLocation, destinationLocation, fileCopyProgressBar);
                        threadObjectStore.setStoreThread(fileCopyThread);
                        threadObjectStore.getStoreThread().start();
                    } else {
                        threadObjectStore.getStoreThread().notify();
                    }


                }
            }
        });

        sourceButton.addActionListener(e -> {
            selectFile = new JFileChooser();
            selectFile.setDialogTitle("Select a file");
            selectFile.showDialog(null, "Ok");
            sourceLocation = selectFile.getSelectedFile().getAbsolutePath();
            fromField.setText(sourceLocation);
        });

        destinationButton.addActionListener(e -> {
            selectFile = new JFileChooser();
            selectFile.setDialogTitle("Copy to");
            selectFile.showDialog(null, "Ok");
            selectFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            selectFile.setAcceptAllFileFilterUsed(false);
            destinationLocation = selectFile.getSelectedFile().getAbsolutePath();
            toField.setText(destinationLocation);

        });

        stopButton.addActionListener(e -> {
            if (threadObjectStore.getStoreThread() == null) {
                JOptionPane.showMessageDialog(null, "There is no file copy process -> Stop failed.");
            } else {
                try {
                    threadObjectStore.getStoreThread().interrupt();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        pauseButton.addActionListener(e -> {
            if (threadObjectStore.getStoreThread() == null) {
                JOptionPane.showMessageDialog(null, "There is no file copy process -> Pause failed.");
            } else {
                try {
                    threadObjectStore.getStoreThread().wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Copier");
        jFrame.setContentPane(new Copier().mainPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


    public void showProgress(long size, long totalSize) {

        int sizeofProgressBar = (int) ((size / totalSize) * 100);

        fileCopyProgressBar.setVisible(true);

        fileCopyProgressBar.setValue(sizeofProgressBar);
    }
}
