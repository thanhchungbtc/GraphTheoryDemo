package Game;

import java.awt.*;
import java.util.*;

/**
 * Created by BTC on 11/25/15.
 */
public class DFSPathfinder extends AbstractPathfinderEngine {


   @Override
   public Stack<Point> doSearch(Point fromPoint, Point toPoint) {
      Stack<SearchNode> stack = new Stack<>();
      Stack<SearchNode> closed = new Stack<>();
      stack.push(new SearchNode(fromPoint));
      while (!stack.empty()) {
         SearchNode currentNode = stack.pop();
         closed.push(currentNode);
         // check if currentNode is target
         if (currentNode.getPosition().equals(toPoint)) {
            System.out.println("PATH FOUND!!!");
            SearchNode step = currentNode;
            // and print out the found path
            System.out.println("Target: " + toPoint);
            Stack<Point> results = new Stack<>();
            while (step != null) {
               System.out.println(step);
               results.push(step.getPosition());
               step = step.getPrevious();
            }
            // TODO: place holder
            return results;
         }
         // start searching

         // for all movable node from currentNode
         Point[] adjacents = dataSource.getPosibleMovesFromPosition(currentNode.getPosition());
         for (int i = 0; i < adjacents.length; i++) {
            Point point = adjacents[i];
            if (point == null) break; // has not any movable point
            // if node has not been visited
            SearchNode node = new SearchNode(point);
            if (!closed.contains(node)) {
               // then visit it
               node.visit();
               node.setPrevious(currentNode);
               stack.push(currentNode);
               stack.push(node);
               break;

            }
         }
      }

      return null;
   }
}

interface PathfinderDataSource {
   public Point[] getPosibleMovesFromPosition(Point p);
}

