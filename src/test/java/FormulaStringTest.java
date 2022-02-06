import com.company.FormulaString;
import com.company.State;
import com.company.Structure;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.Assert.*;

public class FormulaStringTest {
	private Structure structure;

	@Before
	public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

	@Test
	public void test_case1_marking() {
		// Given
		FormulaString formula = new FormulaString("a", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S1",
					new ArrayList<>(Arrays.asList("a", "f")),
					new ArrayList<>(Arrays.asList("S3", "S4"))
				),
				new State("S3",
					new ArrayList<>(Arrays.asList("a", "b")),
					new ArrayList<>(Arrays.asList("S6", "S7"))
				),
				new State("S7",
					new ArrayList<>(Arrays.asList("a", "d")),
					new ArrayList<>(List.of("S1"))
				)
			));

		assertEquals(expected, output);
	}

	@Test
	public void test_case2_not() {
		// Given
		FormulaString formula = new FormulaString("¬a", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S0",
					new ArrayList<>(Arrays.asList("e", "f")),
					new ArrayList<>(Arrays.asList("S1", "S2"))
				),
				new State("S2",
					new ArrayList<>(Arrays.asList("b", "e")),
					new ArrayList<>(Arrays.asList("S3", "S5"))
				),
				new State("S4",
					new ArrayList<>(List.of("c")),
					new ArrayList<>(Arrays.asList("S0", "S6"))
				),
				new State("S5",
					new ArrayList<>(List.of("d")),
					new ArrayList<>(Arrays.asList("S0", "S7"))
				),
				new State("S6",
					new ArrayList<>(Arrays.asList("b", "c")),
					new ArrayList<>(List.of("S2"))
				)
			));

		assertEquals(expected, output);
	}

	@Test
	public void test_case3_intersect() {
		// Given
		FormulaString formula = new FormulaString("a^b", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S3",
					new ArrayList<>(Arrays.asList("a", "b")),
					new ArrayList<>(Arrays.asList("S6", "S7"))
				)
			));

		assertEquals(expected, output);
	}

	@Test
	public void test_case4_next() {
		// Given
		FormulaString formula = new FormulaString("EX(a)", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S0",
					new ArrayList<>(Arrays.asList("e", "f")),
					new ArrayList<>(Arrays.asList("S1", "S2"))
				),
				new State("S1",
					new ArrayList<>(Arrays.asList("a", "f")),
					new ArrayList<>(Arrays.asList("S3", "S4"))
				),
				new State("S2",
					new ArrayList<>(Arrays.asList("b", "e")),
					new ArrayList<>(Arrays.asList("S3", "S5"))
				),
				new State("S3",
					new ArrayList<>(Arrays.asList("a", "b")),
					new ArrayList<>(Arrays.asList("S6", "S7"))
				),
				new State("S5",
					new ArrayList<>(List.of("d")),
					new ArrayList<>(Arrays.asList("S0", "S7"))
				),
				new State("S7",
					new ArrayList<>(Arrays.asList("a", "d")),
					new ArrayList<>(List.of("S1"))
				)
			));

		assertEquals(expected, output);
	}

	@Test
	public void test_case5_untilE() {
		// Given
		FormulaString formula = new FormulaString("E(aUc)", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S4",
						new ArrayList<>(List.of("c")),
						new ArrayList<>(Arrays.asList("S0", "S6"))
				),
				new State("S6",
						new ArrayList<>(Arrays.asList("b", "c")),
						new ArrayList<>(List.of("S2"))
				),
				new State("S1",
					new ArrayList<>(Arrays.asList("a", "f")),
					new ArrayList<>(Arrays.asList("S3", "S4"))
				),
				new State("S3",
					new ArrayList<>(Arrays.asList("a", "b")),
					new ArrayList<>(Arrays.asList("S6", "S7"))
				),
				new State("S7",
					new ArrayList<>(Arrays.asList("a", "d")),
					new ArrayList<>(List.of("S1"))
				)
			));

		assertEquals(expected, output);
	}

	@Test
	public void test_case6_untilA() {
		// Given
		FormulaString formula = new FormulaString("A(aUc)", structure);

		// When
		ArrayList<State> output = formula.apply();

		// Then
		ArrayList<State> expected = new ArrayList<>(
			List.of(
				new State("S4",
					new ArrayList<>(List.of("c")),
					new ArrayList<>(Arrays.asList("S0", "S6"))
				),
				new State("S6",
					new ArrayList<>(Arrays.asList("b", "c")),
					new ArrayList<>(List.of("S2"))
				)
			));

		assertEquals(expected, output);
	}



//	@Test
//	public void test_formula_1() {
//		// Given
//		FormulaString formula = new FormulaString("¬(E(TU¬(E(TU(a∧b)))))", structure);
//
//		// When
//		ArrayList<State> output = formula.apply();
//
//		// Then
//		ArrayList<State> expected = new ArrayList<>(
//			List.of(
//				new State("S1",
//					new ArrayList<>(Arrays.asList("a", "c")),
//					new ArrayList<>(Arrays.asList("S2", "S3"))
//				),
//				new State("S2",
//					new ArrayList<>(Arrays.asList("a", "b")),
//					new ArrayList<>(List.of("S4"))
//				),
//				new State("S3",
//					new ArrayList<>(List.of("a")),
//					new ArrayList<>(List.of("S4"))
//				)
//			)
//		);
//
//		assertEquals(expected, output);
//	}
//
//	@Test
//	public void test_formula_2() {
//		// Given
//		FormulaString formula = new FormulaString("¬(E(TU(E(TU(¬(E(TU¬(b))))))))", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_3() {
//		// Given
//		FormulaString formula = new FormulaString("EX(a^b)", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_4() {
//		// Given
//		FormulaString formula = new FormulaString("¬(a^b)", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_5() {
//		// Given
//		FormulaString formula = new FormulaString("¬(E(TU¬(E(TU(a ∧ b)))))^c", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_6() {
//		// Given
//		FormulaString formula = new FormulaString("(¬(E(TU¬(E(TU(a ∧ b)))))^c)^¬(E(TU(E(TU(¬(E(TU¬(b))))))))", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_7() {
//		// Given
//		FormulaString formula = new FormulaString("a^(¬(E(TU¬(¬(b)))))", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_8() {
//		// Given
//		FormulaString formula = new FormulaString("a^(E(TU(¬(b))))", structure);
//
//		// When
//		formula.apply();
//	}
//
//	@Test
//	public void test_formula_9() {
//		// Given
//		FormulaString formula = new FormulaString("(a^b)", structure);
//
//		// When
//		formula.apply();
//	}
//
	@Test
	public void test_getSubFormula1() {
		FormulaString formula = new FormulaString("TU¬(E(TU(a∧b)))", structure);

		// When
		Pair output = FormulaString.getSubFormulas(formula.value());

		// Then
		assertEquals(new Pair("T", "¬(E(TU(a∧b)))"), output);
	}

	@Test
	public void test_getSubFormula2() {
		FormulaString formula = new FormulaString("¬(E(TU(a∧b)))UT", structure);

		// When
		Pair output = FormulaString.getSubFormulas(formula.value());

		// Then
		assertEquals(new Pair("¬(E(TU(a∧b)))", "T"), output);
	}

	@Test
	public void test_areParenthesisEnclosing1() {
		// Given
		FormulaString formula = new FormulaString("(¬(E(TU(a∧b)))UT)", structure);

		// Then
		assertTrue(formula.areParenthesisEnclosing());
	}

	@Test
	public void test_areParenthesisEnclosing2() {
		// Given
		FormulaString formula = new FormulaString("(¬(E(TU(a∧b)))UT)^c", structure);

		// Then
		assertFalse(formula.areParenthesisEnclosing());
	}
}
