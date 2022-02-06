package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaString {
	private static final String REGEX = "(.+)\\^(.+)";
	static final Pattern pattern = Pattern.compile(REGEX, Pattern.COMMENTS);

	private String value;
	private final Structure structure;

	public FormulaString(String value, Structure structure) {
		this.value = value;
		this.structure = structure;
	}

	public FormulaString setValue(String value) {
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
			ArrayList<State> result = Cases.intersect(setValue(matcher.group(1)).process(), setValue(matcher.group(2)).process(), structure);
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
				Pair subFormulas = getSubFormulas(subFormula);

				ArrayList<State> result = Cases.untilE(setValue((String) subFormulas.getKey()).process(), setValue((String) subFormulas.getValue()).process(), structure);
				System.out.println("\nFormula: " + baseFormula);
				System.out.print("  E " + subFormulas.getKey() + " until " + subFormulas.getValue());
				System.out.println(" -> " + result);
				return result;
			}
			case 'A' -> {
				String subFormula = value.substring(2, value.length()-1);
				Pair subFormulas = getSubFormulas(subFormula);

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
}
