package editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textEditor.currentFile = selectedFile;
            try {
                String content = Files.readString(selectedFile.toPath());
                textArea.setText(content);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!selectedPath.toLowerCase().endsWith(".txt")) {
                    selectedPath += ".txt";
                }
                textEditor.currentFile = new File(selectedPath);
            } else {
                return;
            }
        }

        try {
            Files.writeString(textEditor.currentFile.toPath(), textArea.getText());
            JOptionPane.showMessageDialog(textEditor, "File saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(textEditor, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textEditor.currentFile = null;
        textArea.setText("");
    }
}