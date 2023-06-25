import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public enum ZombieFactory implements SpriteFactory  {



    INSTANCE;
    private BufferedImage tape;

    private ZombieFactory() {
        try {
            tape= ImageIO.read(getClass().getResource("walkingdead.png"));
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
    public Sprite newSprite(int x,int y){
        double scale = Math.random() * 1.8 + 0.2;// wylosuj liczbę z zakresu 0.2 do 2.0

                Zombie z = new Zombie(x,y,scale,tape);
        return z;
    }
}
