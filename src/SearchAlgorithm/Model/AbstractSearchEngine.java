package SearchAlgorithm.Model;

import java.util.*;

/**
 * Created by BTC on 11/26/15.
 */
public abstract class AbstractSearchEngine {

   public SearchEngineDataSource dataSource;
   public abstract SearchResult doSearch(Vertex fromVertex, Vertex toVertex);
   protected boolean traverAll = true;
   protected String stringFromPath(List<Vertex> path) {
      StringBuilder sb = new StringBuilder();
      for (int i = path.size() - 1; i > 0; i--) {
         sb.append(path.get(i).getName() + "->");
      }
      sb.append(path.get(0));
      return sb.toString();
   }

   protected Stack<Vertex> getPathFromNode(Node step) {
      Stack<Vertex> results = new Stack<>();
      while (step != null) {
         System.out.println(step.vertex);
         results.push(step.vertex);
         step = step.previous;
      }
      return results;
   }

   public void setTraverAll(boolean traverAll) {
      this.traverAll = traverAll;
   }
}


