import com.company.Formula;
import com.company.Structure;
import org.junit.Before;
import org.junit.Test;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.Assert.assertEquals;

public class FormulaTransformTest {
	private Structure structure;

	@Before
	public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

	@Test
	public void test_formula_1() {
		// Given
		Formula formula = new Formula("a^b^(AG(¬(b)))", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("a^b^(¬(E(TU(¬(¬(b))))))", output.getValue());
	}

	@Test
	public void test_formula_2() {
		// Given
		Formula formula = new Formula("AG(EF(a^b))", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("¬(E(TU(¬(E(TU(a^b))))))", output.getValue());
	}

	@Test
	public void test_formula_3() {
		// Given
		Formula formula = new Formula("a^b^(EF(¬(b)))", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("a^b^(E(TU(¬(b))))", output.getValue());
	}

	@Test
	public void test_formula_4() {
		// Given
		Formula formula = new Formula("EX(AG(EF(a^b)))^c^(¬EF(EF(AG(b))))", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("EX(¬(E(TU(¬(E(TU(a^b)))))))^c^(¬E(TU(E(TU(¬(E(TU(¬(b)))))))))", output.getValue());
	}

	@Test
	public void test_formula_5() {
		// Given
		Formula formula = new Formula("¬(EF(EF(AG(b))))", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("¬(E(TU(E(TU(¬(E(TU(¬(b)))))))))", output.getValue());
	}

	@Test
	public void test_formula_6() {
		// Given
		Formula formula = new Formula("EX(a^b)", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("EX(a^b)", output.getValue());
	}

	@Test
	public void test_formula_7() {
		// Given
		Formula formula = new Formula("A(aUb)", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("A(aUb)", output.getValue());
	}

	@Test
	public void test_formula_8() {
		// Given
		Formula formula = new Formula("a", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("a", output.getValue());
	}

	@Test
	public void test_formula_9() {
		// Given
		Formula formula = new Formula("a^b", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("a^b", output.getValue());
	}

	@Test
	public void test_formula_10() {
		// Given
		Formula formula = new Formula("E(aUb)", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("E(aUb)", output.getValue());
	}

	@Test
	public void test_formula_11() {
		// Given
		Formula formula = new Formula("¬(a^b)", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("¬(a^b)", output.getValue());
	}

	@Test
	public void test_formula_12() {
		// Given
		Formula formula = new Formula("¬a", structure);

		// When
		Formula output = formula.formulaTransform();

		// Then
		assertEquals("¬a", output.getValue());
	}

}
