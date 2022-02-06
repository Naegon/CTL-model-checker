package com.company;

import java.util.ArrayList;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;

public class Main {

    public static void main(String[] args) {
        try {
            Structure structure = new Structure(readFromFile(PATHFILE));
            System.out.println(structure);
            Formula baseFormula = new Formula("AG(EF(a))^c", structure);
            System.out.println("Base formula: " + baseFormula.getValue());
            Formula transformedFormula = baseFormula.formulaTransform();
            System.out.println("Transformed formula: " + transformedFormula.getValue());
            ArrayList<State> result = transformedFormula.process();
            System.out.println("\n\n|| ----- Résult: ----- ||\n" + result);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
