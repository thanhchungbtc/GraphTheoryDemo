package SearchAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Supports.Config;
import Supports.DialogHelpers;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

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

   public DemoFrame() {
      setupGUI();
      loadSelectGraphCombobox();
      regisEvents();
      initDefaultData();

//      matrixTable.setModel(new MatrixTableModel(graph));
//      int maxWidth = matrixTable.getWidth() / (graph.vertices.size() + 1);
//      matrixTable.setRowHeight(24);
//      for (int i = 0; i < matrixTable.getColumnCount(); i++) {
//         matrixTable.getColumnModel().getColumn(i).setWidth(maxWidth);
//         matrixTable.getColumnModel().getColumn(i).setMaxWidth(maxWidth);
//         matrixTable.getColumnModel().getColumn(i).setMinWidth(maxWidth);
//      }
      timer = new Timer(500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            timerActionPerformed(e);
         }
      });
   }

   private void setupModel(Graph graph) {
      this.graph = graph;
      area.setGraph(graph);
      loadVertexCombobox();
   }

   private void loadMatrixTable() {

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
      headerPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, 3, 0))));
      contentPane.add(headerPanel, BorderLayout.NORTH);
      headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 0));
      
      newButton = new JButton("");
      newButton.setIconTextGap(0);
      headerPanel.add(newButton);
      newButton.setFocusPainted(false);
      newButton.setIcon(new ImageIcon(DemoFrame.class.getResource("/resources/new.png")));
      
      saveButton = new JButton("");
      headerPanel.add(saveButton);
      saveButton.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseReleased(MouseEvent e) {
      		buttonmouseReleased(e);
      	}
      });
      
       saveButton.setIcon(new ImageIcon(DemoFrame.class.getResource("/resources/save.png")));
      
      separator = new JSeparator();
      headerPanel.add(separator);
      
      addModeButton = new JToggleButton("");
      headerPanel.add(addModeButton);
      addModeButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		buttonActionPerform(e);
      	}
      });
      
      addModeButton.setIcon(new ImageIcon(DemoFrame.class.getResource("/resources/edit.png")));
      
      selectGraphCombobox = new JComboBox();
      headerPanel.add(selectGraphCombobox);

      area = new CanvasArea();
      area.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 0)));
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
      NavigationPanel.setBorder(null);
      contentPane.add(NavigationPanel, BorderLayout.WEST);
      GridBagLayout gbl_NavigationPanel = new GridBagLayout();
      gbl_NavigationPanel.columnWidths = new int[]{0, 0, 0};
      gbl_NavigationPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
      gbl_NavigationPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
      gbl_NavigationPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
      NavigationPanel.setLayout(gbl_NavigationPanel);
      
      lblSample = new JLabel("Sample:");
      GridBagConstraints gbc_lblSample = new GridBagConstraints();
      gbc_lblSample.anchor = GridBagConstraints.EAST;
      gbc_lblSample.insets = new Insets(0, 0, 5, 5);
      gbc_lblSample.gridx = 0;
      gbc_lblSample.gridy = 0;
      NavigationPanel.add(lblSample, gbc_lblSample);
      
      lblNewLabel = new JLabel("From:");
      GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
      gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel.gridx = 0;
      gbc_lblNewLabel.gridy = 1;
      NavigationPanel.add(lblNewLabel, gbc_lblNewLabel);
      startCombobox = new JComboBox();
      GridBagConstraints gbc_startCombobox = new GridBagConstraints();
      gbc_startCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_startCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_startCombobox.gridx = 1;
      gbc_startCombobox.gridy = 1;
      NavigationPanel.add(startCombobox, gbc_startCombobox);
      
      lblNewLabel_1 = new JLabel("To:");
      GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
      gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
      gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel_1.gridx = 0;
      gbc_lblNewLabel_1.gridy = 2;
      NavigationPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
      goalCombobox = new JComboBox();
      GridBagConstraints gbc_goalCombobox = new GridBagConstraints();
      gbc_goalCombobox.fill = GridBagConstraints.HORIZONTAL;
      gbc_goalCombobox.insets = new Insets(0, 0, 5, 0);
      gbc_goalCombobox.gridx = 1;
      gbc_goalCombobox.gridy = 2;
      NavigationPanel.add(goalCombobox, gbc_goalCombobox);
      
      lblAlgorithms = new JLabel("Algorithms:");
      GridBagConstraints gbc_lblAlgorithms = new GridBagConstraints();
      gbc_lblAlgorithms.anchor = GridBagConstraints.EAST;
      gbc_lblAlgorithms.insets = new Insets(0, 0, 5, 5);
      gbc_lblAlgorithms.gridx = 0;
      gbc_lblAlgorithms.gridy = 3;
      NavigationPanel.add(lblAlgorithms, gbc_lblAlgorithms);
      
      comboBox = new JComboBox();
      GridBagConstraints gbc_comboBox = new GridBagConstraints();
      gbc_comboBox.insets = new Insets(0, 0, 5, 0);
      gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
      gbc_comboBox.gridx = 1;
      gbc_comboBox.gridy = 3;
      NavigationPanel.add(comboBox, gbc_comboBox);
      
      lblAnimationSpeed = new JLabel("Animation speed:");
      GridBagConstraints gbc_lblAnimationSpeed = new GridBagConstraints();
      gbc_lblAnimationSpeed.anchor = GridBagConstraints.EAST;
      gbc_lblAnimationSpeed.insets = new Insets(0, 0, 5, 5);
      gbc_lblAnimationSpeed.gridx = 0;
      gbc_lblAnimationSpeed.gridy = 4;
      NavigationPanel.add(lblAnimationSpeed, gbc_lblAnimationSpeed);
      
      slider = new JSlider();
      GridBagConstraints gbc_slider = new GridBagConstraints();
      gbc_slider.insets = new Insets(0, 0, 5, 0);
      gbc_slider.fill = GridBagConstraints.HORIZONTAL;
      gbc_slider.gridx = 1;
      gbc_slider.gridy = 4;
      NavigationPanel.add(slider, gbc_slider);
      
      panel_2 = new JPanel();
      GridBagConstraints gbc_panel_2 = new GridBagConstraints();
      gbc_panel_2.fill = GridBagConstraints.BOTH;
      gbc_panel_2.insets = new Insets(0, 0, 5, 0);
      gbc_panel_2.gridx = 1;
      gbc_panel_2.gridy = 5;
      NavigationPanel.add(panel_2, gbc_panel_2);
      panel_2.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
      
      runStepButton = new JButton("Run step");
      panel_2.add(runStepButton);
      
      runButton = new JButton("Run");
      panel_2.add(runButton);
      runButton.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseReleased(MouseEvent e) {
      		buttonmouseReleased(e);
      	}
      });
      
      panel_1 = new JPanel();
      panel_1.setBorder(new TitledBorder(null, "Adjacent matrix", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_panel_1 = new GridBagConstraints();
      gbc_panel_1.anchor = GridBagConstraints.EAST;
      gbc_panel_1.gridwidth = 2;
      gbc_panel_1.fill = GridBagConstraints.VERTICAL;
      gbc_panel_1.gridx = 0;
      gbc_panel_1.gridy = 6;
      NavigationPanel.add(panel_1, gbc_panel_1);
      panel_1.setLayout(new BorderLayout(0, 0));
      
      matrixTable = new JTable();
      panel_1.add(matrixTable, BorderLayout.CENTER);
   }

   private void regisEvents() {
      startCombobox.addActionListener(e -> comboboxActionPerform(e));
      selectGraphCombobox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            comboboxActionPerform(e);
         }
      });
   }
   
   private void buttonActionPerform(ActionEvent e) {
	   if (e.getSource() == addModeButton) {
		   area.inAddMode = addModeButton.isSelected();
	   }
   }

   private void buttonmouseReleased(MouseEvent e) {
  
      // Run button
	   if (e.getSource() == runButton) {
         graph.reset();
         DFSSearchEngine searchEngine = new DFSSearchEngine();
         searchEngine.dataSource = graph;
         Vertex from = (Vertex)startCombobox.getModel().getSelectedItem();
         Vertex to = (Vertex)goalCombobox.getModel().getSelectedItem();
         searchResult = searchEngine.doSearch(from, to);
         timer.start();
      }
      // Save button clicked
      else if (e.getSource() == saveButton) {
         try {
            Graph selectedGraph = (Graph)selectGraphCombobox.getModel().getSelectedItem();
            String fileName = (String)JOptionPane.showInputDialog(null, "Enter file name",
                "Graph name: ", JOptionPane.QUESTION_MESSAGE,null,null, selectedGraph.toString());
            graph.name = fileName;
            saveGraph(this.graph, fileName);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
   }

   private void comboboxActionPerform(ActionEvent e) {
      if (e.getSource() == startCombobox) {
         loadGoalVertexCombobox();
      }
      else if (e.getSource() == selectGraphCombobox) {
         setupModel((Graph)selectGraphCombobox.getModel().getSelectedItem());
         area.repaint();
      }
   }

   private void initDefaultData() {
      if (selectGraphCombobox.getModel().getSize() > 0)
         selectGraphCombobox.setSelectedIndex(0);
   }

   private void loadSelectGraphCombobox() {
      DefaultComboBoxModel<Graph> model = new DefaultComboBoxModel();
      File[] files = new File("sample").listFiles();
      for (File file: files) {
         try {
            model.addElement(loadGraph(file));

         } catch (Exception e) {
            System.out.println("Could not load : " + file.getName());
         }
      }
      if (model.getSize() == 0) {
         model.addElement(new Graph());
      }
      selectGraphCombobox.setModel(model);
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

   private void timerActionPerformed(ActionEvent e) {
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

   private Graph loadGraph(File file) throws ClassNotFoundException, IOException {
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream objectInputStream = new ObjectInputStream(fis);
      Graph result = (Graph)objectInputStream.readObject();
      objectInputStream.close();
      return  result;
   }

   private void saveGraph(Graph graph, String fileName) throws IOException {
      File file = new File("sample/" + fileName);
      if (!file.exists())
         file.createNewFile();

      FileOutputStream fos = new FileOutputStream(file, false);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
      objectOutputStream.writeObject(graph);

      fos.close();
      objectOutputStream.close();
   }

   public static void main(String[] args) {
	  
      DemoFrame frame = new DemoFrame();
      frame.pack();
      frame.setVisible(true);
   }

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
   private JLabel lblSample;
   private JComboBox selectGraphCombobox;
   private JButton saveButton;
   private JSeparator separator;
   private JButton newButton;
   private JToggleButton addModeButton;
   private JPanel panel_2;
   private JButton runStepButton;
   private JMenuBar menuBar;
   private JMenu mnFile;
   private JMenuItem mntmExit;
}
