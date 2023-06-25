import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie implements Sprite{
    BufferedImage tape;
    int x=500;
    int y=500;
    double scale=1;

    int index=0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 200; // z rysunku;
    Zombie(int x,int y, double scale, BufferedImage tape)
    {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape=tape;
    }
    Zombie(int x,int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
        try {
            this.tape = ImageIO.read(getClass().getResource("walkingdead.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param g
     * @param parent
     *
     */

    public void draw(Graphics g, JPanel parent){
        Image img = tape.getSubimage(200*index,0,WIDTH,HEIGHT); // pobierz klatkę
        g.drawImage(img,x,y-(int)(HEIGHT*scale)/2,(int)(WIDTH*scale),(int)(HEIGHT*scale),parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next(){
        x-=5*scale;
        index = (index+1)%10;
    }
public boolean isVisible()
{
    if(x<0-10-(int)(WIDTH*scale)){return false;}
    else return true;
}
   public boolean isHit(int _x,int _y)
    {
if(_x>=x && _y>=(y-HEIGHT*scale*0.5) && _x <=(int)(x+WIDTH*scale) && _y<=(int)(y+HEIGHT*scale)){return true;}
return false;
    }
    public boolean isCloser(Sprite other)
    {
        if(other instanceof Zombie z){
            if(z.scale>this.scale)
            {
                return false;
            }

        }
        return true;
    }
}