package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formula {
	private static final String REGEX = "(.+)\\^(.+)";
	static final Pattern pattern = Pattern.compile(REGEX, Pattern.COMMENTS);

	private String value;
	private final Structure structure;

	public Formula(String value, Structure structure) {
		this.value = value;
		this.structure = structure;
	}

	public Formula setValue(String value) {
		this.value = value;
		return this;
	}

	public ArrayList<State> process() {
		if (value.charAt(0) == '(' && areParenthesisEnclosing()) {
			removeEnclosingParenthesis();
		}

		Matcher matcher = pattern.matcher(value);
		String baseFormula = value;

		if (matcher.find()) {
			ArrayList<State> result = Cases.intersect(setValue(matcher.group(1)).process(), setValue(matcher.group(2)).process());
			System.out.println("\nFormula: " + baseFormula);
			System.out.println("  Intersect(\"" + matcher.group(1) + "\", \"" + matcher.group(2) + "\")");
			System.out.println("Intersect: " + result);
			return result;
		}

		switch (value.charAt(0)) {
			case '¬' -> {
				setValue(value.substring(1));
				if (value.charAt(0) == '(' && areParenthesisEnclosing()) removeEnclosingParenthesis();

				ArrayList<State> result = Cases.not(process(), structure);
				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  not " + value);
				System.out.println(" -> " + result);
				return result;
			}
			case 'E' -> {
				if (value.charAt(1) == 'X') {
					String subFormula = value.substring(3, value.length()-1);

					ArrayList<State> result = Cases.nextTime(setValue(subFormula).process(), structure);
					System.out.println("\nFormula: " + baseFormula);
					System.out.print("  next " + subFormula);
					System.out.println(" -> " + result);
					return result;
				}
				String subFormula = value.substring(2, value.length()-1);
				Pair<String, String> subFormulas = getSubFormulas(subFormula);

				ArrayList<State> result = Cases.untilE(setValue((String) subFormulas.getKey()).process(), setValue((String) subFormulas.getValue()).process(), structure);
				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  E " + subFormulas.getKey() + " until " + subFormulas.getValue());
				System.out.println(" -> " + result);
				return result;
			}
			case 'A' -> {
				String subFormula = value.substring(2, value.length()-1);
				Pair<String, String> subFormulas = getSubFormulas(subFormula);

				ArrayList<State> result = Cases.untilA(setValue((String) subFormulas.getKey()).process(), setValue((String) subFormulas.getValue()).process(), structure);
				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  A " + subFormulas.getKey() + " until " + subFormulas.getValue());
				System.out.println(" -> " + result);
				return result;
			}
			case 'T' -> {
				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  All states");
				System.out.println(" -> " + structure.states);
				return structure.states;
			}
			default -> {
				ArrayList<State> result = Cases.marking(value, structure);

				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  Marking " + value);
				System.out.println(" -> " + result);
				return result;
			}
		}
	}

	public static Pair<String, String> getSubFormulas(String subFormula) {
		Pair<String, String> result;

		String regex = "[¬]?(?:([AE]|EX)?[(].+[)]|[a-z]|[TF])";
		Pattern pattern = Pattern.compile(regex, Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(subFormula);

		if (matcher.find()) {
			String match1 = matcher.group(0);
			matcher.find();
			String match2 = matcher.group(0);

			result = new Pair<>(match1, match2);
		} else {
			result = new Pair<>("", "");
		}
		return result;
	}

	public Boolean areParenthesisEnclosing() {
		int nestLevel = 0;
		char[] charArray = value.toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '(') { nestLevel ++; }
			else if (charArray[i] == ')') { nestLevel --; }
			if (nestLevel == 0) { return (i == charArray.length-1); }
		}

		return false;
	}

	public void removeEnclosingParenthesis() {
		setValue(value.substring(1, value.length()-1));
	}

	public String getValue() {
		return value;
	}

	public Formula formulaTransform() {
		String transformValue = value;
		List<String> test = Arrays.asList("AX", "AG", "AF", "EG", "EF");
		String[] replace = { "¬EX(¬", "¬(E(TU(¬", "A(TU", "¬A(TU¬", "E(TU"};

		for (int i=0;i<test.size();i++){
			transformValue = transformValue.replace(test.get(i), replace[i]);
			transformValue = insertParenthesis(transformValue);
		}

		return new Formula(transformValue, structure);
	}

	public String insertParenthesis(String string){
		String newString = string;
		int nestLevel = 0;
		char encounter = '"';

		for (int i = 0; i < newString.length(); i++) {
			if(newString.charAt(i) == '('){
				nestLevel++;
				encounter = '(';
			}
			else if(newString.charAt(i)  == ')'){
				if(nestLevel!=0){
					nestLevel--;
				}
				encounter = ')';
			}

			if(!(newString.charAt(i)  == ')') && (encounter==')')){
				while(nestLevel!=0){
					newString = addChar(newString, ')', i);
					nestLevel--;
				}
				encounter = '"';
			}
		}
		if(encounter==')'){
			while(nestLevel!=0){
				newString = addChar(newString, ')', newString.length());
				nestLevel--;
			}
		}

		return newString;
	}

	public String addChar(String str, char ch, int position) {
		return str.substring(0, position) + ch + str.substring(position);
	}

	public Boolean statisfyInital(ArrayList<State> result){
		if(result.contains(structure.initialStates)){
			return true;
		}
		return false;
	}

}
