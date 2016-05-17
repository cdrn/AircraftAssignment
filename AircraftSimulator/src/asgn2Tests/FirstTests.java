package asgn2Tests;

import asgn2Aircraft.A380;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */

public class FirstTests {

    //declare the test plane globally
    private A380 testPlane;

    //global test passenger declarations. 2 of each for overflow
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

        // new a380 with one of each class
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

    }


    @Test
    public void tearDown() throws Exception {

    }

    //noSeats
    @Test
    public void noSeatsMsgCheck() throws Exception {
        assertEquals(passFirst.noSeatsMsg(), "No seats available in First");

    }
    //end noSeats


    //start tests for Upgrade
    @Test
    public void attemptupgradeFirstCheckFirst() throws Exception {
        passFirst.upgrade();
        assertEquals(testPlane.getNumFirst(), 1);
    }
    //end tests for upgrade



    //start tests for cancelSeat
    @Test (expected = PassengerException.class)
    public void attemptCancelSeatPassengerIsQueued() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.queuePassenger(75, 101);
        passBusiness.cancelSeat(90);
    }

    @Test (expected = PasssengerException.class)
    public void attemptCancelSeatPassengerIsRefused() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.refusePassenger(80);
        passBusiness.cancelSeat(90);
    }

    @Test (expected = PassengerException.class)
    public void attemptCancelSeatPassengerIsFlown() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.flyPassenger(101);
        passBusiness.cancelSeat(90);
    }

    @Test (expected = PassengerException.class)
    public void attemptCancelSeatCancellationTimeNegative() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.cancelSeat(-1);
    }

    @Test (expected = PassengerException.class)
    public void attemptCancelSeatDepartureTimeLessThanCancellationTime() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.confirmSeat(70, 101);
        passBusiness.cancelSeat(102);
    }

    @Test
    public void cancelSeatConfirmedIsFalse() throws Exception{
        passBusiness.confirmSeat(70, 101);
        passBusiness.cancelSeat(90);
        assertFalse(passBusiness.isConfirmed());
    }

    @Test
    public void cancelSeatNewStateIsFalse() throws Exception{
        passBusiness.confirmSeat(70, 101);
        passBusiness.cancelSeat(90);
        assertTrue(passBusiness.isNew());
    }

    @Test
    public void cancelSeatBookingTimeIsCancellationTime() throws Exception{
        passBusiness.confirmSeat(70, 101);
        passBusiness.cancelSeat(90);
        assertEquals(passBusiness.getBookingTime(), 90);
    }
    //end tests for cancelSeat

    //start tests for confirmSeat
    @Test (expected = PassengerException.class)
    public void attemptConfirmSeatPassengerAlreadyConfirmed() throws Exception {
        passBusiness.confirmSeat(70, 101);
        passBusiness.confirmSeat(70, 101);
    }

    @Test(expected = PassengerException.class)
    public void attemptConfirmSeatPassengerRefused() throws Exception{
        passBusiness.refusePassenger(70);
        passBusiness.confirmSeat(70, 101);

    }

    @Test (expected = PassengerException.class)
    public void attemptConfirmSeatConfirmationTimeNegative() throws Exception{
        passBusiness.confirmSeat(-1, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptConfirmSeatDepartureLessThanConfirmation() throws Exception{
        passBusiness.confirmSeat(102, 101);
    }

    @Test
    public void ConfirmSeatDepartureBordersConfirmation() throws Exception{
        passBusiness.confirmSeat(101,101);
        assertTrue(passBusiness.isConfirmed());
    }

    @Test
    public void ConfirmSeatConfirmationTimeZero() throws Exception{
        passBusiness.confirmSeat(0, 101);
        assertTrue(passBusiness.isConfirmed());
    }

    @Test
    public void ConfirmSeatNewCheckingConfirmationTime() throws Exception{
        passBusiness.confirmSeat(70, 101);
        assertEquals(passBusiness.getConfirmationTime(), 70);
    }

    @Test
    public void ConfirmSeatQueuedCheckingConfirmationTime() throws Exception{
        passBusiness.queuePassenger(70, 101);
        passBusiness.confirmSeat(70, 101);
        assertEquals(passBusiness.getConfirmationTime(), 70);
    }

    @Test
    public void ConfirmSeatNewCheckingDepartureTime() throws Exception{
        passBusiness.confirmSeat(70, 101);
        assertEquals(passBusiness.getDepartureTime(), 101);
    }

    @Test
    public void ConfirmSeatQueuedCheckingDepartureTime() throws Exception{
        passBusiness.queuePassenger(70, 101);
        passBusiness.confirmSeat(70, 101);
        assertEquals(passBusiness.getDepartureTime(), 70);
    }

    @Test
    public void ConfirmSeatNewCheckNewState() throws Exception{
        passBusiness.confirmSeat(70, 101);
        assertFalse(passBusiness.isNew());
    }

    @Test
    public void ConfirmSeatQueuedCheckNewState() throws Exception{
        passBusiness.queuePassenger(70, 101);
        passBusiness.confirmSeat(70, 101);
        assertFalse(passBusiness.isNew());
    }

    @Test
    public void ConfirmSeatNewCheckQueued() throws Exception{
        passBusiness.confirmSeat(70, 101);
        assertFalse(passBusiness.isQueued());
    }

    @Test
    public void ConfirmSeatQueuedCheckQueued() throws Exception{
        passBusiness.queuePassenger(70, 101);
        passBusiness.confirmSeat(70, 101);
        assertFalse(passBusiness.isQueued());
    }

    @Test
    public void ConfirmSeatNewCheckConfirmed() throws Exception{
        passBusiness.confirmSeat(70, 101);
        assertTrue(passBusiness.isConfirmed());
    }

    @Test
    public void ConfirmSeatQueuedCheckConfirmed() throws Exception{
        passBusiness.queuePassenger(70, 101);
        passBusiness.confirmSeat(70, 101);
        assertTrue(passBusiness.isConfirmed());
    }
    //end tests for confirmSeat

    @Test
    public void flyPassenger() throws Exception {

    }

    @Test
    public void queuePassenger() throws Exception {

    }

    @Test
    public void refusePassenger() throws Exception {

    }

    @Test
    public void upgrade1() throws Exception {

    }

    @Test
    public void copyPassengerState() throws Exception {

    }

}