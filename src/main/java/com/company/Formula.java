package com.company;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Formula {
    private String quantState;
    private String quantTrans;
    private ArrayList<State> globalResult;
    private Function func = new Function();


    /// GETTER AND SETTER ///
    public String getQuantState() { return quantState; }
    public void setQuantState(String quantState) { this.quantState = quantState; }

    public String getQuantTrans() { return quantTrans; }
    public void setQuantTrans(String quantTrans) { this.quantTrans = quantTrans; }

    public ArrayList<State> getGlobalResult() { return globalResult; }
    public void setGlobalResult(ArrayList<State> globalResult) { this.globalResult = globalResult; }

    public Function getFunc() { return func; }
    public void setFunc(Function func) { this.func = func; }
    /// GETTER AND SETTER ///


    /// CONSTRUCTOR
    public Formula(String formula, ArrayList<State> states)
    {
        getFunc().setStates(states);

        System.out.println("Initial Formula : " + formula);
        System.out.println("Splited Formula : " + convertStringToCharList(formula) );
//        System.out.println("Sub Formulas : " + subFormulaChecker1(formula));
        subFormulaChecker2(formula);

        System.out.println("Results : \n" +
                "Case: " + getFunc().getCaseFunc() + '\n' +
                "States: " + getFunc().getResult());

    }
    /// CONSTRUCTOR


    /// UTILS FUNCTIONS ///

    private AbstractList<Character> convertStringToCharList(String str) {
        return new AbstractList<Character>() {

            @Override
            public Character get(int index) {
                return str.charAt(index);
            }

            @Override
            public int size() {
                return str.length();
            }
        };
    }

    /// UTILS FUNCTIONS ///


    /// FUNCTIONS ///

    private ArrayList<String> subFormulaChecker1(String formula) {
        AbstractList<Character> listFormula = convertStringToCharList(formula);

        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> tempoList = new ArrayList<String>();
        tempoList.add(String.valueOf(listFormula.get(0)));

        for (int i = 1; i < listFormula.size(); i++) {
            String curr = String.valueOf(listFormula.get(i));

            for (int size = 0; size < tempoList.size(); size++) {
                tempoList.set(size, String.valueOf(tempoList.get(size) + curr));
            }

            if (curr.equals("(")) {
                tempoList.add(curr);
            } else if (curr.equals(")")) {
                finalList.add(tempoList.get(tempoList.size() - 1));
                tempoList.remove(tempoList.size() - 1);
            }
        }

        for (int end = tempoList.size() - 1; end >= 0; end--) {
            finalList.add(tempoList.get(tempoList.size() - 1));
        }

        return finalList;
    }

    private void subFormulaChecker2(String formula) {
        AbstractList<Character> listFormula = convertStringToCharList(formula);

//        ArrayList<String> finalList = new ArrayList<String>();
//        ArrayList<String> tempoList = new ArrayList<String>();
//        tempoList.add(String.valueOf(listFormula.get(0)));

        String firstChar = String.valueOf(listFormula.get(0));

        if (firstChar.equals("¬"))
        {
            setQuantState(String.valueOf(listFormula.get(1)));
            setQuantTrans(String.valueOf(listFormula.get(2)));

            getFunc().setCaseFunc("not");

            getFunc().setPhi1(String.valueOf(listFormula.get(4)));
        }
        else
        {
            setQuantState(String.valueOf(listFormula.get(0)));
            setQuantTrans(String.valueOf(listFormula.get(1)));

            ArrayList<String> subFormula = new ArrayList<>();
            for (int i = 3; i < listFormula.size() - 1; i++)
            {
                subFormula.add(String.valueOf(listFormula.get(i)));
            }

            if(subFormula.contains("m"))
            {
                int index = subFormula.indexOf("m");
                if(subFormula.get(index+1).equals("a") && subFormula.get(index+2).equals("r") && subFormula.get(index+3).equals("k"))
                {
                    getFunc().setCaseFunc("marking");
                    getFunc().setPhi1(String.valueOf(listFormula.get(6)));
                }
            }
            else if(subFormula.contains("^"))
            {
                getFunc().setCaseFunc("intersect");
                getFunc().setPhi1(String.valueOf(listFormula.get(3)));
                getFunc().setPhi2(String.valueOf(listFormula.get(5)));
            }
            else if(subFormula.contains("X"))
            {
                getFunc().setCaseFunc("nextTime");
                getFunc().setPhi1(String.valueOf(listFormula.get(5)));

            }
            else if(subFormula.contains("U"))
            {
                if(subFormula.contains("E")) { getFunc().setCaseFunc("untilE"); }
                else { getFunc().setCaseFunc("untilA"); }

                getFunc().setPhi1(String.valueOf(listFormula.get(4)));
                getFunc().setPhi2(String.valueOf(listFormula.get(6)));
            }
            else
            {
                getFunc().setCaseFunc("Nada de nada !");
                getFunc().setPhi1(String.valueOf(listFormula.get(3)));
            }
        }

        getFunc().caseMaker();
    @Override
    public String toString() {
        return "Formula{" +
                "quantState='" + quantState + '\'' +
                ", quantTrans='" + quantTrans + '\'' +
                ", func=" + func +
                '}';
    }
}
