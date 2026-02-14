import javax.swing.*;
import java.awt.*;

public class Main{
    public static void main(String[] args){

        //Create panel ........
        JFrame frame = new JFrame("JavaInk");
        frame.setLocation(500,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(500,500);
        frame.setLayout(null);

        //Image for frist button
        ImageIcon icon = new ImageIcon("src/files.png");
        Image image = icon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(image);

        //Image for second button
        ImageIcon icon3 = new ImageIcon("src/surround.png");
        Image image2 = icon3.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        ImageIcon icon4 = new ImageIcon(image2);

        //Image for visible action listenerp
        ImageIcon icon5 = new ImageIcon("src/img.png");
        Image image3 = icon5.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        ImageIcon icon6 = new ImageIcon(image3);

        //Label for icon6(visible action listener) set as part of frame
        JLabel label = new JLabel(icon6);
        label.setBounds(100,100,100,100);
        label.setVisible(false);
        frame.add(label);

        //First button
        JButton button = new JButton();
        button.setBounds(0,0,90,20);
        button.setText("File");
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setFocusable(false);
        button.setIcon(icon2);
        button.setHorizontalAlignment(JButton.LEFT);
        button.setIconTextGap(10);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setForeground(Color.RED);
        button.addActionListener(e -> { System.out.println("Welcome to JavaSwing"); });

        //Second button
        JButton button2 = new JButton();
        button2.setBounds(90,0,110,20);
        button2.setIcon(icon4);
        button2.setText("Click");
        button2.setHorizontalAlignment(JButton.LEFT);
        button2.setHorizontalTextPosition(JButton.RIGHT);
        button2.setFocusable(false);
        button2.setIconTextGap(10);
        button2.setBorder(BorderFactory.createEtchedBorder());
        button2.addActionListener(e -> { label.setVisible(true); });

        //Include buttons to panel
        frame.add(button);
        frame.add(button2);


        frame.setVisible(true);
    }
}
