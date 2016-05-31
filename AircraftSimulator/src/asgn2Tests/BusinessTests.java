package asgn2Tests;


import asgn2Aircraft.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Sai on 12/05/2016.
 */
public class BusinessTests {

    private A380 testPlane;

    private asgn2Passengers.Passenger passBusiness;
    private asgn2Passengers.Passenger passFirst;

    @org.junit.Before
    public void setUp() throws Exception {
        // new a380 with one of each class
        testPlane = new A380("new-id", 101, 2,2,2,2);

        //populate dummy passenger
        passBusiness = new asgn2Passengers.Business(70, 101);
    }

    //noSeats
    @org.junit.Test
    public void noSeatsMsgCheck() throws Exception {
        assertEquals(passBusiness.noSeatsMsg(), "No seats available in Business");
    }
    //end noSeats

    //start tests for upgrade
    @Test
    public void upgradeBusinessToFirstCheckID() throws Exception{
        testPlane.confirmBooking(passBusiness, 70);
        passFirst = passBusiness.upgrade();
        assertEquals('F', passFirst.getPassID().charAt(0));

    }
//    @Test
//    public void upgradeBusinessToPremiumBusinessCheck() throws Exception {
//        testPlane.confirmBooking(passBusiness, 70);
//        passBusiness.upgrade();
//        assertEquals(testPlane.getNumBusiness(), 0);
//    }
//
//    @Test
//    public void upgradeBusinessToPremiumPremiumCheck()throws Exception{
//        testPlane.confirmBooking(passBusiness, 70);
//        assertEquals(testPlane.getNumPremium(), 2);
//    }


    //end tests for upgrade
}