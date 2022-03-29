package Main;

import javax.swing.*;

public class Screen extends JFrame {
    public Screen(){
        setTitle("A Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(650,650);
        setLocation(450,150);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Screen screen = new Screen();

    }
}
