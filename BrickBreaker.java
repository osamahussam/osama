package brickbreaker;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.*;

public class BrickBreaker extends JFrame {
    
  
    static class ball extends JPanel implements ActionListener,KeyListener {
        int Score=0;
        double xs=580;//position of slider on x
        double ys=650;// position of slider on y
        double velxs=0;// velocity of slider on x
        double velys=0;//velocity of slider on y
        Bricks brks;
        javax.swing.Timer t = new javax.swing.Timer (10, this);
        
    private static double xp=640,yp=500, velx=-2,vely=+2;
    
    public ball(){
      brks = new Bricks();
     addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    }
    
    File HitSound = new File("hit.wav");
    File WinSound = new File("Win.wav");
    File BarHitSound = new File ("BarHit.wav");
    File LoseSound = new File ("Lose.wav");
  
    private static void PlaySound(File Sound){
            try{
            Clip Hit = AudioSystem.getClip();
            Hit.open(AudioSystem.getAudioInputStream(Sound));
            Hit.start();
            }
            catch(Exception ex){}
            }
    
    private static void StopSound(File Sound){
        try{
            Clip Hit = AudioSystem.getClip();
            Hit.open(AudioSystem.getAudioInputStream(Sound));
            Hit.stop();
            }
            catch(Exception ex){}
        }
    
    public void paintComponent(Graphics g){
    super.paintComponent(g);
 
    Graphics2D g3=((Graphics2D)g);
    g3.setColor(Color.black);
    g3.fill(new Rectangle2D.Double(1,1,1280,720));
    brks.Paintbricks((Graphics2D)g);
    Graphics2D G =(Graphics2D) g;
    Ellipse2D circle = new Ellipse2D.Double(xp,yp,20,20);
    G.setColor(Color.red);
    G.fill(circle);
    G.setColor(Color.CYAN);
    
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.CYAN);
    g2.fill(new Rectangle2D.Double(xs,ys,100,10));
    
    if(yp>650){
    g.setColor(Color.red);
    }
    
   if(Score==1220){
   g.setColor(Color.red);
    PlaySound(WinSound);
   }
   
    t.start();
    
    }
    
    public void actionPerformed (ActionEvent e){
    xs+=(6* velxs);
    //ys+=(6*velys);
    if( xp<0||xp>1240 ){
    velx = velx*-1;
        PlaySound(BarHitSound);
    }
    
    if(yp<0 ){
    
    vely= vely*-1;
    PlaySound(BarHitSound);
    }
    
    if(yp+20 == ys){
        if(xp+10>=xs&& xp<=xs+50){
            if(velx > 0)
                velx = velx *-1;
                vely = vely*-1;
                PlaySound(BarHitSound);
        }
        else if(xp>xs+50 && xp<xs+100){
            if(velx < 0)
                velx = velx * -1;
                vely = vely *-1;
                PlaySound(BarHitSound);
        }
    }
    
    else if(yp+20 > ys){
        if(xp == xs+20){
            if(velx > 0)
                velx *= -1;
            vely *= -1;
            PlaySound(BarHitSound);
        }
        if(xp == xs+100){
            if(velx < 0)
                velx *= -1;
            vely *= -1;
            PlaySound(BarHitSound);
        }
    }
    
   if(yp>650){
       velx=-2;
       vely=-2;
       xs=580;
       ys=650;
       xp=640;
       yp=500;
       Score=0;
       PlaySound(LoseSound);
       brks= new Bricks();
       repaint();
    JFrame x = new JFrame();
    JOptionPane.showMessageDialog(x, "              Game Over");
    int response;			
    response = JOptionPane.showConfirmDialog(x,"Do You Want To Play Again ?","Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) 
    			System.exit(0);
    			
       
   }
   
   for(int i=0;i<11;i++){
   for(int j=0;j<12;j++){
   if(brks.bricks[i][j]==true){
   
       int x= j*brks.brickwidth+40;
       int y = i*brks.brickheight+30;
       int w= 97;
       int h=40;
       Rectangle R = new Rectangle(x,y,w,h);
       Rectangle br = new Rectangle((int)xp,(int)yp,20,20);
       Rectangle temp = R;
       if(br.intersects(temp)){
       brks.brickhit(false,i,j);
       Score+=10;
       if((xp+20<temp.x)||(xp>temp.x+97)){
       velx= velx*-1;
       PlaySound(HitSound);
       StopSound(HitSound);
       }
       
       else{
       vely= vely*-1;
       PlaySound(HitSound);
       StopSound(HitSound);
       }
       }
        }
     }
   
   }
   
    xp=xp+velx;
    yp=yp+vely;
    repaint();
    }
    
    public void up(){
        velxs=0;
    }
    
    public void down(){
    }
    
    public void right(){
        if(xs==1180){
        xs=1180;
        }
        else{
    velxs =2;
    }}
    public void left(){
        if(xs==0)
        {
        xs=0;
        
        }
        else{
    velxs = -2;
    }}
    
    public void keyPressed(KeyEvent e ){
    int key = e.getKeyCode();
    if(key==KeyEvent.VK_RIGHT){
        if(xs>1180){
        up();
        }else{
        right();
        }
    }
    if(key==KeyEvent.VK_LEFT){
        if(xs<0){
      up();  
        
        }else{
    left();
    
    }}
    if(key==KeyEvent.VK_UP){
    up();
    
    }
   
    }
    
    public void keyTyped (KeyEvent e){
      
    }
    
    public void keyReleased (KeyEvent e ){
        velxs=0;
    }
    
    }
    
    public static void main(String[] args) {
        ball b = new ball();
       JFrame x = new JFrame("Bouncing Ball");
       JOptionPane.showMessageDialog(x, "How To Play:  Move the bar using the left & right arrows to hit the bricks using the ball");
       x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       x.setBounds(1, 1, 1280, 720);
       x.add(b);
       x.setVisible(true);
    }
    
    
}
