import com.company.Formula;
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

public class FormulaTest {
	private Structure structure;

	@Before
	public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

	@Test
	public void test_case1_marking() {
		// Given
		Formula formula = new Formula("a", structure);

		// When
		ArrayList<State> output = formula.process();

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
		Formula formula = new Formula("¬a", structure);

		// When
		ArrayList<State> output = formula.process();

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
	public void test_case2_not_withEmptyStates() {
		// Given
		Formula formula = new Formula("¬g", structure);

		// When
		ArrayList<State> output = formula.process();

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
						),
						new State("S7",
								new ArrayList<>(Arrays.asList("a", "d")),
								new ArrayList<>(List.of("S1"))
						)
				));

		assertEquals(expected, output);
	}

	@Test
	public void test_case2_not_not() {
		// Given
		Formula formula = new Formula("¬(¬(a))", structure);

		// When
		ArrayList<State> output = formula.process();

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
	public void test_case3_intersect() {
		// Given
		Formula formula = new Formula("a^b", structure);

		// When
		ArrayList<State> output = formula.process();

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
		Formula formula = new Formula("EX(a)", structure);

		// When
		ArrayList<State> output = formula.process();

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
		Formula formula = new Formula("E(aUc)", structure);

		// When
		ArrayList<State> output = formula.process();

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
		Formula formula = new Formula("A(aUc)", structure);

		// When
		ArrayList<State> output = formula.process();

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

	@Test
	public void test_big_formula() {
		// Given
		Formula formula = new Formula("¬(¬(E(TU(E(TU(a))))))^c", structure);

		// When
		ArrayList<State> output = formula.process();

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

	@Test
	public void test_getSubFormula1() {
		Formula formula = new Formula("TU¬(E(TU(a^b)))", structure);

		// When
		Pair<String, String> output = Formula.getSubFormulas(formula.getValue());

		// Then
		assertEquals(new Pair<>("T", "¬(E(TU(a^b)))"), output);
	}

	@Test
	public void test_getSubFormula2() {
		Formula formula = new Formula("¬(E(TU(a^b)))UT", structure);

		// When
		Pair<String, String> output = Formula.getSubFormulas(formula.getValue());

		// Then
		assertEquals(new Pair<>("¬(E(TU(a^b)))", "T"), output);
	}

	@Test
	public void test_areParenthesisEnclosing1() {
		// Given
		Formula formula = new Formula("(¬(E(TU(a^b)))UT)", structure);

		// Then
		assertTrue(formula.areParenthesisEnclosing());
	}

	@Test
	public void test_areParenthesisEnclosing2() {
		// Given
		Formula formula = new Formula("(¬(E(TU(a^b)))UT)^c", structure);

		// Then
		assertFalse(formula.areParenthesisEnclosing());
	}
}
