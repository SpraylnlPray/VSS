import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        final String server;
        final String client;

        server = JOptionPane.showInputDialog("Enter Server-Address", "localhost");
        client = JOptionPane.showInputDialog("Enter Username");

        if (server == null || server.equals("")) {
            return;
        }
        if (client == null || client.equals("")) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChatClient myClient = new ChatClient(server, client);
                myClient.setVisible(true);
            }
        });


    }
}
