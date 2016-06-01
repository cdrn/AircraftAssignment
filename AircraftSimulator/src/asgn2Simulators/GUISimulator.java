/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 *
 */
package asgn2Simulators;

import asgn2Aircraft.AircraftException;

import asgn2Simulators.Constants;

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

import asgn2Passengers.PassengerException;


/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = -7031008862559936404L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    private JPanel pnlConsole;
    private JPanel pnlErrors;
    private JPanel pnlPassengerSeeds;
    private JPanel pnlSimulation;
    private JPanel pnlButtons;


    private JTextArea outputLabel;

    private JButton btnRun;
    private JButton btnShowGraph;

    private JLabel lblSIMULATION;
    private JLabel lblRandSeed;
    private JTextField randSeedTextField;
    private JLabel lblDailyMean;
    private JTextField dailyMeanTextField;
    private JLabel lblQueueSeed;
    private JTextField queueSeedTextField;
    private JLabel lblCancellation;
    private JTextField cancellationTextField;

    private JLabel lblPASSENGERSEEDS;
    private JLabel lblFirst;
    private JTextField firstTextField;
    private JLabel lblBusiness;
    private JTextField businessTextField;
    private JLabel lblPremium;
    private JTextField premiumTextField;
    private JLabel lblEconomy;
    private JTextField economyTextField;

    private JLabel lblExceptionHeader;
    private JLabel lblException;


    //setup a simulator and log for the sim code
    private Simulator sim;
    private Log log;


    /**
     * @param arg0
     * @throws HeadlessException
     */
    public GUISimulator(String arg0) throws HeadlessException {
        super(arg0);
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        //use a default border layout for the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        pnlConsole = createPanel(Color.pink);
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

    private void addToPanel(Component component, JPanel jp, GridBagConstraints constr) {
        jp.add(component, constr);
    }

    private JLabel createOutputConsole() {
        JLabel oc = new JLabel();
        oc.setBackground(Color.RED);
        //oc.setPreferredSize(new Dimension(600, 450));
        return oc;
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
        outputLabel = new JTextArea();
        //try to add a scrollbar??
        JScrollPane scroll = new JScrollPane (outputLabel);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        pnlConsole.add(scroll);
        //
        GridBagLayout layout = new GridBagLayout();
        outputLabel.setLayout(layout);
        pnlConsole.add(outputLabel, BorderLayout.CENTER);
        pnlConsole.setPreferredSize(new Dimension(250, 100));
    }

    private void createExceptionPanel() {

        GridBagConstraints constraints = new GridBagConstraints();
        pnlErrors = createPanel(Color.CYAN);
        GridBagLayout layout = new GridBagLayout();
        pnlErrors.setLayout(layout);
        this.getContentPane().add(pnlErrors, BorderLayout.SOUTH);
        pnlErrors.setPreferredSize(new Dimension(250, 100));

    }

    private void createButtonPanel() {


        //basic setup of the wrapper panel for the buttons
        GridBagConstraints constraints = new GridBagConstraints();
        pnlButtons = createPanel(Color.GREEN);
        GridBagLayout layout = new GridBagLayout();
        pnlButtons.setLayout(layout);
        this.getContentPane().add(pnlButtons, BorderLayout.EAST);
        pnlButtons.setPreferredSize(new Dimension(250, 100));

        //create the two buttons we want
        btnRun = new JButton("Run");
        btnShowGraph = new JButton("show the graph");


        //adds the buttons to the button panel
        addToPanel(btnRun, pnlButtons, constraints);
        addToPanel(btnShowGraph, pnlButtons, constraints);

        //Adds both buttons to the event listener in this class
        btnRun.addActionListener(this);
        btnShowGraph.addActionListener(this);

    }

    private void createPassengerSeedsPanel() {
        //Set up a gridbagconstraints object
        GridBagConstraints constraints = new GridBagConstraints();

        //Set up the new elements for the button panel
        lblFirst = new JLabel("First");
        firstTextField = new JTextField(5);

        lblEconomy = new JLabel("Business");
        economyTextField = new JTextField(5);

        lblBusiness = new JLabel("Queue Seed");
        businessTextField = new JTextField(5);

        lblPremium = new JLabel("Cancellation");
        premiumTextField = new JTextField(5);

        pnlPassengerSeeds = createPanel(Color.BLUE);
        GridBagLayout layout = new GridBagLayout();
        pnlPassengerSeeds.setLayout(layout);

        this.getContentPane().add(pnlPassengerSeeds, BorderLayout.CENTER);
        pnlPassengerSeeds.setPreferredSize(new Dimension(250, 100));


        //Set the button and label for randseed
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        addToPanel(lblFirst, pnlPassengerSeeds, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(firstTextField, pnlPassengerSeeds, constraints);

        //setup button and label for dailyMean
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        addToPanel(lblBusiness, pnlPassengerSeeds, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(businessTextField, pnlPassengerSeeds, constraints);


        //button and label for QueueSeed
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 2;
        addToPanel(lblPremium, pnlPassengerSeeds, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(premiumTextField, pnlPassengerSeeds, constraints);

        //button and label for Cancellation
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 3;
        addToPanel(lblEconomy, pnlPassengerSeeds, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(economyTextField, pnlPassengerSeeds, constraints);


    }

    private void createSimulationPanel() {
        //Set up a gridbagconstraints object
        GridBagConstraints constraints = new GridBagConstraints();

        //Set up the new elements for the button panel
        lblRandSeed = new JLabel("Rand Seed");
        randSeedTextField = new JTextField(5);

        lblDailyMean = new JLabel("Daily Mean");
        dailyMeanTextField = new JTextField(5);

        lblQueueSeed = new JLabel("Queue Seed");
        queueSeedTextField = new JTextField(5);

        lblCancellation = new JLabel("Cancellation");
        cancellationTextField = new JTextField(5);

        pnlSimulation = createPanel(Color.RED);
        GridBagLayout layout = new GridBagLayout();
        pnlSimulation.setLayout(layout);
        this.getContentPane().add(pnlSimulation, BorderLayout.WEST);
        pnlSimulation.setPreferredSize(new Dimension(250, 100));


        //Set the button and label for randseed
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        addToPanel(lblRandSeed, pnlSimulation, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(randSeedTextField, pnlSimulation, constraints);


        //setup button and label fordailyMean
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        addToPanel(lblDailyMean, pnlSimulation, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(dailyMeanTextField, pnlSimulation, constraints);


        //button and label for QueueSeed
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 2;
        addToPanel(lblQueueSeed, pnlSimulation, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(queueSeedTextField, pnlSimulation, constraints);

        //button and label for Cancellation
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.ipady = 1;      //make this component tall
        constraints.weightx = 5;
        constraints.weighty = 1;
        constraints.ipadx = 3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 3;
        addToPanel(lblCancellation, pnlSimulation, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(cancellationTextField, pnlSimulation, constraints);

    }

    public void runSimulation() throws AircraftException, PassengerException, SimulationException, IOException {

        sim = new Simulator();
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
            //Log progress
            this.log.logQREntries(time, sim);
            this.log.logEntry(time, this.sim);
            boolean flying = (time >= Constants.FIRST_FLIGHT);
            outputLabel.append(sim.getSummary(time, flying));
        }
        this.sim.finaliseQueuedAndCancelledPassengers(Constants.DURATION);
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
        double sdBookings = Double.parseDouble(args[3]);
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


    //Event listener for GUI buttons and forms
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        //action listener, if the button is enabled, do thing.

        if (btnRun.getModel().isArmed()) {
            try {
                runSimulation();
            } catch (AircraftException e1) {
                e1.printStackTrace();
            } catch (PassengerException e1) {
                e1.printStackTrace();
            } catch (SimulationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //outputLabel.append(String.valueOf(sim.getDailyBookings()));
        }

        if (btnShowGraph.getModel().isArmed()) {
            outputLabel.setText("the button has been clicked dude");
            pnlErrors.setBackground(Color.pink);

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
     * @param args
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));

    }



}
