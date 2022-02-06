import com.company.FormulaString;
import com.company.Structure;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.Assert.*;

public class FormulaStringTest {
	private Structure structure;

	@Before
	public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

	@Test
	public void test_formula_1() {
		// Given
		FormulaString formula = new FormulaString("¬(E(TU¬(E(TU(a∧b)))))", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_2() {
		// Given
		FormulaString formula = new FormulaString("¬(E(TU(E(TU(¬(E(TU¬(b))))))))", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_3() {
		// Given
		FormulaString formula = new FormulaString("EX(a^b)", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_4() {
		// Given
		FormulaString formula = new FormulaString("¬(a^b)", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_5() {
		// Given
		FormulaString formula = new FormulaString("¬(E(TU¬(E(TU(a ∧ b)))))^c", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_6() {
		// Given
		FormulaString formula = new FormulaString("(¬(E(TU¬(E(TU(a ∧ b)))))^c)^¬(E(TU(E(TU(¬(E(TU¬(b))))))))", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_7() {
		// Given
		FormulaString formula = new FormulaString("a^(¬(E(TU¬(¬(b)))))", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_8() {
		// Given
		FormulaString formula = new FormulaString("a^(E(TU(¬(b)))", structure);

		// When
		formula.apply();
	}

	@Test
	public void test_formula_9() {
		// Given
		FormulaString formula = new FormulaString("¬(E(TU¬(E(TU(a∧b)))))^c", structure);

		// When
		formula.apply();
	}

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
}
