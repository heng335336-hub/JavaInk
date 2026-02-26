import javax.swing.*;
import java.awt.*;
public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("JavaInk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500); /////////
        frame.setResizable(true);
        frame.setLocation(550,100);
        frame.setLayout(null);

        int x = 200;
        ImageIcon icon = new ImageIcon("src/javaink.png");
        Image image = icon.getImage().getScaledInstance(211 + x,178 + x,Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        frame.setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setSize(500,37);
        panel.setBackground(Color.GRAY);

        JButton file = new JButton("File");
        file.setFocusable(false);
        file.setPreferredSize(new Dimension(56,26));
        JButton view = new JButton("View");
        view.setFocusable(false);
        view.setPreferredSize(new Dimension(60,26));
        JButton edit = new JButton("Edit");
        edit.setFocusable(false);
        edit.setPreferredSize(new Dimension(56,26));
        JButton theme = new JButton("Theme");
        theme.setFocusable(false);
        theme.setPreferredSize(new Dimension(73,26));

        file.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    "The File Button haven't develope by the group owner yet",
                    "Report",
                    JOptionPane.PLAIN_MESSAGE
                    );
        });

        view.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please wait until the group owner develope",
                    "Report",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        edit.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    "Are you serious?",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        });

        theme.addActionListener(e -> {
            JOptionPane.showConfirmDialog(
                    frame,
                    "Help the developer develope?",
                    "Question",
                    JOptionPane.YES_NO_OPTION
            );
        });

        panel.add(file);
        panel.add(view);
        panel.add(edit);
        panel.add(theme);

        frame.add(panel);
        frame.setVisible(true);
    }
}