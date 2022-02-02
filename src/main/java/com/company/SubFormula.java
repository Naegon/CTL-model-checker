package com.company;

import java.util.AbstractList;
import java.util.ArrayList;

public class SubFormula
{
    private String initialFormula;
    private AbstractList<Character> splitInitialFormula;
    private ArrayList<String> listSubFormula;
    private ArrayList<SubFormula> subFormulas = new ArrayList<>();

    private String caseType = "";

    public SubFormula(String formula) {
        this.initialFormula = formula;
        this.splitInitialFormula = convertStringToCharList(this.initialFormula);
        this.listSubFormula = subFormulaChecker1(this.splitInitialFormula);

        System.out.println("Initial Formula : " + this.initialFormula);
        System.out.println("Splited Formula : " + this.splitInitialFormula);
        System.out.println("Sub Formulas" + this.listSubFormula);
    }

    private AbstractList<Character> convertStringToCharList(String str) {
        return new AbstractList<>() {

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

    private ArrayList<String> subFormulaChecker1(AbstractList<Character> listFormula) {
        ArrayList<String> finalList = new ArrayList<>();
        ArrayList<String> tempoList = new ArrayList<>();
        tempoList.add(String.valueOf(listFormula.get(0)));

        for (int i = 1; i < listFormula.size(); i++) {
            String curr = String.valueOf(listFormula.get(i));

            for(int size = 0; size < tempoList.size(); size++)
            {
                tempoList.set(size, tempoList.get(size) + curr);
            }

            if (curr.equals("("))
            {
                tempoList.add(curr);
            }
            else if (curr.equals(")"))
            {
                finalList.add(tempoList.get(tempoList.size() - 1));
                tempoList.remove(tempoList.size() - 1);
            }
        }

        for (int end = tempoList.size() - 1; end >= 0; end --)
        {
            finalList.add(tempoList.get(tempoList.size() - 1));
        }

        return finalList;
    }

}
