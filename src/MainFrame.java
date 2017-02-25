import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MainFrame {
    private HandleKeyEvent handler; 
    private final JFrame frame;
    private JButton[][] butArr;
    private JPanel[] panelArr;
    private JPanel header;
    private JTextField tfScore;
    private JTextField tfHighestScore;
    private JLabel labelScore;
    private JLabel labelHighestScore;
    private final Font f1;
    private final Font f2;
    private Color[] colorArr; 

    public MainFrame(HandleKeyEvent handler) {
        this.handler = handler;
        frame = new JFrame("2048");
        butArr = new JButton[4][4];
        panelArr = new JPanel[7];
        header = new JPanel(new GridLayout(2, 1));
        tfScore = new JTextField(12);
        tfHighestScore = new JTextField(12);
        labelScore = new JLabel("Score"); 
        labelHighestScore = new JLabel("Highest"); 
        f1 = new Font("ope1", Font.PLAIN, 22);
        f2 = new Font("ope1", Font.PLAIN, 26);
        colorArr = new Color[13];
        
        colorArr[0] = new Color(240, 233, 206);
        colorArr[1] = new Color(242, 231, 184);
        colorArr[2] = new Color(245, 188, 66);
        colorArr[3] = new Color(242, 168, 7);
        colorArr[4] = new Color(227, 90, 36);
        colorArr[5] = new Color(237, 12, 12);
        colorArr[6] = new Color(245, 218, 15);
        colorArr[7] = new Color(245, 218, 15);
        colorArr[8] = new Color(217, 193, 15);
        colorArr[9] = new Color(217, 193, 15);
        colorArr[10] = new Color(217, 193, 15);
        colorArr[11] = new Color(61, 242, 85);
        colorArr[12] = new Color(255, 255, 255);
        

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                butArr[i][j] = new JButton();
            }
        }
        
        for (int i = 0; i < 7; i++) {
            panelArr[i] = new JPanel();
            panelArr[i].setLayout(new GridLayout(1, 4));
        }
        panelArr[4].setLayout(new BorderLayout());
        panelArr[5].setLayout(new BorderLayout());
        panelArr[6].setLayout(new GridLayout(4, 1));
    }
    
    public void initFrame() {
        labelScore.setFont(f2);
        tfScore.setFont(f2);
        tfScore.setEnabled(false);
        tfScore.setHorizontalAlignment(JTextField.RIGHT);
        panelArr[4].add(labelScore, BorderLayout.WEST);
        panelArr[4].add(tfScore, BorderLayout.EAST);
        panelArr[4].setBorder(new EmptyBorder(4, 8, 8, 8));

        labelHighestScore.setFont(f2);
        tfHighestScore.setFont(f2);
        tfHighestScore.setEnabled(false);
        tfHighestScore.setHorizontalAlignment(JTextField.RIGHT);
        panelArr[5].add(labelHighestScore, BorderLayout.WEST);
        panelArr[5].add(tfHighestScore, BorderLayout.EAST);
        panelArr[5].setBorder(new EmptyBorder(8, 8, 4, 8));
        header.add(panelArr[5]);
        header.add(panelArr[4]);
        panelArr[6].setBorder(new EmptyBorder(8, 8, 8, 8));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                panelArr[i].add(butArr[i][j]);
                butArr[i][j].setEnabled(false);
                butArr[i][j].setVisible(false);
                butArr[i][j].setFont(f1);
            }
            panelArr[6].add(panelArr[i]);
        }
        
        int highestScoreFromFile = handler.getHighestScoreFromFile();
        handler.setHighestScore(highestScoreFromFile);
        tfHighestScore.setText(String.valueOf(highestScoreFromFile));

        frame.addKeyListener(new MyMonitor1());
        frame.addWindowListener(new MyMonitor2());
        frame.getContentPane().add(header, BorderLayout.NORTH);
        frame.getContentPane().add(panelArr[6], BorderLayout.CENTER);
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        reDrawPanel();
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void reDrawPanel() {
        int[][] butBitMap = handler.getBitMap();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int score = butBitMap[i][j];
                if (score != 0) {
                    switch (score) {
                    case 2:
                       butArr[i][j].setBackground(colorArr[0]);
                       break;
                    case 4:
                       butArr[i][j].setBackground(colorArr[1]);
                       break;
                    case 8:
                       butArr[i][j].setBackground(colorArr[2]);
                       break;
                    case 16:
                       butArr[i][j].setBackground(colorArr[3]);
                       break;
                    case 32:
                       butArr[i][j].setBackground(colorArr[4]);
                       break;
                    case 64:
                       butArr[i][j].setBackground(colorArr[5]);
                       break;
                    case 128:
                       butArr[i][j].setBackground(colorArr[6]);
                       break;
                    case 256:
                       butArr[i][j].setBackground(colorArr[7]);
                       break;
                    case 512:
                       butArr[i][j].setBackground(colorArr[8]);
                       break;
                    case 1024:
                       butArr[i][j].setBackground(colorArr[9]);
                       break;
                    case 2048:
                       butArr[i][j].setBackground(colorArr[10]);
                       break;
                    case 4096:
                       butArr[i][j].setBackground(colorArr[11]);
                       break;
                    case 8192:
                       butArr[i][j].setBackground(colorArr[12]);
                       break;
                    default:
                        break;
                    }
                    int highestScore = handler.getHighestScore();
                    int currentScore = handler.getCurrentScore();
                    if (highestScore < currentScore) {
                        handler.setHighestScore(currentScore);
                        tfHighestScore.setText(String.valueOf(currentScore));
                    }
                    tfScore.setText(String.valueOf(currentScore));
                    butArr[i][j].setText(String.valueOf(score));
                    butArr[i][j].setVisible(true);
                } else {
                    butArr[i][j].setVisible(false);
                }
            }
        }
    }
    
    class MyMonitor1 extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            
            switch(keyCode) {
            case KeyEvent.VK_UP:
                // if NOT death
                if (!handler.handlePressUp()) {
                    reDrawPanel();
                } else {
                    tfHighestScore.setText(String.valueOf(handler.getHighestScoreFromFile()));
                    JOptionPane.showInternalMessageDialog(frame.getContentPane(), "YOU ARE DEAD!");
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!handler.handlePressDown()) {
                    reDrawPanel();
                } else {
                    tfHighestScore.setText(String.valueOf(handler.getHighestScoreFromFile()));
                    JOptionPane.showInternalMessageDialog(frame.getContentPane(), "YOU ARE DEAD!");
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!handler.handlePressLeft()) {
                    reDrawPanel();
                } else {
                    tfHighestScore.setText(String.valueOf(handler.getHighestScoreFromFile()));
                    JOptionPane.showInternalMessageDialog(frame.getContentPane(), "YOU ARE DEAD!");
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!handler.handlePressRight()) {
                    reDrawPanel();
                } else {
                    tfHighestScore.setText(String.valueOf(handler.getHighestScoreFromFile()));
                    JOptionPane.showInternalMessageDialog(frame.getContentPane(), "YOU ARE DEAD!");
                }
                break;
            default:
                break;
            }
        }
    }
    
    class MyMonitor2 extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showInternalConfirmDialog(frame.getContentPane(), 
                    "Do you want to save the game?", "Confirm", 
                    JOptionPane.YES_NO_CANCEL_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE); 

            switch (option) {
            case JOptionPane.YES_OPTION:
                handler.saveHighestScoreToFile();
                handler.saveGame();
                frame.dispose();
                System.exit(0);
                break;
            case JOptionPane.NO_OPTION:
                handler.saveHighestScoreToFile();
                frame.dispose();
                System.exit(0);
                break;
            case JOptionPane.CANCEL_OPTION:
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                break;
            default:
                break;
            }
        }
        
        public void windowOpened(WindowEvent e) {
            int option = JOptionPane.showInternalConfirmDialog(frame.getContentPane(), 
                    "Do you want to recover the game?", "Confirm", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE); 

            switch (option) {
            case JOptionPane.YES_OPTION:
                handler.recoverGame();
                reDrawPanel();
                break;
            case JOptionPane.NO_OPTION:
                break;
            default:
                break;
            }
        }
    }
}
