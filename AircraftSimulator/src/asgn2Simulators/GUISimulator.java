/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 *
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.Console;
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
	private JPanel pnlButtons;
	private JPanel pnlErrors;

	private JButton btnRun;
	private JButton btnShowGraph;

	private JLabel lblSIMULATION;
	private JLabel lblRandSeed;
	private JLabel lblDailyMean;
	private JLabel lblQueueSeed;
	private JLabel lblCancellation;
	private JLabel lblPASSENGERSEEDS;
	private JLabel lblFirst;
	private JLabel lblBusiness;
	private JLabel lblPremium;
	private JLabel lblEconomy;

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

		pnlConsole = createPanel(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
        pnlConsole.setBorder(border);
		createButtonPanel();

		lblSIMULATION = new JLabel("Simulation");
		lblRandSeed = new JLabel("Rand Seed");



		this.getContentPane().add(pnlConsole,BorderLayout.NORTH);
		pnlConsole.setPreferredSize(new Dimension(800, 200));



        repaint();
        this.setVisible(true);
	}

	private void addToPanel(Component component, JPanel jp, GridBagConstraints constr, int x, int y, int width, int height) {
		constr.gridx = x;
		constr.gridy = y;
		constr.gridwidth = width;
		constr.gridheight = height;
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

    private void createButtonPanel() {
		pnlButtons = createPanel(Color.RED);
		GridBagLayout layout = new GridBagLayout();
		pnlButtons.setLayout(layout);
		this.getContentPane().add(pnlButtons,BorderLayout.CENTER);

		pnlButtons.setSize(new Dimension(800, 200));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;

		addToPanel(lblRandSeed, pnlButtons, constraints, 0,0, 2, 1);




        //For each component to be added to this container:
        //...Create the component...
        //...Set instance variables in the GridBagConstraints instance...
        //jp.add(theComponent, c);
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
