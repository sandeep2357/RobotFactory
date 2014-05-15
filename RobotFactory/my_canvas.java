/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Sandeep
 */
public class my_canvas extends Canvas {

    public RobotFactory this_robot;
    public JLabel current_label;
    URL imagesrc = null;
    int click_x = 0;
    int click_y = 0;
    Point position = null;
    Image image;
    HashMap<Point, String> image_positions = new HashMap<Point, String>();
    HashMap<Point, String> label_names = new HashMap<Point, String>();
    public Robot my_robot = null;
    public int current_count = 0;
    public String tape_position;
    public int pass_count = 0;
    public int fail_count = 0;

    public void add_images_to_canvas(Graphics g, HashMap positions) {

        Set images = positions.entrySet();
        Iterator i = images.iterator();
        Point current_position;
        String image_url;
        ImageIcon icon;
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();

            current_position = (Point) me.getKey();
            image_url = (String) me.getValue();

            icon = new javax.swing.ImageIcon(getClass().getResource(image_url));
            image = icon.getImage();
            g.drawImage(image, current_position.x, current_position.y, 45, 45, this);
        }
    }

    public String getLabel(Point p, String movement) {
        String label_name;

        int loc_x = 0;
        int loc_y = 0;

        if (movement == "left" || movement == "top") {
            loc_x = ((p.x + 44) / 45) * 45;
            loc_y = ((p.y + 44) / 45) * 45;
        } else if (movement == "down" || movement == "right") {
            loc_x = (p.x / 45) * 45;
            loc_y = (p.y / 45) * 45;
        }
        Point mod_location = new Point(loc_x, loc_y);

        label_name = (String) label_names.get(mod_location);
        return label_name;
    }
    my_runnable robot_thread = null;
    public Runnable animate_canvas;

    public Point rounded(Point p) {
        int loc_x;
        int loc_y;

        loc_x = (p.x / 45) * 45;
        loc_y = (p.y / 45) * 45;
        Point mod_location = new Point(loc_x, loc_y);

        return mod_location;
    }

    my_canvas(RobotFactory r) {

        this_robot = r;
        image_positions.put(new Point(45 * 6, 0), "resources/images/source.jpg");
        label_names.put(new Point(45 * 6, 0), "source");

        image_positions.put(new Point(45 * 6, 12 * 45), "resources/images/sink.jpg");
        label_names.put(new Point(45 * 6, 12 * 45), "sink");

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {

                position = e.getPoint();
                position.x = (position.x / 45) * 45;
                position.y = (position.y / 45) * 45;

                JLabel current_label = this_robot.active_label;
                if (!(position.x == 270 && position.y == 0) && !(position.y == 540 && position.x == 270)) {
                    if (current_label.getName() == "jLabel3") {
                        image_positions.put(position, "resources/images/conveyor_down.jpg");
                        label_names.put(position, "jLabel3");
                    } else if (current_label.getName() == "jLabel4") {
                        image_positions.put(position, "resources/images/conveyor_left.jpg");
                        label_names.put(position, "jLabel4");
                    } else if (current_label.getName() == "jLabel5") {
                        image_positions.put(position, "resources/images/conveyor_up.jpg");
                        label_names.put(position, "jLabel5");
                    } else if (current_label.getName() == "jLabel6") {
                        image_positions.put(position, "resources/images/conveyor_right.jpg");
                        label_names.put(position, "jLabel6");
                    } else if (current_label.getName() == "jLabel7") {
                        image_positions.put(position, "resources/images/switch_null_down.jpg");
                        label_names.put(position, "jLabel7");
                    } else if (current_label.getName() == "jLabel8") {
                        image_positions.put(position, "resources/images/switch_null_left.jpg");
                        label_names.put(position, "jLabel8");
                    } else if (current_label.getName() == "jLabel9") {
                        image_positions.put(position, "resources/images/switch_null_up.jpg");
                        label_names.put(position, "jLabel9");
                    } else if (current_label.getName() == "jLabel10") {
                        image_positions.put(position, "resources/images/switch_null_right.jpg");
                        label_names.put(position, "jLabel10");
                    } else if (current_label.getName() == "jLabel12") {
                        //System.out.println("Working");
                        image_positions.remove(position);
                        label_names.remove(position);
                    }
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
        });



        //animate_canvas=new my_runnable();


        robot_thread = new my_runnable();
        robot_thread.start();
    }

    public class my_runnable extends Thread {

        private boolean blinker;

        my_runnable() {
            blinker = true;
        }

        public void terminate() {
            blinker = false;
        }

        public void run() {
            while (blinker) {
                try {

                    Thread.sleep(this_robot.animation_speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(my_canvas.class.getName()).log(Level.SEVERE, null, ex);
                }

                repaint();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        int n = 13;
        int width = this.getSize().width;
        int height = this.getSize().height;
        float width_step = 45;
        float height_step = 45;

        for (int i = 0; i <= n + 1; i++) {
            g.drawLine((int) (width_step * i), 0, (int) (width_step * i), height);
        }
        for (int i = 0; i <= n + 1; i++) {
            g.drawLine(0, (int) (height_step * i), width, (int) (height_step * i));
        }
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/source.jpg"));
        image = icon.getImage();
        g.drawImage(image, 45 * 6, 0, 45, 45, this);
        icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/sink.jpg"));
        image = icon.getImage();
        g.drawImage(image, 45 * 6, 12 * 45, 45, 45, this);

        if (this_robot.solution_file != null && this_robot.sol_file == 1) {
            this_robot.sol_file = 0;
            for (int i = 0; i < this_robot.sol_rows; i++) {
                for (int j = 0; j < this_robot.sol_columns; j++) {
                    if (this_robot.sol_data[i][j] == '1') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/source.jpg");
                        label_names.put(position, "source");
                    } else if (this_robot.sol_data[i][j] == '2') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/sink.jpg");
                        label_names.put(position, "sink");
                    } else if (this_robot.sol_data[i][j] == '3') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/conveyor_up.jpg");
                        label_names.put(position, "jLabel5");
                    } else if (this_robot.sol_data[i][j] == '4') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/conveyor_down.jpg");
                        label_names.put(position, "jLabel3");
                    } else if (this_robot.sol_data[i][j] == '5') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/conveyor_left.jpg");
                        label_names.put(position, "jLabel4");
                    } else if (this_robot.sol_data[i][j] == '6') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/conveyor_right.jpg");
                        label_names.put(position, "jLabel6");
                    } else if (this_robot.sol_data[i][j] == '7') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/switch_null_left.jpg");
                        label_names.put(position, "jLabel8");
                    } else if (this_robot.sol_data[i][j] == '8') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/switch_null_down.jpg");
                        label_names.put(position, "jLabel7");
                    } else if (this_robot.sol_data[i][j] == '9') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/switch_null_up.jpg");
                        label_names.put(position, "jLabel9");
                    } else if (this_robot.sol_data[i][j] == 'd') {
                        position = new Point(j * 45, i * 45);
                        image_positions.put(position, "resources/images/switch_null_right.jpg");
                        label_names.put(position, "jLabel10");
                    }
                }
            }
        }
        add_images_to_canvas(g, image_positions); // conveyer loaded

        if (this_robot.active_label != null && (this_robot.active_label.getName() == "jLabel11")) {
            if (this_robot.label_state.get(this_robot.jLabel11) == 1 && current_count <= (this_robot.input_count)) {
                if (my_robot == null) {
                    create_robot();
                }
                if (my_robot != null) {
                    if ((my_robot.step_val / 45) < my_robot.tape_length) {
                        this_robot.jLabel15.setText(my_robot.inp_tape.substring(0, (my_robot.step_val) / 45) + ">" + my_robot.inp_tape.substring((my_robot.step_val) / 45, my_robot.tape_length));
                    }
                    try {
                        my_robot.update_step(this);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(my_canvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.drawImage(my_robot.image, my_robot.location.x, my_robot.location.y, 45, 45, this);
                }
            } else if (this_robot.label_state.get(this_robot.jLabel11) == 1 && current_count > (this_robot.input_count)) {
                try {
                    this.kill_robot(this, null);
                } catch (InterruptedException ex) {
                    Logger.getLogger(my_canvas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (my_robot != null) {
                    if ((my_robot.step_val / 45) < my_robot.tape_length) {
                        this_robot.jLabel15.setText(my_robot.inp_tape.substring(0, (my_robot.step_val) / 45) + ">" + my_robot.inp_tape.substring((my_robot.step_val) / 45, my_robot.tape_length));
                    }
                    g.drawImage(my_robot.image, my_robot.location.x, my_robot.location.y, 45, 45, this);

                }

            }
        }
    }

    public void create_robot() {
        if (current_count < this_robot.input_count) {
            my_robot = new Robot(this_robot.results[current_count], this_robot.tapes[current_count]);
            current_count++;
            this_robot.jLabel13.setText(Integer.toString(current_count));
        }
    }

    void kill_robot(my_canvas c, String source) throws InterruptedException {
        robot_thread.terminate();

        if (source == "sink") {
            if (my_robot.inp_result.charAt(0) == 'F') {
                JOptionPane.showMessageDialog(this_robot.jWindow, "Robot Failed");
                c.fail_count = 1;
            } else if (my_robot.inp_result.charAt(0) == 'P') {
                JOptionPane.showMessageDialog(this_robot.jWindow, "Robot Passed");
                c.pass_count++;
            }
        } else if (source == null) {
            if (my_robot.inp_result.charAt(0) == 'P') {
                JOptionPane.showMessageDialog(this_robot.jWindow, "Robot Failed");
                c.fail_count = 1;
            } else if (my_robot.inp_result.charAt(0) == 'F') {
                JOptionPane.showMessageDialog(this_robot.jWindow, "Robot Passed");
                c.pass_count++;
            }
        }
        my_robot = null;
        if (current_count < this_robot.input_count) {
            if (fail_count == 1) {
                fail_count = 0;
                this_robot.active_label = null;
                (this_robot.label_state).put(this_robot.jLabel11, 0);
                current_count = 0;
                this_robot.jLabel13.setText(Integer.toString(current_count));
                this_robot.jLabel15.setText("");
                try {
                    ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/play.jpg"));
                    this_robot.jLabel11.setIcon(icon);
                } catch (Exception et) {
                    JOptionPane.showMessageDialog(null, et.toString());
                }
            } else {
                create_robot();
            }

        } else if (current_count >= this_robot.input_count) {
            if (pass_count == this_robot.input_count) {
                JOptionPane.showMessageDialog(this_robot.jWindow, "All Robots Passed");
            }
            this_robot.active_label = null;
            (this_robot.label_state).put(this_robot.jLabel11, 0);
            current_count = 0;
            this_robot.jLabel13.setText(Integer.toString(current_count));
            this_robot.jLabel15.setText("");
            try {
                ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/play.jpg"));
                this_robot.jLabel11.setIcon(icon);
            } catch (Exception et) {
                JOptionPane.showMessageDialog(null, et.toString());
            }
        }

        robot_thread = new my_runnable();
        robot_thread.start();
        /*        robot_thread = new Thread(new Runnable() {

        public void run() {
        while (true) {
        try {
        Thread.sleep(this_robot.animation_speed);
        } catch (InterruptedException ex) {
        Logger.getLogger(my_canvas.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
        }
        }
        });
        robot_thread.start();
        }*/
    }
}

class Robot {

    boolean visible = true;
    boolean destroyed = false;
    boolean passed = false;
    Point location = new Point(6 * 45, 0);
    int count;
    String movement = "down";
    String inp_result;
    String inp_tape;
    int tape_length;
    int step_val = 0;
    Image image;

    Robot(String result, String tape) {
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("resources/images/robo.png"));
        image = icon.getImage();
        inp_result = result;
        inp_tape = tape;
        tape_length = inp_tape.length();
    }

    public void set_color() {
    }

    public boolean update_step(my_canvas c) throws InterruptedException {
        if (!visible) {
            return false;
    
        }
        char cur_value = ' ';
        if ((step_val / 45) < tape_length) {
            cur_value = inp_tape.charAt(step_val / 45);
        }
        if (location.y < 45) {
            location.y = (int) ((int) location.y + 5);
            return true;
        }
        if (c.getLabel(location, movement) != null) {
            visible = true;
        }
        if ((c.getLabel(location, movement) == "jLabel3")) {
            location.y = (int) (location.y + 5);
            movement = "down";
        } else if (c.getLabel(location, movement) == "jLabel4") {
            movement = "left";
            location.x = (int) (location.x - 5);
        } else if (c.getLabel(location, movement) == "jLabel5") {
            movement = "top";
            location.y = (int) (location.y - 5);
        } else if (c.getLabel(location, movement) == "jLabel6") {
            movement = "right";
            location.x = (int) (location.x + 5);
        } else if (c.getLabel(location, movement) == "jLabel7") {
            if (cur_value == 'B') {
                movement = "left";
                location.x = (int) (location.x - 5);
            } else if (cur_value == 'R') {
                movement = "right";
                location.x = (int) (location.x + 5);
            } else if (cur_value == ' ') {
                movement = "down";
                location.y = (int) (location.y + 5);
            }
            step_val += 5;
        } else if (c.getLabel(location, movement) == "jLabel8") {
            if (cur_value == 'B') {
                movement = "top";
                location.y = (int) (location.y - 5);
            } else if (cur_value == 'R') {
                movement = "down";
                location.y = (int) (location.y + 5);
            } else if (cur_value == ' ') {
                movement = "left";
                location.x = (int) (location.x - 5);
            }
            step_val += 5;
        } else if (c.getLabel(location, movement) == "jLabel9") {
            if (cur_value == 'B') {
                movement = "right";
                location.x = (int) (location.x + 5);
            } else if (cur_value == 'R') {
                movement = "left";
                location.x = (int) (location.x - 5);
            } else if (cur_value == ' ') {
                movement = "top";
                location.y = (int) (location.y - 5);
            }
            step_val += 5;
        } else if (c.getLabel(location, movement) == "jLabel10") {
            if (cur_value == 'B') {
                movement = "down";
                location.y = (int) (location.y + 5);
            } else if (cur_value == 'R') {
                movement = "top";
                location.y = (int) (location.y - 5);
            } else if (cur_value == ' ') {
                movement = "right";
                location.x = (int) (location.x + 5);
            }
            step_val += 5;
        } else if (c.getLabel(location, movement) == "sink") {
            visible = false;
            //JOptionPane.showMessageDialog(null, "Robot Failed");
            //System.out.println("Failed dialog closed ");
            c.kill_robot(c, "sink");
            return false;
        } else if (c.getLabel(location, movement) == null) {
            visible = false;
            //JOptionPane.showMessageDialog(null, "Robot Failed");
            //System.out.println("Failed dialog closed ");
            c.kill_robot(c, null);
            return false;
        }
        return true;
    }
}
