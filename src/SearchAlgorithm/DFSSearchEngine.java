package SearchAlgorithm;

import java.util.Stack;

/**
 * Created by BTC on 11/26/15.
 */
public class DFSSearchEngine extends SearchEngine {
   public SearchResult doSearch(Vertex fromVertex, Vertex toVertex) {
      SearchResult searchResult = new SearchResult();

      Stack<Node> stack = new Stack<>();
      Stack<Vertex> closed = new Stack<>();

      Stack<Vertex> temp = new Stack<>(); // for show perform step animation

      Node n = new Node(fromVertex);
      stack.push(n);
      closed.push(n.vertex);
      while (!stack.empty()) {
         Node currentNode = stack.pop();
         temp.push(currentNode.vertex);
         // check if currentNode is target
         if (toVertex != null && currentNode.vertex.equals(toVertex)) {
            // and print out the found path
            System.out.print("TARGET: ");
            searchResult.shortestPath = this.getPathFromNode(currentNode);
         }
         // start searching

         // for all movable node from currentNode
         java.util.List<Vertex> adjacents = dataSource.getAdjacentVertices(currentNode.vertex);
         for (Vertex newVertex : adjacents) {
            // if node has not been visited
            Node node = new Node(newVertex);
            if (!closed.contains(newVertex)) {
               closed.push(newVertex);
               node.previous = currentNode;
               stack.push(currentNode);
               stack.push(node);
               break;
            }
         }
      }

      Stack<Vertex> travelPath = new Stack<>();

      while (!temp.empty()) {
         travelPath.push(temp.pop());
      }
      searchResult.traversalPath = travelPath;
      return searchResult;
   }

   @Override
   protected Stack<Vertex> getPathFromNode(Node step) {
      Stack<Vertex> results = new Stack<>();
      while (step != null) {
         System.out.println(step.vertex);
         results.push(step.vertex);
         step = step.previous;
      }
      return results;
   }
}
