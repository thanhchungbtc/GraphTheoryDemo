package Model;

import Supports.Config;
import Supports.Vector2DHelper;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

/**
 * Created by BTC on 11/23/15.
 */
public class Edge {
   Color color;

   Vertex vertex1;
   Vertex vertex2;

   int cost;

   public Edge(Vertex vertex1, Vertex vertex2) {
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
   }

   public boolean contains(int x, int y) {

      return false;
   }

   public boolean contains(Point p) {
      return getCollisionBoundary().contains(p);
   }

   public void draw(Graphics g) {
      g.setColor(this.color);
      g.drawLine(vertex1.getLocation().x, vertex1.getLocation().y, vertex2.getLocation().x, vertex2.getLocation().y);

      g.setColor(Color.black);
      String text = String.valueOf(this.getCost());
      Point centerPoint = getCenterPoint();

      // draw cost
      if (cost == 0) return;
      int textWidth = g.getFontMetrics().stringWidth(text);
      g.setColor(Color.yellow);
      g.fillRect(centerPoint.x - textWidth / 2, centerPoint.y - textWidth / 2, textWidth, textWidth);
      g.setColor(Color.black);
      g.drawString(text, centerPoint.x - textWidth / 2, centerPoint.y + textWidth / 2);
   }

   private Point getCenterPoint() {
      return Vector2DHelper.MutilByScalar(Vector2DHelper.AddVector(vertex1.getLocation(), vertex2.getLocation()), 0.5);
   }

   private double getLenght() {
      return Vector2DHelper.DistanceBetweeen(vertex1.getLocation(), vertex2.getLocation());
   }

   private Rectangle getCollisionBoundary() {
      int diff = (int)getLenght() / 3;
      Point centerPoint = getCenterPoint();
      int x = centerPoint.x - diff / 2;
      int y = centerPoint.y - diff / 2;
      return new Rectangle(x, y, diff, diff);
   }

   public int getCost() {
      return cost;
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   public Vertex getVertex1() {
      return vertex1;
   }

   public Vertex getVertex2() {
      return vertex2;
   }

   @Override
   public String toString() {
      return "e{" +
            ", " + vertex1 +
            ", " + vertex2 +
            ", cost=" + cost +
            '}';
   }
}
