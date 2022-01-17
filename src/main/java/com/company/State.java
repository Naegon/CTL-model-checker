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
        values = toStringArrayList(state.get("values"));
        transitions = toStringArrayList(state.get("transitions"));
    }

    static public ArrayList<State> cast(JSONArray jsonArray) {
        ArrayList<State> states = new ArrayList<>();
        for (Object o : jsonArray) states.add(new State(o));
        return states;
    }

    static public ArrayList<String> toStringArrayList(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<String> strings = new ArrayList<>();
        for (Object o : jsonArray) strings.add(o.toString());
        return strings;
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
    public ArrayList<String> getValues() {
        return values;
    }
}
