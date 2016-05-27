package asgn2Tests;

import asgn2Aircraft.*;
import asgn2Passengers.PassengerException;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */

public class FirstTests {

    //declare the test plane globally
    private A380 testPlane;

    //populate necessary passengers
    private asgn2Passengers.Passenger passBusiness;
    private asgn2Passengers.Passenger passFirst;


    @Before
    public void setUp() throws Exception {

        // new a380 with one of each class
        testPlane = new A380("new-id", 101, 1, 1, 1, 1);

        //populate the dummy passengers before we run tests
        passBusiness = new asgn2Passengers.Business(70, 101);
        passFirst = new asgn2Passengers.First(70, 101);

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

    @Test (expected = PassengerException.class)
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


    //start tests for flyPassenger
    @Test (expected = PassengerException.class)
    public void attemptFlyPassengerNew() throws Exception {
        passBusiness.flyPassenger(101);
    }

    @Test (expected = PassengerException.class)
    public void attemptFlyPassengerRefused() throws Exception{
        passBusiness.refusePassenger(75);
        passBusiness.flyPassenger(101);
    }

    @Test(expected = PassengerException.class)
    public void attemptFlyPassengerQueued() throws Exception{
        passBusiness.queuePassenger(75, 101);
        passBusiness.flyPassenger(101);
    }

    @Test(expected = PassengerException.class)
    public void attemptFlyPassengerFlown() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(101);
        passBusiness.flyPassenger(101);
    }

    @Test(expected = PassengerException.class)
    public void attemptFlyDepartureTimeZero() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(0);
    }

    @Test(expected = PassengerException.class)
    public void attemptFlyDepartureTimeNegative() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(-1);
    }

    @Test
    public void flyPassengerCheckDepartureTime() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(100);
        assertEquals(passBusiness.getDepartureTime(), 100);
    }

    @Test
    public void flyPassengerCheckConfirmed() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(101);
        assertFalse(passBusiness.isConfirmed());
    }

    @Test
    public void flyPassengerCheckFlown() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.flyPassenger(101);
        assertTrue(passBusiness.isFlown());
    }
    //end tests for flyPassenger

    //start tests for queuePassenger
    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerAlreadyQueued() throws Exception {
        passBusiness.queuePassenger(75, 101);
        passBusiness.queuePassenger(75, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerConfirmed() throws Exception {
        passBusiness.confirmSeat(75, 101);
        passBusiness.queuePassenger(75, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerRefused() throws Exception {
        passBusiness.refusePassenger(75);
        passBusiness.queuePassenger(75, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerFlown() throws Exception {
        passBusiness.flyPassenger(100);
        passBusiness.queuePassenger(75, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerQueueTimeNegative() throws Exception {
        passBusiness.queuePassenger(-1, 101);
    }

    @Test (expected = PassengerException.class)
    public void attemptQueuePassengerDepartureTimeLessThanQueueTime() throws Exception {
        passBusiness.queuePassenger(102, 101);
    }

    @Test
    public void queuePassengerCheckQueueTime() throws Exception{
        passBusiness.queuePassenger(75, 101);
        assertEquals(passBusiness.getEnterQueueTime(),75);
    }

    @Test
    public void queuePassengerCheckDepartureTime() throws Exception{
        passBusiness.queuePassenger(75, 101);
        assertEquals(passBusiness.getDepartureTime(), 101);
    }

    @Test
    public void queuePassengerCheckNewState() throws Exception{
        passBusiness.queuePassenger(75, 101);
        assertFalse(passBusiness.isNew());
    }

    @Test
    public void queuePassengerCheckInQueue() throws Exception{
        passBusiness.queuePassenger(75, 101);
        assertTrue(passBusiness.isQueued());
    }
    //end tests for queuePassenger

    //start tests for refusePassenger
    @Test(expected = PassengerException.class)
    public void attemptRefusePassengerConfirmed() throws Exception{
        passBusiness.confirmSeat(75, 101);
        passBusiness.refusePassenger(75);
    }

    @Test(expected = PassengerException.class)
    public void attemptRefusePassengerRefused() throws Exception{
        passBusiness.refusePassenger(75);
        passBusiness.refusePassenger(75);
    }

    @Test(expected = PassengerException.class)
    public void attemptRefusePassengerFlown() throws Exception{
        passBusiness.flyPassenger(101);
        passBusiness.refusePassenger(75);
    }

    @Test(expected = PassengerException.class)
    public void attemptRefuseRefusalTimeNegative() throws Exception{
        passBusiness.refusePassenger(-1);
    }

    @Test(expected = PassengerException.class)
    public void attemptRefusePassengerRefusalTimeLessBookingTime() throws Exception{
        //passBusiness -> bookingTime = 70;
        passBusiness.refusePassenger(69);
    }

    @Test
    public void refusePassengerNewCheckExitQueueTime() throws Exception {
        passBusiness.refusePassenger(75);
        assertEquals(passBusiness.getExitQueueTime(), 75);
    }

    @Test
    public void refusePassengerQueuedCheckExitQueueTime() throws Exception {
        passBusiness.queuePassenger(70, 101);
        passBusiness.refusePassenger(75);
        assertEquals(passBusiness.getExitQueueTime(), 75);
    }

    @Test
    public void refusePassengerNewCheckNewState() throws Exception {
        passBusiness.refusePassenger(75);
        assertFalse(passBusiness.isNew());
    }

    @Test
    public void refusePassengerQueuedCheckQueued() throws Exception {
        passBusiness.refusePassenger(75);
        assertFalse(passBusiness.isQueued());
    }

    @Test
    public void refusePassengerNewCheckRefused() throws Exception {
        passBusiness.refusePassenger(75);
        assertTrue(passBusiness.isRefused());
    }

    @Test
    public void refusePassengerQueuedCheckRefused() throws Exception {
        passBusiness.refusePassenger(75);
        assertTrue(passBusiness.isRefused());
    }
    //end tests for refusePassenger

}