/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 *
 */
package asgn2Simulators;

import asgn2Passengers.PassengerException;
import asgn2Aircraft.AircraftException;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.Border;



/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = -7031008862559936404L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 700;

    private JPanel pnlConsole;
    private JPanel pnlErrors;

    private JTextArea outputLabel;

    private JButton btnRun;
    private JButton btnShowGraph;

    private JTextField randSeedTextField;
    private JTextField dailyMeanTextField;
    private JTextField queueSeedTextField;
    private JTextField cancellationTextField;

    private JTextField firstTextField;
    private JTextField businessTextField;
    private JTextField premiumTextField;
    private JTextField economyTextField;

    private JLabel lblException;


    //setup a simulator and log for the sim code
    private Simulator sim;
    private Log log;


    /**
     * @param arg0 list of arguments contained in GUI components
     * @throws HeadlessException
     */
    public GUISimulator(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setUIFont (new javax.swing.plaf.FontUIResource("Courier New",Font.PLAIN,14));

        //use a default border layout for the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        pnlConsole = createPanel(Color.LIGHT_GRAY);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
        pnlConsole.setBorder(border);

        createOutputPanel();
        createSimulationPanel();
        createPassengerSeedsPanel();
        createButtonPanel();
        createExceptionPanel();
        setDefaultValues();

        this.getContentPane().add(pnlConsole, BorderLayout.NORTH);
        pnlConsole.setPreferredSize(new Dimension(800, 300));

        repaint();
        this.setVisible(true);

    }

    /*
        Simple method to add something to a panel
    */
    private void addToPanel(Component component, JPanel jp, GridBagConstraints constr) {
        jp.add(component, constr);
    }


    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration elementsInGUI = UIManager.getDefaults().keys();
        while (elementsInGUI.hasMoreElements()) {
            Object currentElement = elementsInGUI.nextElement();
            Object value = UIManager.get (currentElement);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (currentElement, f);
        }
    }


    private JPanel createPanel(Color c) {
        JPanel jp = new JPanel();
        jp.setBackground(c);
        return jp;
    }


    //populate the default values by parsing to string from constants file
    private void setDefaultValues(){

        //set the defaults for seed fields
        randSeedTextField.setText(String.valueOf(Constants.DEFAULT_SEED));
        dailyMeanTextField.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
        queueSeedTextField.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
        cancellationTextField.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));

        //set defaults for aircraft passenger probabilities
        firstTextField.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
        premiumTextField.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
        businessTextField.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
        economyTextField.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));

    }


    private void createOutputPanel(){

        outputLabel = new JTextArea(16, 60);
        outputLabel.setEditable(false);

        GridBagLayout layout = new GridBagLayout();
        outputLabel.setLayout(layout);
        JScrollPane scrollPanel = new JScrollPane(outputLabel);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        pnlConsole.add(scrollPanel, BorderLayout.NORTH);
        pnlConsole.add(scrollPanel);
    }


    private void createExceptionPanel() {

        GridBagConstraints constraints = new GridBagConstraints();
        pnlErrors = createPanel(Color.LIGHT_GRAY);
        pnlErrors.setBorder( BorderFactory.createEtchedBorder(Color.WHITE, Color.DARK_GRAY));

        GridBagLayout layout = new GridBagLayout();
        pnlErrors.setLayout(layout);
        this.getContentPane().add(pnlErrors, BorderLayout.SOUTH);
        pnlErrors.setPreferredSize(new Dimension(250, 100));


        lblException = new JLabel("we have encountered an exception my dude");
        lblException.setFont(new Font("Courier New", Font.BOLD, 15));
        lblException.setForeground(Color.RED);
        lblException.setVisible(false);


        //exception label
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,20,0,0);
        addToPanel(lblException, pnlErrors, constraints);




    }

    private void createButtonPanel() {


        //basic setup of the wrapper panel for the buttons
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel pnlButtons = createPanel(Color.LIGHT_GRAY);
        pnlButtons.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.DARK_GRAY));

        GridBagLayout layout = new GridBagLayout();
        pnlButtons.setLayout(layout);
        this.getContentPane().add(pnlButtons, BorderLayout.EAST);
        pnlButtons.setPreferredSize(new Dimension(250, 100));

        //create the two buttons we want
        btnRun = new JButton("Run");
        btnShowGraph = new JButton("show the graph");


        //add run button to pnlButtons
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0,90, 0, 0);
        constraints.ipady = 0;
        constraints.weightx = 5;
        constraints.weighty = 0;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 1;
        constraints.gridy = 1;
        addToPanel(btnRun, pnlButtons, constraints);

        //add graph button to pnlButtons
        constraints.insets = new Insets(30, 45, 0,0);
        constraints.gridy=2;
        addToPanel(btnShowGraph, pnlButtons, constraints);

        //Adds both buttons to the event listener in this class
        btnRun.addActionListener(this);
        btnShowGraph.addActionListener(this);

    }

    private void createPassengerSeedsPanel() {
        //Set up a gridbagconstraints object
        GridBagConstraints constraints = new GridBagConstraints();

        //Set up the new elements for the button panel
        JLabel lblPASSENGERSEEDS = new JLabel("Passenger Seeds");
        lblPASSENGERSEEDS.setFont(new Font("Courier New", Font.BOLD, 16));

        JLabel lblFirst = new JLabel("First");
        firstTextField = new JTextField(5);

        JLabel lblBusiness = new JLabel("Business");
        businessTextField = new JTextField(5);

        JLabel lblPremium = new JLabel("Premium");
        premiumTextField = new JTextField(5);

        JLabel lblEconomy = new JLabel("Economy");
        economyTextField = new JTextField(5);

        JPanel pnlPassengerSeeds = createPanel(Color.LIGHT_GRAY);
        pnlPassengerSeeds.setBorder( BorderFactory.createEtchedBorder(Color.WHITE, Color.DARK_GRAY));
        GridBagLayout layout = new GridBagLayout();
        pnlPassengerSeeds.setLayout(layout);

        this.getContentPane().add(pnlPassengerSeeds, BorderLayout.CENTER);
        pnlPassengerSeeds.setPreferredSize(new Dimension(250, 100));


        //set the label for PassengerSeeds header
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,60,0,0);
        addToPanel(lblPASSENGERSEEDS, pnlPassengerSeeds, constraints);


        //labels
        // anchor - WEST    |   gridx - 0   |   insets - 0,20,0,0
        //first
        constraints.insets = new Insets(0,20,0,0);
        constraints.gridy = 1;
        addToPanel(lblFirst, pnlPassengerSeeds, constraints);
        //business
        constraints.gridy = 2;
        addToPanel(lblBusiness, pnlPassengerSeeds, constraints);
        //premium
        constraints.gridy = 3;
        addToPanel(lblPremium, pnlPassengerSeeds, constraints);
        //economy
        constraints.gridy = 4;
        addToPanel(lblEconomy, pnlPassengerSeeds, constraints);

        //textFields
        //anchor - CENTER   |   gridx - 1   |   insets- 0,100,0,0
        //first
        constraints.insets = new Insets(0,100,0,0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(firstTextField, pnlPassengerSeeds, constraints);
        //business
        constraints.gridy = 2;
        addToPanel(businessTextField, pnlPassengerSeeds, constraints);
        //premium
        constraints.gridy = 3;
        addToPanel(premiumTextField, pnlPassengerSeeds, constraints);
        //economy
        constraints.gridy = 4;
        addToPanel(economyTextField, pnlPassengerSeeds, constraints);
    }

        private void createSimulationPanel() {
        //Set up a gridbagconstraints object
        GridBagConstraints constraints = new GridBagConstraints();

        //Set up the new elements for the button panel
        JLabel lblSIMULATION = new JLabel("Simulation");
        lblSIMULATION.setFont(new Font("Courier New", Font.BOLD, 16));

        JLabel lblRandSeed = new JLabel("Rand Seed");
        randSeedTextField = new JTextField(5);

        JLabel lblDailyMean = new JLabel("Daily Mean");
        dailyMeanTextField = new JTextField(5);

        JLabel lblQueueSeed = new JLabel("Queue Seed");
        queueSeedTextField = new JTextField(5);

        JLabel lblCancellation = new JLabel("Cancellation");
        cancellationTextField = new JTextField(5);

        JPanel pnlSimulation = createPanel(Color.LIGHT_GRAY);
        pnlSimulation.setBorder( BorderFactory.createEtchedBorder(Color.WHITE, Color.DARK_GRAY));


            GridBagLayout layout = new GridBagLayout();
        pnlSimulation.setLayout(layout);
        this.getContentPane().add(pnlSimulation, BorderLayout.WEST);
        pnlSimulation.setPreferredSize(new Dimension(250, 100));


        //Set Label for Simulation header
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 60, 0, 0);
        addToPanel(lblSIMULATION, pnlSimulation, constraints);

        //Set labels
        //anchor - WEST     |   gridx - 0   |   insets - 0,10,0,0
        //randSeed
        constraints.gridy = 1;
        constraints.insets = new Insets(0,10,0,0);
        addToPanel(lblRandSeed, pnlSimulation, constraints);
        //dailyMean
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 2;
        addToPanel(lblDailyMean, pnlSimulation, constraints);
        //queueSeed
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 3;
        addToPanel(lblQueueSeed, pnlSimulation, constraints);
        //cancellation
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 4;
        addToPanel(lblCancellation, pnlSimulation, constraints);


        //Text Fields
        //anchor - CENTER   |   gridx - 1   |   insets - 0,50,0,0
        //randSeed
        constraints.gridy = 1;
        constraints.insets = new Insets(0,50,0,0);
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(randSeedTextField, pnlSimulation, constraints);
        //dailyMean
        constraints.gridy = 2;
        addToPanel(dailyMeanTextField, pnlSimulation, constraints);
        //queueSeed
        constraints.gridy = 3;
        addToPanel(queueSeedTextField, pnlSimulation, constraints);
        //cancellation
        constraints.gridy = 4;
        addToPanel(cancellationTextField, pnlSimulation, constraints);

    }

    private void runSimulation(Simulator paramSim) throws AircraftException, PassengerException, SimulationException, IOException {

        sim = paramSim;
        log = new Log();

        this.sim.createSchedule();
        this.log.initialEntry(this.sim);

        //Main simulation loop
        for (int time = 0; time <= Constants.DURATION; time++) {
            this.sim.resetStatus(time);
            this.sim.generateAndHandleBookings(time);
            this.sim.processNewCancellations(time);
            if (time >= Constants.FIRST_FLIGHT) {
                this.sim.processUpgrades(time);
                this.sim.processQueue(time);
                this.sim.flyPassengers(time);
                this.sim.updateTotalCounts(time);
                this.log.logFlightEntries(time, sim);
            } else {
                this.sim.processQueue(time);
            }


            boolean flying = (time >= Constants.FIRST_FLIGHT);
            outputLabel.append(sim.getSummary(time, flying));
            //Log progress
            this.log.logQREntries(time, sim);
            this.log.logEntry(time, this.sim);
        }
       //this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
        this.log.logQREntries(Constants.DURATION, sim);
        this.log.finalise(this.sim);

        outputLabel.append(sim.finalState());

    }

    /**
     * Helper to process args for Simulator
     *
     * @param args Command line arguments (see usage message)
     * @return new <code>Simulator</code> from the arguments
     * @throws SimulationException if invalid arguments.
     * See {@link asgn2Simulators.Simulator#Simulator(int, int, double, double, double, double, double, double, double)}
     */
    private static Simulator createSimulatorUsingArgs(String[] args) throws SimulationException {
        int seed = Integer.parseInt(args[0]);
        int maxQueueSize = Integer.parseInt(args[1]);
        double meanBookings = Double.parseDouble(args[2]);
        double sdBookings = Constants.DEFAULT_DAILY_BOOKING_SD;
        double firstProb = Double.parseDouble(args[4]);
        double businessProb = Double.parseDouble(args[5]);
        double premiumProb = Double.parseDouble(args[6]);
        double economyProb = Double.parseDouble(args[7]);
        double cancelProb = Double.parseDouble(args[8]);
        return new Simulator(seed,maxQueueSize,meanBookings,sdBookings,firstProb,businessProb,
                premiumProb,economyProb,cancelProb);
    }

    public void SimulationRunner(Simulator sim, Log log) {
        this.sim = sim;
        this.log = log;
    }

    private String[] argsPopulateFromForm(){
        String args[] = new String[9];
        args[0] = randSeedTextField.getText();
        args[1] = queueSeedTextField.getText();
        args[2] = dailyMeanTextField.getText();
        args[3] = "";
        args[4] = firstTextField.getText();
        args[5] = businessTextField.getText();
        args[6] = premiumTextField.getText();
        args[7] = economyTextField.getText();
        args[8] = cancellationTextField.getText();

        return args;
    }

    //Event listener for GUI buttons and forms
    @Override
    public void actionPerformed(ActionEvent e) {

        //action listener, if the button is enabled, do thing.

        if (btnRun.getModel().isArmed()) {
            //clear the console window
            outputLabel.setText("");
            //Grab the user provided arguments
            String args[] = argsPopulateFromForm();


            //try to run the simulation and print with the argument params
            try {
                runSimulation(createSimulatorUsingArgs(args));
                lblException.setVisible(false);

            } catch (AircraftException | PassengerException | SimulationException | IOException e1) {
                e1.printStackTrace();
                lblException.setText(e1.getMessage());
                lblException.setVisible(true);

            }

            //Reset the user input values in the textfields
            setDefaultValues();
        }

        if (btnShowGraph.getModel().isArmed()) {
            outputLabel.setText("the button has been clicked dude");
            pnlErrors.setBackground(Color.LIGHT_GRAY);

        }


    }


    /* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
    @Override
    public void run() {
        createGUI();
    }

    /**
     * @param args required for Swing
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));

    }



}
