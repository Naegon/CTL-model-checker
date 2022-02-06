package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record FormulaString(String value, Structure structure) {
	private static final String REGEX = "(.+)\\^(.+)";
	static final Pattern pattern = Pattern.compile(REGEX, Pattern.COMMENTS);

	public ArrayList<State> apply() {
		Matcher matcher = pattern.matcher(value);

		System.out.println("\nFormula: " + this.value);

		if (value.charAt(0) == '(' && areParenthesisEnclosing()) { return (removeEnclosingParenthesis().apply()); }

		if (matcher.find()) {
			System.out.println("  Intersect(\"" + matcher.group(1) + "\", \"" + matcher.group(2) + "\")");
			return Cases.intersect(new FormulaString(matcher.group(1), structure).apply(), new FormulaString(matcher.group(2), structure).apply(), structure);
		}

		switch (value.charAt(0)) {
			case '¬' -> {
				FormulaString subFormula = new FormulaString(value.substring(1), structure);
				if (subFormula.value.charAt(0) == '(' && subFormula.areParenthesisEnclosing()) subFormula = subFormula.removeEnclosingParenthesis();
				System.out.println("  not " + subFormula.value);
				return Cases.not(subFormula.apply(), structure);
			}
			case 'E' -> {
				if (value.charAt(1) == 'X') {
					String subFormula = value.substring(3, value.length()-1);
					System.out.println("  next " + subFormula);
					return Cases.nextTime(new FormulaString(subFormula, structure).apply(), structure);
				}
				String subFormula = value.substring(2, value.length()-1);
				Pair subFormulas = getSubFormulas(subFormula);
				System.out.println("  E " + subFormulas.getKey() + " until " + subFormulas.getValue());
				return Cases.untilE(new FormulaString((String) subFormulas.getKey(), structure).apply(), new FormulaString((String) subFormulas.getValue(), structure).apply(), structure);
			}
			case 'A' -> {
				String subFormula = value.substring(2, value.length()-1);
				Pair subFormulas = getSubFormulas(subFormula);
				System.out.println("  A " + subFormulas.getKey() + " until " + subFormulas.getValue());
				return Cases.untilA(new FormulaString((String) subFormulas.getKey(), structure).apply(), new FormulaString((String) subFormulas.getValue(), structure).apply(), structure);
			}
			case 'T' -> {
				return structure.states;
			}
			default -> {
				return Cases.marking(this.value, structure);
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

	public FormulaString removeEnclosingParenthesis() {
		return new FormulaString(value.substring(1, value.length()-1), structure);
	}
}
