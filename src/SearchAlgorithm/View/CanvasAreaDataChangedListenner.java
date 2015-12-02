package SearchAlgorithm.View;

import SearchAlgorithm.Model.Edge;
import SearchAlgorithm.Model.Vertex;

/**
 * Created by BTC on 11/28/15.
 */
public interface CanvasAreaDataChangedListenner {
   void costChanged(CanvasArea area, int newCost);
   void vertexAdded(CanvasArea area, Vertex newVertex);
   void edgeAdded(CanvasArea area, Edge newEdge);
}