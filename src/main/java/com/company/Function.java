package com.company;

import java.util.ArrayList;

import static com.company.Cases.*;

public class Function {
    private String phi1 = null;
    private String phi2 = null;
    private CasesName caseFunc;
    private ArrayList<State> result;
    private ArrayList<State> states;

    public Function(String phi1, String phi2, CasesName caseFunc, ArrayList<State> result) {
        this.phi1 = phi1;
        this.phi2 = phi2;
        this.caseFunc = caseFunc;
        this.result = result;
    }

    public Function(ArrayList<State> states) { this.states = states; }

    // TODO: one function to set (caseFunc, Phi1, Phi2?)
    public String getPhi1() { return phi1; }
    public void setPhi1(String phi1) { this.phi1 = phi1; }

    public String getPhi2() { return phi2; }
    public void setPhi2(String phi2) { this.phi2 = phi2; }

    public CasesName getCaseFunc() { return caseFunc; }
    public void setCaseFunc(CasesName caseFunc) { this.caseFunc = caseFunc; }

    public ArrayList<State> getResult() { return result; }
    public void setResult(ArrayList<State> result) { this.result = result; }

    public ArrayList<State> getStates() { return states; }
    public void setStates(ArrayList<State> states) { this.states = states; }

    // TODO: const strings
    public void caseMaker() {
        switch (caseFunc) {
            case NOT -> setResult(not(getStates(), getPhi1()));
            case MARKING -> setResult(marking(getStates(), getPhi1()));
            case INTERSECT -> setResult(intersect(getStates(), getPhi1(), getPhi2()));
            case NEXT_TIME -> setResult(nextTime(getStates(), getPhi1()));
            case UNTIL_E -> setResult(untilE(getStates(), getPhi1(), getPhi2()));
            case UNTIL_A -> setResult(untilA(getStates(), getPhi1(), getPhi2()));
            case DEFAULT -> {
                System.out.println("Attention mal ecrit !");
                setResult(marking(getStates(), getPhi1()));
            }
        }
    }

    @Override
    public String toString() {
        return "Function{" +
                "phi1='" + phi1 + '\'' +
                ", phi2='" + phi2 + '\'' +
                ", caseFunc='" + caseFunc + '\'' +
                ", result=" + result +
                '}';
    }

}
