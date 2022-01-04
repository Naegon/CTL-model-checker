package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Structure {
    private final ArrayList<State> states;
    private final String initialState;

    public Structure(Object input) {
        JSONObject jsonObject =  (JSONObject) input;

        states = State.cast((JSONArray) jsonObject.get("states"));
        initialState = (String) jsonObject.get("initialState");
    }

    @Override
    public String toString() {
        return "Structure:" + "\n" +
                "  | initialState: " + initialState + "\n" +
                "  | states: " + states ;
    }
}
