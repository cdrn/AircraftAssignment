package asgn2Tests;

import asgn2Aircraft.A380;

import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */
public class BusinessTests {

    private A380 testPlane;

    private asgn2Passengers.Passenger passBusiness;

    @org.junit.Before
    public void setUp() throws Exception {
        // new a380 with one of each class
        testPlane = new A380("new-id", 101, 1, 1, 1, 1);

        //populate dummy passenger
        passBusiness = new asgn2Passengers.Business(70, 101);
    }

    //noSeats
    @org.junit.Test
    public void noSeatsMsgCheck() throws Exception {
        assertEquals(passBusiness.noSeatsMsg(), "No seats available in Business")
    }
    //end noSeats

    //start tests for upgrade
    @Test
    public void upgradeBusinessToPremiumBusinessCheck() throws Exception {
        passBusiness.upgrade();
        assertEquals(testPlane.getNumBusiness(), 0);
    }

    @Test
    public void upgradeBusinessToPremiumPremiumCheck()throws Exception{
        passBusiness.upgrade();
        assertEquals(testPlane.getNumPremium(), 2);
    }
    //end tests for upgrade
}