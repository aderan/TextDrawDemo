package com.aderan.textdrawdemo.story;

import android.graphics.Typeface;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author fenglibin
 * @data 24/10/2017.
 */

public class TypefaceUtil {
    private static HashMap<String, Typeface> sTypefaceMap;

    public static Typeface getTypeface(String path) {
        if (sTypefaceMap == null) {
            sTypefaceMap = new HashMap<>();
        }

        Typeface typeface = sTypefaceMap.get(path);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromFile(path);
                sTypefaceMap.put(path, typeface);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        return typeface;
    }
}
