package com.example.geektrust.constant;

import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.service.IMetroService;
import com.example.geektrust.service.MetroServiceImpl;

@FunctionalInterface
interface CommandProcessor {
    void process(IMetroService metroService, String[] tokens);
}

public enum Command {
    BALANCE((svc, tokens) -> svc.createMetroCard(tokens[1], Double.valueOf(tokens[2]))),
    CHECK_IN((svc, inputTokens) -> svc.checkIn(inputTokens[1], PassengerType.valueOf(inputTokens[2]), Station.valueOf(inputTokens[3]))),
    PRINT_SUMMARY((svc, inputTokens) -> svc.printSummary());

    private static final IMetroService metroService = new MetroServiceImpl();

    private CommandProcessor commandProcessor;

    Command(CommandProcessor processor) {
        this.commandProcessor = processor;
    }

    public void process(String[] inputTokens) {
        this.commandProcessor.process(metroService, inputTokens);
    }
}
