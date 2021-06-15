package com.example.modelproject.ui;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class TextCustom {
    public static SpannableStringBuilder setStyle(Label... labels) {
        SpannableStringBuilder str = new SpannableStringBuilder();
        int sizeStart = 0;
        for (Label label : labels) {
            str.append(label.text);
            str.setSpan(new android.text.style.StyleSpan(label.style.type), sizeStart, sizeStart + label.text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sizeStart += label.text.length();
        }
        return str;
    }

    public static SpannableStringBuilder setStyleColor(Label... labels) {
        SpannableStringBuilder str = new SpannableStringBuilder();
        int sizeStart = 0;
        for (Label label : labels) {
            String color = label.color != null ? label.color : "#808080";
            str.append(label.text);
            str.setSpan(new ForegroundColorSpan(Color.parseColor(color)), sizeStart, sizeStart + label.text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new android.text.style.StyleSpan(label.style.type), sizeStart, sizeStart + label.text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sizeStart += label.text.length();
        }
        return str;
    }
}