import com.company.FormulaString;
import com.company.Structure;
import org.junit.Before;
import org.junit.Test;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.Assert.assertEquals;

public class FormulaTransform {
    private Structure structure;

    @Before
    public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

    @Test
    public void test_formula_1() {
        // Given
        FormulaString formula = new FormulaString("a^b^(AG(¬(b)))", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("a^b^(¬(E(TU(¬(¬(b))))))", output.value());
    }
    @Test
    public void test_formula_2() {
        // Given
        FormulaString formula = new FormulaString("AG(EF(a^b))", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("¬(E(TU(¬(E(TU(a^b))))))", output.value());
    }
    @Test
    public void test_formula_3() {
        // Given
        FormulaString formula = new FormulaString("a^b^(EF(¬(b)))", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("a^b^(E(TU(¬(b))))", output.value());
    }
    @Test
    public void test_formula_4() {
        // Given
        FormulaString formula = new FormulaString("EX(AG(EF(a^b)))^c^(¬EF(EF(AG(b))))", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("EX(¬(E(TU(¬(E(TU(a^b)))))))^c^(¬E(TU(E(TU(¬(E(TU(¬(b)))))))))", output.value());
    }
    @Test
    public void test_formula_5() {
        // Given
        FormulaString formula = new FormulaString("¬(EF(EF(AG(b))))", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("¬(E(TU(E(TU(¬(E(TU(¬(b)))))))))", output.value());
    }
    @Test
    public void test_formula_6() {
        // Given
        FormulaString formula = new FormulaString("EX(a^b)", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("EX(a^b)", output.value());
    }
    @Test
    public void test_formula_7() {
        // Given
        FormulaString formula = new FormulaString("A(aUb)", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("A(aUb)", output.value());
    }
    @Test
    public void test_formula_8() {
        // Given
        FormulaString formula = new FormulaString("a", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("a", output.value());
    }
    @Test
    public void test_formula_9() {
        // Given
        FormulaString formula = new FormulaString("a^b", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("a^b", output.value());
    }
    @Test
    public void test_formula_10() {
        // Given
        FormulaString formula = new FormulaString("E(aUb)", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("E(aUb)", output.value());
    }
    @Test
    public void test_formula_11() {
        // Given
        FormulaString formula = new FormulaString("¬(a^b)", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("¬(a^b)", output.value());
    }

    @Test
    public void test_formula_12() {
        // Given
        FormulaString formula = new FormulaString("¬a", structure);

        // When
        FormulaString output = formula.formulaTransform();

        // Then
        assertEquals("¬a", output.value());
    }

}
