import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * Created by hsenid on 5/19/17.
 */
public class Copier {

    private JCheckBox moveCheckBox;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton startButton;
    private JPanel mainPanel;
    private JTextField toField;
    private JTextField fromField;


    public Copier() {
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String fromFieldContent = fromField.getText();
                String toFieldContent = toField.getText();
                File varTmpDir = new File(fromFieldContent);
                File destination = new File(toFieldContent);

                if (fromFieldContent.equals("") || toFieldContent.equals("")) {
                    String messageForNull = (fromFieldContent.equals("") && toFieldContent.equals("") ? "Both paths are empty" : (fromFieldContent.equals("") ? "From path is Empty" : "To path empty"));
                    JOptionPane.showMessageDialog(null, messageForNull);
                } else {

                    if (moveCheckBox.isSelected()) {


                        if (varTmpDir.exists() && destination.isDirectory()) {
                            if (varTmpDir.renameTo(new File(destination.toURI().toString() + varTmpDir.getName()))) {
                                JOptionPane.showMessageDialog(null, "File moved successful");
                            }
//                            JOptionPane.showMessageDialog(null, destination.toURI().toString() + varTmpDir.getName());
                        } else {
                            JOptionPane.showMessageDialog(null, "File Move failed");
                        }
//                        JOptionPane.showMessageDialog(null, "File moved ??");
                    } else {

                        if (varTmpDir.exists() && destination.isDirectory()) {
                            Path source = Paths.get(varTmpDir.toURI().getPath());
                            Path nwdir = Paths.get(destination.toURI().getPath());
                            try {
                                Files.copy(source, nwdir.resolve(source.getFileName()), REPLACE_EXISTING);
                                System.out.println("File Copied");
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, (!varTmpDir.exists() && !destination.isDirectory() ? "Both destinations do not exist" : (!varTmpDir.exists() ? "Source file does not exists" : "File copy destination is not a Folder")));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, (!varTmpDir.exists() && !destination.isDirectory() ? "Both destinations do not exist" : (!varTmpDir.exists() ? "Source file does not exists" : "File copy destination is not a Folder")));
                        }
                    }


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
}
