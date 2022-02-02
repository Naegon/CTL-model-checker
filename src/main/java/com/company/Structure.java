package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Structure {
    public final ArrayList<State> states;
    private final ArrayList<String> initialStates;

    public Structure(Object input) {
        JSONObject jsonObject =  (JSONObject) input;

        initialStates = State.toStringArrayList(jsonObject.get("initialStates"));
        states = State.cast((JSONArray) jsonObject.get("states"));

        if (!initialStateExist()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Initial state %s not described in file %s",
                            initialStates,
                            Utils.PATHFILE
                    )
            );
        }
    }

    private boolean initialStateExist() {
        return states.stream()
                .map(State::getName).toList()
                .containsAll(initialStates);
    }

    @Override
    public String toString() {
        return "Structure:" + "\n" +
                "  | initialState: " + initialStates + "\n" +
                "  | states: " + states ;
    }
}
