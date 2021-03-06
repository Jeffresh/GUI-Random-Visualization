import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
     * GuiTemplate.java
     * Purpose: this program
     * implements a Gui template that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */



public class GuiRandomNumberDisplay extends Frame implements ActionListener, FocusListener {

    private static final long serialVersionUID = 1L;

    /** */
    private static JMenuBar nav_bar;
    private static GenericChart chart;
    private static String gui_title = "Random Generator Display";
    private static String[] engine_generator_names = {
            "generator261a", "generator261b", "generator262", "generator263", "generatorFishmanAndMore1",
            "generatorFishmanAndMore2", "generatorRandu","generatorCombinedWXY",
    };


    @NotNull
    private JMenuBar createTopBar(Color color, Dimension dimension) {

        JMenuBar top_bar = new JMenuBar();
        top_bar.setOpaque(true);
        top_bar.setBackground(color);
        top_bar.setPreferredSize(dimension);

        return top_bar;
    }

    @NotNull
    private JMenu createMenu(@NotNull String menu_name, Font font, Color color) {

        JMenu menu = new JMenu(menu_name);
        menu.setFont(font);
        menu.setForeground(color);
        return menu;
    }

    @NotNull
    private  Map<String, JMenu> createMenusItems(@NotNull Map<String,String[]> items, Color color, Font font) {

        JMenuItem item;
        JMenu m;
        Map<String, JMenu> menus = new HashMap<>();

        for(Map.Entry<String,String[]> menu: items.entrySet()){
            String menu_name = menu.getKey();
            m = createMenu(menu_name, font , color);
            for(String item_name :menu.getValue()) {
                item = new JMenuItem(item_name);
                item.setFont(font);
                item.addActionListener(this);
                m.add(item);
            }
            menus.put(menu_name, m);
        }

        return menus;

    }

    private JMenuBar createNavBar() {

        Font menu_font = new Font("Dialog", Font.PLAIN, 20);
        Color menu_font_color = new Color(168, 168, 168);
        Color navbar_color = new Color(0,0,0);
        Dimension navbar_dimension = new Dimension(200,40);
        Map<String, String[] > menu_items = new HashMap<>();

        menu_items.put("File", new String[]{"Random Generators"});
        menu_items.put("Plot", new String[]{"Chart 1"});
        menu_items.put("Help", new String[]{"Help message"});
        menu_items.put("About", new String[]{"About message"});

        nav_bar = createTopBar(navbar_color, navbar_dimension);

        Map<String, JMenu> menus = createMenusItems(menu_items, menu_font_color, menu_font);

        nav_bar.add(menus.get("File"));
        nav_bar.add(menus.get("Plot"));
        nav_bar.add(Box.createHorizontalGlue());
        nav_bar.add(menus.get("Help"));
        nav_bar.add(menus.get("About"));

        return nav_bar;
    }

    private static JTextField textfield_number_randoms, textfield_seed, textfield_engines_list, textfield_scale_image;

    private static JButton initialize_button, start_button, stop_button;

    private static JComboBox<String>  generator_list_combo_box;

    private JSplitPane createTextFields() {

        textfield_number_randoms = new JTextField();
        textfield_number_randoms.setText(Integer.toString(number_of_randoms_value));
        textfield_number_randoms.addFocusListener(this);

        textfield_seed = new JTextField();
        textfield_seed.setText(Integer.toString(seed_value));
        textfield_seed.addFocusListener(this);

        textfield_engines_list = new JTextField();
        textfield_engines_list.setText(Integer.toString(seed_value));
        textfield_engines_list.addFocusListener(this);

        textfield_scale_image = new JTextField();
        textfield_scale_image.setText(Integer.toString(scale_value));
        textfield_scale_image.addFocusListener(this);

        JLabel label_number_randoms = new JLabel("Number of randoms: ");
        label_number_randoms.setLabelFor(textfield_number_randoms);

        JLabel label_seed = new JLabel("Seed: ");
        label_seed.setLabelFor(textfield_seed);

        JLabel label_scale = new JLabel("Scale image: ");
        label_seed.setLabelFor(textfield_scale_image);

        JLabel label_engines = new JLabel("Random Engines");
        label_engines.setLabelFor(textfield_engines_list);

        //Lay out the text controls and the labels
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        textControlsPane.setLayout(gridbag);
        textControlsPane.setPreferredSize(new Dimension(100, 900));
        textControlsPane.setMinimumSize(new Dimension(100, 900));

        JLabel[] labels = {label_number_randoms, label_seed, label_scale, label_engines};

        JTextField[] textFields = {textfield_number_randoms, textfield_seed, textfield_scale_image};
        generator_list_combo_box = new JComboBox<>(engine_generator_names);
        generator_list_combo_box.addFocusListener(this);

        JComboBox[] combo_box_list = {generator_list_combo_box};


        addLabelTextRows(labels, textFields, combo_box_list, textControlsPane);

        textControlsPane.setBorder(
                                   BorderFactory.createCompoundBorder(
                                                                      BorderFactory.createTitledBorder("Variables"),
                                                                      BorderFactory.createEmptyBorder(5,5,5,5)));
        initialize_button = new JButton("Initialize");
        initialize_button.addActionListener(this);

        start_button = new JButton("Start");
        start_button.addActionListener(this);

        stop_button = new JButton("Stop");
        stop_button.addActionListener(this);

        JPanel botonesPane = new JPanel();

        botonesPane.add(initialize_button,BorderLayout.CENTER);
        botonesPane.add(start_button,BorderLayout.CENTER);
        botonesPane.add(stop_button,BorderLayout.CENTER);

        botonesPane.setPreferredSize(new Dimension(100, 20));
        botonesPane.setMaximumSize(new Dimension(100, 20));
        botonesPane.setMinimumSize(new Dimension(100, 20));

        botonesPane.setBorder(
                  BorderFactory.createCompoundBorder(
                                                     BorderFactory.createTitledBorder("Control"),
                                                     BorderFactory.createEmptyBorder(5,5,5,5)));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              textControlsPane,
                                              botonesPane);
        splitPane.setMaximumSize(new Dimension(800,800));
        splitPane.setMinimumSize(new Dimension(800,800));
        textControlsPane.setMaximumSize(new Dimension(800,800));
        textControlsPane.setMinimumSize(new Dimension(800,800));

        splitPane.setOneTouchExpandable(true);

        return splitPane;   
    }    
 

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields,
                                     JComboBox<String>[] combo_box_list,
                                  Container container){

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels-1; i++){

        	labels[i].setFont(new Font(null, Font.PLAIN,20));
        	textFields[i].setFont(new Font(null, Font.PLAIN,20));
        	textFields[i].setHorizontalAlignment(JTextField.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 1.0;                       //reset to default
//            c.gridheight = 50;
            container.add(labels[i], c);

 
            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.NONE;
            c.weightx = 1.0;
            textFields[i].setColumns(5);
            container.add(textFields[i], c);
        }

        labels[numLabels-1].setFont(new Font(null, Font.PLAIN,20));
        combo_box_list[0].setFont(new Font(null, Font.PLAIN,20));
        ((JLabel)combo_box_list[0].getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
        c.fill = GridBagConstraints.NONE;      //reset to default
        c.weightx = 1.0;                       //reset to default
//            c.gridheight = 19;
        container.add(labels[numLabels-1], c);

        c.gridwidth = GridBagConstraints.REMAINDER;     //end row
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1.0;
//        combo_box_list[0].setColumns(3);
        container.add(combo_box_list[0], c);

        
    }


    private static  void createAndShowGUI(){

        JFrame frame = new JFrame(gui_title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setJMenuBar(new GuiRandomNumberDisplay().createNavBar());

        int xMax = 1000;
        int yMax = 1000;
        caClassTemplate = new Canvas(xMax, yMax);
        caClassTemplate.setPreferredSize(new Dimension(1000, 1000));


        JSplitPane buttons = new GuiRandomNumberDisplay().createTextFields();
        chart = new GenericChart();
//        JSplitPane tools = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttons, chart.chartpanel);

        JSplitPane window = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, caClassTemplate, buttons);
        window.setOneTouchExpandable(true);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setContentPane(window);
        frame.validate();
        frame.repaint();


    }

    private static SwingWorker<Void, GuiRandomNumberDisplay> worker;

    private static Canvas caClassTemplate;

    private static int number_of_randoms_value = 5 ;
    private static int seed_value = 1;
    private static String combobox_value = "generator261a";
    private static int scale_value = 1;

    private static JLabel lnumeric_var_value;
    private static JLabel lstring_var_value;
    private static int task = 0;


    public void showURI(String uri){
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void deleteCanvasLabels(){
        if(lstring_var_value!=null) caClassTemplate.remove(lstring_var_value);
        if(lnumeric_var_value!=null) caClassTemplate.remove(lnumeric_var_value);

    }

    public void actionPerformed(@NotNull ActionEvent e) {

        if(e.getSource() == nav_bar.getMenu(0).getItem(0)) {
//      frame.remove(window);
            task = 0;
            deleteCanvasLabels();
            Canvas.objectNV.initializer(seed_value, number_of_randoms_value, combobox_value);
            caClassTemplate.revalidate();
            caClassTemplate.repaint();
        }

        if(e.getSource() == nav_bar.getMenu(1).getItem(0)){

            worker = new SwingWorker<Void, GuiRandomNumberDisplay>() {
                @Override
                protected Void doInBackground() {
                    try{
                        RealTimeChart realTimeChart = new RealTimeChart();

                        realTimeChart.show();

                    }
                    catch(Exception ex){System.out.println("Worker exception");}
                    return null;
                }
            };
            worker.execute();
        }

        if(e.getSource()==nav_bar.getMenu(3).getItem(0)) {
            String uri = "https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html";
            showURI(uri);
        }

        if(e.getSource()==nav_bar.getMenu(4).getItem(0)) {
            String uri = "https://github.com/Jeffresh";
            showURI(uri);
        }
        
        if(e.getSource()== stop_button) {
            worker.cancel(true);
            worker.cancel(false);
            RandomDisplayTask.stop();
        }

        if(e.getSource() == initialize_button) {

            deleteCanvasLabels();
            Canvas.objectNV = new RandomDisplayTask();
            Canvas.objectNV.plug(caClassTemplate);
            Canvas.objectNV.initializer(seed_value , number_of_randoms_value, combobox_value);
            Canvas.scale_rate = scale_value;

            lnumeric_var_value = new JLabel(Integer.toString(number_of_randoms_value));
            lnumeric_var_value.setFont(new Font(null, Font.PLAIN,50));
            caClassTemplate.add(lnumeric_var_value);


            lstring_var_value = new JLabel(Integer.toString(seed_value));
            lstring_var_value.setFont(new Font(null, Font.PLAIN,50));
            caClassTemplate.add(lstring_var_value);


            caClassTemplate.validate();
            caClassTemplate.repaint();

        }

        if(e.getSource()== start_button) {
            worker = new SwingWorker<Void, GuiRandomNumberDisplay>()
            {
                @Override
                protected Void doInBackground() {
                    try{
                        deleteCanvasLabels();
                        Canvas.objectNV.computeClassNV(1);
                    }
                    catch(Exception ex){System.out.println("Worker exception");}
                    return null;
                }
            };
            worker.execute();
        }

    }

    public void focusGained(FocusEvent e) {
    	//nothing
	}
	public void focusLost(FocusEvent e) {
            String nump;

            try {

                if (e.getSource() == textfield_number_randoms) {
                    nump = textfield_number_randoms.getText();
                    if (!nump.equals("")) {
                        number_of_randoms_value = Integer.parseInt(nump);
                        if (number_of_randoms_value < 0) {
                            number_of_randoms_value = 0;
                            throw new Exception("Invalid Number");

                        }
                    }
                }
            }
            catch (Exception ex){
                String message = "\"Invalid Number\"\n"
                        + "Enter a number between 0 and 1000\n"
                        + " setted 0 by default";
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                        JOptionPane.ERROR_MESSAGE);

            }

            if(e.getSource() == textfield_seed) {
                nump = textfield_seed.getText();
                seed_value = Integer.parseInt(nump);

            }

            if(e.getSource() == generator_list_combo_box)
            {
                JComboBox<String> cb = (JComboBox<String>)e.getSource();
                String op = (String)cb.getSelectedItem();
                assert op != null;
    //            op = op.toLowerCase();
                combobox_value = op;
            }
            if(e.getSource() == textfield_scale_image)
            {
                nump = textfield_scale_image.getText();
                scale_value = Integer.parseInt(nump);
            }

    }
    
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.
                SwingUtilities.
                invokeLater(GuiRandomNumberDisplay::createAndShowGUI);
    }
}

