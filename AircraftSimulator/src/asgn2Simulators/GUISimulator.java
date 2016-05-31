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
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JButton;


/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
    private static final long serialVersionUID = -7031008862559936404L;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;


    private JLabel outputConsole;
    private JPanel gridWrapper;

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
	}

	private void createGUI(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //use a default border layout for the window
        setLayout(new BorderLayout());

        gridWrapper = gridBagPanel();
        outputConsole = returnOutputConsole();



        //add the gridbag wrapper in which to place other elements
        getContentPane().add(gridWrapper,BorderLayout.CENTER);
        gridWrapper.add(outputConsole, GridBagConstraints.PAGE_START);


        /*
        this.getContentPane().add(pnlOne,BorderLayout.CENTER);
        this.getContentPane().add(pnlTwo,BorderLayout.NORTH);
        this.getContentPane().add(pnlThree,BorderLayout.SOUTH);
        this.getContentPane().add(pnlFour,BorderLayout.EAST);
        this.getContentPane().add(pnlFive,BorderLayout.WEST);
        */


        repaint();
        this.setVisible(true);

	}

    private JLabel returnOutputConsole(){
        JLabel oc = new JLabel();
        oc.setBackground(Color.WHITE);
        oc.setPreferredSize(new Dimension(200, 180));
        return oc;
    }

    private JPanel createPanel(Color c) {
        JPanel jp = new JPanel();
        jp.setBackground(c);
        return jp;
    }

    private JPanel gridBagPanel() {
        JPanel jp = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //For each component to be added to this container:
        //...Create the component...
        //...Set instance variables in the GridBagConstraints instance...
        //jp.add(theComponent, c);

        return jp;
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
