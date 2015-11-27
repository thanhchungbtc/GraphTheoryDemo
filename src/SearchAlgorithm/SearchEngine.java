package SearchAlgorithm;

import java.awt.*;
import java.util.*;

/**
 * Created by BTC on 11/26/15.
 */
public abstract class SearchEngine {
   SearchEngineDataSource dataSource;
   public abstract SearchResult doSearch(Vertex fromVertex, Vertex toVertex);
   protected abstract Stack<Vertex> getPathFromNode(Node node);
}


