
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main { 

    static HashMap <String,String> map = new HashMap();
    static int number_ch = 0;
    public static void main(String[] args) { 
        FlatDarkLaf.setup();
        // ///////////////////////////  
        JFrame frame = new JFrame("JavaInk"); //Character count and Line count func2 , test
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //close on X press
        frame.setSize(800,600); //size of the frame
        frame.setLocationRelativeTo(null); //middle screen
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40)); //set background color
        frame.setTitle("JavaInk - Untitled"); //title tab
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

        //About gif
        ImageIcon about_gif = new ImageIcon("goose_walking.gif");
        Image goose = about_gif.getImage().getScaledInstance(100,135, Image.SCALE_DEFAULT);
        about_gif = new ImageIcon(goose);

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
        Image CY = Copy.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Copy = new ImageIcon(CY);

        //Select all
        ImageIcon Selectall = new ImageIcon("select_all.png");
        Image SA = Selectall.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Selectall = new ImageIcon(SA);

        //Paste
        ImageIcon Paste = new ImageIcon("paste.png");
        Image P = Paste.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        Paste = new ImageIcon(P);

        //About Icon
        ImageIcon About = new ImageIcon("about.png");
        Image a = About.getImage().getScaledInstance(18,18, Image.SCALE_AREA_AVERAGING);
        About = new ImageIcon(a);

        // ///////////////////////////
        JMenuBar menubar = new JMenuBar(); //Menu bar is a bar for contain other items like menu item which contain in menu

        JMenu file = new JMenu("File"); //menu is the main box contain menu items
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
        JMenuItem newfile = new JMenuItem("New"); //menu item is the item we set its role
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
        file.add(saveall); //actionListioned
        file.add(closetab); //actionListioned
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
         about.setIcon(About);

        JLabel about_label = new JLabel();
        about_label.setIcon(about_gif);
        about_label.setText("<html>If you find this app helpful<br>please subcribe<html>");
        about_label.setIconTextGap(1);
        about_label.setForeground(new Color(165, 88, 0));
        about_label.setPreferredSize(new Dimension(100, 197));

        ImageIcon about_icon = new ImageIcon("javaink.png");
        Image about_icon2 =  about_icon.getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT);
        final ImageIcon about_icon_final = new ImageIcon(about_icon2);

        about.addActionListener(e -> {
            String[] array = {"Yes" , "No" , "Not interesting"};
            JOptionPane.showOptionDialog(null,
                    about_label,
                    "About",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    about_icon_final,
                    array,
                    null);
            /*JOptionPane.showMessageDialog(
                    null,
                    "If you find this app helpful pls subcribe",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);*/
        });
        help.add(about);
        // ////////////////////////////////
        JTextArea textarea = new JTextArea();  //first text area when start
        textarea.setEditable(true);
        textarea.setBackground(new Color(40, 40, 40));
        textarea.setForeground(new Color(255, 255, 255));
        textarea.setCaretColor(Color.WHITE); //set caret |

        // ////////////////////////////////

        JPanel underinfo = new JPanel(); //Panel contain info in the bottom of the border layout
        underinfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        underinfo.setBackground(new Color(76, 76, 76));
        JLabel label = new JLabel();   //Label text set and put in JPanel underinfo
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
        JFileChooser chooseFile = new JFileChooser(); //choose file
        JScrollPane scroll = new JScrollPane(textarea); //can scroll for text area
        JTabbedPane tab = new JTabbedPane(); //a line which contain file view ....
        /// /////////////////////////////////////////////////// set the action and role to them (jmenu)
        newfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new_file_func(newfile, textarea, frame, tab, label);
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

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                exit_func(frame);
            }
        });

        find.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                FindText(scroll,frame, tab);
            }
        });

        tab.setPreferredSize(new Dimension(100, 20));


        frame.setJMenuBar(menubar);
        //frame.add(scroll, BorderLayout.CENTER);
        frame.add(tab, BorderLayout.CENTER);

        JTextArea instant_textarea = new JTextArea(); //instant text area after open app
        instant_textarea.setBackground(new Color(40, 40, 40));
        instant_textarea.setForeground(new Color(255, 255, 255));
        instant_textarea.setCaretColor(Color.WHITE);

        //undo redo
        UndoManager undoManager = new UndoManager();
        instant_textarea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit())); //getDoc mean get textto allow undo-mamanger

        instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo"); //if we press ctrl + Z then the action work, undo refer to the action
        instant_textarea.getActionMap().put("undo", new AbstractAction(){
            public void actionPerformed(ActionEvent e) { //here undo action
                if(undoManager.canUndo()){ //if it wrote then we can ctrl Z
                    undoManager.undo(); //ctrl z work here
                }
            }
        });
        instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo"); //work same as Ctrl Z but Ctrl Y is redo which seen as opposite from ctrl Z
        instant_textarea.getActionMap().put("redo", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });

        JScrollPane scrollpane = new JScrollPane(instant_textarea); //scrollpane make textarea can scroll
        tab.addTab("Untitled", scrollpane); //put the first tab to tab item

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

        instant_textarea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { update(tab, label); }
            public void removeUpdate(DocumentEvent e)  { update(tab, label); }
            public void changedUpdate(DocumentEvent e) { update(tab, label); }
        });

        frame.add(underinfo, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public static void new_file_func(JMenuItem newfile, JTextArea textarea, JFrame frame, JTabbedPane tab, JLabel label) {

        FileWriter currentFile = null;

        textarea.setText("");
        currentFile = null;
        frame.setTitle("Java Ink - New File");

        JTextArea new_instant_textarea = new JTextArea();
        new_instant_textarea.setBackground(new Color(40, 40, 40));
        new_instant_textarea.setForeground(new Color(255, 255, 255));
        new_instant_textarea.setCaretColor(Color.WHITE);

        new_instant_textarea.getDocument().addDocumentListener(new DocumentListener() {  //these three functions can't alter the name
            public void insertUpdate(DocumentEvent e)  { update(tab, label); } //called update function to count text when first input
            public void removeUpdate(DocumentEvent e)  { update(tab, label); } //still count texts after delete text
            public void changedUpdate(DocumentEvent e) { update(tab, label); } //change mean change the text label to specific number count after wrote and delete
        });

        //undo redo
        UndoManager undoManager = new UndoManager(); //use for control z and y
        new_instant_textarea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit())); //add editable to new instant text area after add new untitled

        new_instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo"); //put ctrl z action key
        new_instant_textarea.getActionMap().put("undo", new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                if(undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        new_instant_textarea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo"); //put ctrl y action
        new_instant_textarea.getActionMap().put("redo", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }
            }
        });

        JScrollPane scrollpane = new JScrollPane(new_instant_textarea); //put new instant text area can be able to scroll
        tab.addTab("Untitled", scrollpane); //set name and scroll into tab, so tab contain them

        int index = tab.indexOfComponent(scrollpane);
        JLabel label2 = new JLabel("Untitled");

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
            int i =  tab.indexOfTabComponent(panel); //number of top UI //panel is already in tab, when press button the panel find later
            if(i != -1){
                tab.remove(i); //delete tab after button X
            }
        });

        panel.add(label2);
        panel.add(button);

        tab.setTabComponentAt(index, panel); //top UI //UI of tab in index like label in panel

        map.put("Untitled", null);

    }

    public static void open_file_func(JFileChooser chooseFile,  JFrame frame, JTabbedPane tab, JScrollPane scroll) {

        JTextArea new_textarea = new JTextArea(); //first declare new  textarea for open tab
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
        //user can choose file location but this is keep as example one
        chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
        int result = chooseFile.showOpenDialog(frame); //show open dialog = open file operation to choose if we chose int result = 1

        if (result == JFileChooser.APPROVE_OPTION) { //result =1, we chose file = 1, mean we really chose the file

            File file = chooseFile.getSelectedFile(); //path into file variable
            System.out.println(file); //test and debug for view //not necessary
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file)); //read text using I/O file
                new_textarea.setText(""); //set nothing for new text area
                String line;
                while ((line = reader.readLine()) != null) { //line variable catch texts in each line of the file
                    System.out.println(line); //test
                    new_textarea.append(line + "\n"); //add those texts in line var to text area line by line using loop
                }

                reader.close(); //disable I/O file

                JScrollPane scrollpane = new JScrollPane(new_textarea); //set to be able to scroll on new text area
                tab.addTab(file.getName(), scrollpane); //we get name of the file and set name to the tab, and the tab has name, then add scroll whihc contain text area so one tab has name and text area

                int index = tab.indexOfComponent(scrollpane); //get index of tab first
                JLabel label = new JLabel(file.getName()); //save name of tab to label

                //prepare JPanel for add the name and X button to the tab. we have index of tab and name of tab
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
                    int i =  tab.indexOfTabComponent(panel); //number of top UI //button will search using panel, panel is everything in a tab now
                    if(i != -1){
                        tab.remove(i); //if found == delete tab
                    }
                });

                panel.add(label);
                panel.add(button);

                tab.setTabComponentAt(index, panel); //top UI //change name of tab and put as panel which has label and button

                map.put(file.getName(), file.getAbsolutePath()); //HashMap for contain name of file and location, easy for delete or do something later

                frame.setTitle("Java Ink - " + file.getName()); //set frame titel (above all)

                for(String key : map.keySet()){
                    System.out.println(key); //test
                }

                for(String value : map.values()){
                    System.out.println(value); //test
                }


            } catch (IOException ex) {
                ex.printStackTrace(); //print error message if  read I/O fail or else
                JOptionPane.showMessageDialog(frame, "Error opening file"); //OptionPane show small message
            }
        }
    }

    public static void save_file_func(JFileChooser chooseFile,  JFrame frame, JTabbedPane tab) {

        int ind = tab.getSelectedIndex(); //get index of selected tab
        if(tab.getTitleAt(ind).equals("Untitled")){ //if it untitled use save as func
            save_as_func(chooseFile,frame,tab);
        }else { //but if its name isn't untitled mean the file is already available in computer
            int index = tab.getSelectedIndex(); //get index
            String file_name = tab.getTitleAt(index); //get Name
            String file_path = map.get(file_name); //get Path from Hashmap

            JScrollPane pane = (JScrollPane) tab.getSelectedComponent(); //read text from text area
            JViewport viewport = pane.getViewport();
            JTextArea textarea = (JTextArea) viewport.getView();

            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file_path)); //write to file and use file_path to save into that path
                bw.write(textarea.getText()); //write to
                bw.close();
            }catch(IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    public static void save_as_func(JFileChooser chooseFile, JFrame frame, JTabbedPane tab) {
        chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
        int result = chooseFile.showSaveDialog(frame); //open save dialog file operator
        if (result == JFileChooser.APPROVE_OPTION) { //check if 1=1
            File file = chooseFile.getSelectedFile(); //contain path of folder which lead to folder for file save
            System.out.println("First " + file);

            JScrollPane pane = (JScrollPane) tab.getSelectedComponent(); //read text area
            JViewport viewport = pane.getViewport();
            JTextArea current_textarea = (JTextArea) viewport.getView();
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(file)); //write to file

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

            if(currentTitle.equals("Untitled")){ //if the file we save as is untitle so it change name and put name+location to map

                Component tabComponent = tab.getTabComponentAt(index); //get selected the tab // get index from it
                JPanel panel = (JPanel) tabComponent; //get tf the jpanel out from tabComponent
                JLabel label = (JLabel) panel.getComponent(0); //get Label out of panel at the component// first component(0) is label
                label.setText(file_path.getName()); //rename the tab

                map.remove("Untitled"); //remove untitled and replace the new name which we already save as
                map.put(file_path.getName(), file_path.getAbsolutePath()); //put new name and location to
            }
            else{ //if the file we save as isn't untitled so it will create a new file which has the same texts info

                JTextArea new_textarea = new JTextArea(); //new text area for new tab
                new_textarea.setText(current_textarea.getText()); //get text from old tab

                new_textarea.setBackground(new Color(40, 40, 40));
                new_textarea.setForeground(new Color(255, 255, 255));
                new_textarea.setCaretColor(Color.WHITE);

                tab.addTab(file_path.getName(), new JScrollPane(new_textarea)); //new tab with name we set , new scroll textarea

                map.put(file_path.getName(), file_path.getAbsolutePath()); //map save new tab with name and location
            }
        }

    }

    static void save_all_func(JTabbedPane tab, JFileChooser chooseFile, JFrame frame){
        int tab_amount = tab.getTabCount(); //count total tab
        int path_amount = map.size(); //get HashMap size
        System.out.println("tab_amount = " + tab_amount);
        System.out.println("path_amount = " + path_amount);

        for(int i = 0; i < tab_amount; i++){ //loop run the save one by one base on tab_amount
            if(tab.getTitleAt(i).equals("Untitled")){ //if the tab is untited
                chooseFile.setCurrentDirectory(new File("D:\\D Documents\\CODE Program\\Java\\X Java Swing\\18 Select a file"));
                int num = chooseFile.showSaveDialog(frame); //so it open save dialog file operator for saving a new file
                if(num == JFileChooser.APPROVE_OPTION){ //check 1=1 true or not
                    File file = chooseFile.getSelectedFile(); //get the path we selected
                    String file_name = file.getName(); //get file name we already set while saving

                    JScrollPane pane = (JScrollPane) tab.getComponentAt(i); //read text from tetxarea
                    JViewport viewport = pane.getViewport();
                    JTextArea current_textarea = (JTextArea) viewport.getView();

                    if(tab.getTitleAt(i).equals("Untitled")){ //check if tab is untitled after saved then we are going to set new name for it in app
                        Component tabComponent = tab.getTabComponentAt(i); //we just get index first
                        JPanel panel = (JPanel) tabComponent; //force get panel from component
                        JLabel label = (JLabel) panel.getComponent(0); // first component in panel is label so we pick the label
                        label.setText(file_name);//new name for label
                        map.put(file_name, chooseFile.getSelectedFile().getAbsolutePath());//save to HashMap
                    }

                    try{
                        FileWriter fr = new FileWriter(file); //then write all texts to file we selected the path
                        fr.write(current_textarea.getText()); //current texts we got recently and write to that file
                        fr.close();
                        tab.setTitleAt(i, file_name); //set title not tab name
                        map.put(file_name, chooseFile.getSelectedFile().getAbsolutePath()); //put as new data in hashmap
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }

                }
            }else{ // if the tab isn't untitled so we save this way
                String title_name =  tab.getTitleAt(i); //get name from tab at index i
                if(map.containsKey(title_name)){ //check if map has that name , so we can put name as key and get value as location path

                    JScrollPane pane = (JScrollPane) tab.getComponentAt(i); //read text again
                    JViewport viewport = pane.getViewport();
                    JTextArea current_textarea = (JTextArea) viewport.getView();

                    try{
                        BufferedWriter bw = new BufferedWriter(new FileWriter(map.get(title_name))); //write to that one file we already have path,
                        bw.write(current_textarea.getText());
                        bw.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }

                }
            }
        }
    }

    static void close_tab(JTabbedPane tab){ //close tab by click
        int selected_tab = tab.getSelectedIndex(); //get index of selected tab (tab we are seeing)
        String title_name = tab.getTitleAt(selected_tab); //get title name
        tab.removeTabAt(selected_tab); //remove tab at index
        if(map.containsKey(title_name)){
            map.remove(title_name); //put name to remove key + value// so the delete one doesn't remain anymore
        }
    }

    static void update(JTabbedPane tab, JLabel label) { //update texts count at the bottom of the frame
        JScrollPane scrollpane = (JScrollPane) tab.getSelectedComponent();  //read text first
        JTextArea textarea = (JTextArea) scrollpane.getViewport().getView();
        String texts = textarea.getText();
        label.setText("Window | UTF-8 | Line: " + texts.split("\n", -1).length + " , Char: " + texts.length()); //split at \n so it read line by line
    }

    static void exit_func(JFrame frame){ //disable app, shutdown app
        frame.dispose();
    } //close app

    static void FindText(JScrollPane scroll, JFrame frame, JTabbedPane tab){ //find text at specific index
        JDialog dialog = new JDialog(frame, "Find", false); //dialog is a small frame
        dialog.setLayout(new FlowLayout()); //layout
        dialog.setSize(250,100); //size
        dialog.setLocationRelativeTo(null); //located in the middle of screen
        JLabel label = new JLabel("Enter the text you want to find:   "); //label for guide

        JButton findBtn = new JButton("Find"); //button for action to find after we type the word we want to find
        JButton findNext = new  JButton("Next"); /// not yet develope

        JTextField field = new JTextField(); //text field is for user input
        dialog.add(label);
        dialog.add(field);
        dialog.add(findBtn);
        dialog.add(findNext); //use dialog to add every component
        dialog.setVisible(true); //set to visible mean can view and appear

        field.addKeyListener(new KeyAdapter() { //set key , when we press key on keyboard then it also work
            public void keyPressed(KeyEvent e) { //key press void
                if(e.getKeyCode() == KeyEvent.VK_ENTER){ //getkeycode mean to press to key then it check if we press Enter key
                    String target_word =  field.getText(); //then get text from field, the text we want to find

                    JScrollPane scrollPane = (JScrollPane) tab.getSelectedComponent(); //read whole texts from area
                    JViewport viewport = scrollPane.getViewport();
                    JTextArea current_textarea = (JTextArea) viewport.getView();

                    String content =  current_textarea.getText(); //save whole texts to content

                    int index = content.indexOf(target_word); //find index of texts in content var and save to index var
                    if(index >= 0){
                        current_textarea.setCaretPosition(index); //set | to the text we want to find
                        current_textarea.select(index, index + target_word.length()); //select the text we found , but not yet seen by our view
                        current_textarea.requestFocusInWindow(); //to seen by our view let the request focus in window
                    }
                }
            }
        });
        findBtn.addActionListener(e ->{ //find button action, same role as Enter key

            String target_word =  field.getText();
            JScrollPane scrollPane = (JScrollPane) tab.getSelectedComponent();
            JViewport viewport = scrollPane.getViewport();
            JTextArea current_textarea = (JTextArea) viewport.getView();
            String content =  current_textarea.getText();

            int index = content.indexOf(target_word);
            if(index >= 0){
                current_textarea.setCaretPosition(index);
                current_textarea.select(index, index + target_word.length());
                current_textarea.requestFocusInWindow();
            }
        });


    }

}


