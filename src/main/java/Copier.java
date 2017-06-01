import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Copier {

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
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
//                        showProgress(fileCopyProgressBar, 76);

                        File src = new File(sourceLocation);
                        File dst = new File(destinationLocation);

                        try {
                            dst.createNewFile();

                            InputStream in = new FileInputStream(src);
                            OutputStream out = new FileOutputStream(dst);
                            long expectedBytes = src.length();
                            long totalBytesCopied = 0;
                            byte[] buf = new byte[1024];
                            int len = 0;


                            while ((len = in.read(buf)) > 0) {

                                out.write(buf, 0, len);
                                totalBytesCopied += len;
                                showProgress(totalBytesCopied, expectedBytes);

                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }
                }

            }

        });

        sourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile = new JFileChooser();
                selectFile.setDialogTitle("Select a file");
                selectFile.showDialog(null, "Ok");
                sourceLocation = selectFile.getSelectedFile().getAbsolutePath();
                fromField.setText(sourceLocation);
            }
        });

        destinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile = new JFileChooser();
                selectFile.setDialogTitle("Copy to");
                selectFile.showDialog(null, "Ok");
                selectFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                selectFile.setAcceptAllFileFilterUsed(false);
                destinationLocation = selectFile.getSelectedFile().getAbsolutePath();
                toField.setText(destinationLocation);

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
