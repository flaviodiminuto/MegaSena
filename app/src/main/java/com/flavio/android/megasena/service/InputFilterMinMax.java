package com.flavio.android.megasena.service;
import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) throws NumberFormatException{
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int value = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, value))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int min, int max, int value) {
        return max > min ? value >= min && value <= max : value >= max && value <= min;
    }
}