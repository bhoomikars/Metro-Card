package com.example.geektrust;

import com.example.geektrust.service.InputParser;

public class Main {

    public static void main(String[] args) {
        new InputParser().processInput(args[0]);
    }
}
