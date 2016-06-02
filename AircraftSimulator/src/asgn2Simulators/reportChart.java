package asgn2Simulators;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import asgn2Aircraft.AircraftException;
import asgn2Passengers.PassengerException;

import java.awt.Color;
import org.jfree.chart.renderer.xy.XYItemRenderer;


public class reportChart extends ApplicationFrame{

    private static final String TITLE1 = "Booking Details";
    private static final String TITLE2 = "Queued and Refused Passengers";

    /**
     * Constructor shares the work with the run method.
     * @param title Frame display title
     */
    public reportChart(final String title, int chart, Simulator sim) throws Exception {
        super(title);
        if (!(chart==0 || chart==1)){
            throw new Exception("invalid chart integer");
        }
        else{
            if (chart==0){
                ChartA(sim);
            }
            else {
                ChartB(sim);
            }
        }
    }

    public void ChartA(Simulator sim) throws SimulationException, PassengerException, AircraftException {
        final XYDataset dataSet = createChartAData(sim);
        final JFreeChart chart = createChartA(dataSet);
        final ChartPanel chartPanel = new ChartPanel(chart);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
        setContentPane(chartPanel);
    }

    public void ChartB(Simulator sim) throws SimulationException, PassengerException, AircraftException {
        final XYDataset dataSet = createChartBData(sim);
        final JFreeChart chart = createChartB(dataSet);
        final ChartPanel chartPanel = new ChartPanel(chart);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
        setContentPane(chartPanel);
    }


//CREATE DATA
    /**
     * Returns a sample dataSet.
     *
     * @return The dataSet.
     * @throws SimulationException
     * @throws PassengerException
     * @throws AircraftException
     */
    private XYDataset createChartAData(Simulator sim) throws SimulationException, AircraftException, PassengerException {
        // create the dataset...
        TimeSeriesCollection tsc = new TimeSeriesCollection();

        TimeSeries f = new TimeSeries("First");
        TimeSeries b = new TimeSeries("Business");
        TimeSeries p = new TimeSeries("Premium");
        TimeSeries e = new TimeSeries("Economy");
        TimeSeries total = new TimeSeries("Total Flown");
        TimeSeries empty = new TimeSeries("Daily Seats Available");

        boolean flying = false;
        String[] summary;
        double numFirst,numBusiness,numPremium,numEconomy,numTotal,numAvailable;

        //Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();

        sim.createSchedule();
        sim.resetStatus(Constants.FIRST_FLIGHT);
        sim.rebookCancelledPassengers(Constants.FIRST_FLIGHT);
        sim.generateAndHandleBookings(Constants.FIRST_FLIGHT);
        sim.processNewCancellations(Constants.FIRST_FLIGHT);


        for (int i = Constants.FIRST_FLIGHT; i < Constants.DURATION; i++) {
            flying = true;
            //These lines are important
            cal.set(2016,0,i,6,0);	//CHANGES DATE W/ EACH LOOP ITERATION
            Date timePoint = cal.getTime();

            sim.resetStatus(i);
            sim.rebookCancelledPassengers(i);
            sim.generateAndHandleBookings(i);
            sim.processNewCancellations(i);
            sim.processUpgrades(i);
            sim.processQueue(i);
            sim.flyPassengers(i);
            sim.updateTotalCounts(i);

            summary 	= sim.getSummary(i, flying).split(":");
            numFirst 	= Double.parseDouble((summary[2].split("F"))[1]);
            numBusiness = Double.parseDouble((summary[3].split("J"))[1]);
            numPremium 	= Double.parseDouble((summary[4].split("P"))[1]);
            numEconomy 	= Double.parseDouble((summary[5].split("Y"))[1]);
            numTotal 	= Double.parseDouble((summary[6].split("T"))[1]);
            numAvailable = Double.parseDouble((summary[7].split("E"))[1]);

            f.add(new Day(timePoint),numFirst);
            b.add(new Day(timePoint),numBusiness);
            p.add(new Day(timePoint),numPremium);
            e.add(new Day(timePoint),numEconomy);
            total.add(new Day(timePoint),numTotal);
            empty.add(new Day(timePoint),numAvailable);
        }
        sim.processQueue(Constants.DURATION);
        flying = false;
        tsc.addSeries(f);
        tsc.addSeries(b);
        tsc.addSeries(p);
        tsc.addSeries(e);
        tsc.addSeries(total);
        tsc.addSeries(empty);
        return tsc;
    }


    /**
     * Private method creates the dataset. Lots of hack code in the
     * middle, but you should use the labelled code below
     * @return collection of time series for the plot
     * @throws PassengerException
     * @throws SimulationException
     * @throws AircraftException
     */
    private XYDataset createChartBData(Simulator sim) throws SimulationException, PassengerException, AircraftException {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries queuedTotal = new TimeSeries("Queued");
        TimeSeries refusedTotal = new TimeSeries("Refused");

        //Base time, data set up - the calendar is needed for the time points
        Calendar cal = GregorianCalendar.getInstance();

        sim.createSchedule();
        sim.resetStatus(Constants.FIRST_FLIGHT);
        sim.rebookCancelledPassengers(Constants.FIRST_FLIGHT);
        sim.generateAndHandleBookings(Constants.FIRST_FLIGHT);
        sim.processNewCancellations(Constants.FIRST_FLIGHT);


        int queued = 0;
        int refused = 0;

        for (int i = Constants.FIRST_FLIGHT; i < Constants.DURATION; i++) {
            //These lines are important
            cal.set(2016,0,i,6,0);	//CHANGES DATE W/ EACH LOOP ITERATION
            Date timePoint = cal.getTime();

            sim.resetStatus(i);
            sim.rebookCancelledPassengers(i);
            sim.generateAndHandleBookings(i);
            sim.processNewCancellations(i);
            sim.processUpgrades(i);
            sim.processQueue(i);
            sim.flyPassengers(i);
            sim.updateTotalCounts(i);


            //ADD TO QUEUED AND ECONOMY
            //queued = num of passengers with queued state in day 'i'
            queued = sim.numInQueue();
            //refused = num of passengers with refused state in day 'i'
            refused = sim.numRefused();


            //This is important - steal it shamelessly
            queuedTotal.add(new Day(timePoint),queued);
            refusedTotal.add(new Day(timePoint),refused);
        }
        //Collection
        sim.processQueue(Constants.DURATION);
        tsc.addSeries(queuedTotal);
        tsc.addSeries(refusedTotal);
        return tsc;
    }


//CREATE CHARTS

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private JFreeChart createChartA(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                TITLE1, "Days", "Passengers", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        //custom colours
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint( 0 , Color.BLACK);
        renderer.setSeriesPaint( 1 , Color.BLUE);
        renderer.setSeriesPaint( 2 , Color.CYAN);
        renderer.setSeriesPaint( 3 , Color.GRAY);
        renderer.setSeriesPaint( 4 , Color.GREEN);
        renderer.setSeriesPaint( 5 , Color.RED);
        plot.setRenderer( renderer );
        //end colours
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }


    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range
     * @param dataset TimeSeriesCollection for plotting
     * @returns chart to be added to panel
     */
    private JFreeChart createChartB(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                TITLE2, "Days", "Passengers", dataset, true, true, false);

        final XYPlot plot = result.getXYPlot();
        //custom colours
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint( 0 , Color.BLACK);
        renderer.setSeriesPaint( 1 , Color.RED);
        plot.setRenderer( renderer );
        //end colours
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }

    /**PLACE THIS IN YOUR GUI SIM CODE**/
    /*
    public static void main( String[ ] args ) throws Exception
    {
       Simulator sim = new Simulator();
       final createChart chart = new createChart("Output Chart", 0, sim);
       chart.pack( );
       RefineryUtilities.centerFrameOnScreen(chart);
       chart.setVisible(true);
    }
*/
}
