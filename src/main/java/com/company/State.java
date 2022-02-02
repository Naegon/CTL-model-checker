package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class State {
    final String name;
    final ArrayList<String> values;
    final ArrayList<String> transitions;

    public State(Object input) {
        JSONObject state = (JSONObject) input;
        name = (String) state.get("name");
        values = Utils.toStringArrayList(state.get("values"));
        transitions = Utils.toStringArrayList(state.get("transitions"));
    }

    public State(String name, ArrayList<String> values, ArrayList<String> transitions) {
        this.name = name;
        this.values = values;
        this.transitions = transitions;
    }

    @Override
    public String toString() {
        return '\n' +
                "    ↳ | name: " + name + '\n' +
                "        | values: " + values + '\n' +
                "        | transitions: " + transitions;
    }

    public String getName() {
        return name;
    }
}
