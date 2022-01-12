package com.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public abstract class Formula {
    // Case one: φ=p
    public static ArrayList<State> marking(ArrayList<State> states, String value) {
        ArrayList<State> atomicsStates = new ArrayList<>();

        for (State state: states) {
            if (state.values.contains(value)) atomicsStates.add(state);
        }

        return atomicsStates;
    }

    public static ArrayList<State> not(ArrayList<State> states, String value) {
        states.removeAll(marking(states, value));
        return states;
    }

    public static ArrayList<State> intersect(ArrayList<State> states, String valuesOne, String valuesTwo) {
        ArrayList<State> output = marking(states, valuesOne);
        output.retainAll(marking(states, valuesTwo));
        return output;
    }

    public static ArrayList<State> nextTime(ArrayList<State> states, String value) {
        ArrayList<State> marking = marking(states, value);
        ArrayList<State> output = new ArrayList<>();

        for (State state: states) {
            if (state.transitions.stream().anyMatch(marking.stream().map(State::getName).collect(Collectors.toList())::contains)) {
                output.add(state);
            }
        }

        return output;
    }

    public static Boolean CaseFive(Structure structure, String value) { return false; }
    public static Boolean CaseSix(Structure structure, String value) { return false; }

}
