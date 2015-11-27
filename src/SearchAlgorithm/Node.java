package SearchAlgorithm;

import java.awt.*;

/**
 * Created by BTC on 11/26/15.
 */
public class Node {

   Vertex vertex;
   Node previous;

   public Node(Vertex vertex) {
      this.vertex = vertex;
   }

   @Override
   public boolean equals(Object obj) {
      Node other = (Node)obj;
      return this.vertex.equals(other.vertex);
   }
}
