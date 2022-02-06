package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public record FormulaString(String value, Structure structure) {
	private static final String REGEX = "(.+)\\^(.+)";
	static final Pattern pattern = Pattern.compile(REGEX, Pattern.COMMENTS);

	public ArrayList<State> apply() {
		Matcher matcher = pattern.matcher(value);

		System.out.println("\nFormula: " + this.value);

		if (matcher.find()) {
			System.out.println("  Intersect(\"" + matcher.group(1) + "\", \"" + matcher.group(2) + "\")");
			return Cases.intersect(new FormulaString(matcher.group(1), structure), new FormulaString(matcher.group(2), structure));
		}

		switch (value.charAt(0)) {
			case '¬' -> {
				String subFormula = value.substring(2, value.length()-1);
				System.out.println("  not " + subFormula);
				return Cases.not(new FormulaString(subFormula, structure));
			}
			case 'E' -> {
				if (value.charAt(1) == 'X') {
					String subFormula = value.substring(3, value.length()-1);
					System.out.println("  next " + subFormula);
					return Cases.nextTime(new FormulaString(subFormula, structure));
				}
				String subFormula = value.substring(2, value.length()-1);
				Pair subFormulas = getSubFormulas(subFormula);
				System.out.println("  E " + subFormulas.getKey() + " until " + subFormulas.getValue());
				return Cases.untilE(new FormulaString((String) subFormulas.getKey(), structure), new FormulaString((String) subFormulas.getValue(), structure));
			}
			case 'A' -> {
				String subFormula = value.substring(2, value.length()-1);
				Pair subFormulas = getSubFormulas(subFormula);
				System.out.println("  A " + subFormulas.getKey() + " until " + subFormulas.getValue());
//					new FormulaString(subFormula).apply();
			}
			default -> {
				return(Cases.marking(this));
			}
		}

		System.out.println("\n");
		return null;
	}

	public static Pair getSubFormulas(String subFormula) {
		Pair result;

		String regex = "[¬]?(?:([AE]|EX)?[(].+[)]|[a-z]|[TF])";
		Pattern pattern = Pattern.compile(regex, Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(subFormula);

		if (matcher.find()) {
			String match1 = matcher.group(0);
			matcher.find();
			String match2 = matcher.group(0);

			result = new Pair(match1, match2);
		} else {
			result = new Pair("", "");
		}
		return result;
	}

	public FormulaString formulaTransform(){
		String transformValue = value;
		List<String> test = Arrays.asList("AX","AG","AF","EG","EF","F");
		String[] replace = {"¬EX(¬","¬(E(TU(¬","A(TU","¬AT(U¬","E(TU","TU"};

		for (int i=0;i<test.size();i++){
			transformValue = transformValue.replace(test.get(i), replace[i]);
			transformValue = insertParenthesis(transformValue);
		}
		return new FormulaString(transformValue,structure);
	}
	public String insertParenthesis(String string){
		String newString = string;
		int nestLevel = 0;
		char enconter ='"';
		for (int i = 0; i < newString.length(); i++) {
			if(newString.charAt(i) == '('){
				nestLevel++;
				enconter = '(';
			}
			else if(newString.charAt(i)  == ')'){
				if(nestLevel!=0){
					nestLevel--;
				}
				enconter = ')';
			}

			if(!(newString.charAt(i)  == ')') && (enconter==')')){
				while(nestLevel!=0){
					newString = addChar(newString, ')', i);
					nestLevel--;
				}
				enconter = '"';
			}
		}
		if(enconter==')'){
			while(nestLevel!=0){
				newString = addChar(newString, ')', newString.length());
				nestLevel--;
			}
		}

		System.out.println(newString);
		return newString;
	}

	public String addChar(String str, char ch, int position) {
		return str.substring(0, position) + ch + str.substring(position);
	}
}
