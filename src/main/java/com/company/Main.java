package com.company;

import java.util.ArrayList;

import static com.company.Utils.*;

public class Main {

    public static void main(String[] args) {
        try {
            Structure structure = new Structure(readFromFile(PATHFILE));
            System.out.println(structure);

            String formule_a_la_con = "¬(EF(c ∧ d) U EF(a ∧ b))";

            String formula2 = "VF(a)";
            String formula3 = "¬VF(a)";
            String formula4 = "VF(a^b)";
            String formula5 = "VF(EXa)";
            String formula6 = "VF(EaUb)";
            String formula7 = "VF(AaUb)";
            FormulaParser fparser = new FormulaParser(formula7, structure.states);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {

            System.out.println("Fin du programme");
        }
    }
}
