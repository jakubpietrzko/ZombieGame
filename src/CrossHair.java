import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;


public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;

    CrossHair(DrawPanel parent){
        this.parent = parent;
    }

    /* x, y to współrzedne celownika
       activated - flaga jest ustawiana po oddaniu strzału (naciśnięciu przyciku myszy)
    */
    int x;
    int y;
    boolean activated = false;
    ArrayList<CrossHairListener> listeners = new ArrayList<CrossHairListener>();
    void draw(Graphics g){
        if(activated){g.setColor(Color.RED);}
        else{ g.setColor(Color.WHITE);}

        int halfSize = 10 / 2; // połowa rozmiaru krzyżyka
        g.drawLine(x - halfSize, y, x + halfSize, y); // linia pozioma
        g.drawLine(x, y - halfSize, x, y + halfSize); // linia pionowa
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        parent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        activated=true;
        notifyListeners();
        parent.repaint();
        Timer timer = new Timer("Timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated=false;
                parent.repaint();
                timer.cancel();
            }
        },300);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    void addCrossHairListener(CrossHairListener e){
        listeners.add(e);
    }
    void notifyListeners(){
        for(var e:listeners)
            e.onShotsFired(x,y);
    }
}
