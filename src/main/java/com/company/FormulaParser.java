package com.company;

import java.util.AbstractList;
import java.util.ArrayList;

public class FormulaParser
{
    public FormulaParser(String formula, ArrayList<State> states)
    {
        Formula myFormula = new Formula(formula, states);
    }
}
