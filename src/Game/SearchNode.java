package Game;

import java.awt.*;

/**
 * Created by BTC on 11/25/15.
 */

class SearchNode {
   public static final int NONE = 0;
   public static final int VISITED = 1;
   Point position;
   SearchNode previous;

   int state = NONE;

   public SearchNode(Point position) {
      this.position = position;
   }

   public Point getPosition() {
      return position;
   }

   public SearchNode getPrevious() {
      return previous;
   }

   public void setState(int state) {
      this.state = state;
   }

   public int getState() {
      return state;
   }

   public void setPrevious(SearchNode previous) {
      this.previous = previous;
   }

   public boolean hasBeenVisited() {
      return this.getState() == VISITED;
   }

   public void visit() {
      this.setState(VISITED);
   }

   @Override
   public String toString() {
      return "SearchNode{" +
            "position=" + position +
            '}';
   }

   @Override
   public boolean equals(Object obj) {
      SearchNode other = (SearchNode)obj;
      return this.getPosition().equals(other.getPosition());
   }
}
