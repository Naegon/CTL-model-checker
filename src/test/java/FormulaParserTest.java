import com.company.Formula;
import com.company.State;
import com.company.Structure;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Utils.PATHFILE;
import static com.company.Utils.readFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaParserTest {
    private Structure structure;

    @Before
    public void initializer() {
        structure = new Structure(readFromFile(PATHFILE));
    }

    @Test
    public void test_formula2() {
        // Given
        String formula = "VF(a)";

        // Then
        Formula myFormula = new Formula(formula, structure.states);
        Formula expectedFormula = new Formula("V", "F", "a", null, "Nada de nada !",
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
        );

        assertEquals(expectedFormula.toString(), myFormula.toString());
    }
}
