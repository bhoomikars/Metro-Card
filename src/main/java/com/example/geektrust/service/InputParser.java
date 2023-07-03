package com.example.geektrust.service;

import com.example.geektrust.constant.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputParser {
    public void processInput(String inputFile) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            String lineOfInput = bufferedReader.readLine();
            while (lineOfInput != null) {
                final String[] inputTokens = lineOfInput.split(" ");
                final Command command = Command.valueOf(inputTokens[0]);
                command.process(inputTokens);
                lineOfInput = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("exception");
        }
    }
}
