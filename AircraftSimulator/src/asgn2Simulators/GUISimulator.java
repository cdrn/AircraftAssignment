/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 *
 */
package asgn2Simulators;

import asgn2Passengers.Business;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.Border;


/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
    private static final long serialVersionUID = -7031008862559936404L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;

    private JPanel pnlConsole;
	private JPanel pnlErrors;
    private JPanel pnlPassengerSeeds;
    private JPanel pnlSimulation;
    private JPanel pnlButtons;

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



	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
	}

	private void createGUI(){
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
		createSimulationPanel();
        createPassengerSeedsPanel();
        createButtonPanel();
        createExceptionPanel();

		this.getContentPane().add(pnlConsole,BorderLayout.NORTH);
		pnlConsole.setPreferredSize(new Dimension(800, 300));

        repaint();
        this.setVisible(true);

	}

	private void addToPanel(Component component, JPanel jp, GridBagConstraints constr) {
		jp.add(component, constr);
	}

    private JLabel createOutputConsole(){
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

    private void createExceptionPanel(){

        GridBagConstraints constraints = new GridBagConstraints();
        pnlErrors = createPanel(Color.CYAN);
        GridBagLayout layout = new GridBagLayout();
        pnlErrors.setLayout(layout);
        this.getContentPane().add(pnlErrors, BorderLayout.SOUTH);
        pnlErrors.setPreferredSize(new Dimension(250, 100));

    }

    private void createButtonPanel(){

        GridBagConstraints constraints = new GridBagConstraints();
        pnlButtons = createPanel(Color.GREEN);
        GridBagLayout layout = new GridBagLayout();
        pnlButtons.setLayout(layout);
        this.getContentPane().add(pnlButtons, BorderLayout.EAST);
        pnlButtons.setPreferredSize(new Dimension(250, 100));

        btnRun = new JButton("Run");
        btnShowGraph = new JButton("show the graph");

        addToPanel(btnRun, pnlButtons, constraints);
        addToPanel(btnShowGraph, pnlButtons, constraints);




    }

    private void createPassengerSeedsPanel(){
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
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
        constraints.ipadx=3;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 3;
        addToPanel(lblCancellation, pnlSimulation, constraints);

        constraints.gridx = 1;
        constraints.weightx = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        addToPanel(cancellationTextField, pnlSimulation, constraints);

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
