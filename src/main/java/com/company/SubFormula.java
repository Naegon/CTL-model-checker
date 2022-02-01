package com.company;

import java.util.AbstractList;
import java.util.ArrayList;

public class SubFormula
{
    private String initialFormula;
    private AbstractList<Character> splitedInitialFormula;
    private ArrayList<String> listSubFormula = new ArrayList<String>();
    private ArrayList<SubFormula> subFormulas = new ArrayList<SubFormula>();

    private String caseType = "";

    public SubFormula(String formula)
    {
        this.initialFormula = formula;
        this.splitedInitialFormula = convertStringToCharList(this.initialFormula);
        this.listSubFormula = subFormulaChecker1(this.splitedInitialFormula);

        System.out.println("Initial Formula : " + this.initialFormula);
        System.out.println("Splited Formula : " + this.splitedInitialFormula);
        System.out.println("Sub Formulas" + this.listSubFormula);
    }

    private AbstractList<Character> convertStringToCharList(String str)
    {
        return new AbstractList<Character>() {

            @Override
            public Character get(int index)
            {
                return str.charAt(index);
            }

            @Override
            public int size()
            {
                return str.length();
            }
        };
    }

    private ArrayList<String> subFormulaChecker1(AbstractList<Character> listFormula)
    {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> tempoList = new ArrayList<String>();
        tempoList.add(String.valueOf(listFormula.get(0)));

        for (int i = 1; i < listFormula.size(); i++) {
            String curr = String.valueOf(listFormula.get(i));

            for(int size = 0; size < tempoList.size(); size++)
            {
                tempoList.set(size, String.valueOf(tempoList.get(size) + curr));
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
