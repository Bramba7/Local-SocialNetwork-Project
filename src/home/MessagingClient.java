/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 *
 * @author moallemi_faraz
 */
public class MessagingClient extends JFrame
{
    LocalsDB db = new LocalsDB();
    JList<String> msgArea;
    String name = null;
    int userInfo;
    String activity = null;
    String enroll_id = null;

    public MessagingClient(int info)
    {
        super("Messaging Window");
        userInfo = info;
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu menu = new JMenu("Members");
//        JMenuItem log = new JMenuItem("login");
//        log.addActionListener(e -> {
//            name = JOptionPane.showInputDialog(this, "Type in name:");
//            setTitle("Chat Client: " + name);
//        });
//        menu.add(log);

        name = db.getUserName(userInfo);
        // get all other chat users
//        String[] allUsers = db.getUsers();
//        for (String u : allUsers) {
//            JMenuItem it = new JMenuItem(u);
//            menu.add(it);
//            it.addActionListener(e -> {
//                partner = u;
//                setTitle("Chat Client: " + name + " -> " + u);
//            });
//        };
        bar.add(menu);
        // menu to filter by activity number
        // 1. need to get activities
        // 2. let user select one
        // 3. then ... ?
        JMenu menu2 = new JMenu("Activities");
        HashMap<String, Pair<String,String>> activities = db.getUserActivities(userInfo);
        for (Map.Entry<String,Pair<String,String>> entry : activities.entrySet()) {
            Pair p = entry.getValue();
            JMenuItem it = new JMenuItem(p.getValue().toString());
            menu2.add(it);
            it.addActionListener(e -> {
                enroll_id = entry.getKey();
                activity = entry.getValue().getKey();
                setTitle("Chat Client: " + name + ": " + entry.getValue());
            });
        }
        bar.add(menu2);
        
        addMessagePanel();
        addAllMessagePanel();
        updateMessages();
    }

    public static void main(String[] args)
    {
        //Create and set up the window.
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new MessagingClient(13);
            frame.setVisible(true);
            ;
        });
    }

    MessagingClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addMessagePanel()
    {
        JTextField msgField = new JTextField(40);
        add(msgField, BorderLayout.SOUTH);
        msgField.addActionListener((e) ->
        {
            db.insert(userInfo, enroll_id, msgField.getText());
            msgField.setText("");
        });
    }

    void addAllMessagePanel() 
    {
        msgArea = new JList<>(db.getAllMessages());
        add(msgArea, BorderLayout.CENTER);
    }

    void updateMessages() 
    {
        Timer timer = new Timer(500, (e) -> 
        {
//            if (name == null) {
//                name = JOptionPane.showInputDialog(this, "Type in name:");
//                setTitle("Chat Client: " + name);
//            }
            remove(msgArea);
            if (enroll_id != null)
            {
                msgArea = new JList(db.getMessagesFor(activity));
            } else
            {
                msgArea = new JList<>(db.getAllMessages());
            }
            add(msgArea, BorderLayout.CENTER);
            validate();
        });
        timer.start();

    }
}
