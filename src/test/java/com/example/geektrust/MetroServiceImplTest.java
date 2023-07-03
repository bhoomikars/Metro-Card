package com.example.geektrust;

import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.service.MetroServiceImpl;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MetroServiceImplTest {

    @InjectMocks
    private MetroServiceImpl metroService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMetroCard() {
        metroService.createMetroCard("MC1", 600.0);
        Assert.assertEquals(metroService.getMetroCardMap().containsKey("MC1"), true);
    }

    @Test
    public void testWhenMetroCardIsInValid() {
        metroService.checkIn("METRO-CARD", PassengerType.ADULT, Station.CENTRAL);
        Assert.assertEquals(false, metroService.getMetroCardMap().containsKey("METRO-CARD"));
    }

    @Test
    public void testCheckInWhenMetroCardISValid() {
        metroService.createMetroCard("MC1", 600.0);
        metroService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);
        Assert.assertEquals(600.0, Station.CENTRAL.getTotalMoneyCollected());
    }

    @Test
    public void testIfDiscountIsGiven() {
        metroService.createMetroCard("MC1", 600.0);
        metroService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);
        metroService.checkIn("MC1", PassengerType.ADULT, Station.AIRPORT);
        Assert.assertEquals(400.0, Station.CENTRAL.getTotalMoneyCollected());
        Assert.assertEquals(100.0, Station.AIRPORT.getTotalMoneyCollected());

    }

    @Test
    public void rechargeIfBalanceIsLessThanTripCharge() {
        metroService.createMetroCard("MC1", 100.0);
        metroService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);
        Assert.assertEquals(4.0, Station.CENTRAL.getTaxCollected());

    }

    @Test
    public void testPrintSummary() {
        metroService.createMetroCard("MC1", 100.0);
        metroService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);
        metroService.printSummary();
    }


}
