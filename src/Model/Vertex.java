package Model;

import Supports.Config;
import Supports.Vector2DHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by BTC on 11/23/15.
 */
public class Vertex{

   Color color;
   int radius;
   Point position;
   String name;

   Color[] colors = new Color[] {
         new Color(250, 150, 10),
         Color.cyan
   };
   int state;
   java.util.List<Edge> edges = new ArrayList<>();

   public void setLocation(Point position) {
      this.position = position;
   }

   public Point getLocation() {
      return this.position;
   }

   public Vertex(Point position) {

      setLocation(position);
      this.radius = Config.Instance().VERTICE_RADIUS;
      color = colors[state];
   }

   public boolean contains(int x, int y) {
      double distance = Vector2DHelper.DistanceBetweeen(new Point(x, y), this.getLocation());
      return distance < radius;
   }

   public boolean contains(Point p) {
      return contains(p.x, p.y);
   }

   public void draw(Graphics g) {
      g.setColor(this.color);
      g.fillOval(getLocation().x - radius, getLocation().y - radius, 2 * radius, 2 * radius);
      g.drawOval(getLocation().x - radius, getLocation().y - radius, 2 * radius, 2 * radius);

      g.setColor(Color.black);
      int textWidth = g.getFontMetrics().stringWidth(this.getName());
      g.drawString(getName(), this.getLocation().x - textWidth / 2, getLocation().y + textWidth / 2);
   }

   public void addEdge(Edge edge) {
      this.edges.add(edge);
   }

   public boolean hasConnectTo(Vertex vertex) {
      for (Edge edge: this.edges) {
         if (vertex == this) continue;
         if (edge.getVertex2() == vertex) {
            return true;
         }
      }
      return false;
   }

   public void resetState() {
      this.setState(VertexState.NONE);
      this.setColor(colors[this.getState()]);
   }
   // get set

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getState() {
      return state;
   }

   public void setState(int state) {
      if (this.state == state) return;
      this.state = state;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   @Override
   public String toString() {
      return this.getName();
   }
}
