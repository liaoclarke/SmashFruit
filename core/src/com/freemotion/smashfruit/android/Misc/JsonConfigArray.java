package com.freemotion.smashfruit.android.Misc;

import java.util.ArrayList;

/**
 * Created by liaoclark on 2016/3/1.
 */
public class JsonConfigArray {
    public String type;
    public String name;
    public ArrayList data;
    public JsonConfigFileParser parser;

    public String getType() {
        return type;
    }

    public ArrayList getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public JsonConfigFileParser getParser() {
        return parser;
    }

    public void setParser(JsonConfigFileParser parser) {
        this.parser = parser;
    }
}
