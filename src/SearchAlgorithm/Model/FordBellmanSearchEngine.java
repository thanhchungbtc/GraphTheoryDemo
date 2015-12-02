package SearchAlgorithm.Model;

import Game.*;
import Supports.Log;

import java.awt.*;
import java.util.Stack;

/**
 * Created by BTC on 12/1/15.
 */
public class FordBellmanSearchEngine extends AbstractPathfinderEngine {
   @Override
   public SearchResult doSearch(Vertex fromVertex, Vertex toVertex) {
      Log.Instance().push("========FORD BELLMAN ALGORITHM======================\n");
      SearchResult searchResult = new SearchResult();
      int numberOfVertices = graph.getVertices().size();
      int[] d = new int[numberOfVertices];
      int[] trace = new int[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
         d[i] = Graph.Max_INFINITY;
      }
      d[fromVertex.id] = 0;
      boolean stop = false;
      int countLoop = 0;
      while (countLoop < numberOfVertices - 1) {
         stop = true;
         countLoop++;
         for (Vertex u: graph.getVertices()) {
            for (Vertex v: graph.getVertices()) {
               if (d[v.id] > d[u.id] + graph.getCostBetween2Vertices(u, v)) {
                  d[v.id] = d[u.id] + graph.getCostBetween2Vertices(u, v);
                  stop = false;
                  trace[v.id] = u.id;
               }
            }
         }
         if (stop) break;
      }
      Stack<Vertex> shortestPath = new Stack<>();

      int finish = toVertex.id;
      Log.Instance().push("Cost: " + d[toVertex.id] + "\n");
      if (d[toVertex.id] != Graph.Max_INFINITY) {
         while(finish != fromVertex.id) {
            shortestPath.push(graph.getVertexByID(finish));
            finish = trace[finish];
         }
         shortestPath.push(graph.getVertexByID(finish));
      }

      searchResult.shortestPath = shortestPath;
      return searchResult;
   }
}
