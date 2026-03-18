import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;

public class Main {

    static HashMap <String,String> map = new HashMap();

    public static void main(String[] args) {
        // ///////////////////////////mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
        JFrame frame = new JFrame("Java Ink");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40));
        frame.setTitle("JavaInk - Untitled");
        // ///////////////////////////
        ImageIcon icon = new ImageIcon("javaink.png");
        frame.setIconImage(icon.getImage());
        // ///////////////////////////
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu view  = new JMenu("View");
        JMenu insert = new JMenu("Insert");
        JMenu theme = new JMenu("Theme");
        JMenu help = new JMenu("Help");
        // /////////////////////////////
        ImageIcon icon_java = new ImageIcon("javaink.png");
        frame.setIconImage(icon_java.getImage());
        // /////////////////////////////
/// /////////////////////////////////////////////////
        JMenuItem newfile = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveas = new JMenuItem("Save as");
        JMenuItem saveall = new JMenuItem("Save all");
        JMenuItem closetab = new JMenuItem("Close Tab");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(newfile); //actionListened
        file.add(open); //actionListioned
        file.add(save); //actionListioned
        file.add(saveas);
        file.add(closetab);
        file.add(exit);

/// ///////////////////////////////////////////////
        JMenuItem find = new JMenuItem("Find");
        JMenuItem findnext = new JMenuItem("Find Next");
        JMenuItem findprev = new JMenuItem("Find Previous");
        JMenuItem replace = new JMenuItem("Replace");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem selectall = new JMenuItem("Select All");
        JMenuItem paste = new JMenuItem("Paste");
        edit.add(find);
        edit.add(findnext);
        edit.add(findprev);
        edit.add(replace);
        edit.add(cut);
        edit.add(copy);
        edit.add(selectall);
        edit.add(paste);
////////////////////////////////////////////////////////////
        JMenuItem zoomin = new JMenuItem("Zoom In");
        JMenuItem zoomout = new JMenuItem("Zoom Out");
        JMenuItem resetzoom = new JMenuItem("Reset Zoom");
        view.add(zoomin);
        view.add(zoomout);
        view.add(resetzoom);
///////////////////////////////////////////////////////////
        JMenuItem insertimage = new JMenuItem("Insert Image");
        JMenuItem insertshape = new JMenuItem("Insert Shape");
        insert.add(insertimage);
        insert.add(insertshape);
///////////////////////////////////////////////////////////
        JMenuItem textscolor = new JMenuItem("Texts Color");
        JMenuItem alltextscolor = new JMenuItem("All Texts Color");
        JMenuItem backgroundcolor = new JMenuItem("Background Color");
        theme.add(textscolor);
        theme.add(alltextscolor);
        theme.add(backgroundcolor);


////////////////////////////////////////////////////////////
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    null,
                    "If you find this app helpful pls subcribe",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        help.add(about);
        // ////////////////////////////////
        JTextArea textarea = new JTextArea();
        textarea.setEditable(true);
        textarea.setBackground(new Color(40, 40, 40));
        textarea.setForeground(new Color(255, 255, 255));
        textarea.setCaretColor(Color.WHITE);
        // ////////////////////////////////
        JPanel underinfo = new JPanel();
        underinfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        underinfo.setBackground(new Color(76, 76, 76));
        JLabel label = new JLabel();
        label.setForeground(new Color(255, 255, 255));
        label.setText("Standard     |   UTF-8   |     WINDOW");
        underinfo.add(label);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(view);
        menubar.add(insert);
        menubar.add(theme);
        menubar.add(help);


        /// //////////////////////Important components/////////////////////////////
        JFileChooser chooseFile = new JFileChooser();
        JScrollPane scroll = new JScrollPane(textarea);
        JTabbedPane tab = new JTabbedPane();
        /// ///////////////////////////////////////////////////
        newfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new_file_func(newfile, textarea, frame );
            }
        });

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                open_file_func(chooseFile, frame, tab, scroll );
            }
        });

        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save_file_func(chooseFile, frame, tab );
            }
        });

        saveas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save_as_func(chooseFile, textarea, frame );
            }
        });

        tab.setPreferredSize(new Dimension(100, 20));


        frame.setJMenuBar(menubar);
        //frame.add(scroll, BorderLayout.CENTER);
        frame.add(tab, BorderLayout.CENTER);
        frame.add(underinfo, BorderLayout.SOUTH);

        JTextArea instant_textarea = new JTextArea();
        instant_textarea.setBackground(new Color(40, 40, 40));
        instant_textarea.setForeground(new Color(255, 255, 255));
        instant_textarea.setCaretColor(Color.WHITE);
        tab.addTab("Untitled", new JScrollPane(instant_textarea));
        map.put("Untitled", null);

        frame.setVisible(true);

    }

    public static void new_file_func(JMenuItem newfile, JTextArea textarea, JFrame frame) {

        FileWriter currentFile = null;

        textarea.setText("");
        currentFile = null;
        frame.setTitle("Java Ink - New File");
    }

    public static void open_file_func(JFileChooser chooseFile,  JFrame frame, JTabbedPane tab, JScrollPane scroll) {

        JTextArea new_textarea = new JTextArea();
        new_textarea.setBackground(new Color(40, 40, 40));
        new_textarea.setForeground(new Color(255, 255, 255));
        new_textarea.setCaretColor(Color.WHITE);

        chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
        int result = chooseFile.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooseFile.getSelectedFile(); //path
            System.out.println(file);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                new_textarea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    new_textarea.append(line + "\n");
                }

                reader.close();

                tab.addTab("x  " + file.getName(), new JScrollPane(new_textarea));

                tab.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        int index = tab.getSelectedIndex();

                        if (index != -1) {

                            if (e.getX() < 30) {
                                tab.remove(index);
                            }
                        }
                    }
                });
                map.put(file.getName(), file.getAbsolutePath());

                frame.setTitle("Java Ink - " + file.getName());


            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error opening file");
            }
        }
    }

    public static void save_file_func(JFileChooser chooseFile,  JFrame frame, JTabbedPane tab) {

        int index = tab.getSelectedIndex();
        String file_name = tab.getTitleAt(index);
        String file_path = map.get(file_name);

        JScrollPane pane = (JScrollPane) tab.getSelectedComponent();
        JViewport viewport = pane.getViewport();
        JTextArea textarea = (JTextArea) viewport.getView();

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file_path));
            bw.write(textarea.getText());
            bw.close();
        }catch(IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to save file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void save_as_func(JFileChooser chooseFile, JTextArea textarea, JFrame frame) {
        chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
        int result = chooseFile.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooseFile.getSelectedFile();

            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(textarea.getText());
                bw.close();
                frame.setTitle("Java Ink - " + file.getName());
            }catch(IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save as", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}