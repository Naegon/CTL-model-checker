package com.company;

import static com.company.Utils.*;

public class Main {

    public static void main(String[] args) {
        try {
            Structure structure = new Structure(readFromFile(PATHFILE));
            System.out.println(structure);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Fin du programme");
        }
    }
}
