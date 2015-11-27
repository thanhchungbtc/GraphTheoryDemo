package SearchAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;

/**
 * Created by 130708 on 2015/11/26.
 */
public class DemoFrame extends JFrame {
   private JPanel contentPane;
   private JPanel logPanel;
   private JTextArea logTextArea;
   private JComboBox startCombobox;
   private JComboBox goalCombobox;
   private Timer timer;

   SearchResult searchResult;
   // view
   CanvasArea area;
   // model
   Graph graph;
   private JPanel panel;
   private JPanel NavigationPanel;
   private JLabel lblNewLabel;
   private JLabel lblNewLabel_1;
   private JLabel lblAlgorithms;
   private JComboBox comboBox;
   private JSlider slider;
   private JLabel lblAnimationSpeed;
   private JButton runButton;
   private JPanel panel_1;
   private JTextArea logTable;
   private JTable matrixTable;
   private JScrollPane scrollPane;

   public DemoFrame() {
      setupGUI();
      initializeData();
      regisEvents();
      initDefaultData();

      matrixTable.setModel(new MatrixTableModel(graph));
      int maxWidth = matrixTable.getWidth() / (graph.vertices.size() + 1);
      matrixTable.setRowHeight(24);
      for (int i = 0; i < matrixTable.getColumnCount(); i++) {
         matrixTable.getColumnModel().getColumn(i).setWidth(maxWidth);
         matrixTable.getColumnModel().getColumn(i).setMaxWidth(maxWidth);
         matrixTable.getColumnModel().getColumn(i).setMinWidth(maxWidth);
      }
      timer = new Timer(500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            performStep(e);
         }
      });
   }

   private void initializeData() {
      graph = new Graph();
      area.setGraph(graph);
   }

   private void loadMatrixTable() {

   }

   private void setupGUI() {
      contentPane = new JPanel(new BorderLayout());
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      logPanel = new JPanel();
      logTextArea = new JTextArea();
      setContentPane(contentPane);

      // header panel
      JPanel headerPanel = new JPanel(new FlowLayout());
      contentPane.add(headerPanel, BorderLayout.NORTH);

      area = new CanvasArea();
      contentPane.add(area, BorderLayout.CENTER);
      
      panel = new JPanel();
      contentPane.add(panel, BorderLayout.SOUTH);
      panel.setLayout(new GridLayout(0, 1, 0, 0));
      
      scrollPane = new JScrollPane();
      panel.add(scrollPane);
      
      logTable = new JTextArea();
      logTable.setLineWrap(true);
      logTable.setRows(5);
      scrollPane.setViewportView(logTable);
      
      NavigationPanel = new JPanel();
      contentPane.add(NavigationPanel, BorderLayout.WEST);
      GridBagLayout gbl_NavigationPanel = new GridBagLayout();
      gbl_NavigationPanel.columnWidths = new int[]{0, 0, 0};
      gbl_NavigationPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
      gbl_NavigationPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
      gbl_NavigationPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
      NavigationPanel.setLayout(gbl_NavigationPanel);
      
      lblNewLabel = new JLabel("From:");
      GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
      gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel.gridx = 0;
      gbc_lblNewLabel.gridy = 0;
      NavigationPanel.add(lblNewLabel, gbc_lblNewLabel);
      startCombobox = new JComboBox();
      GridBagConstraints gbc_startCombobox = new GridBagConstraints();
      gbc_startCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_startCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_startCombobox.gridx = 1;
      gbc_startCombobox.gridy = 0;
      NavigationPanel.add(startCombobox, gbc_startCombobox);
      
      lblNewLabel_1 = new JLabel("To:");
      GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
      gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_1.gridx = 0;
      gbc_lblNewLabel_1.gridy = 1;
      NavigationPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
      goalCombobox = new JComboBox();
      GridBagConstraints gbc_goalCombobox = new GridBagConstraints();
      gbc_goalCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_goalCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_goalCombobox.gridx = 1;
      gbc_goalCombobox.gridy = 1;
      NavigationPanel.add(goalCombobox, gbc_goalCombobox);
      
      lblAlgorithms = new JLabel("Algorithms:");
      GridBagConstraints gbc_lblAlgorithms = new GridBagConstraints();
      gbc_lblAlgorithms.anchor = GridBagConstraints.EAST;
      gbc_lblAlgorithms.insets = new Insets(0, 0, 5, 5);
      gbc_lblAlgorithms.gridx = 0;
      gbc_lblAlgorithms.gridy = 2;
      NavigationPanel.add(lblAlgorithms, gbc_lblAlgorithms);
      
      comboBox = new JComboBox();
      GridBagConstraints gbc_comboBox = new GridBagConstraints();
      gbc_comboBox.insets = new Insets(0, 0, 5, 0);
      gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
      gbc_comboBox.gridx = 1;
      gbc_comboBox.gridy = 2;
      NavigationPanel.add(comboBox, gbc_comboBox);
      
      lblAnimationSpeed = new JLabel("Animation speed:");
      GridBagConstraints gbc_lblAnimationSpeed = new GridBagConstraints();
      gbc_lblAnimationSpeed.anchor = GridBagConstraints.EAST;
      gbc_lblAnimationSpeed.insets = new Insets(0, 0, 5, 5);
      gbc_lblAnimationSpeed.gridx = 0;
      gbc_lblAnimationSpeed.gridy = 3;
      NavigationPanel.add(lblAnimationSpeed, gbc_lblAnimationSpeed);
      
      slider = new JSlider();
      GridBagConstraints gbc_slider = new GridBagConstraints();
      gbc_slider.insets = new Insets(0, 0, 5, 0);
      gbc_slider.fill = GridBagConstraints.HORIZONTAL;
      gbc_slider.gridx = 1;
      gbc_slider.gridy = 3;
      NavigationPanel.add(slider, gbc_slider);
      
      runButton = new JButton("Run");
      runButton.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseReleased(MouseEvent e) {
      		buttonmouseReleased(e);
      	}
      });
     
      GridBagConstraints gbc_runButton = new GridBagConstraints();
      gbc_runButton.insets = new Insets(0, 0, 5, 0);
      gbc_runButton.anchor = GridBagConstraints.EAST;
      gbc_runButton.gridx = 1;
      gbc_runButton.gridy = 4;
      NavigationPanel.add(runButton, gbc_runButton);
      
      panel_1 = new JPanel();
      panel_1.setBorder(new TitledBorder(null, "Adjacent matrix", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_panel_1 = new GridBagConstraints();
      gbc_panel_1.gridwidth = 2;
      gbc_panel_1.insets = new Insets(0, 0, 0, 5);
      gbc_panel_1.fill = GridBagConstraints.BOTH;
      gbc_panel_1.gridx = 0;
      gbc_panel_1.gridy = 5;
      NavigationPanel.add(panel_1, gbc_panel_1);
      panel_1.setLayout(new BorderLayout(0, 0));
      
      matrixTable = new JTable();
      panel_1.add(matrixTable, BorderLayout.CENTER);
   }

   private void regisEvents() {
      startCombobox.addActionListener(e -> comboboxActionPerform(e));
   }
   private void buttonmouseReleased(MouseEvent e) {
  
      // Run button
	   if (e.getSource() == runButton) {
         graph.reset();
         DFSSearchEngine searchEngine = new DFSSearchEngine();
         searchEngine.dataSource = graph;
         Vertex from = (Vertex)startCombobox.getModel().getSelectedItem();
         Vertex to = (Vertex)goalCombobox.getModel().getSelectedItem();
//         Stack<Vertex> path =  searchEngine.doSearch(from, null);
         searchResult = searchEngine.doSearch(from, to);
         timer.start();
      }
   }

   private void comboboxActionPerform(ActionEvent e) {
      if (e.getSource() == startCombobox) {
         loadGoalVertexCombobox();
      } else if (e.getSource() == goalCombobox) {

      }
   }

   private void initDefaultData() {
      Vertex vertex1 = new Vertex(new Point(100, 120));
      Vertex vertex2 = new Vertex(new Point(30, 320));
      Vertex vertex3 = new Vertex(new Point(250, 250));
      Vertex vertex4 = new Vertex(new Point(350, 320));
      Vertex vertex5 = new Vertex(new Point(80, 420));
      Vertex vertex6 = new Vertex(new Point(440, 220));
      Vertex vertex7 = new Vertex(new Point(390, 60));

      graph.addVertex(vertex1);
      graph.addVertex(vertex2);
      graph.addVertex(vertex3);
      graph.addVertex(vertex4);
      graph.addVertex(vertex5);
      graph.addVertex(vertex6);
      graph.addVertex(vertex7);

      graph.addEdge(new Edge(vertex1, vertex2));
      graph.addEdge(new Edge(vertex1, vertex3));
      graph.addEdge(new Edge(vertex1, vertex7));
      graph.addEdge(new Edge(vertex2, vertex4));
      graph.addEdge(new Edge(vertex3, vertex5));
      graph.addEdge(new Edge(vertex6, vertex7));

      loadVertexCombobox();
   }

   private void loadVertexCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(graph.getVertices().toArray());
      startCombobox.setModel(model);

      loadGoalVertexCombobox();
   }

   private void loadGoalVertexCombobox() {
      int selectedItem = 0;
      if (goalCombobox.getModel() != null) {
         selectedItem = goalCombobox.getSelectedIndex();
      }
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(graph.getVertices().toArray());
      model.removeElement(startCombobox.getModel().getSelectedItem());
      goalCombobox.setModel(model);

      if (selectedItem != -1)
         goalCombobox.setSelectedIndex(selectedItem);
   }

   private void performStep(ActionEvent e) {
      if (searchResult.traversalPath.empty()) {
         while (!searchResult.shortestPath.empty()) {
            searchResult.shortestPath.pop().setState(Vertex.SHORTESTPATH);
         }
         repaint();
         timer.stop();
         return;
      }
      Vertex vertex = searchResult.traversalPath.pop();
      switch (vertex.state) {
         case Vertex.NONE:
            vertex.setState(Vertex.VISITED);
            break;
         case Vertex.VISITED:
            vertex.setState(Vertex.BACKTRACKING);
            break;
         case Vertex.BACKTRACKING:
            vertex.setState(Vertex.BACKTRACKING2);
            break;
         case Vertex.BACKTRACKING2:
            vertex.setState(Vertex.BACKTRACKING);
            break;
      }
      area.repaint();
   }

   public static void main(String[] args) {
      DemoFrame frame = new DemoFrame();
      frame.pack();
      frame.setVisible(true);
   }
}
