package SearchAlgorithm.Model;

import Supports.Log;

import java.util.Stack;

/**
 * Created by BTC on 11/26/15.
 */
public class DFSSearchEngine extends AbstractSearchEngine {
   @Override
   public SearchResult doSearch(Vertex fromVertex, Vertex toVertex) {
      Log.Instance().push("========DEPTH FIRST SEARCH======================\n");
      StringBuilder log = new StringBuilder();
      SearchResult searchResult = new SearchResult();

      Stack<Node> stack = new Stack<>();
      Stack<Vertex> closed = new Stack<>();

      Stack<Vertex> temp = new Stack<>(); // for show perform step animation

      Node n = new Node(fromVertex);
      stack.push(n);
      closed.push(n.vertex);
      int numberOfLoop = 1;
      while (!stack.empty()) {
         numberOfLoop++;
         Node currentNode = stack.pop();
         // write log
         log.append(currentNode.vertex.getName());
         temp.push(currentNode.vertex);
         // check if currentNode is target
         if (toVertex != null && currentNode.vertex.equals(toVertex) &&
               searchResult.shortestPath == null) {
            // and print out the found path

            searchResult.shortestPath = this.getPathFromNode(currentNode);
            StringBuilder shortestLog = new StringBuilder("Shortest Path from " + fromVertex.getName() + "->" + toVertex.getName() + ": " + stringFromPath(searchResult.shortestPath) + "\n");
            shortestLog.append("Loop count: " + numberOfLoop + "\n");
            Log.Instance().push(shortestLog.toString());
            if (!traverAll) return searchResult;
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
         if (!stack.isEmpty()) {
            // log
            log.append("->");
            //-----------
         }
      }

      Stack<Vertex> travelPath = new Stack<>();

      while (!temp.empty()) {
         travelPath.push(temp.pop());
      }
      searchResult.traversalPath = travelPath;
      Log.Instance().push("Travel Path: " + log.toString() + "\n");
      if (searchResult.shortestPath == null) Log.Instance().push("No way from " + fromVertex.getName() + "->" + toVertex.getName());
      return searchResult;
   }

}
