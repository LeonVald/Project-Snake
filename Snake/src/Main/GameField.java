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
    private final int ALL_DOTS = 14;
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
    private boolean inGame = true;
    private int point;

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
        point = 0;
    }

    public void createApple() {
        apple_X = new Random().nextInt(19)*DOT_SIZE;
        apple_Y = new Random().nextInt(19)*DOT_SIZE;
    }


    public void Load_Texture(){
        ImageIcon picture_a = new ImageIcon("C:\\Users\\sofiy\\Desktop\\ALL\\Lesson\\Project\\Snake\\apple.png");
        apple = picture_a.getImage();
        ImageIcon picture_d = new ImageIcon("C:\\Users\\sofiy\\Desktop\\ALL\\Lesson\\Project\\Snake\\dot.png");
        dot = picture_d.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple,apple_X,apple_Y,this);
            for (int i = 0; i < A_DOT; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            g.setColor(Color.CYAN);
            g.drawString(str,SIZE/2-20,SIZE/2-20);
            String score = "Score ";
            g.setColor(Color.WHITE);
            g.drawString(score + point,SIZE/2-12,SIZE/2);
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
        }
    }
}
