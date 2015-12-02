package SearchAlgorithm.Model;

import Supports.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by BTC on 11/28/15.
 */
public class BFSSearchEngine extends AbstractSearchEngine {

   private void addToClose(Vertex vertex, List<Vertex> closed) {
      closed.add(vertex);
      // log
//      Log.Instance().push(vertex.getName());
      //-----------
   }

   @Override
   public SearchResult doSearch(Vertex fromVertex, Vertex toVertex) {
      Log.Instance().push("\n========BREATH FIRST SEARCH======================\n");
      StringBuilder log = new StringBuilder();
      SearchResult searchResult = new SearchResult();
      Stack<Vertex> temp = new Stack<>(); // for show perform step animation

      // begin algorithm
      Queue<Node> open = new LinkedList<>();
      Stack<Vertex> closed = new Stack<>();

      Node n = new Node(fromVertex);
      open.add(n); // push start vertex to queue, ready to visit
      addToClose(n.vertex, closed);

      int numberOfLoop = 1;
      while (!open.isEmpty()) {
         Node currentNode = open.poll();
         numberOfLoop++;
      // write log
         log.append(currentNode.vertex.getName());

         temp.push(currentNode.vertex);
         // check if currentNode is target
         if (toVertex != null && currentNode.vertex.equals(toVertex) &&
               searchResult.shortestPath == null) {
            // and print out the found path
            searchResult.shortestPath = this.getPathFromNode(currentNode);
            StringBuilder shortestLog = new StringBuilder("Path from " + fromVertex.getName() + "->" + toVertex.getName() + ": " + stringFromPath(searchResult.shortestPath) + "\n");
            shortestLog.append("Loop count: " + numberOfLoop + "\n");
            Log.Instance().push(shortestLog.toString());
            if (!traverAll) return searchResult;
         }
         // start searching
         // for all adjacent nodes from currentNode
         java.util.List<Vertex> adjacents = dataSource.getAdjacentVertices(currentNode.vertex);
         for (Vertex newVertex : adjacents) {
            Node node = new Node(newVertex);
            if (!closed.contains(newVertex)) {
               node.previous = currentNode;
               closed.push(node.vertex);
               open.add(node);
            }
         }

         if (!open.isEmpty()) {
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
      if (searchResult.shortestPath == null) Log.Instance().push("No way from " + fromVertex.getName() + "->" + toVertex.getName());
      Log.Instance().push("Travel Path: " + log.toString() + "\n");

      return searchResult;
   }

}
