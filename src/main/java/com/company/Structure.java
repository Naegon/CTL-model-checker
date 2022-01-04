package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Structure {
    private final ArrayList<State> states;
    private final String initialState;

    public Structure(Object input) {
        JSONObject jsonObject =  (JSONObject) input;

        initialState = (String) jsonObject.get("initialState");
        states = State.cast((JSONArray) jsonObject.get("states"));

        if (!initialStateExist()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Initial state %s not described in file %s",
                            initialState,
                            Utils.PATHFILE
                    )
            );
        }
    }

    private boolean initialStateExist() {
        return states.stream()
                .filter(state -> state.getName().equals(initialState))
                .toList()
                .size() != 1;
    }

    @Override
    public String toString() {
        return "Structure:" + "\n" +
                "  | initialState: " + initialState + "\n" +
                "  | states: " + states ;
    }
}
