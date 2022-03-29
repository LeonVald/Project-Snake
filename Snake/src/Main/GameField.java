package Main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{

    private static final int MY_CONST = 10;
    private final int SIZE = 650;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 100;
    private Image dot;
    private Image apple;
    private int apple_X;
    private int apple_Y;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int A_DOT;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean startGame = true;
    private boolean inGame = false;
    private int point;
    private boolean pause = false;

    public GameField(){
        setBackground(Color.black);
        Load_Texture();
        UnitGames();
        addKeyListener(new FieldKey());
        setFocusable(true);
    }

    public void UnitGames(){
        A_DOT = 3;
        for (int i = 0; i < A_DOT; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(200,this);
        timer.start();
        createApple();
        score();
    }



    public void score() {
        point = 1;
    }

    public void createApple() {
        apple_X = new Random().nextInt(35)*DOT_SIZE;
        apple_Y = new Random().nextInt(35)*DOT_SIZE;
    }


    public void Load_Texture(){
        ImageIcon picture_a = new ImageIcon("resources\\apple.png");
        apple = picture_a.getImage();
        ImageIcon picture_d = new ImageIcon("resources\\dot.png");
        dot = picture_d.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startGame) {
            String str = "Start game";
            g.setColor(Color.GREEN);
            g.drawString(str, 285, 235);
            String key = "press Enter";
            g.setColor(Color.WHITE);
            g.drawString(key, 285, 255);
            String pause = "'Esc' to pause; 'Space' to continue ";
            g.setColor(Color.WHITE);
            g.drawString(pause, 230, 275);

            if (inGame) {
                g.setColor(Color.BLACK);
                g.fillRect(230,210,285,260);
                g.drawImage(apple, apple_X, apple_Y, this);
                for (int i = 0; i < A_DOT; i++) {
                    g.drawImage(dot, x[i], y[i], this);
                }

            } else {
                if (point == 1) {
                    timer.stop();
                }
                else{
                    String gameOver = "GAME OVER ";
                    g.drawString(gameOver, 285, 315);
                    String score = "SCORE : ";
                    g.setColor(Color.CYAN);
                    g.drawString( score + (point - 1), 285, 335);
                    timer.stop();
                }
            }
        }
    }

    public void move(){
        for (int i = A_DOT; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if (left){
            x[0] -= DOT_SIZE;
        }

        if(right){
            x[0] += DOT_SIZE;
        }

        if(up) {
            y[0] -= DOT_SIZE;
        }

        if(down) {
            y[0] += DOT_SIZE;
        }
    }

    public  void AppleCheck(){
        if(x[0] == apple_X && y[0] == apple_Y){
            A_DOT++;
            point += MY_CONST;
            createApple();
        }
    }

    public void CollisionsCheck(){
        for (int i = A_DOT; i > 0; i--){
            if (i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if (x[0] > SIZE){
            inGame = false;
        }

        if (x[0] < 0){
            inGame = false;
        }

        if (y[0] > SIZE){
            inGame = false;
        }

        if (y[0] < 0){
            inGame = false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(inGame){
            AppleCheck();
            CollisionsCheck();
            move();

        }
        repaint();
    }

    class FieldKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && ! right){
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && ! left){
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && ! down){
                up = true;
                left = false;
                right = false;
            }

            if ( key == KeyEvent.VK_DOWN && ! up){
                down = true;
                left= false;
                right = false;
            }

            if ( key == KeyEvent.VK_ESCAPE){
                timer.stop();
                pause = true;
            }

            if ( key == KeyEvent.VK_SPACE){
                timer.start();
                pause = false;
            }

            if ( key == KeyEvent.VK_ENTER && ! inGame){
                timer.stop();
                inGame = true;
                point = 1;
                UnitGames();
                left = false;
                right = true;
                up = false;
                down = false;
            }
        }
    }
}
