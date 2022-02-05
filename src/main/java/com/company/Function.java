package com.company;

import java.util.ArrayList;

import static com.company.Cases.*;

public class Function {
    private String phi1 = null;
    private String phi2 = null;
    private CasesName caseFunc;
    private ArrayList<State> result;
    private ArrayList<State> states;

    //Nanoformula = idle1 ∧ idle2
    //Microformula = EF(idle1 ∧ idle2)
    //MiniFormula = ¬(EF(idle1 ∧ idle2))
    //MediumFormula = EtrueU¬(EF(idle1 ∧ idle2))
    //MaxiFormula = ¬(EtrueU¬(EF(idle1 ∧ idle2)))

    //MaxiFormula.getResult() = return not(MediumFormula.getResult())

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

    public ArrayList<State> getStates() { return states; }
    public void setStates(ArrayList<State> states) { this.states = states; }

    // TODO: const strings
//    public ArrayList<State> caseMaker() {
//        return switch (caseFunc) {
//            case NOT -> not(getStates(), marking(getStates(), getPhi1()));
//            case MARKING -> marking(getStates(), getPhi1());
//            case INTERSECT -> intersect(marking(getStates(), getPhi1()), marking(getStates(), getPhi2()));
//            case NEXT_TIME -> nextTime(getStates(), marking(getStates(), getPhi1()));
//            case UNTIL_E -> untilE(getStates(), marking(getStates(), getPhi1()), marking(getStates(), getPhi2()));
//            case UNTIL_A -> untilA(getStates(), marking(getStates(), getPhi1()), marking(getStates(), getPhi2()));
//        };
//    }

    @Override
    public String toString() {
        return "Function{" +
                "phi1='" + phi1 + '\'' +
                ", phi2='" + phi2 + '\'' +
                ", caseFunc='" + caseFunc + '\'' +
                '}';
    }

}
