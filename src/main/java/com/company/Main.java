package com.company;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.Utils.*;

public class Main {

    public static void main(String[] args) {
        try {
            Structure structure = new Structure(readFromFile(PATHFILE));
            System.out.println(structure);

            ArrayList<String> formulas = new ArrayList<>(Arrays.asList(
                "¬(EF(c ∧ d) U EF(a ∧ b))",
                "∀F(a)",
                "¬∀F(a)",
                "∀F(a^b)",
                "∀F(EXa)",
                "∀F(EaUb)",
                "∀F(AaUb)"
            ));

            Formula myFormula = new Formula(structure.states);
            myFormula.subFormulaChecker(formulas.get(0));
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Fin du programme");
        }
    }
}
