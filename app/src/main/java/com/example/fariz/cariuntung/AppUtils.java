package com.example.fariz.cariuntung;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Fariz on 10/29/2018.
 */

public class AppUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
