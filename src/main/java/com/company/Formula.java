package com.company;

import java.util.ArrayList;

import static com.company.Cases.CasesName.*;

public class Formula {
    private String initialFormula;
    private String quantState;
    private String quantTrans;
    private final Function func;
    private ArrayList<State> states;
    private ArrayList<State> finalResult;


    /// GETTER AND SETTER ///
    public void setQuantState(String quantState) { this.quantState = quantState; }
    public void setQuantTrans(String quantTrans) { this.quantTrans = quantTrans; }
    public ArrayList<State> getFinalResult() { return finalResult; }
    public Function getFunc() { return func; }
/// GETTER AND SETTER ///

    /// CONSTRUCTOR
    public Formula(ArrayList<State> states)
    {
        func = new Function(states);
    }
    public Formula(String quantState, String quantTrans, Function func) {
        this.quantState = quantState;
        this.quantTrans = quantTrans;
        this.func = func;
    }
    /// CONSTRUCTOR


    public ArrayList<State> getResult(String formula)
    {
        String newformula = transformFormula(formula);
        //Check if subFormula
            //if yes
                //return formulaMaker(subformula)
            //else
                //return formulaMaker(newformula)

        return formulaMaker(newformula);
    }

    public String transformFormula(String formula)
    {
        String newFormula = "";
        //Transform
        return newFormula;
    }

//    public Cases.CasesName getCase(Formula value)
//    {
//        //Check case majoritaire
//
//    }

    public ArrayList<State> formulaMaker(String formula) {
        String firstChar = String.valueOf(formula.charAt(0));

        // TODO: Transformer
        // TODO: Trouver s'il y a des sous-formules

        if (firstChar.equals("¬")) {
            setQuantState(String.valueOf(formula.charAt(1)));
            setQuantTrans(String.valueOf(formula.charAt(2)));

            func.setCaseFunc(NOT);
            func.setPhi1(String.valueOf(formula.charAt(4)));

//            return func.caseMaker();
            return null;
        }

        setQuantState(String.valueOf(formula.charAt(0)));
        setQuantTrans(String.valueOf(formula.charAt(1)));

        ArrayList<String> subFormula = new ArrayList<>();
        for (int i = 3; i < formula.length() - 1; i++) {
            subFormula.add(String.valueOf(formula.charAt(i)));
        }

        if (subFormula.contains("mark")) {
            func.setCaseFunc(MARKING);
            func.setPhi1(String.valueOf(formula.charAt(6)));
        }

        else if (subFormula.contains("^")) {
            func.setCaseFunc(INTERSECT);
            func.setPhi1(String.valueOf(formula.charAt(3)));
            func.setPhi2(String.valueOf(formula.charAt(5)));
        }

        else if (subFormula.contains("X")) {
            func.setCaseFunc(NEXT_TIME);
            func.setPhi1(String.valueOf(formula.charAt(5)));
        }

        else if (subFormula.contains("U")) {
            if (subFormula.contains("E")) { func.setCaseFunc(UNTIL_E); }
            else { func.setCaseFunc(UNTIL_A); }

            func.setPhi1(String.valueOf(formula.charAt(4)));
            func.setPhi2(String.valueOf(formula.charAt(6)));
        }

        else {
            func.setCaseFunc(MARKING);
            func.setPhi1(String.valueOf(formula.charAt(3)));
        }

        System.out.println("Results: \n" +
                "Case: " + func.getCaseFunc() + '\n' +
                "States: " + getFinalResult());

//        return func.caseMaker();
        return null;
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
