package com.company;

import java.util.ArrayList;

public class Formula {
    private String quantState;
    private String quantTrans;
    private final Function func;

    /// GETTER AND SETTER ///
    public void setQuantState(String quantState) { this.quantState = quantState; }
    public void setQuantTrans(String quantTrans) { this.quantTrans = quantTrans; }
    /// GETTER AND SETTER ///

    /// CONSTRUCTOR
    public Formula(ArrayList<State> states) {
        func = new Function(states);
        func.setStates(states);
    }

    public Formula(String quantState, String quantTrans, Function func) {
        this.quantState = quantState;
        this.quantTrans = quantTrans;
        this.func = func;
    }
    /// CONSTRUCTOR

    public void subFormulaChecker2(String formula) {
        String firstChar = String.valueOf(formula.charAt(0));

        // TODO: appel recursif (négation du résultat de l'appel sur une subformula)
        if (firstChar.equals("¬")) {
            setQuantState(String.valueOf(formula.charAt(1)));
            setQuantTrans(String.valueOf(formula.charAt(2)));

            func.setCaseFunc("not");
            func.setPhi1(String.valueOf(formula.charAt(4)));

            func.caseMaker();
            return;
        }

        setQuantState(String.valueOf(formula.charAt(0)));
        setQuantTrans(String.valueOf(formula.charAt(1)));

        ArrayList<String> subFormula = new ArrayList<>();
        for (int i = 3; i < formula.length() - 1; i++) {
            subFormula.add(String.valueOf(formula.charAt(i)));
        }

        if (subFormula.contains("mark")) {
            func.setCaseFunc("marking");
            func.setPhi1(String.valueOf(formula.charAt(6)));
        }

        else if (subFormula.contains("^")) {
            func.setCaseFunc("intersect");
            func.setPhi1(String.valueOf(formula.charAt(3)));
            func.setPhi2(String.valueOf(formula.charAt(5)));
        }

        else if (subFormula.contains("X")) {
            func.setCaseFunc("nextTime");
            func.setPhi1(String.valueOf(formula.charAt(5)));
        }

        else if (subFormula.contains("U")) {
            if (subFormula.contains("E")) { func.setCaseFunc("untilE"); }
            else { func.setCaseFunc("untilA"); }

            func.setPhi1(String.valueOf(formula.charAt(4)));
            func.setPhi2(String.valueOf(formula.charAt(6)));
        }

        else {
            func.setCaseFunc("Nada de nada !");
            func.setPhi1(String.valueOf(formula.charAt(3)));
        }

        func.caseMaker();

        System.out.println("Results : \n" +
                "Case: " + func.getCaseFunc() + '\n' +
                "States: " + func.getResult());
    }

    @Override
    public String toString() {
        return "Formula{" +
                "quantState='" + quantState + '\'' +
                ", quantTrans='" + quantTrans + '\'' +
                ", func=" + func +
                '}';
    }
}
