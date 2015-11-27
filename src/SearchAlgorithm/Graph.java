package SearchAlgorithm;

import Supports.Config;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 130708 on 2015/11/26.
 */
public class Graph implements SearchEngineDataSource, Serializable {
   List<Vertex> vertices;
   List<Edge> edges;
   int[][] adjacentMatrix;
   String name = "Unknown";
   boolean isDirectedGraph = false;

   public Graph() {
      this.vertices = new ArrayList<>();
      this.edges = new ArrayList<>();
      adjacentMatrix = new int[Config.MAX_NUBMER_OF_VERTEX][Config.MAX_NUBMER_OF_VERTEX];
   }

   public void addVertex(Vertex vertex) {
      vertices.add(vertex);
      vertex.id = vertices.size() - 1;
   }

   public void addEdge(Edge edge) {
      int r = edge.vertex1.id;
      int c = edge.vertex2.id;
      // if there's already edge betwwen to vertices, return
      if (adjacentMatrix[r][c] != 0) return;

      adjacentMatrix[r][c] = edge.cost;
      adjacentMatrix[c][r] = edge.cost;
      edges.add(edge);
      if (!isDirectedGraph)
         edges.add(new Edge(edge.vertex2, edge.vertex1));
   }

   public void draw(Graphics g) {
      for (Edge edge : this.edges) {
         edge.draw(g);
      }
      for (Vertex vertex : this.vertices) {
         vertex.draw(g);
      }
   }

   public Edge getEdgeAtPoint(Point point) {
      for (Edge edge : this.edges) {
         if (edge.contains(point)) {
            return edge;
         }
      }
      return null;
   }

   public Vertex getVertexAtPoint(Point point) {
      for (Vertex vertex : vertices) {
         if (vertex.contains(point)) {
            return vertex;
         }
      }
      return null;
   }

   public List<Vertex> getVertices() {
      return vertices;
   }

   public void reset() {
      for (Vertex vertex: vertices) {
         vertex.resetState();
      }
   }
//   @Override
//   public String toString() {
//      StringBuilder sb = new StringBuilder("Graph: " + vertices.size() + " vertices, " + edges.size() + " edges" +
//            "{\n\tVertices: ");
//      for (Vertex vertex : vertices) {
//         sb.append(vertex.toString() + ", ");
//      }
//      sb.append("\n\tEdges:");
//      for (Edge edge : edges) {
//         sb.append(edge.toString() + ", ");
//      }
//      sb.append("\n}");
//      return sb.toString();
//   }


   @Override
   public String toString() {
      return this.name;
   }

   @Override
   public List<Vertex> getAdjacentVertices(Vertex vertex) {
      List<Vertex> vertexList = new LinkedList<>();

      for (Vertex v: vertices) {
         if (adjacentMatrix[vertex.id][v.id] != 0)
            vertexList.add(v);
      }

      return vertexList;
   }


}
