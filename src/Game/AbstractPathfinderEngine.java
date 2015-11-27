package Game;

import java.awt.*;
import java.util.Stack;

/**
 * Created by BTC on 11/25/15.
 */
public abstract class AbstractPathfinderEngine {
   public PathfinderDataSource dataSource;
   public abstract Stack<Point> doSearch(Point fromPoint, Point toPoint);
}
