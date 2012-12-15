import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Action;

public class Mouse1 extends Applet
   implements MouseListener, MouseMotionListener, ActionListener {

   // Applet variables 
   int width, height;
   int mx, my;  // the mouse coordinates
   boolean isButtonPressed = false;

   Image backbuffer;
   Graphics backg;

   Button start; 

   // TSP variables
   TSP tour;
   ArrayList<Point> points = new ArrayList<Point>();

   public void init() {
      width = getSize().width;
      height = getSize().height;

      backbuffer = createImage( width, height );
      backg = backbuffer.getGraphics();
      backg.setColor( Color.black );
      backg.fillRect( 0, 0, width, height );
      backg.setColor( Color.white );

      start = new Button("Start");
      this.add(start);

      start.addActionListener(this);

      points = new ArrayList<Point>();

      addMouseListener( this );
      addMouseMotionListener( this );
   }

   public void mouseEntered( MouseEvent e ) {
      // called when the pointer enters the applet's rectangular area
   }
   public void mouseExited( MouseEvent e ) {
      // called when the pointer leaves the applet's rectangular area
   }
   public void mouseClicked( MouseEvent e ) {
      mx = e.getX();
      my = e.getY();
      backg.drawOval(mx, my, 8, 8);
      repaint();
      e.consume();
      Point p = new Point(mx, my);
   }

   public void mousePressed( MouseEvent e ) {  // called after a button is pressed down
      e.consume();
   }

   public void mouseReleased( MouseEvent e ) {  // called after a button is released
      e.consume();
   }

   public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
      e.consume();
   }

   public void mouseDragged( MouseEvent e ) {
      e.consume();
   }

   public void setPenColor(){
      Graphics g;
   }

   public void update( Graphics g ) {
      g.drawImage( backbuffer, 0, 0, this );
   }

   public void paint( Graphics g ) {
      update(g);
   }

   public void actionPerformed(ActionEvent a){

      Point pts[] = new Point[points.size()];
      points.toArray(pts);
      tour = new TSP(pts);
   }
}