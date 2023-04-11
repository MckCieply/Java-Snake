import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT = 25;
    static final int AVALIBLE_UNITS = (WIDTH * HEIGHT)/UNIT;
    static final int DELAY = 75;
    final int[] x = new int[AVALIBLE_UNITS];
    final int[] y = new int[AVALIBLE_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start(); 
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        
        for(int i=0;i<HEIGHT/UNIT;i++){
            g.drawLine(i*UNIT, 0, i*UNIT, HEIGHT);
            g.drawLine(0, i*UNIT, WIDTH, i*UNIT);
        }
        g.setColor(Color.green);
        g.fillOval(appleX, appleY, UNIT, UNIT);
    }
    public void newApple(){
        appleX = random.nextInt((int)(WIDTH/UNIT)) * UNIT;
        appleY = random.nextInt((int)(HEIGHT/UNIT)) * UNIT;
    }
    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT;
            case 'D':
                y[0] = y[0] + UNIT;
            case 'L':
                x[0] = x[0] - UNIT;
            case 'R':
                x[0] = x[0] + UNIT;
        }
    }
    public void checkApple(){

    }
    public void checkCollision(){

    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
    public class MyKeyAdapter extends KeyAdapter{
         @Override
         public void keyPressed(KeyEvent e ){
             
         }
    }
}
