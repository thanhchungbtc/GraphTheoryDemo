package SearchAlgorithm.Model;

import Supports.Config;
import Supports.Vector2DHelper;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by 130708 on 2015/11/26.
 */
public class Vertex implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4437043974878325387L;
public static final int NONE = 0;
   public static final int VISITED = 1;
   public static final int BACKTRACKING = 2;
   public static final int BACKTRACKING2 = 3;
   public static final int SHORTESTPATH = 4;
   transient final int radius = Config.VERTICE_RADIUS;
   Point position;
   int id;
   public int state;
   transient static final Color[] colors = new Color[] {Color.white, Color.cyan, Color.GRAY, Color.darkGray, Color.orange };

   public Vertex(Point position) {
      this.position = position;
   }

   private Color getColor() {
      return  colors[state];
   }

   public void resetState() {
      this.state = NONE;
   }

   public void draw(Graphics g) {
      g.setColor(this.getColor());

      g.fillOval(position.x - radius, position.y - radius, 2 * radius, 2 * radius);
      g.drawOval(position.x - radius, position.y - radius, 2 * radius, 2 * radius);

      g.setColor(Color.black);
      String text = getName();
      int textWidth = g.getFontMetrics().stringWidth(text);
      g.drawString(text, this.position.x - textWidth / 2, position.y + textWidth / 2);
   }

   public boolean contains(Point point) {
      double distance = Vector2DHelper.DistanceBetweeen(point, this.position);
      return distance < radius;
   }

   public Point getPosition() {
      return position;
   }

   public void setState(int state) {
      this.state = state;
   }

   public void setPosition(Point position) {
      this.position = position;
   }

   @Override
   public String toString() {
      return getName();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Vertex)) return false;
      Vertex other = (Vertex)obj;
      return this.id == other.id;
   }

   public String getName() {
      return String.valueOf(id + 1);
   }
}

