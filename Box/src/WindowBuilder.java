import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;

public class WindowBuilder extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    static WindowBuilder frame;
    ImageIcon faces[];
    int[] dice;
    int numOfDice;
    
    private final int BUTTON_WIDTH = 50;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_X = 140;
    private final int BUTTON_Y = 50; 
    private JPanel contentPane;
    private JButton[] buttons;
    private JButton btnRollTwo;
    private JButton btnNewGame;
    private JLabel lblUpdate;
    private JLabel lblDice;
    private JLabel lblDice_1;
    
    private Board board;
    private JButton btnRollOne;
    private JLabel lblRollDice;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new WindowBuilder();
                    frame.setVisible(true);
                    frame.setResizable(true);
                    frame.setDefaultCloseOperation(WindowBuilder.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public WindowBuilder() {
        faces = new ImageIcon[7];
        for(int i = 0; i <= 6; i++) {
            faces[i] = new ImageIcon(this.getClass().getResource(i + ".jpg"));
        }
        dice = new int[] {-1, -1};
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWhatsInThe = new JLabel("Shut the Box!");
        lblWhatsInThe.setHorizontalAlignment(SwingConstants.CENTER);
        lblWhatsInThe.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblWhatsInThe.setBounds(129, 11, 177, 30);
        contentPane.add(lblWhatsInThe);
        
        lblDice = new JLabel("dice1");
        lblDice.setIcon(faces[0]);
        lblDice.setBounds(17, 108, 50, 50);
        contentPane.add(lblDice);
        
        lblDice_1 = new JLabel("dice2");
        lblDice_1.setIcon(faces[0]);
        lblDice_1.setBounds(77, 108, 50, 50);
        contentPane.add(lblDice_1);
        
        lblUpdate = new JLabel("");
        lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        lblUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblUpdate.setBounds(49, 209, 337, 52);
        contentPane.add(lblUpdate);

        lblRollDice = new JLabel("Roll Dice!");
        lblRollDice.setHorizontalAlignment(SwingConstants.CENTER);
        lblRollDice.setBounds(31, 47, 84, 14);
        contentPane.add(lblRollDice);
        
        buttons = new JButton[9];
        int x = 0;
        int y = 0;
        for(int i = 0; i <= 8; i++) {
            if(i % 3 == 0 && i != 0) {
                x = 0;
                y += BUTTON_HEIGHT;
            }
            buttons[i] = new JButton("" + (i + 1));
            buttons[i].setBounds(BUTTON_X + ((BUTTON_WIDTH + 1) * x), (BUTTON_Y + y), BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons[i].addActionListener(this);
            contentPane.add(buttons[i]);
            x++;
        }
        
        btnRollOne = new JButton("One");
        btnRollOne.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRollOne.setBounds(10, 67, 60, 30);
        btnRollOne.addActionListener(this);
        btnRollOne.setEnabled(false);
        contentPane.add(btnRollOne);
        
        btnRollTwo = new JButton("Two");
        btnRollTwo.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRollTwo.setBounds(73, 67, 60, 30);
        btnRollTwo.addActionListener(this);
        contentPane.add(btnRollTwo);
        
        btnNewGame = new JButton("New Game");
        btnNewGame.setBounds(312, 89, 112, 50);
        btnNewGame.addActionListener(this);
        contentPane.add(btnNewGame);
        
        board = new Board();
    }
    
    /**
     * Performs specific action(s) based on the event that occurs
     * 
     * @param e event that occurred
     */
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < buttons.length; i++) {
            if(e.getSource() == buttons[i]) {
                int hitResult = board.hit(i);
                buttons[i].setBackground(board.getSquares()[i].getColor());
                if(board.getDiceTotal() == 0 && board.checkScore() == 0) {
                    boardWin(0);
                }
                else {
                    lblUpdate.setText(boardResponse(hitResult));
                    if(hitResult == 2 && board.canUseOneDice()) {
                        btnRollOne.setEnabled(true);
                    }
                }               
            }
        }
        if(e.getSource() == btnRollTwo) {
            numOfDice = 2;
            dice = board.rollDice(numOfDice);
            System.out.println("dice1 = " + dice[0] + ", dice2 = " + dice[1]);
            if(dice[0] >= 0) {
                lblDice.setIcon(faces[dice[0]]);
                lblDice_1.setIcon(faces[dice[1]]);
                if(!board.checkLose()) {
                    lblUpdate.setText(board.getDiceTotal() + "! Pick some numbers.");
                } else {
                    boardWin(board.checkScore());
                }
            } else {
                lblUpdate.setText("You need to pick some numbers first!");
            }
        }
        if(e.getSource() == btnRollOne) {
            numOfDice = 1;
            dice = board.rollDice(numOfDice);
            System.out.println("dice1 = " + dice[0] + ", dice2 = " + dice[1]);
            if(dice[0] >= 0) {
                lblDice.setIcon(faces[dice[0]]);
                lblDice_1.setIcon(faces[dice[1]]);
                if(!board.checkLose()) {
                    lblUpdate.setText(board.getDiceTotal() + "! Pick some numbers.");
                } else {
                    boardWin(board.checkScore());
                }
            } else {
                lblUpdate.setText("You need to pick some numbers first!");
            }
        }
        if(e.getSource() == btnNewGame) {
            resetBoard();
        }
    }
    
    public String boardResponse(int num) {
        if(num == 0) {
            return "Number is already chosen. Choose another.";
        } else if(num == 1) {
            return "Wow, good choice. Pick another!";
        } else if(num == 2) {
            return "Good job. Roll again!";
        } else if(num == 3) {
            return "Roll new dice first";
        } else if(num == 4) {
            return "<html><center>That won't add up... Choose again</center></html>";
        }
        return "Error";
    }
    
    public void boardWin(int score) {
        if(score == 0) {
            lblUpdate.setText("Winner Winner Chicken Dinner! Score = 0!");
        } else {
            lblUpdate.setText("Game over! Score = " + score);
        }
        btnRollOne.setEnabled(false);
        btnRollTwo.setEnabled(false);
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
    }
    
    public void resetBoard() {
        board.resetDiceTotal();
        lblUpdate.setText("");
        btnRollOne.setEnabled(false);
        btnRollTwo.setEnabled(true);
        lblDice.setIcon(faces[0]);
        lblDice_1.setIcon(faces[0]);
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
            board.getSquares()[i].setSquareHit(false);
            board.getSquares()[i].setBackground(null);
            buttons[i].setBackground(board.getSquares()[i].getColor());
        }
        
    }
}
