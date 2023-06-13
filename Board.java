import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;


public class Board extends JPanel implements ActionListener{
    // board class behaves as a action listner
    int height = 400;
    int width = 400;

    int Max_Dots = 1600;
    int Dot_Size = 10;
    int Dots;

    int x[] = new int[Max_Dots];
    int y[]= new int[Max_Dots];

    int apple_x;
    int apple_y;

    Image body, head, apple;
    Timer timer;
    
    int DELAY = 300;// delaay define the speed of snake

    boolean leftDir = true;
    boolean rightDir = false;
    boolean upDir = false;
    boolean downDir = false;

    boolean inGame = true;

    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        initgame();
        loadImages();
    }

    // initialize game
    public void initgame(){
        // inside this we initialize the co-ordinates of snake we need 2 array that store x and y direc.
        Dots =3;
        // initilize the snakes position
        x[0]=250;
        y[0]=250;

        for(int i=1; i<Dots; i++){
            x[i] = x[0]+(Dot_Size*i);
            y[i] = y[0];
        }
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start(); 
    }
    //load image from folder 
    public void loadImages(){

        ImageIcon bodyIcon = new ImageIcon("images/dot.png");
        body  = bodyIcon.getImage();// get image and this will drop at a particular pos

        ImageIcon headIcon = new ImageIcon("images/head.png");
          head = headIcon.getImage();

         ImageIcon appleIcon = new ImageIcon("images/apple.png");
        apple  = appleIcon.getImage();

    }

    //draw images at snake and apple position

    @Override
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            doDrawaing(g);
    }

    // draw img at given pos
    public void doDrawaing(Graphics g){
       if(inGame){
         g.drawImage(apple, apple_x, apple_y, this);
        for(int i=0; i<Dots; i++){
            if(i==0){
                g.drawImage(head, x[0], y[0], this);
            }else{
                g.drawImage(body, x[i], y[i], this);
            }
        }
       }else{
        gameOver(g);
        timer.stop();
       }
    }

    // randomize apples location

    public void locateApple(){
        apple_x = ((int)(Math.random()*39)) *Dot_Size;
        apple_y = ((int)(Math.random()*39)) *Dot_Size;
    }

    // checkk collision with border and body
    public void checkCollision(){
        // body
        for(int i=1; i<Dots ; i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){// x[0] and y[0] is head of  snake
                inGame =false;
            }
        }
        // border
        if(x[0]<0 || x[0]>=width || y[0]<0 || y[0]>=height ){
            inGame = false;
        }
    }
    // display game over msg
    public void gameOver(Graphics g){ // we need to draw a message so we use graphics
        String msg = "Game Over";
        int score = (Dots-3)*100;
        String scoremsg = "Score:"+Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
         g.drawString(msg, (width -fontMetrics.stringWidth(msg))/2, height /4); 
        g.drawString(scoremsg, (width -fontMetrics.stringWidth(scoremsg))/2, 3*(height /4));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
       if(inGame){
        checkApple();
        checkCollision();
        move();
       }
        repaint();
    }

    // snake move
    public void move(){
        for(int i = Dots;i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDir){
            x[0]-=Dot_Size;
        } 
        if(rightDir){
            x[0]+=Dot_Size;
        }
        if(upDir){
            y[0]-=Dot_Size;
        }
        if(downDir){
            y[0]+=Dot_Size;
        }
    }
    //make snake to eat apple 
    public void checkApple(){
        if(apple_x==x[0] && apple_y ==y[0]){
            Dots++;
            locateApple();
        }
    }

    //implement controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key==keyEvent.VK_LEFT && !rightDir){
                leftDir = true;
                upDir = false;
                downDir = false;
            }
            if(key==keyEvent.VK_RIGHT && !leftDir){
                rightDir = true;
                upDir = false;
                downDir = false;
            }
            if(key==keyEvent.VK_UP && !downDir){
                upDir = true;
                leftDir = false;
                rightDir = false;
            }
            if(key==keyEvent.VK_DOWN && !upDir){
                downDir = true;
                leftDir = false;
               rightDir = false;
            }
        }
    }
}
