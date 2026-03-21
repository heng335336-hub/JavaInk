import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {

    static HashMap <String,String> map = new HashMap();

    public static void main(String[] args) {
        // /////////////////////////// ah chkout
        JFrame frame = new JFrame("JavaInk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40));
        frame.setTitle("JavaInk - Untitled");
        // ///////////////////////////

        //App Icon
        ImageIcon icon = new ImageIcon("javaink.png");
        frame.setIconImage(icon.getImage());

        //New file icon
        ImageIcon newfile_icon = new ImageIcon("newfile.png");
        Image nfi = newfile_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        newfile_icon = new ImageIcon(nfi);

        //Open file icon
        ImageIcon open_icon = new ImageIcon("open.png");
        Image oi = open_icon.getImage().getScaledInstance(17,18, Image.SCALE_AREA_AVERAGING);
        open_icon = new ImageIcon(oi);

        //Save file icon
        ImageIcon save_icon = new ImageIcon("save.png");
        Image si = save_icon.getImage().getScaledInstance(17,18, Image.SCALE_AREA_AVERAGING);
        save_icon = new ImageIcon(si);

        //Save as icon
        ImageIcon saveas_icon = new ImageIcon("saveas.png");
        Image sai = saveas_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        saveas_icon = new ImageIcon(sai);

        //Close Tab Icon
        ImageIcon closetab_icon = new ImageIcon("closetab.png");
        Image cti = closetab_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        closetab_icon = new ImageIcon(cti);

        //Exit icon
        ImageIcon exit_icon = new ImageIcon("exit.png");
        Image ei = exit_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        exit_icon = new ImageIcon(ei);

        // Insert Image icon
        ImageIcon insertimg_icon = new ImageIcon("insertimage.png");
        Image iimg = insertimg_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        insertimg_icon = new ImageIcon(iimg);

       // Insert Shape icon
        ImageIcon insertshape_icon = new ImageIcon("insertshape.png");
        Image ishape = insertshape_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        insertshape_icon = new ImageIcon(ishape);

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
        newfile.setIcon(newfile_icon);
        JMenuItem open = new JMenuItem("Open");
        open.setIcon(open_icon);
        JMenuItem save = new JMenuItem("Save");
        save.setIcon(save_icon);
        JMenuItem saveas = new JMenuItem("Save as");
        saveas.setIcon(saveas_icon);
        JMenuItem saveall = new JMenuItem("Save all");
        JMenuItem closetab = new JMenuItem("Close Tab");
        closetab.setIcon(closetab_icon);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setIcon(exit_icon);

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
        insertimage.setIcon(insertimg_icon);

        JMenuItem insertshape = new JMenuItem("Insert Shape");
        insertshape.setIcon(insertshape_icon);
        
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
                new_file_func(newfile, textarea, frame, tab );
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
                save_as_func(chooseFile, frame, tab );
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

        JScrollPane scrollpane = new JScrollPane(instant_textarea);
        tab.addTab("Untitled", scrollpane);

        int index = tab.indexOfComponent(scrollpane);
        JLabel instant_label = new JLabel("Untitled");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
        panel.setOpaque(false);

        JButton button = new JButton("X");
        button.setForeground(new Color(94, 94, 94));
        button.setMargin(new Insets(0,1,0,1));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            int i =  tab.indexOfTabComponent(panel); //number of top UI
            if(i != -1){
                tab.remove(i);
            }
        });

        panel.add(instant_label);
        panel.add(button);

        tab.setTabComponentAt(index, panel); //top UI

        map.put("Untitled", null);



        frame.setVisible(true);

    }

    public static void new_file_func(JMenuItem newfile, JTextArea textarea, JFrame frame, JTabbedPane tab) {

        FileWriter currentFile = null;

        textarea.setText("");
        currentFile = null;
        frame.setTitle("Java Ink - New File");

        JTextArea new_instant_textarea = new JTextArea();
        new_instant_textarea.setBackground(new Color(40, 40, 40));
        new_instant_textarea.setForeground(new Color(255, 255, 255));
        new_instant_textarea.setCaretColor(Color.WHITE);

        JScrollPane scrollpane = new JScrollPane(new_instant_textarea);
        tab.addTab("Untitled", scrollpane);

        int index = tab.indexOfComponent(scrollpane);
        JLabel label = new JLabel("Untitled");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
        panel.setOpaque(false);

        JButton button = new JButton("X");
        button.setForeground(new Color(94, 94, 94));
        button.setMargin(new Insets(0,1,0,1));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            int i =  tab.indexOfTabComponent(panel); //number of top UI
            if(i != -1){
                tab.remove(i);
            }
        });

        panel.add(label);
        panel.add(button);

        tab.setTabComponentAt(index, panel); //top UI

        map.put("Untitled", null);

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

                JScrollPane scrollpane = new JScrollPane(new_textarea);
                tab.addTab(file.getName(), scrollpane);

                int index = tab.indexOfComponent(scrollpane);
                JLabel label = new JLabel(file.getName());

                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
                panel.setOpaque(false);

                JButton button = new JButton("X");
                button.setForeground(new Color(94, 94, 94));
                button.setMargin(new Insets(0,1,0,1));
                button.setFocusPainted(false);
                button.setFocusable(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.addActionListener(e -> {
                    int i =  tab.indexOfTabComponent(panel); //number of top UI
                    if(i != -1){
                        tab.remove(i);
                    }
                });

                panel.add(label);
                panel.add(button);

                tab.setTabComponentAt(index, panel); //top UI

                map.put(file.getName(), file.getAbsolutePath());

                frame.setTitle("Java Ink - " + file.getName());

                for(String key : map.keySet()){
                    System.out.println(key);
                }

                for(String value : map.values()){
                    System.out.println(value);
                }


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

    public static void save_as_func(JFileChooser chooseFile, JFrame frame, JTabbedPane tab) {
        chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
        int result = chooseFile.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooseFile.getSelectedFile();
            System.out.println("First " + file);

            JScrollPane pane = (JScrollPane) tab.getSelectedComponent();
            JViewport viewport = pane.getViewport();
            JTextArea current_textarea = (JTextArea) viewport.getView();

            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                bw.write(current_textarea.getText());
                bw.close();
                frame.setTitle("Java Ink - " + file.getName());
            }catch(IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save as", "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("Second " + chooseFile.getSelectedFile().getAbsolutePath());

            int index = tab.getSelectedIndex();
            String currentTitle = tab.getTitleAt(index);
            File file_path = chooseFile.getSelectedFile();

            if(currentTitle.equals("Untitled")){

                tab.setTitleAt(index, file_path.getName());

                map.remove("Untitled");
                map.put(file_path.getName(), file_path.getAbsolutePath());
            }
            else{

                JTextArea new_textarea = new JTextArea();
                new_textarea.setText(current_textarea.getText());

                new_textarea.setBackground(new Color(40, 40, 40));
                new_textarea.setForeground(new Color(255, 255, 255));
                new_textarea.setCaretColor(Color.WHITE);

                tab.addTab(file_path.getName(), new JScrollPane(new_textarea));

                map.put(file_path.getName(), file_path.getAbsolutePath());
            }
        }

    }


}
