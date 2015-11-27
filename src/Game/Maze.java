package Game;

import java.awt.*;

/**
 * Created by BTC on 11/25/15.
 */
public class Maze {
   Tile[][] tiles;
   int tileWidth = Config.TILEWIDTH;
   int tileHeight = Config.TILEHEIGHT;
   int mapWidth;
   int mapHeight;

   public Maze(int mapWidth, int mapHeight) {
      this.mapWidth = mapWidth;
      this.mapHeight = mapHeight;
      init();
      generateRandomMap();
   }

   public void draw(Graphics g) {
      for (int r = 0 ; r < mapWidth; r++) {
         for (int c = 0; c < mapWidth; c++) {
            tiles[r][c].draw(g);
         }
      }
   }

   public Point coordAtPosition(Point p) {
      Point point = new Point(p.x / tileWidth, p.y / tileHeight);
      if (isValidPosition(point))return point;
      return null;
   }

   public boolean isMoveablePosition(Point p) {
      return isValidPosition(p) && tiles[p.y][p.x].getValue() == 1;
   }

   public boolean isValidPosition(Point point) {
      return !(point.x < 0 || point.x >= mapWidth || point.y < 0 || point.y >= mapHeight);
   }

   public int getWidthInPixel() {
      return tileWidth * mapWidth;
   }

   public int getHeightInPixel() {
      return tileHeight * mapHeight;
   }

   public void setValueForTileAt(Point point, int value) {
      tiles[point.y][point.x].setValue(value);
   }

   private void init() {
      tiles = new Tile[mapWidth][mapHeight];
      for (int i = 0; i < mapHeight; i++) {
         for (int j = 0; j < mapWidth; j++) {
            tiles[i][j] = new Tile(new Point(j, i));
         }
      }
   }

   private void generateRandomMap() {
      int obstacleCount = (int)(mapWidth * mapHeight * 0.20);
      for (int i = 0; i < obstacleCount; i++) {
         int row = (int)(Math.random() * mapHeight);
         int col = (int)(Math.random() * mapWidth);
         tiles[row][col].setValue(Tile.OBSTACLE);
      }
   }
}
