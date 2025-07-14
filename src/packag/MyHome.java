package packag;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MyHome implements ActionListener {

    // This class represents the home page of the application.
    // The frame is the main window of the application.
    JFrame frame = new JFrame("PlayListMonor Home");
    // The button to open the playlist page.
    // When the user clicks this button, the playlist page will be opened.
    JButton playListButton = new JButton("PlayList");

    // The image icon for the home page.
    ImageIcon imageHome = new ImageIcon(new ImageIcon(getClass().getResource("/packag/Images/playlist.jpeg")).getImage());
    // The label to display the image on the home page.
    JLabel imageLabel = new JLabel();

    MyHome() {
        // This constructor initializes the home page of the application.
        int frameWidth = 420;
        int frameHeight = 420;

        int imageWidth = 300;
        int imageHeight = 150;
        int imageX = (frameWidth - imageWidth) / 2;
        int imageY = 50;

        int buttonWidth = 200;
        int buttonHeight = 40;
        int buttonX = (frameWidth - buttonWidth) / 2;
        int buttonY = 300;

        // Set the image icon to the label.
        imageLabel.setBounds(imageX, imageY, imageWidth, imageWidth);
        imageLabel.setIcon(imageHome);
        playListButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        playListButton.setFont(new Font("Monospaced", Font.BOLD, 25));
        playListButton.setForeground(Color.white);
        playListButton.setBackground(Color.black);
        playListButton.setFocusable(false);
        playListButton.setBorderPainted(false);
        playListButton.addActionListener(this);

        // Add the label to the frame.
        frame.add(imageLabel);
        // Add the button to the frame.
        frame.add(playListButton);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @SuppressWarnings("static-access")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playListButton) {
            frame.dispose();
            MyPlayListPage.launchPlaylist(); // Correctly calls the static main method of MyPlayListPage
        }
    }

}
