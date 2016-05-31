package asgn2Tests;

import asgn2Aircraft.*;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */
public class PremiumTests {

    private A380 testPlane;
    private asgn2Passengers.Passenger passPremium;
    private asgn2Passengers.Passenger passBusiness;


    @org.junit.Before
    public void setUp() throws Exception {
        //set up test plane
        testPlane = new A380("new-id", 101, 2, 2, 2, 2);
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
    public void upgradePremiumToBusinessCheckID() throws Exception{
        testPlane.confirmBooking(passPremium, 70);
        passBusiness = passPremium.upgrade();
        assertEquals('J', passBusiness.getPassID().charAt(0));

    }
//    @Test
//    public void upgradePremiumToFirstPremiumCheck() throws Exception {
//        passPremium.upgrade();
//        assertEquals(testPlane.getNumPremium(), 0);
//    }
//
//    @Test
//    public void upgradePremiumToFirstFirstCheck() throws Exception {
//
//        testPlane.confirmBooking(passPremium, 70);
//        passPremium.upgrade();
//        assertEquals(testPlane.getNumFirst(), 2);
//    }
    //end tests for upgrade
}