package com.example.geektrust.service;

import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;

public interface IMetroService {

    MetroCard createMetroCard(String name, Double balance);

    void checkIn(String metroCardId, PassengerType passengerType, Station station);

    void printSummary();

}
