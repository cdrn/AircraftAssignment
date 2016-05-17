package asgn2Tests;

import asgn2Aircraft.A380;
import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */
public class EconomyTests {

    private A380 testPlane;

    private asgn2Passengers.Passenger passEconomy;

    @org.junit.Before
    public void setUp() throws Exception {
        //new A380 with one of each class
        testPlane = new A380("new-id",101, 1,1,1,1);

        //populate dummy passenger
        passEconomy = new asgn2Passengers.Economy(70,101);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    //noSeats
    @Test
    public void noSeatsMsgCheck() throws Exception{
        assertEquals(passEconomy.noSeatsMsg(), "No seats available in Economy");
    }
    //end noSeats


    //start tests for upgrade
    @Test
    public void upgradeEconomyToBusinessEconCheck() throws Exception {
        passEconomy.upgrade();
        assertEquals(testPlane.getNumEconomy(), 0);
    }

    @Test
    public void upgradeEconomyToBusinessBusinessCheck() throws Exception {
        passEconomy.upgrade();
        assertEquals(testPlane.getNumBusiness(), 2);
    }
    //end tests for upgrade
}