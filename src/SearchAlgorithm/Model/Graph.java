package SearchAlgorithm.Model;

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
   private static final long serialVersionUID = -2889859630665873453L;

   public static final int Max_INFINITY = 1000;

   List<Vertex> vertices;

   int[][] adjacentMatrix;
   public String name = "Unknown";
   boolean isDirectedGraph = true;

   // this is only use for convenience handle the UI
   private List<Edge> edges;

   public Graph() {
      this.vertices = new ArrayList<>();
      this.edges = new ArrayList<>();
      adjacentMatrix = new int[Config.MAX_NUBMER_OF_VERTEX][Config.MAX_NUBMER_OF_VERTEX];
      for (int r = 0; r < Config.MAX_NUBMER_OF_VERTEX; r++) {
         for (int c =0 ; c < Config.MAX_NUBMER_OF_VERTEX; c++) {
            adjacentMatrix[r][c] = Max_INFINITY;
         }
      }
   }

   public void addVertex(Vertex vertex) {
      vertices.add(vertex);
      vertex.id = vertices.size() - 1;
   }

   public void addEdge(Edge edge) {
      int r = edge.vertex1.id;
      int c = edge.vertex2.id;
      // if there's already edge betwwen to vertices, return
      if (adjacentMatrix[r][c] != Max_INFINITY) return;

      // update adjacent matrix
      adjacentMatrix[r][c] = edge.cost;
      if (!this.isDirectedGraph)
         adjacentMatrix[c][r] = edge.cost;

      // convenience for handle UI
      edges.add(edge);
   }

   public void setCostForEdge(Edge edge, int newCost) {
      edge.setCost(newCost);

      setCostBetween2Vertices(edge.vertex1, edge.vertex2, newCost);
   }

   public void setDirectedGraph(boolean isDirected) {
      if (this.isDirectedGraph != isDirected) {
         this.isDirectedGraph = isDirected;
         // refine adjacent matrix
         for (Edge edge : this.edges) {
            int reversedWayCost = edge.cost;
            if (isDirectedGraph) reversedWayCost = Max_INFINITY; // there's no way back
            setCostBetween2Vertices(edge.vertex2, edge.vertex1, reversedWayCost);
         }
         printOutMatrix();
      }
   }

   public boolean isDirectedGraph() {
      return isDirectedGraph;
   }

   public void draw(Graphics g) {
      // DRAW EDGES
      for (Edge edge : edges) {
         edge.draw(g, isDirectedGraph);
      }
      // DRAW VERTICES
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

   public Edge getEdgeBetweenToVertex(Vertex vertex1, Vertex vertex2) {
      for (Edge edge : edges) {
         if ((edge.vertex1 == vertex1 && edge.vertex2 == vertex2) ||
               (edge.vertex1 == vertex2 && edge.vertex2 == vertex1)) return edge;
      }
      return null;
   }

   public List<Vertex> getVertices() {
      return vertices;
   }

   public void reset() {
      for (Vertex vertex : vertices) {
         vertex.resetState();
      }
      for (Edge edge : edges) {
         edge.resetState();
      }
   }

   @Override
   public String toString() {
      return this.name;
   }

   @Override
   public List<Vertex> getAdjacentVertices(Vertex vertex) {
      List<Vertex> vertexList = new LinkedList<>();
      for (Vertex v : vertices) {
         if (getCostBetween2Vertices(vertex, v) != 0)
            vertexList.add(v);
      }
      return vertexList;
   }

   public Vertex getVertexByID(int id) {
      for (Vertex vertex: this.vertices) {
         if (vertex.id == id) return vertex;
      }
      return null;
   }

   public int getCostAt(int index1, int index2) {
      return this.adjacentMatrix[index1][index2];
   }

   public int getCostBetween2Vertices(Vertex vertex1, Vertex vertex2) {
      return adjacentMatrix[vertex1.id][vertex2.id];
   }

   public void setCostBetween2Vertices(Vertex vertex1, Vertex vertex2, int cost) {
      adjacentMatrix[vertex1.id][vertex2.id] = cost;
      int r = vertex1.id;
      int c = vertex2.id;
      if (!this.isDirectedGraph)
         adjacentMatrix[c][r] = cost;
   }

   private void printOutMatrix() {
      System.out.println("ADJACENT MATRIX");
      System.out.print("V\t");
      for (Vertex vertex : vertices) {
         System.out.print(vertex.getName() + "\t");
      }
      System.out.println();
      for (Vertex vertex1 : vertices) {
         System.out.print(vertex1.getName() + "\t");
         for (Vertex vertex2 : vertices) {
            System.out.print(getCostBetween2Vertices(vertex1, vertex2) + "\t");
         }
         System.out.println();
      }
   }
//      @Override
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

   // Warshall algotihm
   public List<Graph> connectedComponents() {
      List<Graph> graphs = new LinkedList<>();
      return graphs;
   }

}
