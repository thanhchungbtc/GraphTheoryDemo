package SearchAlgorithm;

import Supports.DialogHelpers;
import Supports.Line;
import Supports.Vector2DHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by 130708 on 2015/11/26.
 */
public class CanvasArea extends JPanel {
   Graph graph;

   Vertex currentVertex;
   Point previousPoint;
   // temporary line
   Line tempLine;

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(800, 550);
   }

   public CanvasArea() {
      setBackground(Color.lightGray);
      addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            verticeMousePressed(e);
         }

         @Override
         public void mouseReleased(MouseEvent e) {
            verticeMouseReleased(e);
         }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            verticeMouseDragged(e);
         }
      });
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (tempLine != null) {
         tempLine.draw(g);
      }
      graph.draw(g);
   }

   public void verticeMousePressed(MouseEvent e) {
      if (e.getClickCount() == 2) {
         Edge clickedEdge = graph.getEdgeAtPoint(e.getPoint());
         if (clickedEdge == null) return;
         String input = DialogHelpers.showInPutDialog("Input", "Enter cost: ");
         int number = Integer.parseInt(input);
         clickedEdge.setCost(number);
         repaint();
         return;
      }

      Vertex selectedVertex = graph.getVertexAtPoint(e.getPoint());
      if (selectedVertex == null) {
         if (SwingUtilities.isLeftMouseButton(e)) {
            graph.addVertex(new Vertex(e.getPoint()));
            repaint();
         }
         return;
      }
      currentVertex = selectedVertex;
      if (SwingUtilities.isLeftMouseButton(e)) {
         previousPoint = e.getPoint();
      } else if (SwingUtilities.isRightMouseButton(e)) {
         tempLine = new Line();
         tempLine.setPoint1(e.getPoint());
      }
   }

   public void verticeMouseDragged(MouseEvent e) {
      if (SwingUtilities.isLeftMouseButton(e)) {
         if (currentVertex == null) return;

         Point diff = Vector2DHelper.SubstractVector(e.getPoint(), previousPoint);
         currentVertex.setPosition(Vector2DHelper.AddVector(currentVertex.getPosition(), diff));
         previousPoint = e.getPoint();
         repaint();

      } else if (SwingUtilities.isRightMouseButton(e)) {
         if (tempLine != null) {
            tempLine.setPoint2(e.getPoint());
            repaint();
         }
      }
   }

   public void verticeMouseReleased(MouseEvent e) {
      if (tempLine != null) {
         tempLine = null;
         Vertex selectedVertex = graph.getVertexAtPoint(e.getPoint());
         if (selectedVertex != null) {
            Edge edge = new Edge(currentVertex, selectedVertex);
            graph.addEdge(edge);
         }
         repaint();
      }
      currentVertex = null;
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame();
      frame.setContentPane(new CanvasArea());
      frame.pack();
      frame.setVisible(true);
   }

   public void setGraph(Graph graph) {
      this.graph = graph;
   }
}
