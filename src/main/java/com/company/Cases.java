package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Cases {

    // Case one: φ=p
    public static ArrayList<State> marking(String transition, Structure structure) {
        ArrayList<State> atomicsStates = new ArrayList<>();

        for (State state: structure.states) {
            if (state.values.contains(transition)) atomicsStates.add(state);
        }

        return atomicsStates;
    }

    /** Case two: φ = ¬ψ
     * @param phi An ArrayList of States satisfying the sub-formula
     * @return An ArrayList of States satisfying the condition
     */
    public static ArrayList<State> not(ArrayList<State> phi, Structure structure) {
        structure.states.removeAll(phi);
        return structure.states;
    }

    // Case three:  φ = ψ1 ^ ψ2
    public static ArrayList<State> intersect(ArrayList<State> phi1, ArrayList<State> phi2, Structure structure) {
        phi1.retainAll(phi2);
        return phi1;
    }

    // Case four: φ = EXψ
    public static ArrayList<State> nextTime(ArrayList<State> phi, Structure structure) {
        ArrayList<State> output = new ArrayList<>();

        for (State state: structure.states) {
            if (state.transitions.stream().anyMatch(phi.stream().map(State::getName).toList()::contains)) {
                output.add(state);
            }
        }

        return output;
    }

    /** Case five: φ = Eψ1 U ψ2
     * @param phi1 An ArrayList of States satisfying the sub-formula
     * @param phi2 An ArrayList of States satisfying the sub-formula
     * @param structure The Kripke structure being studied
     * @return An ArrayList of States satisfying the condition
     */
    public static ArrayList<State> untilE(ArrayList<State> phi1, ArrayList<State> phi2, Structure structure) {
        ArrayList<State> result = new ArrayList<>();

        ArrayList<State> seenBefore = new ArrayList<>(phi2);

        while (phi2.size() != 0) {
            State q = phi2.get(0);
            result.add(phi2.get(0));
            phi2.remove(0);

            ArrayList<State> antecedents = getAntecedents(structure.states, q);

            for (State state: antecedents) {
                if(!seenBefore.contains(state)) {
                    seenBefore.add(state);
                    if (phi1.contains(state) && !result.contains(state)){
                        phi2.add(state);
                    }
                }
            }
        }
        return result;
    }

    // Case 6: φ = Aψ1 U ψ2
    public static ArrayList<State> untilA(ArrayList<State> phi1, ArrayList<State> phi2, Structure structure) {
        ArrayList<State> result = new ArrayList<>();

        Hashtable<String, Integer> dictDegrees = new Hashtable<>();
        for (State state: structure.states) { dictDegrees.put(state.getName(), state.transitions.size()); }

        while(phi2.size()!=0){
            State q = phi2.get(0);
            result.add(phi2.get(0));
            phi2.remove(0);

            ArrayList<State> antecedents = getAntecedents(structure.states, q);

            for (State state: antecedents) {
                dictDegrees.put(state.getName(), dictDegrees.get(state.getName()) - 1);
                if(dictDegrees.get(state.name) == 0 && phi2.contains(state) && !result.contains(state)) phi2.add(state);
            }
        }
        return result;
    }

    private static ArrayList<State> getAntecedents(ArrayList<State> states, State q) {
        ArrayList<State> antecedents = new ArrayList<>();
        for (State state: states) { if (state.transitions.contains(q.name)) antecedents.add(state); }

        return antecedents;
    }

    public enum CasesName { NOT, MARKING, INTERSECT, NEXT_TIME, UNTIL_E, UNTIL_A }

}
