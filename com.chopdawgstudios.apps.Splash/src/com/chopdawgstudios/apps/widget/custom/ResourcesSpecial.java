package com.chopdawgstudios.apps.widget.custom;

import java.lang.reflect.Field;
import android.content.Context;
import com.chopdawgstudios.apps.R;

public class ResourcesSpecial {
    public static int getResId(String variableName, Context context, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.none;
        } 
    }
}
