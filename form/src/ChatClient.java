import javax.swing.*;

public class ChatClient extends JFrame {
    private JPanel rootPanel;
    private JButton sendButton;
    private JTextField chatInput;
    private JLabel onlineHeader;
    private JPanel onlinePanel;
    private JPanel mainPanel;
    private JPanel chatContentPanel;

    public ChatClient(String server, String client) {
        add(rootPanel);

        setTitle("User " + client + " at " + server);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
