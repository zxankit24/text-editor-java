import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public TextEditor() {
        // Set up the JFrame
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a JTextArea for text input
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create a JScrollPane to hold the JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create a JMenuBar with File and Edit menus
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Create File menu items
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        // Add action listeners to the menu items
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        // Set the menu bar for the JFrame
        setJMenuBar(menuBar);

        // Create a JFileChooser for opening and saving files
        fileChooser = new JFileChooser();

        // Display the JFrame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("New")) {
            textArea.setText("");
        } else if (command.equals("Open")) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    reader.close();
                    textArea.setText(sb.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Save")) {
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Exit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Run the GUI code on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
            }
        });
    }
}
