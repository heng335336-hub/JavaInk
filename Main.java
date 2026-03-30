import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;

public class Main {

    static HashMap <String,String> map = new HashMap();

    public static void main(String[] args) {
        // /////////////////////////// Redo Undo Icon(phanin,gekeng) + images
        JFrame frame = new JFrame("JavaInk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40));
        frame.setTitle("JavaInk - Untitled");
        // ///////////////////////////

        //App Icon   //(Heng)
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

        //Save all Icon
        ImageIcon save_all = new ImageIcon("save all.png");
        Image sv = save_all.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        save_all = new ImageIcon(sv);

        //Close Tab Icon
        ImageIcon closetab_icon = new ImageIcon("closetab.png");
        Image cti = closetab_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        closetab_icon = new ImageIcon(cti);

        //Exit icon
        ImageIcon exit_icon = new ImageIcon("exit.png");
        Image ei = exit_icon.getImage().getScaledInstance(20,21, Image.SCALE_AREA_AVERAGING);
        exit_icon = new ImageIcon(ei);

        // Insert Image icon (Phanin)
        ImageIcon insertimg_icon = new ImageIcon("insertimage.png");
        Image iimg = insertimg_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        insertimg_icon = new ImageIcon(iimg);

        // Insert Shape icon
        ImageIcon insertshape_icon = new ImageIcon("insertshape.png");
        Image ishape = insertshape_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        insertshape_icon = new ImageIcon(ishape);

        // view icon  (Gekeng)
        ImageIcon zoomin_icon=new ImageIcon("Free_black_zoom_in_icon_vector_png_cad_-_Pixsector__Free_vector_images__mockups__PSDs_and_photos-removebg-preview.png");
        Image zi = zoomin_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        zoomin_icon = new ImageIcon(zi);
        ImageIcon zoomout_icon=new ImageIcon("Free_black_zoom_out_icon_vector_png_cad_-_Pixsector__Free_vector_images__mockups__PSDs_and_photos-removebg-preview.png");
        Image zo = zoomout_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        zoomout_icon = new ImageIcon(zo);
        ImageIcon resetzoom_icon=new ImageIcon("reset_Icon_-_Free_PNG___SVG_1921187_-_Noun_Project-removebg-preview.png");
        Image rz = resetzoom_icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        resetzoom_icon = new ImageIcon(rz);

        //Theme Icon (Vansak)
        //Text color Icon
        ImageIcon Text_color_Icon = new ImageIcon("text-format.png");
        Image TA = Text_color_Icon.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Text_color_Icon = new ImageIcon(TA);
        //All text color
        ImageIcon all_text_color = new ImageIcon("bucket.png");
        Image TAC = all_text_color.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        all_text_color = new ImageIcon(TAC);
        //bg color
        ImageIcon bg_color = new ImageIcon("paint.png");
        Image BG = bg_color.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        bg_color = new ImageIcon(BG);

        //Edit Icon (Vansak)
        //Find
        ImageIcon Find = new ImageIcon("find.png");
        Image FI = Find.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Find = new ImageIcon(FI);

        //Find next
        ImageIcon FindNext = new ImageIcon("find_next.png");
        Image FN = FindNext.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        FindNext = new ImageIcon(FN);

        //Find Previous
        ImageIcon FindPrev = new ImageIcon("find_prev.png");
        Image FP = FindPrev.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        FindPrev = new ImageIcon(FP);

        //Replace
        ImageIcon Replace = new ImageIcon("replace.png");
        Image RP = Replace.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Replace = new ImageIcon(RP);

        //Cut
        ImageIcon Cut = new ImageIcon("cut.png");
        Image CT = Cut.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Cut = new ImageIcon(CT);

        //Copy
        ImageIcon Copy = new ImageIcon("copy.png");
        Image CY = Replace.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Copy = new ImageIcon(CY);

        //Select all
        ImageIcon Selectall = new ImageIcon("select_all.png");
        Image SA = Replace.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Selectall = new ImageIcon(SA);

        //Paste
        ImageIcon Paste = new ImageIcon("paste.png");
        Image P = Paste.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Paste = new ImageIcon(P);

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
        saveall.setIcon(save_all);
        JMenuItem closetab = new JMenuItem("Close Tab");
        closetab.setIcon(closetab_icon);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setIcon(exit_icon);

        file.add(newfile); //actionListened
        file.add(open); //actionListioned
        file.add(save); //actionListioned
        file.add(saveas); //actionListioned
        file.add(saveall);
        file.add(closetab);
        file.add(exit);

/// ///////////////////////////////////////////////
        JMenuItem find = new JMenuItem("Find");
        find.setIcon(Find);
        JMenuItem findnext = new JMenuItem("Find Next");
        findnext.setIcon(FindNext);
        JMenuItem findprev = new JMenuItem("Find Previous");
        findprev.setIcon(FindPrev);
        JMenuItem replace = new JMenuItem("Replace");
        replace.setIcon(Replace);
        JMenuItem cut = new JMenuItem("Cut");
        cut.setIcon(Cut);
        JMenuItem copy = new JMenuItem("Copy");
        copy.setIcon(Copy);
        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setIcon(Selectall);
        JMenuItem paste = new JMenuItem("Paste");
        paste.setIcon(Paste);
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
        /// /////////////////////////////////////
        zoomin.setIcon(zoomin_icon);
        zoomout.setIcon(zoomout_icon);
        resetzoom.setIcon(resetzoom_icon);
///////////////////////////////////////////////////////////
        JMenuItem insertimage = new JMenuItem("Insert Image");
        insertimage.setIcon(insertimg_icon);

        JMenuItem insertshape = new JMenuItem("Insert Shape");
        insertshape.setIcon(insertshape_icon);

        insert.add(insertimage);
        insert.add(insertshape);

///////////////////////////////////////////////////////////
        JMenuItem textscolor = new JMenuItem("Texts Color");
        textscolor.setIcon(Text_color_Icon);
        JMenuItem alltextscolor = new JMenuItem("All Texts Color");
        alltextscolor.setIcon(all_text_color);
        JMenuItem backgroundcolor = new JMenuItem("Background Color");
        backgroundcolor.setIcon(bg_color);
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
                save_file_func(chooseFile, frame, tab);
            }
        });

        saveas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save_as_func(chooseFile, frame, tab);
            }
        });

        saveall.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save_all_func(tab, chooseFile, frame);
            }
        });

        closetab.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                close_tab(tab);
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

        //undo redo
        UndoManager undoManager = new UndoManager();
        instant_textarea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        instant_textarea.getActionMap().put("undo", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        instant_textarea.getActionMap().put("redo", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });

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

        //undo redo
        UndoManager undoManager = new UndoManager();
        new_instant_textarea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        new_instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        new_instant_textarea.getActionMap().put("undo", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        new_instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        new_instant_textarea.getActionMap().put("redo", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });

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

        //undo redo
        UndoManager undoManager = new UndoManager();
        new_textarea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        new_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        new_textarea.getActionMap().put("undo", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        new_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        new_textarea.getActionMap().put("redo", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });

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

            int index = tab.getSelectedIndex();  //get index of the tab and set rename from "untitled"
            String currentTitle = tab.getTitleAt(index);
            File file_path = chooseFile.getSelectedFile();

            if(currentTitle.equals("Untitled")){

                Component tabComponent = tab.getTabComponentAt(index);
                JPanel panel = (JPanel) tabComponent;
                JLabel label = (JLabel) panel.getComponent(0); // first component(0) is label
                label.setText(file_path.getName());

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

    static void save_all_func(JTabbedPane tab, JFileChooser chooseFile, JFrame frame){
        int tab_amount = tab.getTabCount();
        int path_amount = map.size();
        System.out.println("tab_amount = " + tab_amount);
        System.out.println("path_amount = " + path_amount);

        for(int i = 0; i < tab_amount; i++){
            if(tab.getTitleAt(i).equals("Untitled")){
                chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
                int num = chooseFile.showSaveDialog(frame);
                if(num == JFileChooser.APPROVE_OPTION){
                    File file = chooseFile.getSelectedFile();
                    String file_name = file.getName();

                    JScrollPane pane = (JScrollPane) tab.getComponentAt(i);
                    JViewport viewport = pane.getViewport();
                    JTextArea current_textarea = (JTextArea) viewport.getView();

                    if(tab.getTitleAt(i).equals("Untitled")){
                        Component tabComponent = tab.getTabComponentAt(i);
                        JPanel panel = (JPanel) tabComponent;
                        JLabel label = (JLabel) panel.getComponent(0); // first component is label
                        label.setText(file_name);
                        map.put(file_name, chooseFile.getSelectedFile().getAbsolutePath());
                    }

                    try{
                        FileWriter fr = new FileWriter(file);
                        fr.write(current_textarea.getText());
                        fr.close();
                        tab.setTitleAt(i, file_name);
                        map.put(file_name, chooseFile.getSelectedFile().getAbsolutePath());
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }

                }
            }else{
                String title_name =  tab.getTitleAt(i);
                if(map.containsKey(title_name)){

                    JScrollPane pane = (JScrollPane) tab.getComponentAt(i);
                    JViewport viewport = pane.getViewport();
                    JTextArea current_textarea = (JTextArea) viewport.getView();

                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(map.get(title_name)));
                        bw.write(current_textarea.getText());
                        bw.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }

                }
            }
        }
    }

    static void close_tab(JTabbedPane tab){
        int selected_tab = tab.getSelectedIndex();
        String title_name = tab.getTitleAt(selected_tab);
        tab.removeTabAt(selected_tab);
        if(map.containsKey(title_name)){
            map.remove(title_name);
        }
    }


}
