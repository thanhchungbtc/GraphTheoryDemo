package Controller;

import Algorithms.DepthFirstSearch;
import Model.Edge;
import Model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Queue;

/**
 * Created by BTC on 11/24/15.
 */
public class MainForm extends JFrame {

   private JPanel contentPane;
   private JButton addVertexButton;
   private JPanel logPanel;
   private JTextArea logTextArea;
   private JButton dfsButton;
   private JComboBox startCombobox;
   private JComboBox goalCombobox;
   CanvasArea area;

   private void regisEvents() {
      area.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            area.verticeMousePressed(e);
         }

         @Override
         public void mouseReleased(MouseEvent e) {
            area.verticeMouseReleased(e);
         }
      });
      area.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            area.verticeMouseDragged(e);
         }
      });

      addVertexButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent e) {
            buttonmouseReleased(e);
         }
      });
      dfsButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent e) {
            buttonmouseReleased(e);
         }
      });
      startCombobox.addActionListener(e -> comboboxActionPerform(e));
   }

   private void initDefaultData() {
      Vertex vertex1 = new Vertex(new Point(100, 120));
      Vertex vertex2 = new Vertex(new Point(30, 320));
      Vertex vertex3 = new Vertex(new Point(250, 250));
      Vertex vertex4 = new Vertex(new Point(350, 320));
      Vertex vertex5 = new Vertex(new Point(80, 420));
      Vertex vertex6 = new Vertex(new Point(440, 220));
      Vertex vertex7 = new Vertex(new Point(390, 60));

      area.addVertex(vertex1);
      area.addVertex(vertex2);
      area.addVertex(vertex3);
      area.addVertex(vertex4);
      area.addVertex(vertex5);
      area.addVertex(vertex6);
      area.addVertex(vertex7);

      area.addEdge(new Edge(vertex1, vertex2));
      area.addEdge(new Edge(vertex3, vertex1));
      area.addEdge(new Edge(vertex3, vertex5));
      area.addEdge(new Edge(vertex6, vertex7));
      area.addEdge(new Edge(vertex7, vertex1));
      area.addEdge(new Edge(vertex4, vertex2));
      loadVertexCombobox();
   }

   // events
   private void buttonmouseReleased(MouseEvent e) {
      if (e.getSource() == addVertexButton) {
         area.addVertexAtPosition(new Point(100, 100));
         loadVertexCombobox();
      }
      // DFS Search button
      else if (e.getSource() == dfsButton) {
         repaint();
         DepthFirstSearch depthFirstSearch = new DepthFirstSearch(area.getGraph());
         depthFirstSearch.setStartVertex((Vertex)startCombobox.getModel().getSelectedItem());
         depthFirstSearch.setGoalVertex((Vertex)goalCombobox.getModel().getSelectedItem());
         Queue<Vertex> foundPath = depthFirstSearch.doSearch();
         System.out.print("Found path: ");
         SwingWorker<Void, Integer> worker = new SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {

               for (Vertex vertex : foundPath) {
                  Thread.sleep(800);

                  String vertName = vertex.getName() + ", ";
                  logTextArea.setText(logTextArea.getText() + vertName);
                  vertex.setColor(Color.CYAN);
                  area.repaint();
               }

               return null;
            }

            @Override
            protected void done() {
               logTextArea.setText(logTextArea.getText() + "Done");
            }
         };
         worker.execute();
      }
   }

   private void comboboxActionPerform(ActionEvent e) {
      if (e.getSource() == startCombobox) {
         loadGoalVertexCombobox();
      } else if (e.getSource() == goalCombobox) {

      }
   }

   public MainForm() {
      setupGUI();

   }

   private void setupGUI() {
      setContentPane(contentPane);
      area = new CanvasArea();
      contentPane.add(area, BorderLayout.CENTER);
      regisEvents();
      initDefaultData();
   }

   private void loadVertexCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(area.getGraph().getVertices().toArray());
      startCombobox.setModel(model);

      loadGoalVertexCombobox();
   }

   private void loadGoalVertexCombobox() {
      int selectedItem = 0;
      if (goalCombobox.getModel() != null) {
         selectedItem = goalCombobox.getSelectedIndex();
      }
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(area.getGraph().getVertices().toArray());
      model.removeElement(startCombobox.getModel().getSelectedItem());
      goalCombobox.setModel(model);

      goalCombobox.setSelectedIndex(selectedItem);
   }

   public static void main(String[] args) {
      MainForm mainForm = new MainForm();
      mainForm.setLocation(150, 50);
      mainForm.pack();
      mainForm.setVisible(true);
   }
}
