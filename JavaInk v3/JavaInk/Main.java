import javax.swing.*;                                   // Swing UI toolkit (windows, menus, buttons, text panes...)
import javax.swing.event.DocumentListener;               // lets us react when text is typed/deleted
import javax.swing.event.DocumentEvent;                  // the event object DocumentListener receives
import javax.swing.filechooser.FileFilter;                // base type for Open/Save file-type filters
import javax.swing.filechooser.FileNameExtensionFilter;   // ready-made filter that matches by file extension
import javax.swing.text.*;                                 // StyledDocument, StyleConstants, etc. for rich text
import javax.swing.text.rtf.RTFEditorKit;                 // reads/writes .rtf (Rich Text Format) files
import javax.swing.undo.UndoManager;                      // tracks edits so Ctrl+Z / Ctrl+Y work
import java.awt.*;                                          // Color, Dimension, Font, layouts, etc.
import java.awt.event.*;                                    // KeyEvent, ActionEvent, KeyAdapter, WindowAdapter...
import java.io.*;                                            // File, streams and readers/writers for saving/loading
import java.util.List;                                      // used by the Custom > Saved Paths feature
import java.util.ArrayList;                                 // used by the Custom > Saved Paths feature
import java.util.prefs.Preferences;                         // persists saved paths across app restarts
import com.formdev.flatlaf.FlatDarkLaf;                    // FlatLaf's dark theme, used for "Dark"
import com.formdev.flatlaf.FlatLightLaf;                   // FlatLaf's light theme, used for "Light"

public class Main {

    static JDialog find_dialog = null;      // the single shared Find/Replace dialog (created lazily, reused)
    static JPanel store_panel_1 = null;     // remembers the "Find" row so Replace can be spliced in/out of the dialog
    static JPanel store_panel_2 = null;     // remembers, the button row so Replace can be spliced in/out of the dialog

    static JLabel statusLabel;   // char/line counter, bottom bar
    static JLabel zoomLabel;     // zoom percentage, bottom bar

    // ///////////////////////////////////////////////////////////////////
    // Background Color theme (Theme -> Background Color -> Dark / Light / Default)
    // ///////////////////////////////////////////////////////////////////
    // Dark   = FlatLaf's own dark theme (FlatDarkLaf)
    // Light  = FlatLaf's own light theme (FlatLightLaf)
    // Default = no FlatLaf at all - plain, original Java Swing look-and-feel (cross-platform "Metal")
    // Colors below are placeholders only; the real colors are pulled live from UIManager
    // (i.e. straight from whichever look-and-feel is active) inside applyTheme().
    static Color BG_COLOR = new Color(40, 40, 40);   // current theme's background color (overwritten on startup)
    static Color FG_COLOR = Color.WHITE;              // current theme's text color (overwritten on startup)
    static String currentTheme = "Dark";              // name of the active Background Color style, app opens on "Dark"

    static final int BASE_FONT_SIZE = 14;   // starting/100%-zoom font size for every text pane

    public static void main(String[] args) {
        FlatDarkLaf.setup();   // install FlatLaf's dark look-and-feel before any Swing component is built (app opens in "Dark")
        // ///////////////////////////
        JFrame frame = new JFrame("JavaInk");                 // the main application window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closing the window quits the whole app
        //Get user screen size
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) (screen.width * 0.51);   // 40% of screen width
        int height = (int) (screen.height * 0.5); // 40% of screen height

        frame.setSize(width, height);                           // initial window size in pixels
        frame.setLocationRelativeTo(null);                    // center the window on screen
        frame.setLayout(new BorderLayout());                  // menu on top, editor in center, status bar on bottom
        frame.getContentPane().setBackground(BG_COLOR);       // paint the window background (re-applied properly once applyTheme runs below)
        frame.setTitle("JavaInk - Untitled");                 // title bar text before any file is opened
        // ///////////////////////////

        // App Icon
        // loadImage() safely resolves "pngs/..." whether the app is launched from the project folder or elsewhere,
        // and returns null instead of a broken/corrupt image if the file can't be found - this is what was
        // causing the red/broken icon before, since a failed ImageIcon was still being handed to setIconImage().
        Image appIconImage = loadImage("pngs/javaink.png");   // try to load the JavaInk icon
        if (appIconImage != null) frame.setIconImage(appIconImage);   // only set it if it actually loaded

        // New file icon
        ImageIcon newfile_icon = loadIcon("pngs/newfile.png", 20, 21);

        // Open file icon
        ImageIcon open_icon = loadIcon("pngs/open.png", 17, 18);

        // Save file icon
        ImageIcon save_icon = loadIcon("pngs/save.png", 17, 18);

        // Save as icon
        ImageIcon saveas_icon = loadIcon("pngs/saveas.png", 20, 21);

        // Save all Icon
        ImageIcon save_all = loadIcon("pngs/save all.png", 20, 21);

        // Close Tab Icon
        ImageIcon closetab_icon = loadIcon("pngs/closetab.png", 20, 21);

        // Exit icon
        ImageIcon exit_icon = loadIcon("pngs/exit.png", 20, 21);

        // View icons (zoom)
        ImageIcon zoomin_icon = loadIcon("pngs/Free_black_zoom_in_icon_vector_png_cad_-_Pixsector__Free_vector_images__mockups__PSDs_and_photos-removebg-preview.png", 18, 18);
        ImageIcon zoomout_icon = loadIcon("pngs/Free_black_zoom_out_icon_vector_png_cad_-_Pixsector__Free_vector_images__mockups__PSDs_and_photos-removebg-preview.png", 18, 18);
        ImageIcon resetzoom_icon = loadIcon("pngs/reset_Icon_-_Free_PNG___SVG_1921187_-_Noun_Project-removebg-preview.png", 18, 18);

        // Theme icons
        ImageIcon Text_color_Icon = loadIcon("pngs/text-format.png", 18, 18);
        ImageIcon all_text_color = loadIcon("pngs/bucket.png", 18, 18);

        // Edit icons
        ImageIcon Find = loadIcon("pngs/find.png", 18, 18);
        ImageIcon Replace = loadIcon("pngs/replace.png", 18, 18);
        ImageIcon Cut = loadIcon("pngs/cut.png", 18, 18);
        ImageIcon Copy = loadIcon("pngs/copy.png", 18, 18);
        ImageIcon Selectall = loadIcon("pngs/select_all.png", 18, 18);
        ImageIcon Paste = loadIcon("pngs/paste.png", 18, 18);

        // About Icon
        ImageIcon About = loadIcon("pngs/about.png", 18, 18);

        // ///////////////////////////
        JMenuBar menubar = new JMenuBar();     // the horizontal bar that holds File/Edit/View/Theme/Help

        JMenu file = new JMenu("File");        // top-level "File" menu
        JMenu edit = new JMenu("Edit");        // top-level "Edit" menu
        JMenu view = new JMenu("View");        // top-level "View" menu (zoom controls)
        JMenu theme = new JMenu("Theme");      // top-level "Theme" menu (text/background colors)
        JMenu help = new JMenu("Help");        // top-level "Help" menu (About)
        JMenu custom = new JMenu("Custom");
        // Note: the "Insert" menu (Insert Image / Insert Shape) has been removed per request.
        // /////////////////////////////


////////////////////////////////////////////////////////////
// Help menu
//////////////////////////////////////////////////////////////

        JMenuItem shortcuts = new JMenuItem("Keyboard Shortcuts");     // create Keyboard Shortcuts item
        JMenuItem about = new JMenuItem("About");                      // create About item

        shortcuts.addActionListener(e -> {                             // when Keyboard Shortcuts is clicked
            String shortcutText =
                    "<html>" +
                            "<h2>Keyboard Shortcuts</h2>" +

                            "<b>File</b><br>" +
                            "Ctrl + N &nbsp;&nbsp; New<br>" +
                            "Ctrl + O &nbsp;&nbsp; Open<br>" +
                            "Ctrl + S &nbsp;&nbsp; Save<br>" +
                            "Ctrl + W &nbsp;&nbsp; Close Tab<br>" +
                            "Ctrl + Shift + S &nbsp;&nbsp; Save As<br><br>" +

                            "<b>Edit</b><br>" +
                            "Ctrl + F &nbsp;&nbsp; Find<br>" +
                            "Ctrl + R &nbsp;&nbsp; Replace<br>" +
                            "Ctrl + X &nbsp;&nbsp; Cut<br>" +
                            "Ctrl + C &nbsp;&nbsp; Copy<br>" +
                            "Ctrl + V &nbsp;&nbsp; Paste<br>" +
                            "Ctrl + A &nbsp;&nbsp; Select All<br>" +
                            "Ctrl + Z &nbsp;&nbsp; Undo<br>" +
                            "Ctrl + Y &nbsp;&nbsp; Redo<br><br>" +

                            "<b>View</b><br>" +
                            "Ctrl + = &nbsp;&nbsp; Zoom In<br>" +
                            "Ctrl + - &nbsp;&nbsp; Zoom Out<br>" +
                            "Ctrl + 0 &nbsp;&nbsp; Reset Zoom<br>" +
                            "Ctrl + Mouse Wheel &nbsp;&nbsp; Zoom In / Out" +

                            "</html>";

            JOptionPane.showMessageDialog(
                    null,
                    shortcutText,
                    "Keyboard Shortcuts",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        about.addActionListener(e -> {                                  // when About is clicked
            String aboutText =
                    "<html>" +
                            "<h2>Java Text Editor</h2>" +
                            "<p><b>Version:</b> 1.0</p>" +

                            "<p>This is a simple text editor application developed " +
                            "using Java Swing.</p>" +

                            "<p><b>Features:</b></p>" +
                            "<ul>" +
                            "<li>Create, open, save, and save as text files</li>" +
                            "<li>Find and replace text</li>" +
                            "<li>Cut, copy, paste, and select all</li>" +
                            "<li>Undo and redo editing actions</li>" +
                            "<li>Zoom in, zoom out, and reset zoom</li>" +
                            "<li>Multiple document tabs</li>" +
                            "</ul>" +

                            "<p>This application was created as a Java Swing project " +
                            "for learning and practicing desktop application development.</p>" +

                            "<p><b>Developed with:</b> Java Swing <br> Contact: heng334335@gmail.com </p>" +

                            "</html>";

            JOptionPane.showMessageDialog(
                    null,
                    aboutText,
                    "About Java Text Editor",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        help.add(shortcuts);                                            // add Keyboard Shortcuts to Help
        help.addSeparator();                                            // add separator
        help.add(about);                                                 // add About to Help



        JMenuItem newfile = new JMenuItem("New");         // "File > New" menu entry
        newfile.setIcon(newfile_icon);                    // attach its icon (null is fine - Swing just shows no icon)
        JMenuItem open = new JMenuItem("Open");           // "File > Open" menu entry
        open.setIcon(open_icon);                          // attach its icon
        JMenuItem save = new JMenuItem("Save");           // "File > Save" menu entry
        save.setIcon(save_icon);                          // attach its icon
        JMenuItem saveas = new JMenuItem("Save as");      // "File > Save as" menu entry
        saveas.setIcon(saveas_icon);                      // attach its icon
        JMenuItem saveall = new JMenuItem("Save all");    // "File > Save all" menu entry
        saveall.setIcon(save_all);                        // attach its icon
        JMenuItem closetab = new JMenuItem("Close Tab");  // "File > Close Tab" menu entry
        closetab.setIcon(closetab_icon);                  // attach its icon
        JMenuItem exit = new JMenuItem("Exit");           // "File > Exit" menu entry
        exit.setIcon(exit_icon);                          // attach its icon

        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));                       // Ctrl+N shortcut
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));                          // Ctrl+O shortcut
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));                          // Ctrl+S shortcut
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)); // Ctrl+Shift+S shortcut

        file.add(newfile);   // put "New" into the File menu
        file.add(open);      // put "Open" into the File menu
        file.add(save);      // put "Save" into the File menu
        file.add(saveas);    // put "Save as" into the File menu
        file.add(saveall);   // put "Save all" into the File menu
        file.add(closetab);  // put "Close Tab" into the File menu
        file.add(exit);      // put "Exit" into the File menu

        /// ///////////////////////////////////////////////
        JMenuItem find = new JMenuItem("Find");           // "Edit > Find" menu entry
        find.setIcon(Find);                               // attach its icon
        JMenuItem replace = new JMenuItem("Replace");     // "Edit > Replace" menu entry
        replace.setIcon(Replace);                         // attach its icon
        JMenuItem cut = new JMenuItem("Cut");             // "Edit > Cut" menu entry
        cut.setIcon(Cut);                                 // attach its icon
        JMenuItem copy = new JMenuItem("Copy");           // "Edit > Copy" menu entry
        copy.setIcon(Copy);                               // attach its icon
        JMenuItem selectall = new JMenuItem("Select All");// "Edit > Select All" menu entry
        selectall.setIcon(Selectall);                     // attach its icon
        JMenuItem paste = new JMenuItem("Paste");         // "Edit > Paste" menu entry
        paste.setIcon(Paste);                             // attach its icon

        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));      // Ctrl+F shortcut
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));   // Ctrl+R shortcut
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));       // Ctrl+X shortcut
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));      // Ctrl+C shortcut
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));     // Ctrl+V shortcut
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)); // Ctrl+A shortcut

        edit.add(find);         // put "Find" into the Edit menu
        edit.add(replace);      // put "Replace" into the Edit menu
        edit.addSeparator();    // visual divider between find/replace and clipboard actions
        edit.add(cut);          // put "Cut" into the Edit menu
        edit.add(copy);         // put "Copy" into the Edit menu
        edit.add(paste);        // put "Paste" into the Edit menu
        edit.add(selectall);    // put "Select All" into the Edit menu
        ////////////////////////////////////////////////////////////
        JMenuItem zoomin = new JMenuItem("Zoom In");        // "View > Zoom In" menu entry
        JMenuItem zoomout = new JMenuItem("Zoom Out");      // "View > Zoom Out" menu entry
        JMenuItem resetzoom = new JMenuItem("Reset Zoom");  // "View > Reset Zoom" menu entry
        zoomin.setIcon(zoomin_icon);                        // attach its icon
        zoomout.setIcon(zoomout_icon);                      // attach its icon
        resetzoom.setIcon(resetzoom_icon);                  // attach its icon

        zoomin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK)); // Ctrl+= shortcut
        zoomout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK)); // Ctrl+- shortcut
        resetzoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));   // Ctrl+0 shortcut

        view.add(zoomin);      // put "Zoom In" into the View menu
        view.add(zoomout);     // put "Zoom Out" into the View menu
        view.add(resetzoom);   // put "Reset Zoom" into the View menu
        ///////////////////////////////////////////////////////////
        JMenuItem textscolor = new JMenuItem("Text Color");         // "Theme > Text Color" menu entry (colors the selected text)
        textscolor.setIcon(Text_color_Icon);                        // attach its icon
        JMenuItem alltextscolor = new JMenuItem("All Text Color");  // "Theme > All Text Color" menu entry (colors the whole document)
        alltextscolor.setIcon(all_text_color);                      // attach its icon
        theme.add(textscolor);       // put "Text Color" into the Theme menu
        theme.add(alltextscolor);    // put "All Text Color" into the Theme menu
        theme.addSeparator();        // visual divider between text-color actions and the background style picker

        // ------------------------------------------------------------
        // "Theme > Background Color" submenu: pick the app's overall look
        //   Dark    -> FlatLaf's dark theme
        //   Light   -> FlatLaf's light theme
        //   Default -> no FlatLaf, plain original Java Swing look-and-feel
        // ------------------------------------------------------------
        JMenu backgroundColor = new JMenu("Background Color");                     // new submenu that groups the three styles
        JRadioButtonMenuItem darkThemeItem = new JRadioButtonMenuItem("Dark", true); // "Dark" option, selected by default since the app opens in Dark
        JRadioButtonMenuItem lightThemeItem = new JRadioButtonMenuItem("Light");     // "Light" option (FlatLaf's light theme)
        JRadioButtonMenuItem defaultThemeItem = new JRadioButtonMenuItem("Default"); // "Default" option (plain vanilla Swing, no FlatLaf)

        ButtonGroup backgroundGroup = new ButtonGroup();  // ensures only one of the three radio items can be checked at a time
        backgroundGroup.add(darkThemeItem);               // register "Dark" in the mutually-exclusive group
        backgroundGroup.add(lightThemeItem);              // register "Light" in the mutually-exclusive group
        backgroundGroup.add(defaultThemeItem);            // register "Default" in the mutually-exclusive group

        backgroundColor.add(darkThemeItem);       // put "Dark" into the Background Color submenu
        backgroundColor.add(lightThemeItem);      // put "Light" into the Background Color submenu
        backgroundColor.add(defaultThemeItem);    // put "Default" into the Background Color submenu
        theme.add(backgroundColor);               // put the whole "Background Color" submenu into the Theme menu

        help.add(about);   // put "About" into the Help menu

        // ------------------------------------------------------------
        // "Custom" menu: lets the user save/paste file paths so they can be
        // reopened later without browsing for them again (add, remove, open).
        // ------------------------------------------------------------
        JMenuItem savedPathsItem = new JMenuItem("Saved Paths...");   // opens the manage-paths dialog
        savedPathsItem.setToolTipText("Save a file path here so you can jump straight back to it later");
        custom.add(savedPathsItem);                                    // put it into the Custom menu
        // its actionListener is attached further below, once "tab" exists (see "Custom > Saved Paths" wiring)

        menubar.add(file);    // add "File" to the menu bar
        menubar.add(edit);    // add "Edit" to the menu bar
        menubar.add(view);    // add "View" to the menu bar
        menubar.add(theme);   // add "Theme" to the menu bar
        menubar.add(custom);  // add "Custom" to the menu bar
        menubar.add(help);    // add "Help" to the menu bar

        // ////////////////////////////////
        JPanel underinfo = new JPanel();                              // the bottom status bar strip
        underinfo.setLayout(new FlowLayout(FlowLayout.RIGHT));        // right-align its labels
        underinfo.setBackground(new Color(76, 76, 76));               // initial status-bar background (re-applied per theme below)

        statusLabel = new JLabel();                                        // shows "Window | UTF-8 | Line: x, Char: y"
        statusLabel.setForeground(FG_COLOR);                               // color it with the active theme's text color
        statusLabel.setText("Window | UTF-8 | Line: 1 , Char: 0");         // initial text before any typing happens

        zoomLabel = new JLabel("Zoom: 100%");                               // shows the current zoom percentage
        zoomLabel.setForeground(FG_COLOR);                                  // color it with the active theme's text color
        zoomLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));  // small right padding so it doesn't touch statusLabel

        underinfo.add(zoomLabel);      // place the zoom label in the status bar
        underinfo.add(statusLabel);    // place the status label in the status bar

        /// //////////////////////Important components/////////////////////////////
        JFileChooser chooseFile = new JFileChooser();     // shared Open/Save file-picker dialog
        JTabbedPane tab = new JTabbedPane(); // holds one tab per open document
        tab.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("control W"), "closeTab" );
        tab.getActionMap().put("closeTab", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { close_tab(tab); } });
        savedPathsItem.addActionListener(e -> openCustomPathsDialog(frame, tab)); // "Custom > Saved Paths" -> open the add/remove/open dialog
        tab.setPreferredSize(new Dimension(100, 20));      // minimal preferred size; BorderLayout stretches it to fill anyway

        /// /////////////////////////////////////////////////// menu actions
        newfile.addActionListener(e -> new_file_func(frame, tab));                        // "New" -> open a fresh Untitled tab
        open.addActionListener(e -> open_file_func(chooseFile, frame, tab));              // "Open" -> load a file into a new tab
        save.addActionListener(e -> save_file_func(chooseFile, frame, tab));              // "Save" -> write current tab to disk
        saveas.addActionListener(e -> save_as_func(chooseFile, frame, tab));              // "Save as" -> write current tab to a new path
        saveall.addActionListener(e -> save_all_func(tab, chooseFile, frame));            // "Save all" -> write every open tab
        closetab.addActionListener(e -> close_tab(tab));                                   // "Close Tab" -> remove the active tab
        exit.addActionListener(e -> exit_func(frame));                                     // "Exit" -> close the app

        find.addActionListener(e -> openFindDialog(frame, tab));         // "Find" -> show/focus the Find dialog

        replace.addActionListener(e -> openReplaceDialog(frame, tab));   // "Replace" -> show the Find dialog with Replace controls added

        cut.addActionListener(e -> {                     // "Cut" -> remove selected text into the clipboard
            JTextPane p = getCurrentTextPane(tab);        // get the text pane of the active tab
            if (p != null) p.cut();                       // perform the cut if a tab is actually open
        });
        copy.addActionListener(e -> {                    // "Copy" -> copy selected text to the clipboard
            JTextPane p = getCurrentTextPane(tab);        // get the text pane of the active tab
            if (p != null) p.copy();                       // perform the copy if a tab is actually open
        });
        paste.addActionListener(e -> {                   // "Paste" -> insert clipboard text at the caret
            JTextPane p = getCurrentTextPane(tab);        // get the text pane of the active tab
            if (p != null) p.paste();                      // perform the paste if a tab is actually open
        });
        selectall.addActionListener(e -> {               // "Select All" -> select the entire document
            JTextPane p = getCurrentTextPane(tab);        // get the text pane of the active tab
            if (p != null) {                                // only proceed if a tab is actually open
                p.requestFocusInWindow();                    // give the text pane keyboard focus first
                p.selectAll();                               // then select all of its text
            }
        });

        /////////////////////////////////////zoom//////////////////////////////////////
        zoomin.addActionListener(e -> zoom_func(tab, 2));      // "Zoom In" -> increase font size by 2pt
        zoomout.addActionListener(e -> zoom_func(tab, -2));    // "Zoom Out" -> decrease font size by 2pt
        resetzoom.addActionListener(e -> zoom_func(tab, 0));   // "Reset Zoom" -> snap back to the base font size (100%)

        /////////////////////////////////////theme (text color)/////////////////////////
        textscolor.addActionListener(e -> {                                    // "Text Color" -> recolor only the selected text
            JTextPane pane = getCurrentTextPane(tab);                          // get the text pane of the active tab
            if (pane == null) return;                                          // nothing to do if no tab is open
            int start = pane.getSelectionStart();                              // start offset of the current selection
            int end = pane.getSelectionEnd();                                  // end offset of the current selection
            if (start == end) {                                                // start == end means nothing is selected
                JOptionPane.showMessageDialog(frame, "Select the text you want to color first.", "No Selection", JOptionPane.INFORMATION_MESSAGE); // nudge the user to select text first
                return;                                                        // stop here, no selection to color
            }
            Color chosen = JColorChooser.showDialog(frame, "Choose Text Color", pane.getForeground()); // open the color-picker dialog
            if (chosen != null) {                                              // null means the user cancelled
                applyColor(pane, start, end - start, chosen);                  // paint just the selected range with the chosen color
            }
        });

        alltextscolor.addActionListener(e -> {                                 // "All Text Color" -> recolor the entire document
            JTextPane pane = getCurrentTextPane(tab);                          // get the text pane of the active tab
            if (pane == null) return;                                         // nothing to do if no tab is open
            Color chosen = JColorChooser.showDialog(frame, "Choose Color for All Text", pane.getForeground()); // open the color-picker dialog
            if (chosen != null) {                                             // null means the user cancelled
                StyledDocument doc = pane.getStyledDocument();                 // the document backing this text pane
                applyColor(pane, 0, doc.getLength(), chosen);                  // paint the whole document with the chosen color
                MutableAttributeSet inputAttrs = pane.getInputAttributes();    // attributes used for text typed from now on
                StyleConstants.setForeground(inputAttrs, chosen);              // make newly-typed text use the chosen color too
            }
        });

        /////////////////////////////////////theme (background color)/////////////////////////
        darkThemeItem.addActionListener(e -> applyTheme("Dark", frame, tab, underinfo));       // switch the whole app to FlatLaf's Dark theme
        lightThemeItem.addActionListener(e -> applyTheme("Light", frame, tab, underinfo));     // switch the whole app to FlatLaf's Light theme
        defaultThemeItem.addActionListener(e -> applyTheme("Default", frame, tab, underinfo)); // switch the whole app to plain vanilla Swing (no FlatLaf)

        frame.setJMenuBar(menubar);           // attach the menu bar to the window
        frame.add(tab, BorderLayout.CENTER);  // the tabbed editor area fills the middle of the window

        // First tab, created through the shared helper so it behaves exactly like any other tab
        createNewTabbedTextPane(tab, frame, "Untitled");   // open the initial empty "Untitled" tab (no file path yet)

        frame.add(underinfo, BorderLayout.SOUTH);   // the status bar sits along the bottom of the window

        applyTheme("Dark", frame, tab, underinfo);  // pull real colors from the Dark FlatLaf theme now that every component exists, so the app opens correctly themed

        frame.setVisible(true);   // finally show the fully-built window to the user
    }

    // ///////////////////////////////////////////////////////////////////
    // Icon loading helpers (fix for icons/window-icon showing as a broken/red image)
    // ///////////////////////////////////////////////////////////////////

    /**
     * Loads an image from the classpath (e.g. "pngs/javaink.png").
     * This works both when running from an IDE/loose classes folder AND when running from
     * inside a packaged JAR, because it reads through the classloader's resource lookup
     * instead of treating the path as a real file on disk (which breaks once the pngs
     * folder is zipped inside the JAR - that was the cause of the missing icons).
     * Returns null - instead of a broken image - if the resource truly can't be found or read,
     * so callers can simply skip setting an icon rather than showing a corrupt/red icon.
     */
    static Image loadImage(String relativePath) {
        String resourcePath = relativePath.startsWith("/") ? relativePath : "/" + relativePath; // classpath lookups are rooted, so ensure a leading slash
        java.net.URL url = Main.class.getResource(resourcePath);            // find the resource on the classpath (works in IDE output folder and inside a JAR)
        if (url == null) {                                                  // not found anywhere on the classpath
            System.err.println("Image not found: " + relativePath);        // log it so it's easy to spot in the console
            return null;                                                    // hand back null instead of a broken image
        }
        ImageIcon raw = new ImageIcon(url);                                  // let Swing load and decode the resource
        if (raw.getImageLoadStatus() != MediaTracker.COMPLETE) {             // decoding failed (corrupt/unsupported file)
            System.err.println("Image failed to load: " + relativePath);    // log it so it's easy to spot in the console
            return null;                                                    // hand back null instead of a broken image
        }
        return raw.getImage();                                              // successfully loaded - hand back the raw image
    }

    /** Loads and scales an icon for menu use; returns null (never a broken icon) if loading failed. */
    static ImageIcon loadIcon(String relativePath, int width, int height) {
        Image img = loadImage(relativePath);                                                        // load the raw image safely
        if (img == null) return null;                                                                // propagate the failure as null
        return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));      // scale it to the requested menu-icon size
    }

    // ///////////////////////////////////////////////////////////////////
    // Background Color theme switching
    // ///////////////////////////////////////////////////////////////////

    /**
     * Switches the app between "Dark" (FlatLaf dark theme), "Light" (FlatLaf light theme),
     * and "Default" (plain vanilla Swing look-and-feel, no FlatLaf at all).
     * All colors are read live from UIManager after the look-and-feel is installed, so the
     * app always shows the real colors of whichever theme is active rather than hand-picked ones.
     */
    static void applyTheme(String themeName, JFrame frame, JTabbedPane tab, JPanel underinfo) {
        currentTheme = themeName;   // remember which style is active

        try {
            switch (themeName) {                                            // install the requested look-and-feel
                case "Light":                                               // FlatLaf's own light theme
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case "Default":                                             // plain original Java Swing look-and-feel (no FlatLaf)
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    break;
                case "Dark":                                                // FlatLaf's own dark theme
                default:                                                    // fall back to Dark for any unrecognized name
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
            }
        } catch (Exception ex) {                                            // look-and-feel classes not found / failed to instantiate
            ex.printStackTrace();                                           // log details for debugging
        }

        SwingUtilities.updateComponentTreeUI(frame);                        // repaint every component in the window with the new look-and-feel
        if (find_dialog != null) SwingUtilities.updateComponentTreeUI(find_dialog); // keep the Find/Replace dialog in sync too, if it's open

        // Pull the real colors of the theme we just installed straight from UIManager
        Color themeBg = UIManager.getColor("TextPane.background");          // the look-and-feel's own text-area background
        Color themeFg = UIManager.getColor("TextPane.foreground");          // the look-and-feel's own text-area foreground
        BG_COLOR = (themeBg != null) ? themeBg : Color.WHITE;               // fall back to white if a theme doesn't define this
        FG_COLOR = (themeFg != null) ? themeFg : Color.BLACK;               // fall back to black if a theme doesn't define this

        Color panelBg = UIManager.getColor("Panel.background");             // the look-and-feel's general panel/background color
        if (panelBg == null) panelBg = BG_COLOR;                            // fall back to the text-pane color if not defined

        frame.getContentPane().setBackground(panelBg);                      // repaint the window's own background
        underinfo.setBackground(panelBg);                                   // recolor the status bar to match the active theme
        if (statusLabel != null) statusLabel.setForeground(FG_COLOR);       // recolor the line/char counter text
        if (zoomLabel != null) zoomLabel.setForeground(FG_COLOR);           // recolor the zoom percentage text

        // Re-color every already-open tab's text pane so switching themes affects existing documents too
        for (int i = 0; i < tab.getTabCount(); i++) {                              // walk every open tab
            Component c = tab.getComponentAt(i);                                   // each tab's content is a JScrollPane
            if (c instanceof JScrollPane) {                                        // safety check before casting
                Component view = ((JScrollPane) c).getViewport().getView();        // the JTextPane living inside the scroll pane
                if (view instanceof JTextPane) {                                   // safety check before casting
                    JTextPane pane = (JTextPane) view;                             // the actual text editor for this tab
                    pane.setBackground(BG_COLOR);                                  // repaint its background
                    pane.setForeground(FG_COLOR);                                  // recolor its default text
                    pane.setCaretColor(FG_COLOR);                                  // keep the blinking caret visible against the new background
                }
            }
        }

        frame.revalidate();       // re-layout the window now that colors/sizes may have changed
        frame.repaint();          // force a fresh paint pass so the new colors are visible immediately
    }

    // ///////////////////////////////////////////////////////////////////
    // Helpers shared by every tab (new / open / save-as-copy)
    // ///////////////////////////////////////////////////////////////////

    static JTextPane createTextPane() {
        JTextPane pane = new JTextPane();                                       // a new blank rich-text editor
        pane.setBackground(BG_COLOR);                                           // paint it with the active theme's background
        pane.setForeground(FG_COLOR);                                           // paint its text with the active theme's foreground
        pane.setCaretColor(FG_COLOR);                                           // caret matches the foreground color so it's always visible
        pane.setFont(pane.getFont().deriveFont((float) BASE_FONT_SIZE));        // start at the base (100%) font size
        pane.putClientProperty("fontSize", BASE_FONT_SIZE);                     // stash the current size for the zoom logic to read/update
        return pane;                                                            // hand the ready-to-use pane back to the caller
    }

    /** Creates a fully wired text pane (undo/redo, find/replace keys, doc listener, zoom wheel), adds it as a new closable tab, and returns it. Caller is responsible for updating `map`. */
    static JTextPane createNewTabbedTextPane(JTabbedPane tab, JFrame frame, String title) {
        JTextPane pane = createTextPane();                        // build the themed, sized text editor
        JScrollPane scrollPane = new JScrollPane(pane);           // wrap it so long documents can scroll
        addTabWithCloseButton(tab, title, scrollPane);            // add it as a new tab with a small "X" close button
        attachUndoRedo(pane);                                     // wire up Ctrl+Z / Ctrl+Y
        attachFindReplaceKeys(pane, frame, tab);                  // wire up Ctrl+F / Ctrl+R inside the editor itself
        attachDocListener(pane, tab);                             // keep the status bar's line/char count live
        attachZoomWheel(pane, scrollPane, tab);                   // let Ctrl+MouseWheel zoom while normal scrolling still works
        tab.setSelectedComponent(scrollPane);                     // switch focus to the newly created tab

        return pane;                                              // hand the pane back so the caller can load content into it
    }

    static void addTabWithCloseButton(JTabbedPane tab, String title, JScrollPane scrollPane) {
        tab.addTab(title, scrollPane);                        // register the new tab with its content
        int index = tab.indexOfComponent(scrollPane);         // find where it landed so we can attach a custom tab header

        JLabel tabLabel = new JLabel(title);                              // shows the tab's title text
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0)); // small container holding title + close button
        panel.setOpaque(false);                                           // let the tab strip's own background show through

        JButton closeBtn = new JButton("X");                    // the little close button on each tab
        closeBtn.setForeground(new Color(94, 94, 94));          // muted grey "X" color
        closeBtn.setMargin(new Insets(0, 1, 0, 1));              // minimal padding so the tab stays compact
        closeBtn.setFocusPainted(false);                         // no focus ring around the button
        closeBtn.setFocusable(false);                            // keep keyboard focus on the editor, not this button
        closeBtn.setContentAreaFilled(false);                    // transparent background, just the "X" glyph
        closeBtn.setBorderPainted(false);                        // no visible button border
        closeBtn.addActionListener(e -> {                        // when the "X" is clicked...
            int i = tab.indexOfTabComponent(panel);              // find which tab this close button belongs to
            if (i != -1) {                                       // guard in case the tab was already removed
                tab.remove(i);                                   // remove the tab (and its editor) from the UI
            }
        });

        panel.add(tabLabel);                       // title text first
        panel.add(closeBtn);                       // close button right after it
        tab.setTabComponentAt(index, panel);       // use this custom panel as the tab's header instead of plain text
    }

    static void attachUndoRedo(JTextPane pane) {
        UndoManager undoManager = new UndoManager();                                  // tracks the edit history for this pane
        pane.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit())); // record every edit as it happens

        pane.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");   // bind Ctrl+Z to the "undo" action name
        pane.getActionMap().put("undo", new AbstractAction() {                 // define what "undo" actually does
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) undoManager.undo();                 // step back one edit if possible
            }
        });
        pane.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");   // bind Ctrl+Y to the "redo" action name
        pane.getActionMap().put("redo", new AbstractAction() {                 // define what "redo" actually does
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) undoManager.redo();                 // step forward one edit if possible
            }
        });
    }

    static void attachFindReplaceKeys(JTextPane pane, JFrame frame, JTabbedPane tab) {
        pane.addKeyListener(new KeyAdapter() {                                     // listen for raw key presses inside the editor
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {        // Ctrl+F pressed while typing
                    openFindDialog(frame, tab);                                    // show/focus the Find dialog
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) { // Ctrl+R pressed while typing
                    openReplaceDialog(frame, tab);                                 // show the Find dialog with Replace added
                }
            }
        });
    }

    static void attachDocListener(JTextPane pane, JTabbedPane tab) {
        pane.getStyledDocument().addDocumentListener(new DocumentListener() {  // fires on every text change
            public void insertUpdate(DocumentEvent e) { update(tab); }         // text inserted -> refresh status bar
            public void removeUpdate(DocumentEvent e) { update(tab); }         // text removed -> refresh status bar
            public void changedUpdate(DocumentEvent e) { update(tab); }        // attributes changed -> refresh status bar
        });
    }

    /** Lets Ctrl+MouseWheel zoom while plain wheel scrolling keeps working normally (adding a MouseWheelListener directly on a text component disables Swing's automatic scroll forwarding, so we forward it ourselves). */
    static void attachZoomWheel(JTextPane pane, JScrollPane scrollPane, JTabbedPane tab) {
        pane.addMouseWheelListener(e -> {                                    // fires on every wheel movement over the editor
            if (e.isControlDown()) {                                        // Ctrl held -> treat it as a zoom gesture
                zoom_func(tab, e.getWheelRotation() < 0 ? 1 : -1);           // scroll up = zoom in, scroll down = zoom out
            } else {                                                        // no Ctrl -> normal scrolling
                JScrollBar bar = scrollPane.getVerticalScrollBar();         // the scroll pane's vertical scrollbar
                bar.setValue(bar.getValue() + e.getUnitsToScroll() * bar.getUnitIncrement()); // move it manually since we intercepted the wheel event
            }
        });
    }

    static JTextPane getCurrentTextPane(JTabbedPane tab) {
        Component c = tab.getSelectedComponent();                 // whatever component is behind the active tab
        if (!(c instanceof JScrollPane)) return null;              // not a text tab (or no tab open) -> nothing to return
        Component view = ((JScrollPane) c).getViewport().getView(); // the component living inside that scroll pane
        return (view instanceof JTextPane) ? (JTextPane) view : null; // return it only if it's really a JTextPane
    }

    // ///////////////////////////////////////////////////////////////////
    // Per-tab file path tracking
    // ///////////////////////////////////////////////////////////////////
    // Each tab's on-disk path is stored directly on its own JTextPane (as a client property)
    // instead of in a title -> path map. A title-keyed map breaks as soon as two tabs share
    // the same title (e.g. two files that happen to have the same filename, opened from
    // different folders) - the second one silently overwrites the first one's path in the map,
    // so "Save All" (and even a normal Save) can end up writing to the wrong file or
    // "doing nothing" for a tab whose path got clobbered. Keeping the path on the pane itself
    // means every tab always knows its own correct path, no matter what other tabs are open.

    /** Returns the absolute file path this tab's pane was last opened from / saved to, or null if it's never been saved. */
    static String getFilePath(JTextPane pane) {
        Object o = pane.getClientProperty("filePath");        // read back whatever we stashed on this pane
        return (o instanceof String) ? (String) o : null;     // only trust it if it's really a String
    }

    /** Remembers the absolute file path this tab's pane is now saved to. */
    static void setFilePath(JTextPane pane, String path) {
        pane.putClientProperty("filePath", path);              // stash it directly on the pane, not in a global map
    }

    static void applyColor(JTextPane pane, int offset, int length, Color color) {
        if (length <= 0) return;                                    // nothing to color if the range is empty
        SimpleAttributeSet colorAttr = new SimpleAttributeSet();     // a fresh attribute set to carry just the color
        StyleConstants.setForeground(colorAttr, color);              // set the foreground color attribute
        // replace=false: merge in the color without wiping out other attributes (e.g. font size from zoom)
        pane.getStyledDocument().setCharacterAttributes(offset, length, colorAttr, false); // apply it to the given text range
    }

    // ///////////////////////////////////////////////////////////////////
    // File menu actions
    // ///////////////////////////////////////////////////////////////////

    public static void new_file_func(JFrame frame, JTabbedPane tab) {
        createNewTabbedTextPane(tab, frame, "Untitled");   // open a brand-new empty tab (its filePath client property is null until it's saved)
        frame.setTitle("JavaInk - Untitled");               // reflect the new tab in the window title
        tab.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("control W"), "closeTab" );
        tab.getActionMap().put("closeTab", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { close_tab(tab); } });
    }

    public static void open_file_func(JFileChooser chooseFile, JFrame frame, JTabbedPane tab) {
        configureChooser(chooseFile);                          // set up the .txt/.rtf filters for the Open dialog
        int result = chooseFile.showOpenDialog(frame);         // show the dialog and wait for the user's choice
        if (result != JFileChooser.APPROVE_OPTION) return;     // user cancelled -> nothing to do

        File file = chooseFile.getSelectedFile();               // the file the user picked
        openFileFromDisk(file, frame, tab);                     // shared loading logic (also used by Custom > Saved Paths)
    }

    /**
     * Reads a file from disk into a brand-new tab. Shared by the "File > Open" file-chooser
     * flow and the "Custom > Saved Paths" flow, so both stay in sync with one implementation.
     */
    private static void openFileFromDisk(File file, JFrame frame, JTabbedPane tab) {
        DefaultStyledDocument loaded = new DefaultStyledDocument(); // a scratch document to read the file's contents into
        try {
            if (isRtfPath(file.getName())) {                                   // .rtf files carry formatting, so parse them specially
                RTFEditorKit rtfKit = new RTFEditorKit();                       // knows how to read/write the RTF format
                try (InputStream is = new FileInputStream(file)) {              // open the file for reading
                    rtfKit.read(is, loaded, 0);                                 // parse RTF content straight into `loaded`
                }
            } else {                                                            // plain text files
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // open the file line by line
                    StringBuilder sb = new StringBuilder();                     // accumulates the file's full text
                    String line;                                                // holds each line as it's read
                    boolean first = true;                                       // tracks whether we need a leading newline
                    while ((line = reader.readLine()) != null) {                // read until end of file
                        if (!first) sb.append("\n");                            // re-insert the newline readLine() stripped off
                        sb.append(line);                                        // append this line's text
                        first = false;                                          // subsequent lines do get a newline prefix
                    }
                    loaded.insertString(0, sb.toString(), null);                // dump the assembled text into the document
                }
            }
        } catch (Exception ex) {                                                 // covers I/O errors and bad RTF/BadLocation issues
            ex.printStackTrace();                                                // log details for debugging
            JOptionPane.showMessageDialog(frame, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE); // tell the user
            return;                                                              // abort, no tab was created
        }
        tab.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("control W"), "closeTab" );
        tab.getActionMap().put("closeTab", new AbstractAction() { @Override public void actionPerformed(ActionEvent e) { close_tab(tab); } });

        JTextPane pane = createNewTabbedTextPane(tab, frame, file.getName());    // open a new tab named after the file
        copyStyledContent(loaded, pane.getStyledDocument());                     // copy the parsed content (with styling) into the new tab
        setFilePath(pane, file.getAbsolutePath());                               // remember where this tab's file lives on disk (stored on the pane itself)
        frame.setTitle("JavaInk - " + file.getName());                           // reflect the opened file in the window title
    }

    // ///////////////////////////////////////////////////////////////////
    // Custom > Saved Paths: let the user paste/save file paths so they can
    // reopen them later without browsing again. Add, remove, and open.
    // Paths are stored via Java's Preferences API, so they persist even
    // after the app is closed and reopened (and survive across the JAR/exe).
    // ///////////////////////////////////////////////////////////////////
    private static final String SAVED_PATHS_PREF_KEY = "custom_saved_paths"; // key under which the joined path list is stored

    /** Reads the saved paths back out of Preferences (empty list if none saved yet). */
    static List<String> loadSavedPaths() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);   // per-user storage, keyed to this app's package
        String joined = prefs.get(SAVED_PATHS_PREF_KEY, "");              // stored as one big string, "\n"-separated
        List<String> result = new ArrayList<>();                          // the list we'll hand back
        if (!joined.isEmpty()) {                                          // only split if something was actually saved
            for (String p : joined.split("\n")) {                        // each line is one saved path
                if (!p.isBlank()) result.add(p);                          // skip any stray empty lines
            }
        }
        return result;
    }

    /** Overwrites the saved-paths list in Preferences with the given list. */
    static void saveSavedPaths(List<String> paths) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);   // same node used by loadSavedPaths()
        prefs.put(SAVED_PATHS_PREF_KEY, String.join("\n", paths));        // join back into one "\n"-separated string
    }

    /** Opens the file at the given saved path into a new tab, or warns if it no longer exists. */
    static void open_file_from_saved_path(String path, JFrame frame, JTabbedPane tab) {
        File file = new File(path);                                       // resolve the saved path to a real File
        if (!file.exists() || !file.isFile()) {                           // the file may have moved/been deleted since it was saved
            JOptionPane.showMessageDialog(frame, "File not found:\n" + path, "Open Saved Path", JOptionPane.WARNING_MESSAGE);
            return;                                                       // nothing to open
        }
        openFileFromDisk(file, frame, tab);                                // reuse the exact same loading logic as "File > Open"
    }

    /** Shows the "Custom > Saved Paths" dialog: paste a path to save it, pick one to open it or delete it. */
    static void openCustomPathsDialog(JFrame frame, JTabbedPane tab) {
        DefaultListModel<String> model = new DefaultListModel<>();        // backing model for the JList below
        for (String p : loadSavedPaths()) model.addElement(p);            // pre-fill with whatever was saved previously

        JList<String> list = new JList<>(model);                          // shows every saved path
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);       // only one path selected/acted on at a time
        JScrollPane listScroll = new JScrollPane(list);                   // scrollable in case the list grows long
        listScroll.setPreferredSize(new Dimension(420, 180));             // comfortable default size

        JTextField pathField = new JTextField();                          // where the user pastes a new path
        pathField.setPreferredSize(new Dimension(300, 28));
        JButton addBtn = new JButton("Add");                              // saves pathField's text into the list
        JButton removeBtn = new JButton("Remove");                        // deletes the selected entry
        JButton openBtn = new JButton("Open");                            // opens the selected entry's file
        JButton closeBtn = new JButton("Close");                          // dismisses the dialog

        JPanel addRow = new JPanel(new BorderLayout(6, 0));               // paste field + Add button, side by side
        addRow.add(pathField, BorderLayout.CENTER);
        addRow.add(addBtn, BorderLayout.EAST);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Remove / Open / Close, right-aligned
        buttonRow.add(removeBtn);
        buttonRow.add(openBtn);
        buttonRow.add(closeBtn);

        JPanel content = new JPanel(new BorderLayout(8, 8));              // overall dialog layout
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(addRow, BorderLayout.NORTH);
        content.add(listScroll, BorderLayout.CENTER);
        content.add(buttonRow, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Custom Saved Paths", true);  // modal so it behaves like the other dialogs in this app
        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);                              // center over the main window

        Runnable persist = () -> {                                        // small helper so Add/Remove don't repeat this
            List<String> current = new ArrayList<>();
            for (int i = 0; i < model.size(); i++) current.add(model.get(i));
            saveSavedPaths(current);
        };

        addBtn.addActionListener(e -> {                                   // "Add" -> save the pasted path
            String path = pathField.getText().trim();                     // whatever the user pasted/typed
            if (path.isEmpty()) return;                                   // nothing to add
            if (!model.contains(path)) {                                  // avoid duplicate entries
                model.addElement(path);                                   // show it in the list immediately
                persist.run();                                            // write the updated list to Preferences
            }
            pathField.setText("");                                        // clear the field for the next paste
        });
        pathField.addActionListener(e -> addBtn.doClick());               // pressing Enter in the field also adds it

        removeBtn.addActionListener(e -> {                                // "Remove" -> delete the selected path
            int idx = list.getSelectedIndex();                            // which row is selected
            if (idx >= 0) {                                                // something is actually selected
                model.remove(idx);                                        // drop it from the visible list
                persist.run();                                            // write the updated list to Preferences
            }
        });

        openBtn.addActionListener(e -> {                                  // "Open" -> load the selected path's file
            String selected = list.getSelectedValue();                    // the chosen path, or null if none selected
            if (selected != null) {
                open_file_from_saved_path(selected, frame, tab);          // open it into a new tab
                dialog.dispose();                                         // close the dialog once opened
            }
        });
        list.addMouseListener(new MouseAdapter() {                        // double-click a row as a shortcut for "Open"
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) openBtn.doClick();
            }
        });

        closeBtn.addActionListener(e -> dialog.dispose());                // just closes the dialog, no changes needed

        dialog.setVisible(true);                                          // show it and block until the user is done
    }

    public static void save_file_func(JFileChooser chooseFile, JFrame frame, JTabbedPane tab) {
        int index = tab.getSelectedIndex();               // which tab is currently active
        if (index < 0) return;                             // no tab open -> nothing to save
        JTextPane current = getCurrentTextPane(tab);        // that tab's editor
        if (current == null) return;                        // safety check, shouldn't normally happen

        String path = getFilePath(current);                  // the file path this specific tab is already saved to, if any
        if (path == null) {                                  // never saved before -> we don't know where to write it
            save_as_func(chooseFile, frame, tab);            // fall back to "Save as" so the user picks a location
        } else {                                             // already has a known file path -> just overwrite it
            try {
                writeToFile(current, new File(path));        // write the current content back to that same path
            } catch (IOException ex) {                        // disk error, permissions, etc.
                ex.printStackTrace();                         // log details for debugging
                JOptionPane.showMessageDialog(frame, "Failed to save file", "Error", JOptionPane.ERROR_MESSAGE); // tell the user
            }
        }
    }

    public static void save_as_func(JFileChooser chooseFile, JFrame frame, JTabbedPane tab) {
        JTextPane current = getCurrentTextPane(tab);        // the tab we're saving
        if (current == null) return;                         // nothing open -> nothing to do

        configureChooser(chooseFile);                        // set up the .txt/.rtf filters for the Save dialog
        int result = chooseFile.showSaveDialog(frame);       // show the dialog and wait for the user's choice
        if (result != JFileChooser.APPROVE_OPTION) return;   // user cancelled -> nothing to do

        File file = ensureExtension(chooseFile.getSelectedFile(), chooseFile.getFileFilter()); // add .txt/.rtf if the user didn't type one
        try {
            writeToFile(current, file);                        // write the current content to the chosen file
            frame.setTitle("JavaInk - " + file.getName());      // reflect the new filename in the window title

            int index = tab.getSelectedIndex();                 // the tab we just saved
            String previousPath = getFilePath(current);         // this tab's own saved path before this save, if any

            if (previousPath == null) {                                    // first-ever save of a fresh tab -> rename it in place
                Component tabComponent = tab.getTabComponentAt(index);      // the custom title+close-button panel for this tab
                JPanel panel = (JPanel) tabComponent;                       // cast it back to the panel we built earlier
                JLabel label = (JLabel) panel.getComponent(0);              // the title JLabel is always the first child
                label.setText(file.getName());                             // update the visible tab text
                tab.setTitleAt(index, file.getName());                     // update the underlying tab title too

                setFilePath(current, file.getAbsolutePath());               // record the real file path on this tab's pane
            } else {                                                        // "Save as" on an already-named tab creates a new tab so the original stays untouched
                JTextPane newPane = createNewTabbedTextPane(tab, frame, file.getName()); // open a fresh tab for the copy
                copyStyledContent(current.getStyledDocument(), newPane.getStyledDocument()); // clone the content (with styling) into it
                setFilePath(newPane, file.getAbsolutePath());               // record where the new tab's file lives
            }
        } catch (IOException ex) {                                          // disk error, permissions, etc.
            ex.printStackTrace();                                           // log details for debugging
            JOptionPane.showMessageDialog(frame, "Failed to save as", "Error", JOptionPane.ERROR_MESSAGE); // tell the user
        }
    }

    static void save_all_func(JTabbedPane tab, JFileChooser chooseFile, JFrame frame) { //
        int tabCount = tab.getTabCount();                       // how many tabs are currently open
        for (int i = 0; i < tabCount; i++) {                    // walk every open tab
            Component c = tab.getComponentAt(i);                 // this tab's content
            if (!(c instanceof JScrollPane)) continue;           // skip anything that isn't a text tab
            JScrollPane scrollPane = (JScrollPane) c;            // this tab's scroll wrapper
            Component view = scrollPane.getViewport().getView(); // the component inside it
            if (!(view instanceof JTextPane)) continue;          // skip anything that isn't a text editor
            JTextPane textPane = (JTextPane) view;               // the actual editor for this tab

            String path = getFilePath(textPane);                 // the file path this specific tab is already saved to, if any (stored on the pane itself)
            if (path == null) {                                  // never saved before -> ask the user where to put it
                configureChooser(chooseFile);                     // set up the .txt/.rtf filters for the Save dialog
                int result = chooseFile.showSaveDialog(frame);    // show the dialog and wait for the user's choice
                if (result == JFileChooser.APPROVE_OPTION) {      // user picked a location
                    File file = ensureExtension(chooseFile.getSelectedFile(), chooseFile.getFileFilter()); // add .txt/.rtf if needed
                    try {
                        writeToFile(textPane, file);                                // write this tab's content out
                        Component tabComponent = tab.getTabComponentAt(i);          // the custom title+close-button panel for this tab
                        JPanel panel = (JPanel) tabComponent;                       // cast it back to the panel we built earlier
                        JLabel label = (JLabel) panel.getComponent(0);              // the title JLabel is always the first child
                        label.setText(file.getName());                             // update the visible tab text
                        tab.setTitleAt(i, file.getName());                         // update the underlying tab title too
                        setFilePath(textPane, file.getAbsolutePath());             // record the real file path on this tab's pane
                    } catch (IOException ex) {                                     // disk error, permissions, etc.
                        ex.printStackTrace();                                      // log details for debugging
                    }
                }
            } else {                                              // already has a known file path -> just overwrite it
                try {
                    writeToFile(textPane, new File(path));         // write this tab's content back to its existing path
                } catch (IOException ex) {                          // disk error, permissions, etc.
                    ex.printStackTrace();                           // log details for debugging
                }
            }
        }
    }

    static void close_tab(JTabbedPane tab) {
        int selected = tab.getSelectedIndex();     // which tab is currently active
        if (selected < 0) return;                   // nothing open -> nothing to close
        tab.removeTabAt(selected);                   // remove the tab (and its editor) from the UI
    }

    static void exit_func(JFrame frame) {
        frame.dispose();   // close the window, which (given EXIT_ON_CLOSE) ends the application
    }

    // ///////////////////////////////////////////////////////////////////
    // Saving  loading RTF rich text format
    // ///////////////////////////////////////////////////////////////////

    static boolean isRtfPath(String name) {
        return name != null && name.toLowerCase().endsWith(".rtf");   // true only for filenames ending in .rtf
    }

    static void configureChooser(JFileChooser chooser) {
        chooser.resetChoosableFileFilters();                                                                        // clear any filters left over from a previous use
        FileNameExtensionFilter rtfFilter = new FileNameExtensionFilter("Rich Text Format (*.rtf) - keeps text color", "rtf"); // filter for .rtf files
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Plain Text (*.txt)", "txt");                // filter for .txt files
        chooser.addChoosableFileFilter(txtFilter);                                                                   // let the user pick plain text
        chooser.addChoosableFileFilter(rtfFilter);                                                                   // let the user pick RTF
        chooser.setFileFilter(rtfFilter);                                                                            // RTF is selected by default since it preserves colors
    }

    static File ensureExtension(File file, FileFilter filter) {
        String name = file.getName();                                    // the filename the user typed/selected
        if (name.contains(".")) return file;                             // already has an extension -> leave it alone
        String ext = "txt";                                              // fall back to .txt if we can't determine one
        if (filter instanceof FileNameExtensionFilter) {                 // the active filter tells us which extension to use
            String[] exts = ((FileNameExtensionFilter) filter).getExtensions(); // the filter's accepted extensions
            if (exts.length > 0) ext = exts[0];                          // use its first (primary) extension
        }
        return new File(file.getParentFile(), name + "." + ext);         // rebuild the File with the extension appended
    }

    static void writeToFile(JTextPane pane, File file) throws IOException {
        StyledDocument doc = pane.getStyledDocument();     // the document holding this tab's text + styling
        try {
            if (isRtfPath(file.getName())) {                              // saving as .rtf preserves colors/formatting
                RTFEditorKit rtfKit = new RTFEditorKit();                  // knows how to read/write the RTF format
                try (OutputStream os = new FileOutputStream(file)) {       // open the destination file for writing
                    rtfKit.write(os, doc, 0, doc.getLength());             // serialize the whole document as RTF
                }
            } else {                                                       // saving as plain text drops formatting
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) { // open the destination file for writing
                    bw.write(doc.getText(0, doc.getLength()));             // write out just the raw text
                }
            }
        } catch (BadLocationException ble) {                               // shouldn't happen since we use the full valid range
            throw new IOException(ble);                                    // wrap it as an IOException so callers only need one catch
        }
    }

    /** Copies text + character attributes (colors, sizes, ...) from one StyledDocument into another. */
    static void copyStyledContent(StyledDocument fromDoc, StyledDocument toDoc) {
        try {
            if (toDoc.getLength() > 0) toDoc.remove(0, toDoc.getLength());   // clear the destination first so we don't append onto old content
            int len = fromDoc.getLength();                                   // total length of the source document
            for (int i = 0; i < len; ) {                                     // walk the source document run by run
                Element el = fromDoc.getCharacterElement(i);                 // the styling "run" that covers position i
                int end = Math.min(el.getEndOffset(), len);                  // where that run ends (clamped to the doc length)
                String text = fromDoc.getText(i, end - i);                   // the actual characters in this run
                toDoc.insertString(toDoc.getLength(), text, el.getAttributes()); // copy them into the destination with the same styling
                i = end;                                                      // advance past this run to the next one
            }
        } catch (BadLocationException ex) {                                   // shouldn't happen given the bounds above
            ex.printStackTrace();                                             // log details for debugging
        }
    }

    static void update(JTabbedPane tab) {
        JTextPane pane = getCurrentTextPane(tab);                            // the editor whose stats we're about to show
        if (pane == null || statusLabel == null) return;                    // nothing to update if there's no tab or no label yet
        try {
            StyledDocument doc = pane.getStyledDocument();                   // the document backing the active tab
            String text = doc.getText(0, doc.getLength());                   // its full current text
            statusLabel.setText("Window | UTF-8 | Line: " + text.split("\n", -1).length + " , Char: " + text.length()); // recompute line count and char count
        } catch (BadLocationException ignored) {                              // shouldn't happen given the bounds above
        }
    }

    // ///////////////////////////////////////////////////////////////////
    // Zoom
    // ///////////////////////////////////////////////////////////////////

    static void zoom_func(JTabbedPane tab, int change) {
        JTextPane pane = getCurrentTextPane(tab);        // the editor being zoomed
        if (pane == null) return;                          // nothing open -> nothing to zoom

        Object stored = pane.getClientProperty("fontSize");                        // the size we stashed earlier for this pane
        int currentSize = (stored instanceof Integer) ? (Integer) stored : BASE_FONT_SIZE; // fall back to the base size if nothing stored yet
        int newSize = (change == 0) ? BASE_FONT_SIZE : currentSize + change;       // change==0 means "reset zoom" -> snap back to base
        if (newSize < 6) newSize = 6;                        // don't let the font shrink below readable size
        if (newSize > 72) newSize = 72;                      // don't let the font grow absurdly large

        StyledDocument doc = pane.getStyledDocument();       // the document whose text we're resizing
        SimpleAttributeSet sizeAttr = new SimpleAttributeSet(); // a fresh attribute set to carry just the size
        StyleConstants.setFontSize(sizeAttr, newSize);        // set the font-size attribute
        doc.setCharacterAttributes(0, doc.getLength(), sizeAttr, false); // merge: keeps colors, only changes size

        MutableAttributeSet inputAttrs = pane.getInputAttributes();  // attributes used for text typed from now on
        StyleConstants.setFontSize(inputAttrs, newSize);              // make newly-typed text use the new size too

        pane.setFont(pane.getFont().deriveFont((float) newSize));    // also resize the pane's base font (affects empty-document display)
        pane.putClientProperty("fontSize", newSize);                  // remember the new size for the next zoom action

        if (zoomLabel != null) {                                                  // update the status bar if it exists yet
            int percent = Math.round((newSize * 100f) / BASE_FONT_SIZE);          // convert the font size back into a percentage
            zoomLabel.setText("Zoom: " + percent + "%");                          // show it to the user
        }
    }

    // ///////////////////////////////////////////////////////////////////
    // Find / Replace
    // ///////////////////////////////////////////////////////////////////

    static void openFindDialog(JFrame frame, JTabbedPane tab) {
        if (find_dialog == null) {                       // build the dialog lazily, only the first time it's needed
            find_dialog = FindText(frame, tab);           // construct the Find dialog UI
            installFindDialogCleanup(tab);                 // make sure closing it clears highlights and state
        }
        find_dialog.toFront();          // bring it in front of the main window if it was already open
        find_dialog.setVisible(true);   // show it
    }

    static void openReplaceDialog(JFrame frame, JTabbedPane tab) {
        if (find_dialog == null) {                       // build the dialog lazily, only the first time it's needed
            find_dialog = FindText(frame, tab);           // construct the Find dialog UI
            installFindDialogCleanup(tab);                 // make sure closing it clears highlights and state
        }
        replacer(find_dialog, frame, tab);   // splice the Replace row into the dialog
        find_dialog.toFront();               // bring it in front of the main window if it was already open
        find_dialog.setVisible(true);        // show it
    }

    static void installFindDialogCleanup(JTabbedPane tab) {
        find_dialog.addWindowListener(new WindowAdapter() {          // listen for the dialog being closed
            @Override
            public void windowClosing(WindowEvent w) {
                JTextPane current = getCurrentTextPane(tab);          // the currently active editor, if any
                if (current != null) current.getHighlighter().removeAllHighlights(); // clear any "Find All" highlights left behind
                store_panel_1 = null;      // forget the cached Find row so a fresh dialog gets rebuilt next time
                store_panel_2 = null;      // forget the cached button row so a fresh dialog gets rebuilt next time
                find_dialog.dispose();     // actually destroy the dialog window
                find_dialog = null;        // clear the shared reference so openFindDialog() rebuilds it next time
            }
        });
    }

    public static void replacer(JDialog find_dialog, JFrame frame, JTabbedPane tab) {
        Container contentStore = find_dialog.getContentPane();   // the dialog's content area we're about to rearrange

        if (store_panel_1 == null) {                                      // first time Replace is opened for this dialog instance
            store_panel_1 = (JPanel) contentStore.getComponent(1);        // remember the "find field + button" row
            store_panel_2 = (JPanel) contentStore.getComponent(2);        // remember the "next/prev/all" button row
        }
        JPanel store_panel_3 = Replace_func();     // build the new "replace field + button" row

        contentStore.removeAll();          // clear the dialog so we can rebuild it with Replace included
        contentStore.add(store_panel_1);   // re-add the find row
        contentStore.add(store_panel_2);   // re-add the next/prev/all row
        contentStore.add(store_panel_3);   // add the new replace row
        contentStore.revalidate();         // recompute layout for the new set of components
        contentStore.repaint();            // redraw the dialog with its new contents

        JPanel store_panel_of_find = (JPanel) contentStore.getComponent(0);          // the top "label" panel
        JTextField store_field_of_find = (JTextField) store_panel_of_find.getComponent(0); // (unused directly here, but located for symmetry)

        JPanel store_panel_of_replace = (JPanel) contentStore.getComponent(2);        // the replace row we just added
        JButton store_button_of_replace = (JButton) store_panel_of_replace.getComponent(1); // its "Replace" button
        JTextField store_field_of_replace = (JTextField) store_panel_of_replace.getComponent(0); // its replacement-text field

        // Remove any listener added on a previous Ctrl+R / Replace open so replacements don't fire multiple times
        for (ActionListener al : store_button_of_replace.getActionListeners()) {   // walk any listeners left from a previous open
            store_button_of_replace.removeActionListener(al);                      // strip them out to avoid duplicate replacements
        }

        store_button_of_replace.addActionListener(e -> {                 // wire up the (now-clean) Replace button
            String replaceGoal = store_field_of_replace.getText();        // the text to insert
            String replaceTarget = store_field_of_find.getText();         // the text to search for

            JTextPane current = getCurrentTextPane(tab);                  // the editor we're replacing text in
            if (current == null || replaceTarget.isEmpty()) {             // nothing open, or nothing to search for
                JOptionPane.showMessageDialog(frame, "The replacement is unsuccessful", "Report", JOptionPane.WARNING_MESSAGE); // let the user know
                return;                                                    // stop, nothing more to do
            }
            StyledDocument doc = current.getStyledDocument();             // the document we're editing
            try {
                String content = doc.getText(0, doc.getLength());          // its full current text
                int index = content.indexOf(replaceTarget);                 // find the first occurrence of the search text
                if (index != -1) {                                          // found it
                    doc.remove(index, replaceTarget.length());              // delete the old text
                    doc.insertString(index, replaceGoal, null);             // insert the replacement in its place
                } else {                                                    // not found
                    JOptionPane.showMessageDialog(frame, "The replacement is unsuccessful", "Report", JOptionPane.WARNING_MESSAGE); // let the user know
                }
            } catch (BadLocationException ex) {                             // shouldn't happen given the bounds above
                JOptionPane.showMessageDialog(frame, "The replacement is unsuccessful", "Report", JOptionPane.WARNING_MESSAGE); // let the user know
            }
        });

        find_dialog.setVisible(true);   // make sure the (now Replace-equipped) dialog is shown
    }

    static JDialog FindText(JFrame frame, JTabbedPane tab) {
        JDialog dialog = new JDialog(frame, "Find", false);   // non-modal so the user can keep editing while it's open
        dialog.setLayout(new GridLayout(3, 1));                // three stacked rows: label, find field, action buttons
        dialog.setSize(600, 200);                              // fixed initial size
        dialog.setLocationRelativeTo(null);                    // center it on screen

        JButton findBtn = new JButton("Find");     // runs the first search
        JButton findNext = new JButton("Next");    // jumps to the next match
        JButton findPrev = new JButton("Prev");    // jumps to the previous match
        JButton findAll = new JButton("All");      // highlights every match at once

        JPanel panel_for_label_guide = new JPanel();                                  // row 1: just the instructional label
        panel_for_label_guide.setLayout(new FlowLayout(FlowLayout.CENTER));           // centered label
        JLabel label_guide = new JLabel("Enter text to find");                        // the instructional text
        label_guide.setFont(new Font(label_guide.getFont().getName(), Font.BOLD, 20)); // make it stand out
        panel_for_label_guide.add(label_guide);                                        // place it in row 1

        JTextField field = new JTextField();                       // where the user types the search text
        field.setPreferredSize(new Dimension(300, 30));             // give it a comfortable width/height

        JPanel all_findBtn_panel = new JPanel();                         // row 3: Next/Prev/All buttons
        all_findBtn_panel.setLayout(new FlowLayout(FlowLayout.LEFT));    // left-aligned buttons
        all_findBtn_panel.add(findNext);                                  // place "Next"
        all_findBtn_panel.add(findPrev);                                  // place "Prev"
        all_findBtn_panel.add(findAll);                                   // place "All"

        JPanel dialog_panel = new JPanel();                        // row 2: the search field + "Find" button
        dialog_panel.setLayout(new FlowLayout(FlowLayout.LEFT));   // left-aligned
        dialog_panel.add(field);                                    // place the text field
        dialog_panel.add(findBtn);                                  // place the "Find" button

        dialog.add(panel_for_label_guide);   // row 1 into the dialog
        dialog.add(dialog_panel);            // row 2 into the dialog
        dialog.add(all_findBtn_panel);       // row 3 into the dialog

        final int[] currentIndex = {-1};   // tracks the offset of the most recent match (array so the lambdas below can mutate it)

        // Shared highlight painter used by Find / Next / Prev so a match is always visibly painted
        // (not just "selected"), regardless of whether the text pane currently has keyboard focus.
        Highlighter.HighlightPainter matchPainter =
                new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 224, 0, 145));

        Runnable doFind = () -> {                                        // shared logic for the first "Find"
            String target_word = field.getText();                        // what the user typed
            JTextPane current_textarea = getCurrentTextPane(tab);        // the editor to search in
            if (current_textarea == null || target_word.isEmpty()) return; // nothing to search, or nothing typed
            try {
                Document doc = current_textarea.getDocument();            // the document to search
                String content = doc.getText(0, doc.getLength());         // its full current text

                current_textarea.getHighlighter().removeAllHighlights();  // clear any previous "Find All" highlights

                int index = content.indexOf(target_word);                  // search from the very start
                currentIndex[0] = index;                                   // remember where we landed
                if (index >= 0) {                                          // found a match
                    current_textarea.setCaretPosition(index);              // move the caret there
                    current_textarea.select(index, index + target_word.length()); // select the matched text
                    current_textarea.getHighlighter().addHighlight(index, index + target_word.length(), matchPainter); // paint it explicitly so it's visible even without window focus
                    current_textarea.requestFocusInWindow();               // bring keyboard focus back to the editor
                }
            } catch (BadLocationException ignored) {                       // shouldn't happen given the bounds above
            }
            findNext.setEnabled(true);   // now that a search has run, allow stepping to the next match
            findPrev.setEnabled(true);   // ...and the previous match
            findAll.setEnabled(true);    // ...and highlighting all matches
        };

        field.addKeyListener(new KeyAdapter() {           // let pressing Enter in the field trigger a search too
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {  // Enter key pressed
                    doFind.run();                            // run the same logic as clicking "Find"
                }
            }
        });
        findNext.setEnabled(false);   // disabled until a first search has happened
        findPrev.setEnabled(false);   // disabled until a first search has happened
        findAll.setEnabled(false);    // disabled until a first search has happened
        findBtn.addActionListener(e -> doFind.run());   // "Find" button just runs the shared search logic

        findNext.addActionListener(e -> {                                 // "Next" -> jump forward to the following match
            String target_word = field.getText();                        // what we're searching for
            JTextPane current_textarea = getCurrentTextPane(tab);        // the editor to search in
            if (current_textarea == null || target_word.isEmpty()) return; // nothing to search, or nothing typed
            try {
                Document doc = current_textarea.getDocument();            // the document to search
                String content = doc.getText(0, doc.getLength());         // its full current text

                current_textarea.getHighlighter().removeAllHighlights();  // clear any previous highlights

                int index = content.indexOf(target_word, currentIndex[0] + 1); // search just past the last match
                if (index < 0) index = content.indexOf(target_word);           // not found further down -> wrap around to the start
                currentIndex[0] = index;                                       // remember the new position
                if (index >= 0) {                                              // found a match
                    current_textarea.setCaretPosition(index);                  // move the caret there
                    current_textarea.select(index, index + target_word.length()); // select the matched text
                    current_textarea.getHighlighter().addHighlight(index, index + target_word.length(), matchPainter); // paint it explicitly so it's always visible
                    current_textarea.requestFocusInWindow();                   // bring keyboard focus back to the editor
                }
            } catch (BadLocationException ignored) {                           // shouldn't happen given the bounds above
            }
        });

        findPrev.addActionListener(e -> {                                 // "Prev" -> jump back to the preceding match
            String target_word = field.getText();                        // what we're searching for
            JTextPane current_textarea = getCurrentTextPane(tab);        // the editor to search in
            if (current_textarea == null || target_word.isEmpty()) return; // nothing to search, or nothing typed
            try {
                Document doc = current_textarea.getDocument();            // the document to search
                String content = doc.getText(0, doc.getLength());         // its full current text

                current_textarea.getHighlighter().removeAllHighlights();  // clear any previous highlights

                int index = content.lastIndexOf(target_word, currentIndex[0] - 1); // search just before the last match
                if (index < 0) index = content.lastIndexOf(target_word);           // not found further up -> wrap around to the end
                currentIndex[0] = index;                                            // remember the new position
                if (index >= 0) {                                                   // found a match
                    current_textarea.setCaretPosition(index);                       // move the caret there
                    current_textarea.select(index, index + target_word.length());   // select the matched text
                    current_textarea.getHighlighter().addHighlight(index, index + target_word.length(), matchPainter); // paint it explicitly so it's always visible
                    current_textarea.requestFocusInWindow();                        // bring keyboard focus back to the editor
                }
            } catch (BadLocationException ignored) {                                // shouldn't happen given the bounds above
            }
        });

        findAll.addActionListener(e -> {                                  // "All" -> highlight every occurrence at once
            String target_word = field.getText();                        // what we're searching for
            JTextPane current_textarea = getCurrentTextPane(tab);        // the editor to search in
            if (current_textarea == null || target_word.isEmpty()) return; // nothing to search, or nothing typed
            try {
                Document doc = current_textarea.getDocument();            // the document to search
                String content = doc.getText(0, doc.getLength());         // its full current text

                Highlighter highlighter = current_textarea.getHighlighter();  // manages highlight painting for this editor
                highlighter.removeAllHighlights();                             // clear any previous highlights first

                int index = 0;                                                 // where to resume searching from
                while ((index = content.indexOf(target_word, index)) >= 0) {   // keep finding matches until none remain
                    highlighter.addHighlight(index, index + target_word.length(), matchPainter); // paint a highlight over this match
                    index += target_word.length();                             // continue searching after this match
                }
            } catch (BadLocationException ex) {                               // shouldn't happen given the bounds above
                ex.printStackTrace();                                          // log details for debugging
            }
        });

        return dialog;   // hand the fully wired dialog back to the caller
    }

    static JPanel Replace_func() {
        JPanel dialog_panel_of_replace_func = new JPanel();                       // the "replace field + button" row
        dialog_panel_of_replace_func.setLayout(new FlowLayout(FlowLayout.LEFT));  // left-aligned contents

        JTextField replace_field = new JTextField();                     // where the user types the replacement text
        replace_field.setPreferredSize(new Dimension(300, 30));           // comfortable width/height
        JButton replace_button = new JButton("Replace");                  // triggers the actual replacement

        dialog_panel_of_replace_func.add(replace_field);    // place the text field
        dialog_panel_of_replace_func.add(replace_button);   // place the button

        return dialog_panel_of_replace_func;   // hand the row back so replacer() can splice it into the dialog
    }
}