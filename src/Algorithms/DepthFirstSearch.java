package Algorithms;

import Model.Graph;
import Model.Vertex;
import Model.VertexState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by BTC on 11/24/15.
 */
public class DepthFirstSearch {
   Graph graph;
   Vertex startVertex;
   Vertex goalVertex;

   Queue<Vertex> foundPath;
   public DepthFirstSearch(Graph graph) {
      this.graph = graph;
   }

   public Queue<Vertex> doSearch() {
      foundPath = new LinkedList<>();
      // reset data
      this.graph.reset();

      dfs(startVertex);
      return foundPath;
   }

   private void dfs(Vertex start) {
      visitVertex(start);
      List<Vertex> vertices = graph.getVertices();
      foundPath.add(start);
      for (Vertex vertex: vertices) {

         if (vertex.getState() != VertexState.VISITED && start.hasConnectTo(vertex)) {
//            if (vertex == goalVertex) {
//               foundPath.add(goalVertex);
//               return;
//            } else
            dfs(vertex);
         }
      }
   }

   public Vertex getStartVertex() {
      return startVertex;
   }

   public void setStartVertex(Vertex startVertex) {
      this.startVertex = startVertex;
   }

   public Vertex getGoalVertex() {
      return goalVertex;
   }

   public void setGoalVertex(Vertex goalVertex) {
      this.goalVertex = goalVertex;
   }

   private void visitVertex(Vertex vertex) {
      System.out.println("visit " + vertex.getName());
      vertex.setState(VertexState.VISITED);
   }


}
