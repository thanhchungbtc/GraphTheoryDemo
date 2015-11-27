package SearchAlgorithm;

/**
 * Created by BTC on 11/26/15.
 */
public interface SearchEngineDataSource {
   public java.util.List<Vertex> getAdjacentVertices(Vertex vertex);
}