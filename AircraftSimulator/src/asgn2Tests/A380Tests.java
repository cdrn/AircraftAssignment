package asgn2Tests;

import asgn2Passengers.Passenger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import asgn2Aircraft.*;


import static org.junit.Assert.*;


/**
 * All of the tests for the a380 class, which inherits from
 * the aircraft class, testing the inherited methods as well as
 * the class methods
 */
public class A380Tests {

    //declare the testplane globally
    private A380 testPlane;

    //global test passenger declarations
    private asgn2Passengers.Passenger passBusiness;
    private asgn2Passengers.Passenger passEconomy;
    private asgn2Passengers.Passenger passPremium;
    private asgn2Passengers.Passenger passFirst;

    @Before
    public void setUp() throws Exception {
        //create the dummy test plane for the tests.
        testPlane = new A380("new-id", 101);

        //spin up some dummy passengers for later
        passBusiness = new asgn2Passengers.Business(70, 101);
        passEconomy = new asgn2Passengers.Economy(70, 101);
        passPremium = new asgn2Passengers.Premium(70, 101);
        passFirst = new asgn2Passengers.First(70, 101);
    }

    @After
    public void tearDown() throws Exception {

    }


    //Testblock for cancel booking tests

    @Test
    public void cancelBookingOneBusinessClass() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);
        //cancel booking and assert that the flight no longer contains business class passenger
        testPlane.cancelBooking(passBusiness, 99);
        assertFalse(testPlane.hasPassenger(passBusiness));
    }

    @Test
    public void cancelBookingOneEconomyClass() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);
        //cancel different booking and assert no longer contains passenger
        testPlane.cancelBooking(passEconomy, 99);
        assertFalse(testPlane.hasPassenger(passEconomy));

    }

    @Test
    public void cancelBookingOnePremiumClass() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);
        //cancel different booking and assert no longer contains passenger
        testPlane.cancelBooking(passPremium, 99);
        assertFalse(testPlane.hasPassenger(passPremium));

    }

    @Test
    public void cancelBookingOneFirstClass() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);
        //cancel different booking and assert no longer contains passenger
        testPlane.cancelBooking(passFirst, 99);
        assertFalse(testPlane.hasPassenger(passFirst));

    }


    @Test (expected = Exception.class)
    public void cancelBookingThrowsExceptionTooLateToCancel() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);
        //assert that cancellation cannot be done after the flight
        testPlane.cancelBooking(passFirst, 102);
    }

    @Test(expected = Exception.class)
    public void cancelBookingPassFirstNotConfirmed() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        //assert that booking must be confirmed
        testPlane.cancelBooking(passFirst, 98);

    }

    @Test
    public void cancelBooking() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passFirst, 98);

    }



    //end testblock for cancel booking tests.

    @Test
    public void confirmBookingPassBusinessAssertTrue() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        assertTrue(testPlane.hasPassenger(passBusiness));
    }

    @Test
    public void finalState() throws Exception {

    }

    @Test
    public void flightEmpty() throws Exception {

    }

    @Test
    public void flightFull() throws Exception {

    }

    @Test
    public void flyPassengers() throws Exception {

    }

    @Test
    public void getBookings() throws Exception {

    }

    @Test
    public void getNumBusiness() throws Exception {

    }

    @Test
    public void getNumEconomy() throws Exception {

    }

    @Test
    public void getNumFirst() throws Exception {

    }

    @Test
    public void getNumPassengers() throws Exception {

    }

    @Test
    public void getNumPremium() throws Exception {

    }

    @Test
    public void getPassengers() throws Exception {

    }

    @Test
    public void getStatus() throws Exception {

    }

    @Test
    public void hasPassenger() throws Exception {

    }

    @Test
    public void initialState() throws Exception {

    }

    @Test
    public void seatsAvailable() throws Exception {

    }

    @Test
    public void upgradeBookings() throws Exception {

    }

}