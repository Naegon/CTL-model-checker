package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.Utils.*;

public class Main {

    public static void main(String[] args) throws IOException {
//        try {
//            Structure structure = new Structure(readFromFile(PATHFILE));
//            System.out.println(structure);
//            Formula baseFormula = new Formula("AG(EF(a))^c", structure);
//            System.out.println("Base formula: " + baseFormula.getValue());
//            Formula transformedFormula = baseFormula.formulaTransform();
//            System.out.println("Transformed formula: " + transformedFormula.getValue());
//            ArrayList<State> result = transformedFormula.process();
//            System.out.println("\n\n|| ----- Résult: ----- ||\n" + result);
//        }
//        catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
        interfaceCtl();
    }

    private static void interfaceCtl() throws IOException {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        Structure structure = null;
        String CTLFormula = null;

        while(choice!=4){
            System.out.println();
            System.out.println("-------------FORMAL MODELING INTERFACE-----------------");
            System.out.println("1-Choose Schema");
            System.out.println("2-Choose CTL Formula ");
            System.out.println("3-Execute the CTL Formula");
            System.out.println("4-Quit");
            System.out.print("Choice : ");
            choice = scan.nextInt();

            switch (choice){
                case 1: {
                    System.out.println("Choose a schema between 0 and 9");
                    System.out.print("Choice : ");
                    choice = scan.nextInt();
                    structure = new Structure(readFromFile(PATHFILESCHEMA+choice+".json"));
                    choice=0;
                    System.out.println(structure);
                    break;
                }
                case 2:{
                    Object file = readFromFile(PATHFILEFORMULA);
                    JSONObject jsonObject =  (JSONObject) file;
                    JSONArray formulas = (JSONArray) jsonObject.get("formulas");
                    ArrayList<String> formulaCTL = new ArrayList<>();
                    for (Object o : formulas) {
                        JSONObject formula= (JSONObject) o;
                        formulaCTL.add((String) formula.get("formula"));
                        System.out.println(formula.get("name")+":"+formula.get("formula"));
                    }
                    System.out.println("Choose a formula:");
                    System.out.print("Choice : ");
                    choice = scan.nextInt();

                    CTLFormula = formulaCTL.get(choice-1);
                    choice=0;
                    System.out.println("Formula choose:"+CTLFormula);
                    break;
                }
                case 3:{
                    if(structure == null){
                        System.out.println();
                        System.out.println(red("You did not choose a Schema!"));
                        break;
                    }
                    else if(CTLFormula == null){
                        System.out.println();
                        System.out.println(red("You did not choose a CTL Formula!"));
                        break;
                    }

                    try {
                        Formula myFormula = new Formula(CTLFormula, structure).formulaTransform();
                        System.out.println("Base formula: " + CTLFormula);
                        System.out.println("Transformed formula: " + myFormula.getValue());
                        ArrayList<State> result = myFormula.process();
                        System.out.println("\n\n|| ----- Result: ----- ||\n" + result);
                    }
                    catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        System.out.println("---------------System quit-----------------");
    }
}
