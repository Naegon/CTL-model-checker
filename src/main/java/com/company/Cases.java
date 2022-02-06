package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Cases {

    // Case one: φ=p
    public static ArrayList<State> marking(FormulaString formulaString) {
        ArrayList<State> atomicsStates = new ArrayList<>();

        for (State state: formulaString.structure().states) {
            if (state.values.contains(formulaString.value())) atomicsStates.add(state);
        }

        return atomicsStates;
    }

    /** Case two: φ = ¬ψ
     * @param formulaString
     * @return not(subFormula)
     */
    public static ArrayList<State> not(FormulaString formulaString) {
        ArrayList<State> base = formulaString.structure().states;
        base.removeAll(formulaString.apply());
        return base;
    }

    // Case three:  φ = ψ1 ∧ ψ2
    public static ArrayList<State> intersect(FormulaString formula1, FormulaString formula2) {
        ArrayList<State> output = formula1.apply();
        output.retainAll(formula2.apply());
        return output;
    }

    // Case four: φ = EXψ
    public static ArrayList<State> nextTime(FormulaString formulaString) {
        ArrayList<State> output = new ArrayList<>();

        ArrayList<State> markingPhi = formulaString.apply();

        for (State state: formulaString.structure().states) {
            if (state.transitions.stream().anyMatch(markingPhi.stream().map(State::getName).toList()::contains)) {
                output.add(state);
            }
        }

        return output;
    }

    // Case five: φ = Eψ1 U ψ2
    public static ArrayList<State> untilE(FormulaString formula1, FormulaString formula2) {
        ArrayList<State> seenBefore = new ArrayList<>();
        ArrayList<State> result = new ArrayList<>();

        ArrayList<State> markingPhi1 = formula1.apply();
        ArrayList<State> markingPhi2 = formula2.apply();

        while (markingPhi2.size() != 0) {
            State q = markingPhi2.get(0);
            result.add(markingPhi2.get(0));
            markingPhi2.remove(0);

            ArrayList<State> antecedents = getAntecedents(formula1.structure().states, q);

            for (State state: antecedents) {
                if(seenBefore.contains(state)) break;

                seenBefore.add(state);
                if (markingPhi1.contains(state) && !result.contains(state)){
                    markingPhi2.add(state);
                }
            }
        }
        return result;
    }

    // Case 6: φ = Aψ1 U ψ2
    public static ArrayList<State> untilA(FormulaString formula1, FormulaString formula2) {
        ArrayList<State> result = new ArrayList<>();

        Hashtable<String, Integer> dictDegrees = new Hashtable<>();
        for (State state: formula1.structure().states) { dictDegrees.put(state.getName(), state.transitions.size()); }

        ArrayList<State> markingPhi1 = formula1.apply();
        ArrayList<State> markingPhi2 = formula2.apply();

        while(markingPhi2.size()!=0){
            State q = markingPhi2.get(0);
            result.add(markingPhi2.get(0));
            markingPhi2.remove(0);

            ArrayList<State> antecedents = getAntecedents(formula1.structure().states, q);

            for (State state: antecedents) {
                dictDegrees.put(state.getName(), dictDegrees.get(state.getName()) - 1);
                if(dictDegrees.get(state.name) == 0 && markingPhi1.contains(state) && !result.contains(state)) markingPhi2.add(state);
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
