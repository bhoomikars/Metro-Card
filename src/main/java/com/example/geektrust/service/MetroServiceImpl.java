package com.example.geektrust.service;

import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MetroServiceImpl implements IMetroService {

    private final Map<String, MetroCard> metroCardMap = new HashMap<>();

    public Map<String, MetroCard> getMetroCardMap() {
        return metroCardMap;
    }

    private static final Map<PassengerType, Double> TRIP_CHARGES = new HashMap<>();

    private static final double DISCOUNT_PERCENTAGE = 0.5;


    static {
        TRIP_CHARGES.put(PassengerType.ADULT, Double.valueOf(200));
        TRIP_CHARGES.put(PassengerType.KID, Double.valueOf(50));
        TRIP_CHARGES.put(PassengerType.SENIOR_CITIZEN, Double.valueOf(100));
    }

    @Override
    public MetroCard createMetroCard(String metroCardId, Double balance) {
        MetroCard metroCard = new MetroCard(metroCardId, balance);
        metroCardMap.putIfAbsent(metroCardId, metroCard);
        return metroCardMap.get(metroCardId);
    }

    @Override
    public void checkIn(String metroCardId, PassengerType passengerType, Station station) {
        if (!metroCardMap.containsKey(metroCardId)) {
            return;
        }
        MetroCard metroCard = metroCardMap.get(metroCardId);
        Pair<Double, Double> travelChargeAndDiscount = getEffectiveChargeForTravel(metroCard, passengerType, station);
        double rechargedAmount = 0;
        if (metroCard.checkIfEligibleForTravel(travelChargeAndDiscount.getLeft())) {
            station.checkIn(metroCard, passengerType, travelChargeAndDiscount.getLeft());
        } else {
            rechargedAmount = metroCard.rechargeMetroCard(travelChargeAndDiscount.getLeft());
            station.checkIn(metroCard, passengerType, travelChargeAndDiscount.getLeft());
        }

        station.collectExpenseAtTheStation(rechargedAmount, travelChargeAndDiscount.getLeft(), travelChargeAndDiscount.getRight());// TODO : calculate discount
    }

    private Pair<Double, Double> getEffectiveChargeForTravel(MetroCard metroCard, PassengerType passengerType, Station station) {
        double charges = TRIP_CHARGES.get(passengerType);
        double discountApplied = 0;

        boolean isEligibleForDiscount = metroCard.isEligibleForDiscount(station);
        if (isEligibleForDiscount) {
            discountApplied = (DISCOUNT_PERCENTAGE * charges);
            charges -= discountApplied;
        }
        return Pair.of(charges, discountApplied);
    }

    @Override
    public void printSummary() {
        Arrays.stream(Station.values()).forEach(station -> station.printStationSummary());
    }
}
