package SearchAlgorithm.Controllers;

import SearchAlgorithm.Model.*;
import SearchAlgorithm.View.CanvasArea;
import SearchAlgorithm.View.CanvasAreaDataChangedListenner;
import Supports.DialogHelpers;
import Supports.Log;
import Supports.LogContentChangedListenner;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by 130708 on 2015/11/26.
 */
public class DemoFrame extends JFrame implements LogContentChangedListenner, CanvasAreaDataChangedListenner {
   private JPanel contentPane;
   private JPanel logPanel;

   private JComboBox startCombobox;
   private JComboBox goalCombobox;
   private Timer timer;
   private String[] algorithms = {"Depth First Search", "Breath First Search", "Ford Bellman", "Dijkstra", "A Star", "Hill Climbing"};
   SearchResult searchResult;
   // view
   CanvasArea area;
   // model
   Graph graph;

   public DemoFrame() {
      setupGUI();
      loadAngorithmsCombobox();
      regisEvents();
      initDefaultData();
      contentSplitPane.setDividerLocation(300);
   }

   private void loadMatrixTable() {
      if (this.graph != null) {
         matrixTable.setShowHorizontalLines(true);
         matrixTable.setShowVerticalLines(true);
         matrixTable.setGridColor(Color.lightGray);
         matrixTable.setModel(new MatrixTableModel(this.graph));
         for (int i = 0; i < matrixTable.getColumnCount(); i++) {
            matrixTable.getColumnModel().getColumn(i).setWidth(25);
            matrixTable.getColumnModel().getColumn(i).setMinWidth(25);
            matrixTable.getColumnModel().getColumn(i).setMaxWidth(25);
         }
      }
   }

   private void setupModel(Graph graph) {
      this.graph = graph;
      area.setGraph(graph);
      loadVertexCombobox();
      isDirectGraphCheckBox.setSelected(graph.isDirectedGraph());
      loadMatrixTable();
      saveButton.setEnabled(true);
   }

   private void regisEvents() {
      startCombobox.addActionListener(e -> comboboxActionPerform(e));
      selectGraphCombobox.addActionListener(e -> comboboxActionPerform(e));
      newButton.addActionListener(e -> buttonActionPerformed(e));
      openButton.addActionListener(e -> buttonActionPerformed(e));
      timer = new Timer(500, e -> timerActionPerformed(e));
      area.dataChangedListenner = this;
      isDirectGraphCheckBox.addActionListener(e -> checkBoxActionListenner(e));
      timeSpeedSlider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            sliderStateChanged(e);
         }
      });
      addModeButton.addActionListener(e -> checkBoxActionListenner(e));
      runButton.addActionListener(e -> buttonActionPerformed(e));
   }

   private void checkBoxActionListenner(ActionEvent e) {
      // ADD MODE BUTTON
      if (e.getSource() == addModeButton) {
         area.inAddMode = addModeButton.isSelected();
      }
      // IS DIRECTED GRAPH CHECKBOX
      else if (e.getSource() == isDirectGraphCheckBox) {
         if (graph != null) graph.setDirectedGraph(isDirectGraphCheckBox.isSelected());
         area.repaint();
         loadMatrixTable();
      }
   }

   private void sliderStateChanged(ChangeEvent e) {
      timer.setDelay(timeSpeedSlider.getValue() * 100);
   }

   private void buttonActionPerformed(ActionEvent e) {
      // NEW BUTTON
      if (e.getSource() == newButton) {
         setupModel(new Graph());
      }
      // RUN BUTTON
      else if (e.getSource() == runButton) {
         if (graph == null || startCombobox.getSelectedIndex() == -1 || goalCombobox.getSelectedIndex() == -1) return;
         graph.reset();
         AbstractSearchEngine searchEngine;
         switch (algorithmCombobox.getSelectedIndex()) {
            case 0: // DFS
               searchEngine = new DFSSearchEngine();
               break;
            case 1: // BFS
               searchEngine = new BFSSearchEngine();
               break;
            case 2: // Ford Bellman
               searchEngine = new FordBellmanSearchEngine();
               ((AbstractPathfinderEngine) searchEngine).graph = this.graph;
               break;
            case 3: // Dijkstra
               searchEngine = new DijkstraPathfinderEngine();
               ((AbstractPathfinderEngine) searchEngine).graph = this.graph;
               break;
            default:
               searchEngine = new DFSSearchEngine();
               break;
         }
         searchEngine.dataSource = graph;
         Vertex from = (Vertex) startCombobox.getModel().getSelectedItem();
         Vertex to = (Vertex) goalCombobox.getModel().getSelectedItem();
         searchEngine.setTraverAll(travelAllCombobox.isSelected());
         searchResult = searchEngine.doSearch(from, to);
         // start searching
         timer.start();
      }
      // SAVE BUTTON
      else if (e.getSource() == saveButton) {
         try {
            saveGraph(this.graph);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
      // OPEN BUTTON
      else if (e.getSource() == openButton) {
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         fileChooser.setCurrentDirectory(new File("."));
         fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file | *.graph", "graph"));
         fileChooser.setDialogTitle("Choose folder contains graph file");
         if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            DefaultComboBoxModel<Graph> model = new DefaultComboBoxModel();
            if (fileChooser.getSelectedFile().isDirectory()) {
               File[] files = fileChooser.getSelectedFile().listFiles();
               for (File file : files) {
                  try {
                     model.addElement(loadGraph(file));

                  } catch (Exception e1) {
                     System.out.println("Could not load : " + file.getName());
                  }
               }
            } else {
               File file = fileChooser.getSelectedFile();
               try {

                  model.addElement(loadGraph(file));
               } catch (Exception e1) {
                  Log.Instance().push("Could not load : " + file.getName());
               }
            }
            if (model.getSize() == 0) {
               DialogHelpers.showError("Error", "No valid file found! Created new one");
               model.addElement(new Graph());
            }
            selectGraphCombobox.setModel(model);
            selectGraphCombobox.setSelectedIndex(0);
         }
      }
   }

   private void comboboxActionPerform(ActionEvent e) {
      if (e.getSource() == startCombobox) {
         loadGoalVertexCombobox();
      } else if (e.getSource() == selectGraphCombobox) {
         setupModel((Graph) selectGraphCombobox.getModel().getSelectedItem());
         area.repaint();
      }
   }

   private void initDefaultData() {
      setupModel(new Graph());
   }


   private void loadAngorithmsCombobox() {
      DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(algorithms);
      algorithmCombobox.setModel(model);
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

      if (selectedItem != -1 && model.getSize() > 0)
         goalCombobox.setSelectedIndex(selectedItem);
   }

   Vertex previousVertex;
   private void showShortestPath() {
      if (!searchResult.shortestPath.empty()) {
         Vertex currentVertex = searchResult.shortestPath.pop();
         currentVertex.setState(Vertex.SHORTESTPATH);
         if (previousVertex != null) {
            graph.getEdgeBetweenToVertex(previousVertex, currentVertex).isShortesPath = true;
         }
         previousVertex = currentVertex;
      } else {
         previousVertex = null;
         timer.stop();
      }
   }

   private void timerActionPerformed(ActionEvent e) {
      if (searchResult.traversalPath == null || searchResult.traversalPath.empty()) {
         showShortestPath();
         repaint();
         return;
      }
      String travelLog = graphInfoTextArea.getText().trim();
      Vertex vertex = searchResult.traversalPath.pop();
      if (!travelLog.equals("")) travelLog += "->";
      travelLog += vertex.getName();
      graphInfoTextArea.setText(travelLog);
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

   private Graph loadGraph(File file) throws ClassNotFoundException, IOException {

      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream objectInputStream = new ObjectInputStream(fis);
      Graph result = (Graph) objectInputStream.readObject();
      objectInputStream.close();
      return result;
   }

   private void saveGraph(Graph graph) throws IOException {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setCurrentDirectory(new File("."));
      fileChooser.setSelectedFile(new File(graph.name));
      fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Graph file(*.graph)", "graph"));
      fileChooser.setDialogTitle("Save graph file");
      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
         File file = fileChooser.getSelectedFile();
         String fileWithExtension = file.getName();
         if (!fileWithExtension.endsWith(".graph")) {
            graph.name = fileWithExtension;
            fileWithExtension += ".graph";
            file = new File(fileChooser.getCurrentDirectory().getName() + "/" + fileWithExtension);
         } else {
            graph.name = fileWithExtension.substring(0, fileWithExtension.lastIndexOf('.'));
         }
         FileOutputStream fos = new FileOutputStream(file, false);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
         objectOutputStream.writeObject(graph);
         fos.close();
         objectOutputStream.close();
      }
   }

   @Override
   public void logContentChanged(Log log) {
      logTextArea.setText(log.getDescription());
   }

   @Override
   public void costChanged(CanvasArea area, int newCost) {
      loadMatrixTable();

   }

   @Override
   public void vertexAdded(CanvasArea area, Vertex newVertex) {

      loadMatrixTable();
      DefaultComboBoxModel startModel = (DefaultComboBoxModel) startCombobox.getModel();
      startModel.addElement(newVertex);

      if (startModel.getSize() > 1) {
         DefaultComboBoxModel goalModel = (DefaultComboBoxModel) goalCombobox.getModel();
         goalModel.addElement(newVertex);
      }

   }

   @Override
   public void edgeAdded(CanvasArea area, Edge newEdge) {
      loadMatrixTable();
   }

   private void setupGUI() {
      contentPane = new JPanel(new BorderLayout());
      contentPane.setBorder(new EmptyBorder(0, 0, 5, 0));
      logPanel = new JPanel();
      logTextArea = new JTextArea();

      menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      mnFile = new JMenu("File");
      menuBar.add(mnFile);

      mntmExit = new JMenuItem("Exit");
      mnFile.add(mntmExit);
      setContentPane(contentPane);
      JPanel headerPanel = new JPanel();
      headerPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 5, 0), new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, 3, 0))));
      contentPane.add(headerPanel, BorderLayout.NORTH);
      headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

      newButton = new JButton("New");

      newButton.setIconTextGap(0);
      headerPanel.add(newButton);
      newButton.setFocusPainted(false);

      openButton = new JButton("Open");

      headerPanel.add(openButton);

      saveButton = new JButton("Save");
      saveButton.setEnabled(false);
      headerPanel.add(saveButton);
      saveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
         }
      });

      separator = new JSeparator();
      headerPanel.add(separator);

      addModeButton = new JCheckBox("Click to add vertex");
      addModeButton.setSelected(true);

      headerPanel.add(addModeButton);

      isDirectGraphCheckBox = new JCheckBox("Is Directed Graph");
      isDirectGraphCheckBox.setSelected(true);
      headerPanel.add(isDirectGraphCheckBox);

      selectGraphCombobox = new JComboBox();
      headerPanel.add(selectGraphCombobox);

      area = new CanvasArea();
      area.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));


      footerPanel = new JPanel();
      footerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.add(footerPanel, BorderLayout.SOUTH);
      footerPanel.setLayout(new GridLayout(0, 2, 5, 0));

      panel_3 = new JPanel();
      footerPanel.add(panel_3);
      panel_3.setLayout(new BorderLayout(0, 0));

      scrollPane_1 = new JScrollPane();
      panel_3.add(scrollPane_1);

      graphInfoTextArea = new JTextArea();
      scrollPane_1.setViewportView(graphInfoTextArea);

      lblGraphInfomation = new JLabel("Graph Infomation");
      panel_3.add(lblGraphInfomation, BorderLayout.NORTH);

      panel_4 = new JPanel();
      footerPanel.add(panel_4);
      panel_4.setLayout(new BorderLayout(0, 0));

      scrollPane = new JScrollPane();
      panel_4.add(scrollPane);

      logTextArea = new JTextArea();
      logTextArea.setLineWrap(true);
      logTextArea.setRows(5);
      scrollPane.setViewportView(logTextArea);

      lblErrorlist = new JLabel("Log");
      panel_4.add(lblErrorlist, BorderLayout.NORTH);

      leftPanel = new JPanel();
      leftPanel.setMaximumSize(new Dimension(200, 200));
      GridBagLayout gbl_leftPanel = new GridBagLayout();
      gbl_leftPanel.columnWidths = new int[]{293, 0};
      gbl_leftPanel.rowHeights = new int[]{187, 0, 0, 0, 0};
      gbl_leftPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
      gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
      leftPanel.setLayout(gbl_leftPanel);

      graphTravelPanel = new JPanel();
      GridBagConstraints gbc_graphTravelPanel = new GridBagConstraints();
      gbc_graphTravelPanel.fill = GridBagConstraints.HORIZONTAL;
      gbc_graphTravelPanel.insets = new Insets(0, 0, 5, 0);
      gbc_graphTravelPanel.anchor = GridBagConstraints.NORTH;
      gbc_graphTravelPanel.gridx = 0;
      gbc_graphTravelPanel.gridy = 0;
      leftPanel.add(graphTravelPanel, gbc_graphTravelPanel);
      graphTravelPanel.setBorder(new TitledBorder(new CompoundBorder(new EmptyBorder(0, 5, 5, 5), new LineBorder(new Color(0, 0, 0))), "Traversal Graph Algorithms", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
      GridBagLayout gbl_graphTravelPanel = new GridBagLayout();
      gbl_graphTravelPanel.columnWidths = new int[]{0, 0, 0};
      gbl_graphTravelPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
      gbl_graphTravelPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
      gbl_graphTravelPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
      graphTravelPanel.setLayout(gbl_graphTravelPanel);

      lblNewLabel = new JLabel("From:");
      GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
      gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel.gridx = 0;
      gbc_lblNewLabel.gridy = 0;
      graphTravelPanel.add(lblNewLabel, gbc_lblNewLabel);
      startCombobox = new JComboBox();
      GridBagConstraints gbc_startCombobox = new GridBagConstraints();
      gbc_startCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_startCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_startCombobox.gridx = 1;
      gbc_startCombobox.gridy = 0;
      graphTravelPanel.add(startCombobox, gbc_startCombobox);

      lblNewLabel_1 = new JLabel("To:");
      GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
      gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_1.gridx = 0;
      gbc_lblNewLabel_1.gridy = 1;
      graphTravelPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
      goalCombobox = new JComboBox();
      GridBagConstraints gbc_goalCombobox = new GridBagConstraints();
      gbc_goalCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_goalCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_goalCombobox.gridx = 1;
      gbc_goalCombobox.gridy = 1;
      graphTravelPanel.add(goalCombobox, gbc_goalCombobox);

      lblAnimationSpeed = new JLabel("Anim speed:");
      GridBagConstraints gbc_lblAnimationSpeed = new GridBagConstraints();
      gbc_lblAnimationSpeed.anchor = GridBagConstraints.EAST;
      gbc_lblAnimationSpeed.insets = new Insets(0, 0, 5, 5);
      gbc_lblAnimationSpeed.gridx = 0;
      gbc_lblAnimationSpeed.gridy = 2;
      graphTravelPanel.add(lblAnimationSpeed, gbc_lblAnimationSpeed);

      timeSpeedSlider = new JSlider();

      timeSpeedSlider.setValue(5);
      timeSpeedSlider.setMaximum(10);
      GridBagConstraints gbc_timeSpeedSlider = new GridBagConstraints();
      gbc_timeSpeedSlider.fill = GridBagConstraints.HORIZONTAL;
      gbc_timeSpeedSlider.insets = new Insets(0, 0, 5, 0);
      gbc_timeSpeedSlider.gridx = 1;
      gbc_timeSpeedSlider.gridy = 2;
      graphTravelPanel.add(timeSpeedSlider, gbc_timeSpeedSlider);

      lblAlgorithms = new JLabel("Algorithms:");
      GridBagConstraints gbc_lblAlgorithms = new GridBagConstraints();
      gbc_lblAlgorithms.anchor = GridBagConstraints.EAST;
      gbc_lblAlgorithms.insets = new Insets(0, 0, 5, 5);
      gbc_lblAlgorithms.gridx = 0;
      gbc_lblAlgorithms.gridy = 3;
      graphTravelPanel.add(lblAlgorithms, gbc_lblAlgorithms);

      algorithmCombobox = new JComboBox();
      GridBagConstraints gbc_algorithmCombobox = new GridBagConstraints();
      gbc_algorithmCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_algorithmCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_algorithmCombobox.gridx = 1;
      gbc_algorithmCombobox.gridy = 3;
      graphTravelPanel.add(algorithmCombobox, gbc_algorithmCombobox);

      panel = new JPanel();
      GridBagConstraints gbc_panel = new GridBagConstraints();
      gbc_panel.anchor = GridBagConstraints.EAST;
      gbc_panel.gridx = 1;
      gbc_panel.gridy = 4;
      graphTravelPanel.add(panel, gbc_panel);
      GridBagLayout gbl_panel = new GridBagLayout();
      gbl_panel.columnWidths = new int[]{0, 0, 0};
      gbl_panel.rowHeights = new int[]{0, 0};
      gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
      gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
      panel.setLayout(gbl_panel);

      travelAllCombobox = new JCheckBox("Travel all");
      travelAllCombobox.setSelected(true);
      GridBagConstraints gbc_travelAllCombobox = new GridBagConstraints();
      gbc_travelAllCombobox.insets = new Insets(0, 0, 0, 5);
      gbc_travelAllCombobox.gridx = 0;
      gbc_travelAllCombobox.gridy = 0;
      panel.add(travelAllCombobox, gbc_travelAllCombobox);

      runButton = new JButton("Run");
      GridBagConstraints gbc_runButton = new GridBagConstraints();
      gbc_runButton.anchor = GridBagConstraints.EAST;
      gbc_runButton.gridx = 1;
      gbc_runButton.gridy = 0;
      panel.add(runButton, gbc_runButton);

      circuitPanel = new JPanel();
      circuitPanel.setBorder(new TitledBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(0, 0, 0))), "Circuit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_circuitPanel = new GridBagConstraints();
      gbc_circuitPanel.anchor = GridBagConstraints.NORTH;
      gbc_circuitPanel.insets = new Insets(0, 0, 5, 0);
      gbc_circuitPanel.fill = GridBagConstraints.HORIZONTAL;
      gbc_circuitPanel.gridx = 0;
      gbc_circuitPanel.gridy = 1;
      leftPanel.add(circuitPanel, gbc_circuitPanel);
      GridBagLayout gbl_circuitPanel = new GridBagLayout();
      gbl_circuitPanel.columnWidths = new int[]{0, 0, 0};
      gbl_circuitPanel.rowHeights = new int[]{0, 0, 0};
      gbl_circuitPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
      gbl_circuitPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
      circuitPanel.setLayout(gbl_circuitPanel);

      lblNewLabel_2 = new JLabel("Select circuit");
      GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
      gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel_2.gridx = 0;
      gbc_lblNewLabel_2.gridy = 0;
      circuitPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

      selectCircuitCombobox = new JComboBox();
      GridBagConstraints gbc_selectCircuitCombobox = new GridBagConstraints();
      gbc_selectCircuitCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_selectCircuitCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_selectCircuitCombobox.gridx = 1;
      gbc_selectCircuitCombobox.gridy = 0;
      circuitPanel.add(selectCircuitCombobox, gbc_selectCircuitCombobox);

      btnNewButton = new JButton("Show");
      GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
      gbc_btnNewButton.anchor = GridBagConstraints.EAST;
      gbc_btnNewButton.gridx = 1;
      gbc_btnNewButton.gridy = 1;
      circuitPanel.add(btnNewButton, gbc_btnNewButton);

      matrixPanel = new JPanel();
      matrixPanel.setBorder(new TitledBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(0, 0, 0))), "Adjacent matrix", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_matrixPanel = new GridBagConstraints();
      gbc_matrixPanel.insets = new Insets(0, 0, 5, 0);
      gbc_matrixPanel.fill = GridBagConstraints.BOTH;
      gbc_matrixPanel.gridx = 0;
      gbc_matrixPanel.gridy = 2;
      leftPanel.add(matrixPanel, gbc_matrixPanel);
      matrixPanel.setLayout(new GridLayout(0, 1, 0, 0));

      scrollPane_2 = new JScrollPane();
      matrixPanel.add(scrollPane_2);

      matrixTable = new JTable();
      scrollPane_2.setViewportView(matrixTable);
      matrixTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


      contentSplitPane = new JSplitPane();
      contentSplitPane.setBorder(null);
      contentSplitPane.setLeftComponent(leftPanel);
      contentSplitPane.setRightComponent(area);
      contentPane.add(contentSplitPane, BorderLayout.CENTER);
   }

   private JPanel footerPanel;
   private JPanel graphTravelPanel;
   private JLabel lblNewLabel;
   private JLabel lblNewLabel_1;
   private JLabel lblAlgorithms;
   private JComboBox algorithmCombobox;
   private JSlider timeSpeedSlider;
   private JLabel lblAnimationSpeed;
   private JButton runButton;
   private JScrollPane scrollPane;
   private JComboBox selectGraphCombobox;
   private JButton saveButton;
   private JSeparator separator;
   private JButton newButton;
   private JMenuBar menuBar;
   private JMenu mnFile;
   private JMenuItem mntmExit;
   private JCheckBox addModeButton;
   private JButton openButton;
   private JTextArea logTextArea;
   private JCheckBox isDirectGraphCheckBox;
   private JTextArea graphInfoTextArea;
   private JScrollPane scrollPane_1;
   private JLabel lblGraphInfomation;
   private JLabel lblErrorlist;
   private JPanel panel_3;
   private JPanel panel_4;
   private JPanel leftPanel;
   private JPanel circuitPanel;
   private JLabel lblNewLabel_2;
   private JComboBox selectCircuitCombobox;
   private JButton btnNewButton;
   private JPanel matrixPanel;
   private JSplitPane contentSplitPane;
   private JTable matrixTable;
   private JScrollPane scrollPane_2;
   private JPanel panel;
   private JCheckBox travelAllCombobox;

   public static void main(String[] args) {

      DemoFrame frame = new DemoFrame();
      frame.setLocation(100, 100);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      Log.Instance().addContentChangedListener(frame);
      frame.pack();
      frame.setVisible(true);
   }
}
