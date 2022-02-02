package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Function
{
    private String phi1 = null;
    private String phi2 = null;
    private String caseFunc;
    private ArrayList<State> result;
    private ArrayList<State> states;

    // TODO: one function to set (caseFunc, Phi1, Phi2?)
    public String getPhi1() { return phi1; }
    public void setPhi1(String phi1) { this.phi1 = phi1; }

    public String getPhi2() { return phi2; }
    public void setPhi2(String phi2) { this.phi2 = phi2; }

    public String getCaseFunc() { return caseFunc; }
    public void setCaseFunc(String caseFunc) { this.caseFunc = caseFunc; }

    public ArrayList<State> getResult() { return result; }
    public void setResult(ArrayList<State> result) { this.result = result; }

    public ArrayList<State> getStates() { return states; }
    public void setStates(ArrayList<State> states) { this.states = states; }

    // TODO: const strings
    public void caseMaker() {
        switch (getCaseFunc()) {
            case "not" -> setResult(Cases.not(getStates(), getPhi1()));
            case "marking" -> setResult(Cases.marking(getStates(), getPhi1()));
            case "intersect" -> setResult(Cases.intersect(getStates(), getPhi1(), getPhi2()));
            case "nextTime" -> setResult(Cases.nextTime(getStates(), getPhi1()));
            case "untilE" -> setResult(Cases.untilE(getStates(), getPhi1(), getPhi2()));
            case "untilA" -> setResult(Cases.untilA(getStates(), getPhi1(), getPhi2()));
            default -> {
                System.out.println("Attention mal ecrit !");
                setResult(Cases.marking(getStates(), getPhi1()));
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