/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Robot.java
 *
 * Created on Mar 6, 2012, 3:15:35 PM
 */
package Robot;

import java.awt.Color;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.awt.event.*;
//import java.text.Normalizer.Form;
import java.io.FileInputStream;
import java.util.HashMap;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Sandeep
 */
public class RobotFactory extends javax.swing.JFrame implements MouseListener, MouseMotionListener, MouseWheelListener, ChangeListener {

    public JLabel[] labels;
    public JLabel active_label;
    public int input_count;
    public String[] results;
    public String[] tapes;
    public int animation_speed = 100;
    File challenge_file = null;
    File solution_file = null;
    public char[][] sol_data;
    public int sol_rows;
    public int sol_columns;
    public int sol_file=0;

    /** Creates new form Robot */
    public RobotFactory() {
        initComponents();
        labels = new JLabel[]{jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel15};
        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/conveyor_down.jpg"));
            jLabel3.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/conveyor_left.jpg"));
            jLabel4.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/conveyor_up.jpg"));
            jLabel5.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/conveyor_right.jpg"));
            jLabel6.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/switch_null_down.jpg"));
            jLabel7.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/switch_null_left.jpg"));
            jLabel8.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/switch_null_up.jpg"));
            jLabel9.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/switch_null_right.jpg"));
            jLabel10.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/play.jpg"));
            jLabel11.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/delete.jpg"));
            jLabel12.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        jMenuItem1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser browse = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Robot", "rbt");
                browse.setFileFilter(filter);
                browse.showOpenDialog(jWindow);
                setTitle(getTitle() + " - " + browse.getSelectedFile().getName());
                File file = browse.getSelectedFile();
                challenge_file = file;
                try {
                    setGoal(file);
                } catch (Exception exp) {
                }
                jLabel13.setText("0");
                jLabel15.setText("");
            }
        });

        jMenuItem2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser browse = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Grid", "grd");
                browse.setFileFilter(filter);
                browse.showOpenDialog(jWindow);
                File file = browse.getSelectedFile();
                solution_file = file;
                sol_file=1;
                try {
                    setSolution(file);
                } catch (Exception exp) {
                }

            }
        });

        jMenuItem3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jMenuItem4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (challenge_file == null) {
                    JOptionPane.showMessageDialog(null, "Challenge File Not Loaded");
                } else {
                    active_label = (JLabel) jLabel11;
                    label_state.put(jLabel11, 1);
                    try {
                        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/pause.jpg"));
                        jLabel11.setIcon(icon);
                    } catch (Exception et) {
                        JOptionPane.showMessageDialog(null, et.toString());
                    }

                }
            }
        });

        jMenuItem5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                active_label = (JLabel) jLabel11;
                label_state.put(jLabel11, 0);
                try {
                    ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/play.jpg"));
                    jLabel11.setIcon(icon);
                } catch (Exception et) {
                    JOptionPane.showMessageDialog(null, et.toString());
                }
            }
        });

        jMenuItem6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jMenuItem6.getText() == "Faster") {
                    jMenuItem6.setText("Slower");
                    animation_speed = 10;
                } else if (jMenuItem6.getText() == "Slower") {
                    jMenuItem6.setText("Faster");
                    animation_speed = 100;
                }
            }
        });

        jMenuItem7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, new String("Help/About"));
            }
        });
        for (int i = 0; i < 15; i++) {
            labels[i].addMouseListener(this);
        }
        reset_labelState();
    }

    public void reset_labelState() {
        for (int i = 2; i < 12; i++) {
            labels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLUE));
            label_state.put(labels[i], 0);
        }
    }

    public void setGoal(File file) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(file);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        strLine = br.readLine();
        input_count = Integer.valueOf(br.readLine());
        results = new String[input_count];
        tapes = new String[input_count];

        for (int i = 0; i < input_count; i++) {
            results[i] = br.readLine();
            tapes[i] = br.readLine();
        }
        jLabel1.setText(strLine);
        jLabel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        br.close();
    }

    public void setSolution(File file) throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream(file);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        strLine = br.readLine();
        sol_rows = Integer.valueOf(strLine);
        strLine = br.readLine();
        sol_columns = Integer.valueOf(strLine);
        sol_data = new char[sol_rows][sol_columns];
        //Read File Line By Line
        for (int i = 0; i < sol_rows; i++) {
            strLine = br.readLine();
            for (int j = 0; j < 2 * sol_columns; j += 2) {
                sol_data[i][(j / 2)] = strLine.charAt(j);
            }
        }
        br.close();
    }
    public HashMap<JLabel, Integer> label_state = new HashMap<JLabel, Integer>();

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        canvas1 = new my_canvas(this);
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Robot Factory");
        setBounds(new java.awt.Rectangle(100, 40, 606, 606));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(616, 491));

        jPanel2.setPreferredSize(new java.awt.Dimension(117, 469));

        //jLabel3.setText("jLabel3");
        jLabel3.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel3.setName("jLabel3"); // NOI18N

        //jLabel4.setText("jLabel4");
        jLabel4.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel4.setName("jLabel4"); // NOI18N

        //jLabel5.setText("jLabel5");
        jLabel5.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel5.setName("jLabel5"); // NOI18N

        //jLabel6.setText("jLabel6");
        jLabel6.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel6.setName("jLabel6"); // NOI18N

        //jLabel7.setText("jLabel7");
        jLabel7.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel7.setName("jLabel7"); // NOI18N

        //jLabel8.setText("jLabel8");
        jLabel8.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel8.setName("jLabel8"); // NOI18N

        //jLabel9.setText("jLabel9");
        jLabel9.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel9.setName("jLabel9"); // NOI18N

        //jLabel10.setText("jLabel10");
        jLabel10.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel10.setMinimumSize(new java.awt.Dimension(34, 14));
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(34, 14));

        //jLabel11.setText("jLabel11");
        jLabel11.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel11.setMinimumSize(new java.awt.Dimension(34, 14));
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(34, 14));
        /*jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });*/

        //jLabel12.setText("jLabel12");
        jLabel12.setMaximumSize(new java.awt.Dimension(36, 16));
        jLabel12.setMinimumSize(new java.awt.Dimension(34, 14));
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(34, 14));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel5, jLabel6});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel7, jLabel8});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel9});

        canvas1.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(453, 453, 453))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1207, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setText("Robot:");

        jLabel13.setText("0 ");

        jLabel14.setText("Tape:");

        jLabel15.setText("");

        jMenu1.setMnemonic('F');
        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open Challenge");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Load Solution");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Quit");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setMnemonic('G');
        jMenu2.setText("Game");

        jMenuItem4.setText("Go");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Pause");
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Faster");
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setMnemonic('H');
        jMenu3.setText("Help");

        jMenuItem7.setMnemonic('H');
        jMenuItem7.setText("Help");
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jLabel14, jLabel15, jLabel2});

        getAccessibleContext().setAccessibleName("myframe");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    JFrame jWindow;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                RobotFactory panel = new RobotFactory();
                panel.setVisible(true);

                HashMap<JLabel, Integer> label_state = new HashMap<JLabel, Integer>();
                label_state.put(panel.labels[2], 0);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private my_canvas canvas1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    //int jlabel3_state=0;
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if (label_state.get(source) == 0) {
            if (source.getName() == "jLabel11") {
                if (challenge_file == null) {
                    JOptionPane.showMessageDialog(null, "Challenge File Not Loaded");
                } else {
                    reset_labelState();
                    active_label = (JLabel) source;
                    label_state.put(source, 1);
                    try {
                        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/pause.jpg"));
                        jLabel11.setIcon(icon);
                    } catch (Exception et) {
                        JOptionPane.showMessageDialog(null, et.toString());
                    }
                }

            } else {
                reset_labelState();
                active_label = (JLabel) source;
                label_state.put(source, 1);
            }
            source.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
        } else {
            active_label = null;
            label_state.put(source, 0);
            if (source.getName() == "jLabel11") {
                try {
                    ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/play.jpg"));
                    jLabel11.setIcon(icon);
                } catch (Exception et) {
                    JOptionPane.showMessageDialog(null, et.toString());
                }
            }
            source.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLUE));
        }

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    public void stateChanged(ChangeEvent e) {
    }
}
