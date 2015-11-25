package Supports;

import java.awt.*;

/**
 * Created by BTC on 11/24/15.
 */
public class Line {
   Point point1;
   Point point2;

   public void draw(Graphics g) {
      g.drawLine(point1.x, point1.y, point2.x, point2.y);
   }

   public Point getPoint1() {
      return point1;
   }

   public void setPoint1(Point point1) {
      this.point1 = point1;
   }

   public Point getPoint2() {
      return point2;
   }

   public void setPoint2(Point point2) {
      this.point2 = point2;
   }
}
