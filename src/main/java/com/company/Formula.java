package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    public static ArrayList<State> until(ArrayList<State> states, String psyOne, String psyTwo) {
        ArrayList<State> markingTwo = marking(states, psyTwo);

        ArrayList<State> seenBefore = new ArrayList<>();
        ArrayList<State> result = new ArrayList<>();
        ArrayList<State> pool = markingTwo;

        while(pool.size()!=0){
            ArrayList<State> antecedents = new ArrayList<>();
            State q = pool.get(0);
            result.add(pool.get(0));
            pool.remove(0);

            for (State state: states) {
                if (state.transitions.contains(q.name)) antecedents.add(state);
            }
            for (State state: antecedents) {
                if(seenBefore.contains(state)) break;

                seenBefore.add(state);
                if (state.values.contains(psyOne) && !result.contains(state)){
                    pool.add(state);
                }
            }
        }
        return result;
    }
    public static Boolean CaseSix(Structure structure, String value) { return false; }

}
