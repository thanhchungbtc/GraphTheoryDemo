package Supports;

import java.awt.*;

/**
 * Created by BTC on 11/23/15.
 */      
public class Vector2DHelper {
   public static Point AddVector(Point v1, Point v2) {
      return new Point(v1.x + v2.x, v1.y + v2.y);
   }

   public static Point MutilByScalar(Point vector, double scalar) {
      int newX = (int) (vector.x * scalar);
      int newY = (int) (vector.y * scalar);
      return new Point(newX, newY);
   }

   public static Point SubstractVector(Point v1, Point v2) {
      return new Point(v1.x - v2.x, v1.y - v2.y);
   }

   public static double DistanceBetweeen(Point vector1, Point vector2) {
      double a = vector2.x - vector1.x;
      double b = vector2.y - vector1.y;
      return Math.sqrt(a * a + b * b);
   }
}

