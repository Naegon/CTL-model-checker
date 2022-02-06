package com.company;

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
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;

        if (!name.equals(state.name)) return false;
        if (!values.equals(state.values)) return false;
        return transitions.equals(state.transitions);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + values.hashCode();
        result = 31 * result + transitions.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }
}
