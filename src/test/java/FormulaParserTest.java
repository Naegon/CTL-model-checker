import com.company.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Cases.CasesName.*;
import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaParserTest {
    private Structure structure;

    @Before
    public void initializer() { structure = new Structure(readFromFile(PATHFILE)); }

    @Test
    public void test_formula2() {
        // Given
        String formula = "VF(a)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", null, DEFAULT,
                new ArrayList<>(
                    List.of(
                        new State("S1",
                            new ArrayList<>(Arrays.asList("a", "c")),
                            new ArrayList<>(Arrays.asList("S2", "S3"))
                        ),
                        new State("S2",
                            new ArrayList<>(Arrays.asList("a", "b")),
                            new ArrayList<>(List.of("S4"))
                        ),
                        new State("S3",
                            new ArrayList<>(List.of("a")),
                            new ArrayList<>(List.of("S4"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }

    @Test
    public void test_formula3() {
        // Given
        String formula = "¬VF(a)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", null, NOT,
                new ArrayList<>(
                    List.of(
                        new State("S0",
                            new ArrayList<>(List.of("c")),
                            new ArrayList<>(List.of("S1"))
                        ),
                        new State("S4",
                            new ArrayList<>(List.of("b")),
                            new ArrayList<>(List.of("S1"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }

    @Test
    public void test_formula4() {
        // Given
        String formula = "VF(a^b)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", "b", INTERSECT,
                new ArrayList<>(
                    List.of(
                        new State("S2",
                            new ArrayList<>(Arrays.asList("a", "b")),
                            new ArrayList<>(List.of("S4"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }

    @Test
    public void test_formula5() {
        // Given
        String formula = "VF(EXa)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", null, NEXT_TIME,
                new ArrayList<>(
                    List.of(
                        new State("S0",
                            new ArrayList<>(List.of("c")),
                            new ArrayList<>(List.of("S1"))
                        ),
                        new State("S1",
                            new ArrayList<>(Arrays.asList("a", "c")),
                            new ArrayList<>(Arrays.asList("S2", "S3"))
                        ),
                        new State("S4",
                            new ArrayList<>(List.of("b")),
                            new ArrayList<>(List.of("S1"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }

    @Test
    public void test_formula6() {
        // Given
        String formula = "VF(EaUb)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", "b", UNTIL_E,
                new ArrayList<>(
                    List.of(
                        new State("S2",
                            new ArrayList<>(Arrays.asList("a", "b")),
                            new ArrayList<>(List.of("S4"))
                        ),
                        new State("S4",
                            new ArrayList<>(List.of("b")),
                            new ArrayList<>(List.of("S1"))
                        ),
                        new State("S1",
                            new ArrayList<>(Arrays.asList("a", "c")),
                            new ArrayList<>(Arrays.asList("S2","S3"))
                        ),
                        new State("S3",
                            new ArrayList<>(List.of("a")),
                            new ArrayList<>(List.of("S4"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }

    @Test
    public void test_formula7() {
        // Given
        String formula = "VF(AaUb)";

        // When
        Formula myFormula = new Formula(structure.states);
        myFormula.subFormulaChecker(formula);

        // Then
        Formula expectedFormula = new Formula("V", "F",
            new Function("a", "b", UNTIL_A,
                new ArrayList<>(
                    List.of(
                        new State("S2",
                            new ArrayList<>(Arrays.asList("a", "b")),
                            new ArrayList<>(List.of("S4"))
                        ),
                        new State("S4",
                            new ArrayList<>(List.of("b")),
                            new ArrayList<>(List.of("S1"))
                        ),
                        new State("S3",
                            new ArrayList<>(List.of("a")),
                            new ArrayList<>(List.of("S4"))
                        ),
                        new State("S1",
                            new ArrayList<>(Arrays.asList("a", "c")),
                            new ArrayList<>(Arrays.asList("S2", "S3"))
                        )
                    )
                )
            )
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }
}
