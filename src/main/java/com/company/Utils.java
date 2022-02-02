package com.company;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    public static ArrayList<State> toStateArrayList(JSONArray jsonArray) {
        ArrayList<State> states = new ArrayList<>();
        for (Object o : jsonArray) states.add(new State(o));
        return states;
    }

    public static ArrayList<String> toStringArrayList(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<String> strings = new ArrayList<>();
        for (Object o : jsonArray) strings.add(o.toString());
        return strings;
    }

}
