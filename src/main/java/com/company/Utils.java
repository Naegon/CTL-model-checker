package com.company;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public abstract class Utils {
    public static final String PATHFILE = "src/main/java/com/company/files/schematest.json";

    public static Object readFromFile(String path) {
        JSONParser parser = new JSONParser();

        try {
            return parser.parse(new FileReader(path));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
