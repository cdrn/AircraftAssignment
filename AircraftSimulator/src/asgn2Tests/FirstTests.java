package asgn2Tests;

import asgn2Aircraft.A380;
import asgn2Passengers.Passenger;

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

        //confirm all the seats for the dummy passengers (the first of each)
        passBusiness.confirmSeat(70, 101);
        passEconomy.confirmSeat(70, 101);
        passPremium.confirmSeat(70, 101);
        passFirst.confirmSeat(70, 101);


    }


    @Test
    public void tearDown() throws Exception {

    }

    //start tests for noSeats

    @Test
    public void noSeatsMsg() throws Exception {

    }
    //end tests for noSeats

    //start tests for Upgrade

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

    @Test
    public void attemptupgradeFirstCheckFirst() throws Exception {
        passFirst.upgrade();
        assertEquals(testPlane.getNumFirst(), 1);
    }
    //end tests for upgrade

    @Test
    public void cancelSeat() throws Exception {

    }

    @Test
    public void confirmSeat() throws Exception {

    }

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