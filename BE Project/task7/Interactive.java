import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Interactive extends JFrame {
    private JTextField keyTextField;
    private JTextArea notesTextArea;

    public Interactive() {
        setTitle("Interactive GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("<");
        JButton syncButton = new JButton("SYNC");
        JButton nextButton = new JButton(">");
        buttonPanel.add(backButton);
        buttonPanel.add(syncButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Panel for key text field
        JPanel keyPanel = new JPanel();
        keyTextField = new JTextField(20);
        keyPanel.add(keyTextField);
        add(keyPanel, BorderLayout.CENTER);

        // Panel for notes text area
        JPanel notesPanel = new JPanel();
        notesTextArea = new JTextArea(20, 100);
        notesTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        notesPanel.add(scrollPane);
        add(notesPanel, BorderLayout.SOUTH);

        // Update button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform update action here
                String key = keyTextField.getText();
                // Example: Printing key 
                System.out.println("Key: " + key);
               
            }
        });
        add(updateButton, BorderLayout.WEST);

        // Access file from URL-1a
        JButton accessFileButton1a = new JButton("Access File 1a");
        accessFileButton1a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("https://ibics.co.in/mescoep_groupa/interactive/Abc.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(accessFileButton1a, BorderLayout.EAST);

        // Access file from URL-1b
        JButton accessFileButton1b = new JButton("Access File 1b");
        accessFileButton1b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("https://ibics.co.in/mescoep_groupa/interactive/ia_list.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(accessFileButton1b, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interactive interactive = new Interactive();
                interactive.setVisible(true);
            }
        });
    }
}