package com.example.th3_ript.bai4;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoStorage {
    private static final String PREF_NAME = "TodoPrefs";
    private static final String KEY_TODOS = "todos";
    private final SharedPreferences prefs;
    private final Gson gson;

    public TodoStorage(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTodos(List<TodoItem> todos) {
        String json = gson.toJson(todos);
        prefs.edit().putString(KEY_TODOS, json).apply();
    }

    public List<TodoItem> loadTodos() {
        String json = prefs.getString(KEY_TODOS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<TodoItem>>() {}.getType();
        return gson.fromJson(json, type);
    }
}

