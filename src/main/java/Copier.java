import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * Created by hsenid on 5/19/17.
 */
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
    private JFileChooser selectFile;
    private String sourceLocation;
    private String destinationLocation;
    public Copier() {

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
                        try {
                            Files.copy(pathOfSource, pathOfDestination, StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(null, "File copy successful!!!");
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "File copy failed!!!");
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
}
