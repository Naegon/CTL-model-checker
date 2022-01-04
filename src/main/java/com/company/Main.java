package com.company;

import static com.company.Utils.*;

public class Main {

    public static void main(String[] args) {
	    Structure structure = new Structure(readFromFile(PATHFILE));
        System.out.println(structure);
    }
}
