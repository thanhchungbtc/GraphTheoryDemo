package SearchAlgorithm;

import Supports.Vector2DHelper;

import java.awt.*;

/**
 * Created by 130708 on 2015/11/26.
 */
public class Edge {
   Color color;

   Vertex vertex1;
   Vertex vertex2;
   int cost;

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

   public void draw(Graphics g) {
      g.setColor(this.color);
      g.drawLine(vertex1.position.x, vertex1.position.y, vertex2.position.x, vertex2.position.y);

      g.setColor(Color.black);
      String text = String.valueOf(this.cost);
      Point centerPoint = getCenterPoint();

      // draw cost
      if (cost == 1) return;
      int textWidth = g.getFontMetrics().stringWidth(text);
      g.setColor(Color.yellow);
      g.fillRect(centerPoint.x - textWidth / 2, centerPoint.y - textWidth / 2, textWidth, textWidth);
      g.setColor(Color.black);
      g.drawString(text, centerPoint.x - textWidth / 2, centerPoint.y + textWidth / 2);
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   @Override
   public String toString() {
      return "(" + vertex1 + ", " + vertex2 + ")";
   }
}
