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
        if(running){
            for(int i=0;i<HEIGHT/UNIT;i++){
                g.drawLine(i*UNIT, 0, i*UNIT, HEIGHT);
                g.drawLine(0, i*UNIT, WIDTH, i*UNIT);
            }
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, UNIT, UNIT);

            for(int i = 0; i<bodyParts; i++){
                if(i == 0 ){
                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT, UNIT);
                }
                else{
                    g.setColor(Color.orange);
                    g.fillRect(x[i], y[i], UNIT, UNIT);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
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
                break;
            case 'D':
                y[0] = y[0] + UNIT;
                break;
            case 'L':
                x[0] = x[0] - UNIT;
                break;
            case 'R':
                x[0] = x[0] + UNIT;
                break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts += 1;
            applesEaten += 1;
            newApple();
        }
    }
    public void checkCollision(){
        // Check for head-body collision
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        // Check for left border colision
        if(x[0] < 0) {
            running = false;
        }
        // Check for right border colision
        if(x[0] > WIDTH) {
            running = false;
        }
        // Check for top border colision
        if(y[0] < 0) {
            running = false;
        }
        // Check for bottom border colision
        if(y[0] > HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over"))/2, HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
    public class MyKeyAdapter extends KeyAdapter{
         @Override
         public void keyPressed(KeyEvent e ){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;

            }
         }
    }
}
