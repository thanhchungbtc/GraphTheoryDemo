package SearchAlgorithm.Model;

import Supports.Log;

import java.util.Stack;

/**
 * Created by BTC on 12/1/15.
 */
public class DijkstraPathfinderEngine extends AbstractPathfinderEngine {
   @Override
   public SearchResult doSearch(Vertex fromVertex, Vertex toVertex) {
      Log.Instance().push("========DIJKSTRA ALGORITHM======================\n");
      SearchResult searchResult = new SearchResult();

      int n = graph.getVertices().size();
      int[] d = new int[n];
      boolean[] free = new boolean[n]; for (int i = 0; i < n; i++) free[i] = true;
      int[] trace = new int[n];
      d[fromVertex.id] = 0;

      int u, v;
      while (true) {
         u = 0;
         int min = graph.Max_INFINITY;
         for (int i = 0; i < n; i++) {
            if (free[i] && d[i] < min) {
               u = i;
            }
         }
         if (u == 0 || u == toVertex.id) break;
         free[u] = false;
         for (v = 0; v < n; v++) {
            int cost = graph.getCostBetween2Vertices(graph.getVertexByID(u), graph.getVertexByID(v));
            if (free[v] && d[v] > d[u] + cost) {
               d[v] = d[u] + cost;
               trace[v] = u;
            }
         }
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
