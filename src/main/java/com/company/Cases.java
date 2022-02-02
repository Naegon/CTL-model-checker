package com.company;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.Collectors;

public abstract class Cases {

    // Case one: φ=p
    public static ArrayList<State> marking(ArrayList<State> states, String value) {
        ArrayList<State> atomicsStates = new ArrayList<>();

        for (State state: states) {
            if (state.values.contains(value)) atomicsStates.add(state);
        }

        return atomicsStates;
    }

    // Case two: φ = ¬ψ
    public static ArrayList<State> not(ArrayList<State> states, String value) {
        states.removeAll(marking(states, value));
        return states;
    }

    // Case three:  φ = ψ1 ∧ ψ2
    public static ArrayList<State> intersect(ArrayList<State> states, String valuesOne, String valuesTwo) {
        ArrayList<State> output = marking(states, valuesOne);
        output.retainAll(marking(states, valuesTwo));
        return output;
    }

    // Case four: φ = EXψ
    public static ArrayList<State> nextTime(ArrayList<State> states, String value) {
        ArrayList<State> marking = marking(states, value);
        ArrayList<State> output = new ArrayList<>();

        for (State state: states) {
            if (state.transitions.stream().anyMatch(marking.stream().map(State::getName).toList()::contains)) {
                output.add(state);
            }
        }

        return output;
    }

    // Case five: φ = Eψ1 U ψ2
    public static ArrayList<State> untilE(ArrayList<State> states, String psyOne, String psyTwo) {
        ArrayList<State> markingTwo = marking(states, psyTwo);

        ArrayList<State> seenBefore = new ArrayList<>();
        ArrayList<State> result = new ArrayList<>();
        ArrayList<State> pool = markingTwo;

        while(pool.size()!=0){
            State q = pool.get(0);
            result.add(pool.get(0));
            pool.remove(0);

            ArrayList<State> antecedents = getAntecedents(states, q);

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

    // Case 6: φ = Aψ1 U ψ2
    public static ArrayList<State> untilA(ArrayList<State> states, String psyOne, String psyTwo) {
        ArrayList<State> markingOne = marking(states, psyOne);
        ArrayList<State> markingTwo = marking(states, psyTwo);

        ArrayList<State> result = new ArrayList<>();
        ArrayList<State> pool = markingTwo;

        Hashtable<String, Integer> dictDegrees = new Hashtable<>();
        for (State state: states) { dictDegrees.put(state.getName(), state.transitions.size()); }

        while(pool.size()!=0){
            State q = pool.get(0);
            result.add(pool.get(0));
            pool.remove(0);

            ArrayList<State> antecedents = getAntecedents(states, q);

            for (State state: antecedents) {
                dictDegrees.put(state.getName(), dictDegrees.get(state.getName()) - 1);
                if(dictDegrees.get(state.name) == 0 && markingOne.contains(state) && !result.contains(state)) pool.add(state);
            }
        }
        return result;
    }

    private static ArrayList<State> getAntecedents(ArrayList<State> states, State q) {
        ArrayList<State> antecedents = new ArrayList<>();
        for (State state: states) { if (state.transitions.contains(q.name)) antecedents.add(state); }

        return antecedents;
    }

}
