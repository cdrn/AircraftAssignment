package asgn2Tests;

import asgn2Aircraft.*;
import asgn2Passengers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * All of the tests for the a380 class, which inherits from
 * the aircraft class, testing the inherited methods as well as
 * the class methods
 */
public class A380Tests {

    //declare the testplane globally
    private A380 testPlane;

    //global test passenger declarations. 2 of each animal (for overflows)
    private asgn2Passengers.Passenger passBusiness;
    private asgn2Passengers.Passenger passBusiness2;
    private asgn2Passengers.Passenger passEconomy;
    private asgn2Passengers.Passenger passEconomy2;
    private asgn2Passengers.Passenger passPremium;
    private asgn2Passengers.Passenger passPremium2;
    private asgn2Passengers.Passenger passFirst;
    private asgn2Passengers.Passenger passFirst2;


    @Before
    public void setUp() throws Exception {

        //spins up an a380 with 1 of each class to easily test boundary cases.
        testPlane = new A380("new-id", 101, 1, 1, 1, 1);

        //populate the dummy passengers before we run tests
        passBusiness = new asgn2Passengers.Business(70, 101);
        passBusiness2 = new asgn2Passengers.Business(70, 101);
        passEconomy = new asgn2Passengers.Economy(70, 101);
        passEconomy2 = new asgn2Passengers.Economy(70, 101);
        passPremium = new asgn2Passengers.Premium(70, 101);
        passPremium2 = new asgn2Passengers.Premium(70, 101);
        passFirst = new asgn2Passengers.First(70, 101);
        passFirst2 = new asgn2Passengers.First(70, 101);

        //confirm all the seats for the dummy passengers (the first of each)
        /*
        passBusiness.confirmSeat(71, 101);
        passEconomy.confirmSeat(71, 101);
        passPremium.confirmSeat(71, 101);
        passFirst.confirmSeat(71, 101);
        */

    }

   /* @After
    public void tearDown() throws Exception {

    }
*/

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

    @Test(expected = AircraftException.class)
    public void cancelBookingPassFirstNotConfirmedOnPlane() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passPremium, 98);
        //try to cancel unconfirmed booking
        testPlane.cancelBooking(passFirst, 98);

    }


    //stub
    @Test (expected = AircraftException.class)
    public void cancelBookingPassengerNotConfirmedSeatInPassengerClass() throws Exception {
        Passenger testPassenger = new First(71, 101);
        testPlane.cancelBooking(testPassenger, 97);

    }

    //end testblock for cancel booking tests.



    //start testblock for confirmBooking method


    @Test
    public void confirmBookingPassBusinessAssertTrue() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        assertTrue(testPlane.hasPassenger(passBusiness));
    }

    //tries to overbook business class to check that it throws the correct exception (aircraftexception).
    //does this for every class to make sure the correct overbooking exception is thrown
    @Test(expected = AircraftException.class)
    public void confirmBookingPassBusinessThrowsExceptionAircraftNoSeats() throws Exception {
        testPlane.confirmBooking(passBusiness, 98);
        testPlane.confirmBooking(passBusiness2, 98);
    }

    @Test(expected = AircraftException.class)
    public void confirmBookingPassEconomyThrowsExceptionAircraftNoSeats() throws Exception {
        testPlane.confirmBooking(passEconomy, 98);
        testPlane.confirmBooking(passEconomy2, 98);
    }


    @Test(expected = AircraftException.class)
    public void confirmBookingPassPremiumThrowsExceptionAircraftNoSeats() throws Exception {
        testPlane.confirmBooking(passPremium, 98);
        testPlane.confirmBooking(passPremium2, 98);
    }


    @Test(expected = AircraftException.class)
    public void confirmBookingPassFirstThrowsExceptionAircraftNoSeats() throws Exception {
        testPlane.confirmBooking(passFirst, 98);
        testPlane.confirmBooking(passFirst2, 98);
    }

    //Tries to put a passenger from the wrong state in the booking queue
    //by cancelling the passenger first
    @Test(expected = PassengerException.class)
    public void confirmBookingFirstClassWrongStateCancelled() throws Exception {
        passFirst.cancelSeat(97);
        testPlane.confirmBooking(passFirst, 98);

    }
    //Tries to put a passenger from the wrong state in the booking queue
    //who has never been confirmed
    @Test(expected = PassengerException.class)
    public void confirmBookingFirstClassWrongStateNeverConfirmed() throws Exception {
        Passenger unconfirmedPassenger = new asgn2Passengers.First(70, 101);
        testPlane.confirmBooking(unconfirmedPassenger, 98);

    }
    //Tries to put in a passenger with an invalid confirmation time later than departure
    @Test(expected = PassengerException.class)
    public void confirmBookingLaterTimeThanDeparture() throws Exception {
        testPlane.confirmBooking(passFirst, 102);

    }

    //Tries to put in a passenger just inside the confirmation boundary case (same day)
    @Test
    public void confirmBookingLaterTimeSameDayAsDeparture() throws Exception {
        testPlane.confirmBooking(passFirst, 101);
        assertTrue(passFirst.isConfirmed());

    }

    //Tries to put in a passenger in with a wacko departure time that doesn't match the plane
    //just outside the upper boundary of the plane (1 more)
    @Test(expected = PassengerException.class)
    public void confirmBookingDepartureTimeIsTooLateOuterBoundary() throws Exception {
        Passenger departurePassenger = new asgn2Passengers.First(70, 102);
        testPlane.confirmBooking(departurePassenger, 100);

    }

    //Same as above but departure time is too early in the passenger declaration
    @Test(expected = PassengerException.class)
    public void confirmBookingDepartureTimeIsTooEarlyInnerBoundaryThrowException() throws Exception {
        Passenger departurePassenger = new asgn2Passengers.First(70, 100);
        testPlane.confirmBooking(departurePassenger, 101);

    }

    //End confirmbooking text block


    //MISC tests


    //FIX THIS TEST LATER
    //FIX TEST STRING
    @Test
    public void finalState() throws Exception {
        assertEquals(testPlane.finalState(), "new-id Pass: 4");

    }

    //non empty flight
    @Test
    public void flightEmptyFalse() throws Exception {
        assertFalse(testPlane.flightEmpty());

    }

    //empty flight
    @Test
    public void flightEmptyTrue() throws Exception {
        Aircraft testCraft = new A380("testid", 50);
        assertTrue(testCraft.flightEmpty());
    }

    @Test
    public void flightFullTrue() throws Exception {
        assertTrue(testPlane.flightFull());
    }

    @Test
    public void flightFullFalse() throws Exception{
        Aircraft testCraft = new A380("testid", 50);
        assertFalse(testCraft.flightFull());
    }


    //check a passenger from each class is marked as flown

    @Test
    public void flyPassengersAssertFlownEconomy() throws Exception {
        testPlane.flyPassengers(101);
        assertTrue(passEconomy.isFlown());

    }

    @Test
    public void flyPassengersAssertFlownPremium() throws Exception {
        testPlane.flyPassengers(101);
        assertTrue(passPremium.isFlown());

    }

    @Test
    public void flyPassengersAssertFlownFirst() throws Exception {
        testPlane.flyPassengers(101);
        assertTrue(passFirst.isFlown());

    }

    @Test
    public void flyPassengersAssertFlownBusiness() throws Exception {
        testPlane.flyPassengers(101);
        assertTrue(passBusiness.isFlown());

    }

    //end flypassengers

    //test an exception is thrown for a bad state passenger
    @Test (expected = PassengerException.class)
    public void flyPassengersThrowPassengerExceptionBadStateEconomy() throws Exception {
        passEconomy.cancelSeat(100);
        testPlane.flyPassengers(101);
        assertFalse(passEconomy.isFlown());

    }

    //Assert the getbookings returns the expected output when full.
    @Test
    public void getBookingsFullPlaneAllClasses() throws Exception {
        Bookings dummyBooking = new Bookings(1, 1, 1, 1, 4, 0);
        assertEquals(testPlane.getBookings(), dummyBooking);


    }

    //Assert the getbookings returns the expected output when empty
    @Test
    public void getBookingsEmptyPlane() throws Exception {

        A380 newPlane = new A380("test-id", 101, 1, 1, 1, 1);
        Bookings dummyBooking = new Bookings(0, 0, 0, 0, 0, 4);
        assertEquals(newPlane.getBookings(), dummyBooking);
    }

    //Simple tests for all the getters (in case)

    @Test
    public void getNumBusiness() throws Exception {
        testPlane.confirmBooking(passBusiness, 70);
        assertEquals(testPlane.getNumBusiness(), 1);

    }

    @Test
    public void getNumEconomy() throws Exception {
        testPlane.confirmBooking(passEconomy, 70);
        assertEquals(testPlane.getNumEconomy(), 1);

    }

    @Test
    public void getNumFirst() throws Exception {
        testPlane.confirmBooking(passFirst, 70);
        assertEquals(testPlane.getNumFirst(), 1);

    }

    @Test
    public void getNumPassengers() throws Exception {
        testPlane.confirmBooking(passEconomy, 70);
        testPlane.confirmBooking(passPremium, 70);
        testPlane.confirmBooking(passBusiness, 70);
        testPlane.confirmBooking(passFirst, 70);
        assertEquals(testPlane.getNumPassengers(), 4);
    }

    @Test
    public void getNumPremium() throws Exception {
        testPlane.confirmBooking(passPremium, 70);
        assertEquals(testPlane.getNumPremium(), 1);
    }

    @Test
    public void getPassengers() throws Exception {
        testPlane.confirmBooking(passBusiness, 70);
        testPlane.confirmBooking(passEconomy, 70);
        testPlane.confirmBooking(passPremium, 70);
        testPlane.confirmBooking(passFirst, 70);
        List<Passenger> dummyList = new ArrayList<>();
        dummyList.add(0, passBusiness);
        dummyList.add(1, passEconomy);
        dummyList.add(2, passPremium);
        dummyList.add(3, passFirst);
        assertEquals(testPlane.getPassengers(), dummyList);
    }

    @Test
    public void getPassengersNull() throws Exception {

        List<Passenger> dummyList = new ArrayList<>();
        assertEquals(testPlane.getPassengers(), dummyList);

    }


    //TODO
//    @Test
//    public void getStatus() throws Exception {
//        assertEquals(testPlane.getStatus(101), "string that needs to be implemented");
//    }

    //simple getter tests end


    @Test
    public void hasPassengerFromBusinessClass() throws Exception {
        testPlane.confirmBooking(passBusiness, 70);
        assertTrue(testPlane.hasPassenger(passBusiness));

    }

    @Test
    public void hasPassengerFromPremiumClass() throws Exception {
        testPlane.confirmBooking(passPremium, 70);
        assertTrue(testPlane.hasPassenger(passPremium));

    }

    @Test
    public void hasPassengerFromFirstClass() throws Exception {
        testPlane.confirmBooking(passFirst, 70);
        assertTrue(testPlane.hasPassenger(passFirst));

    }

    @Test
    public void hasPassengerFromEconomyClass() throws Exception {
        testPlane.confirmBooking(passEconomy, 70);
        assertTrue(testPlane.hasPassenger(passEconomy));

    }

    @Test
    public void initialState() throws Exception {
        assertTrue(true);


    }

    @Test
    public void seatsAvailable() throws Exception {
        assertTrue(true);


    }

    @Test
    public void upgradeBookings() throws Exception {
        assertTrue(true);

    }

}