package Game;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by BTC on 11/25/15.
 */
public class Cat implements PathfinderDataSource{
   Maze maze;
   AbstractPathfinderEngine pathfinder;
   Point position;
   int width = Config.TILEWIDTH;
   int height = Config.TILEHEIGHT;
   Stack<Point> pathToMove;

   Image image;

   private void init() {
      pathfinder = new DFSPathfinder();
      pathfinder.dataSource = this;
      try {
         image = ImageIO.read(new File("images/cat.gif"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void moveTo(Point target) {
      pathToMove = pathfinder.doSearch(position, target);
   }

   public boolean popStep() {
      if (pathToMove == null || pathToMove.isEmpty()) {
         System.out.println("No move!");
         return false;
      }
      setPosition(pathToMove.pop());
      maze.setValueForTileAt(position, Tile.MOVED);
      return true;
   }

   public Cat(Maze maze, Point point) {
      this.position = point;
      this.maze = maze;
      init();
   }

   @Override
   public Point[] getPosibleMovesFromPosition(Point p) {
      Point[] results = new Point[4];
      int x = p.x;
      int y = p.y;
      int index = 0;
      Point top = new Point(x, y - 1);
      Point right = new Point(x + 1, y);
      Point bottom = new Point(x, y + 1);
      Point left = new Point(x - 1, y);
      if (maze.isMoveablePosition(top)) results[index++] = top;
      if (maze.isMoveablePosition(right)) results[index++] = right;
      if (maze.isMoveablePosition(bottom)) results[index++] = bottom;
      if (maze.isMoveablePosition(left)) results[index++] = left;

      return results;
   }

   public void draw(Graphics g) {
      g.drawImage(image, position.x * width, position.y * height, width, height, null);
   }

   public Point getPosition() {
      return position;
   }

   public void setPosition(Point position) {
      this.position = position;
   }
}
