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
                "VF(a)",
                "¬VF(a)",
                "VF(a^b)",
                "VF(EXa)",
                "VF(EaUb)",
                "VF(AaUb)"
            ));

            new Formula(formulas.get(3), structure.states);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Fin du programme");
        }
    }
}
