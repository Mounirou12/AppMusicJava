package packag;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class MyPlayListPage implements ChangeListener, ActionListener {

    private static Clip[] clip;
    private static int currentClipIndex = 0;
    private static JSlider slider;  // Déclarez ceci comme variable de classe

    static JButton stopButton;
    static JButton playButton;
    static JButton nextButton;
    static JButton previousButton;

    static JButton randomButton;
    static JButton repeatButton;


    

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // This class represents the playlist page of the application.
        JFrame frame = new JFrame("PlayListMonor PlayList");
        frame.setLayout(null);
        int frameWidth = 500;
        int frameHeight = 500;

        int imageWidth = 250;
        int imageHeight = 120;

        //
        ImageIcon playmusIcon = new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/play.jpeg")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH));
        // The image icon for the timer.
        ImageIcon imageTimerIcon = new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/timer.jpg")).getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
        //The image icon for the buttons
        ImageIcon stopIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/stop.png")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        ImageIcon playIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/play.png")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        ImageIcon nextIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/next.png")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        ImageIcon previousIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/previous.png")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        ImageIcon randomIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/random.jpg")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        ImageIcon repeatIcon = makeIconCircular(new ImageIcon(new ImageIcon(MyPlayListPage.class.getResource("/packag/Images/repeat.png")).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));

        JPanel sliderPanel;

        // Set the play music label with the play music icon.
        JLabel playMusicLabel = new JLabel();
        playMusicLabel.setIcon(playmusIcon);
        playMusicLabel.setHorizontalAlignment(JLabel.CENTER);
        playMusicLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        playMusicLabel.setBounds(100, 20, imageWidth, imageHeight);

        // Create buttons for the playlist page and add the image icon for all button.
        stopButton = new RoundButton(stopIcon);
        playButton = new RoundButton(playIcon);
        nextButton = new RoundButton(nextIcon);
        previousButton = new RoundButton(previousIcon);
        randomButton = new RoundButton(randomIcon);
        repeatButton = new RoundButton(repeatIcon);

        //Actionlistener for the buttons
        playButton.addActionListener(e -> {
            if (clip[currentClipIndex].isRunning()) {
                clip[currentClipIndex].stop();  // Pause
            } else {
                clip[currentClipIndex].start();  // Play
            }
        });
        nextButton.addActionListener(e -> {
            changeClip((currentClipIndex + 1) % clip.length);
        });
        previousButton.addActionListener(e -> {
                    changeClip((currentClipIndex - 1 + clip.length) % clip.length);
        });
        randomButton.addActionListener(e -> {
            int randomIndex;
            do {
                randomIndex = (int) (Math.random() * clip.length);
            } while (randomIndex == currentClipIndex && clip.length >1);
            changeClip(randomIndex);
        });
        repeatButton.addActionListener(e -> { 
            clip[currentClipIndex].setFramePosition(0);
            clip[currentClipIndex].start();
            slider.setValue(0);
        });
        stopButton.addActionListener(e -> {
            clip[currentClipIndex].stop();
            clip[currentClipIndex].setMicrosecondPosition(0);
            slider.setValue(0);  // Réinitialise le slider
        });
        //The list of musics
        File file1 = new File("src/packag/music/forcement.wav");
        File file2 = new File("src/packag/music/mbife.wav");
        File file3 = new File("src/packag/music/rodela.wav");

        AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(file1);
        AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(file2);
        AudioInputStream audioInputStream3 = AudioSystem.getAudioInputStream(file3);

        Clip clip1 = AudioSystem.getClip();
        Clip clip2 = AudioSystem.getClip();
        Clip clip3 = AudioSystem.getClip();

        clip1.open(audioInputStream1);
        clip2.open(audioInputStream2);
        clip3.open(audioInputStream3);

        clip = new Clip[3];
        clip[0] = clip1;
        clip[1] = clip2;
        clip[2] = clip3;

        //Create Table
        // Set the names colums to a table.
        String[] columnJLabels = {"#Title             ", "Album", "       Duration"};

        //Create the data who will be displayed in the table
        Object[][] datalist = {{"","",""},
            {"forcement", "Album1", formatDuration(clip1.getFrameLength())},
            {"mbife", "Album2", formatDuration(clip2.getFrameLength())},
            {"rodela", "Album3", formatDuration(clip3.getFrameLength())}
        };
        int xRowHeiht = 30; // Height of each row in the table
        int xHeightTable = xRowHeiht * datalist.length; // Total height of the table based on the number of rows

        // Create a panel to hold the table.
        JTable table = new JTable(datalist, columnJLabels){
            public Class<?> getColumnClass(int Column){
                return String.class;
            } 
        };
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(xRowHeiht);
        table.setBounds(0, 280, 500, xHeightTable); // Positionne la table sans scroll
        table.setShowGrid(false); // Disable grid lines
        table.setShowHorizontalLines(false); // Disable horizontal lines
        table.setShowVerticalLines(false); // Disable vertical lines
        table.setForeground(Color.white);//set the color of police in white
        table.setBackground(Color.black);

        // Set the table header font and alignment.
        //Get back the table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setBounds(0, 270, 500, 30); // Place le header juste au-dessus de la table
        header.setForeground(Color.white); // Set the header text color to white
        header.setBackground(Color.black); // Set the header background color to black
        header.setBorder(null);

        // Renderer to align left
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        // Renderer to align center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Renderer to align right
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // Appliquer l'alignement aux cellules
        header.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        header.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        header.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 6, 0));
        controlPanel.add(randomButton);
        controlPanel.add(previousButton);
        controlPanel.add(playButton);
        controlPanel.add(nextButton);
        controlPanel.add(repeatButton);
        controlPanel.setBackground(Color.black);
        controlPanel.setBounds(80, 160, 300, 60);

        // Set the icons for the buttons.
        // Create a panel to hold the slider.
        sliderPanel = new JPanel();
        //Create a slider for allow the user to click or drag to jump to a specific time
        slider = new JSlider(0, (int) clip[currentClipIndex].getFrameLength(), 0);

        slider.setBounds(10, 5, 300, 18);
        slider.setOrientation(SwingConstants.HORIZONTAL);

        //Update the slider when the music is playing
        Timer timer = new Timer(100, e -> {
            if (clip[currentClipIndex] != null && clip[currentClipIndex].isRunning()) {
                slider.setValue(clip[currentClipIndex].getFramePosition());
            }
        });
        timer.start();

        //When the user click on the slider, it will change the position of the music
       
        sliderPanel.setBounds(80, 230, 320, 30); // Set bounds for the slider panel
        sliderPanel.setLayout(null); // Set layout to null for absolute positioning
        //sliderPanel.setBackground(Color.black);
        sliderPanel.setBorder(null);
        slider.setBackground(Color.black);
        slider.setBorder(null);
        slider.setPaintLabels(false);
        slider.setForeground(Color.white);
        slider.setUI(new RoundSliderUI(slider));

        sliderPanel.add(slider);

        // Panel principal avec BorderLayout
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.getContentPane().setBackground(Color.black);
        //frame.add(labelsPanel);
        frame.add(header);
        frame.add(table);
        frame.add(sliderPanel);
        frame.add(controlPanel);
        frame.add(playMusicLabel);

        //Add the labels to the panel
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static ImageIcon makeIconCircular(ImageIcon icon) {
        int size = Math.min(icon.getIconWidth(), icon.getIconHeight());
        BufferedImage mask = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mask.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, size, size);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(icon.getImage(), 0, 0, size, size, null);
        g2.dispose();
        return new ImageIcon(mask);

    }

    private static void changeClip(int newIndex){
        clip[currentClipIndex].stop();  
        currentClipIndex = newIndex;
        clip[currentClipIndex].setFramePosition(0);
        slider.setMaximum(clip[currentClipIndex].getFrameLength());
        slider.setValue(0);
        clip[currentClipIndex].start();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChanged'");
    }

    // Supprimez la boucle while infinie qui bloque le thread
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private static String formatDuration(long frames){
        AudioFormat format = clip[currentClipIndex].getFormat();
        float frameRate = format.getFrameRate();
        long seconds = (long) (frames / frameRate);
        return  String.format("%01d:%02d", seconds / 60, seconds % 60);
    }

    public static void launchPlaylist(){
        try {
            main(new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
