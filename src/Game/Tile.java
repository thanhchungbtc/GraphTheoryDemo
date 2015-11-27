package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by BTC on 11/25/15.
 */
public class Tile {

   public final static int FLOOR = 1;
   public final static int OBSTACLE = 2;
   public final static int MOVED = 3;
   Point coord;
   int value;
   Image image;
   int width = Config.TILEWIDTH;
   int height = Config.TILEHEIGHT;

   public Tile(Point position) {
      this.coord = position;
      this.setValue(FLOOR);
   }

   public void draw(Graphics g) {
      g.drawImage(image, coord.x * width, coord.y * height, width, height, null);
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
      if (value == FLOOR) {
         try {
            image = ImageIO.read(new File("images/floor.jpg"));
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else if (value == OBSTACLE){
         try {
            image = ImageIO.read(new File("images/wall.png"));
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else if (value == MOVED){
         try {
            image = ImageIO.read(new File("images/grass.png"));
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
