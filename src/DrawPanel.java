import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class DrawPanel  extends JPanel implements CrossHairListener{
    BufferedImage background;


    ArrayList<Sprite> sprites= new ArrayList<Sprite>();
    AnimationThread thread=new AnimationThread();
    SpriteFactory factory;
    CrossHair celownik=new CrossHair(this);
    DrawPanel(URL backgroundImagageURL,SpriteFactory factory) {
        try {
            background = ImageIO.read(backgroundImagageURL);


            thread.start();
        } catch (IOException e) {
            System.out.print("wyjatek");
            e.printStackTrace();
            return;
        }
        this.factory=factory;
        this.addMouseMotionListener(celownik);
        this.addMouseListener(celownik);
        celownik.addCrossHairListener(this);

    }

    public class ZombieDistanceComparator implements Comparator<Sprite> {
        @Override
        public int compare(Sprite z1, Sprite z2) {
            if (z1.isCloser(z2)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);


        sprites.sort(new ZombieDistanceComparator());
        for(int i=0;  i<sprites.size();i++)
        {
            sprites.get(i).draw(g,this);
        }
        celownik.draw(g);

        //g.drawImage(z1.tape, z1.x, z1.y,z1.WIDTH,z1.HEIGHT,this);
    }

    @Override
    public void onShotsFired(int x, int y) {

        for(int i=sprites.size()-1;  i>=0;i--)
        {
            if(sprites.get(i).isHit(x,y))
            {
                sprites.remove(i);

                break;
            }
        }
    }



    class AnimationThread extends Thread{
        private boolean running= true;
        public void stopThread() {
            running = false;
        }
        public void run(){

            for(int z=1;running;z++)  {
                if(z%30==0) {

                    sprites.add(factory.newSprite(getWidth(),(int)(0.7*getHeight())));//tutaj dodac
                }

                for(int i=0;  i<sprites.size();i++)
                    {

                        sprites.get(i).next();
                        if(!sprites.get(i).isVisble())
                        {
                            sprites.remove(i);
                        }


                    }

                    repaint();
                    try {
                        sleep(1000 / 30);  // 30 klatek na sekundę
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }

                }

        }
    }
}