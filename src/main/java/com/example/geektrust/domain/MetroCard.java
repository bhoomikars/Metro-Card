package com.example.geektrust.domain;

import java.util.Objects;
import java.util.Optional;

public class MetroCard {

    private final String metroCardNumber;

    private Optional<Station> lastStation = Optional.empty();
    private double balance;


    public MetroCard(String metroCardNumber, double balance) {
        this.metroCardNumber = metroCardNumber;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroCard metroCard = (MetroCard) o;
        return Double.compare(metroCard.balance, balance) == 0 && Objects.equals(metroCardNumber, metroCard.metroCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metroCardNumber, balance);
    }

    public double rechargeMetroCard(double moneyRequiredFOrTravel) {
        double moneyToBeRecharged = (moneyRequiredFOrTravel - this.balance);
        recharge(moneyToBeRecharged);
        return moneyToBeRecharged;
    }

    public boolean checkIfEligibleForTravel(Double money) {
        return (this.balance >= money);
    }

    private void recharge(Double money) {
        this.balance += money;
    }

    private void addJourneyHistory(Station station) {
        if (this.lastStation.equals(Optional.empty())) {
            this.lastStation = Optional.ofNullable(station);
            return;
        }
        Station lastStation = this.lastStation.get();
        if (!lastStation.equals(station)) {
            this.lastStation = Optional.empty();
        }
    }


    public boolean isEligibleForDiscount(Station fromStation) {
        return this.lastStation.filter(station -> (!station.equals(fromStation))).orElse(null) != null;
    }

    public void checkIn(double amount, Station station) {
        addJourneyHistory(station);
        balance -= amount;
    }


}
