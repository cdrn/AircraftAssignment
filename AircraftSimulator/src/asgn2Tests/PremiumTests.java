package asgn2Tests;

import asgn2Aircraft.A380;

import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */
public class PremiumTests {

    private A380 testPlane;
    private asgn2Passengers.Passenger passPremium;


    @org.junit.Before
    public void setUp() throws Exception {
        //set up test plane
        testPlane = new A380("new-id", 101, 1, 1, 1, 1);
        //populate dummy passenger
        passPremium = new asgn2Passengers.Premium(70, 101);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    //noSeats
    @Test
    public void noSeatsMsgCheck() throws Exception{
        assertEquals(passPremium.noSeatsMsg(), "No seats available in Premium");
    }
    //end noSeats

    //start tests for upgrade
    @Test
    public void upgradePremiumToFirstPremiumCheck() throws Exception {
        passPremium.upgrade();
        assertEquals(testPlane.getNumPremium(), 0);
    }

    @Test
    public void upgradePremiumToFirstFirstCheck() throws Exception {
        passPremium.upgrade();
        assertEquals(testPlane.getNumFirst(), 2);
    }
    //end tests for upgrade
}