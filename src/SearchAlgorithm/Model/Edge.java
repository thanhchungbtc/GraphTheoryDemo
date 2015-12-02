package SearchAlgorithm.Model;

import Supports.Config;
import Supports.Vector2DHelper;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Created by 130708 on 2015/11/26.
 */
public class Edge implements Serializable {

   /**
	 * 
	 */
	private static final long serialVersionUID = -2967006570360712389L;
Vertex vertex1;
   Vertex vertex2;
   int cost;
   public boolean isShortesPath = false;
   private double getLength() {
      return Vector2DHelper.DistanceBetweeen(vertex1.position, vertex2.position);
   }

   private Rectangle getCollisionBoundary() {
      int diff = (int)getLength() / 3;
      Point centerPoint = getCenterPoint();
      int x = centerPoint.x - diff / 2;
      int y = centerPoint.y - diff / 2;
      return new Rectangle(x, y, diff, diff);
   }
   public void resetState() {
      this.isShortesPath = false;
   }
   public Edge(Vertex vertex1, Vertex vertex2) {
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
      this.cost = 1;
   }

   private Point getCenterPoint() {
      return Vector2DHelper.MutilByScalar(Vector2DHelper.AddVector(vertex1.position, vertex2.position), 0.5);
   }

   public boolean contains(Point p) {
      return getCollisionBoundary().contains(p);
   }

   public void draw(Graphics g, boolean isDirected) {
      if (isShortesPath) {
         g.setColor(Color.magenta);
      } else {
         g.setColor(Color.black);
      }
//      g.drawLine(vertex1.position.x, vertex1.position.y, vertex2.position.x, vertex2.position.y);

      if (isDirected)
         drawArrow(g, vertex1.position.x, vertex1.position.y, vertex2.position.x, vertex2.position.y);
      else {
         g.drawLine(vertex1.position.x, vertex1.position.y, vertex2.position.x, vertex2.position.y);
      }
      String text = "(" + String.valueOf(this.cost) + ")";
      Point centerPoint = getCenterPoint();
      // draw cost
      int textWidth = g.getFontMetrics().stringWidth(text);
      g.setColor(Config.BACKGROUND_COLOR);
      g.fillRect(centerPoint.x - textWidth / 2, centerPoint.y - textWidth / 2, textWidth, textWidth + 5);
      g.setColor(Color.black);
      g.drawString(text, centerPoint.x - textWidth / 2, centerPoint.y + textWidth / 2);


   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   @Override
   public String toString() {
      return "(" + vertex1 + ", " + vertex2 + ", cost " + cost + ")";
   }

   private final int ARR_SIZE = 4;

   void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
      Graphics2D g = (Graphics2D) g1.create();

      double dx = x2 - x1, dy = y2 - y1;
      double angle = Math.atan2(dy, dx);
      int len = (int) Math.sqrt(dx*dx + dy*dy);
      AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
      at.concatenate(AffineTransform.getRotateInstance(angle));
      g.transform(at);

      // Draw horizontal arrow starting in (0, 0)
      g.drawLine(0, 0, len, 0);
      len -= Config.VERTICE_RADIUS;
      g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
            new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
   }

   public void paintComponent(Graphics g) {
      for (int x = 15; x < 200; x += 16)
         drawArrow(g, x, x, x, 150);
      drawArrow(g, 30, 300, 300, 190);
   }
}
