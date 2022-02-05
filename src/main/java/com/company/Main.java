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
                "¬(EF(c∧d)UEF(a∧b))",
                "∀F(a)",
                "¬∀F(a)",
                "∀F(a^b)",
                "∀F(EXa)",
                "∀F(EaUc)",
                "∀F(AaUc)"
            ));

            Formula myFormula = new Formula(structure.states);
            myFormula.formulaMaker(formulas.get(6));
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Fin du programme");
        }
    }
}
