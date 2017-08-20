package com.srfaytkn.android.lib;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by srfaytkn on 8/13/17.
 */

public class MaskedEditText extends AppCompatEditText implements TextWatcher {

    private String mask;
    private String allowedChars;
    private String completeWith;
    private char maskedSymbol = '#';
    private boolean lock;

    public MaskedEditText(Context context) {
        super(context);
        setUp();
    }

    public MaskedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
        setUp();
    }

    public MaskedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(attrs);
        setUp();
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MaskedEditText);

        mask = attributes.getString(R.styleable.MaskedEditText_mask);
        allowedChars = attributes.getString(R.styleable.MaskedEditText_allowedChars);
        completeWith = attributes.getString(R.styleable.MaskedEditText_completeWith);

        if (attributes.getString(R.styleable.MaskedEditText_maskedSymbol) != null) {
            maskedSymbol = attributes.getString(R.styleable.MaskedEditText_maskedSymbol).charAt(0);
        }
    }

    private void setUp() {
        setUpAllowedChars();
        addTextChangedListener(this);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(mask.length());
        setFilters(filters);
    }

    private void setUpAllowedChars() {
        if (allowedChars == null) {
            return;
        }

        Set<String> allowedChs = new HashSet<>(Arrays.asList(mask.split("")));
        allowedChs.addAll(Arrays.asList(allowedChars.split("")));

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : allowedChs) {
            stringBuilder.append(s);
        }

        allowedChars = String.valueOf(stringBuilder);
        setKeyListener(DigitsKeyListener.getInstance(allowedChars));
    }

    public String getCompletedText() {
        StringBuilder text = new StringBuilder(getText().toString());

        for (int i = text.length(); i < mask.length(); i++) {
            if (mask.charAt(i) == maskedSymbol) {
                text.append(completeWith);
            } else {
                text.append(mask.charAt(i));
            }
        }
        return text.toString();
    }

    public String getUnMaskedText() {
        StringBuilder text = new StringBuilder();

        char[] textChars = getText().toString().toCharArray();
        char[] maskChars = mask.toCharArray();

        for (int i = 0; i < textChars.length; i++) {
            if (maskChars[i] == maskedSymbol) {
                text.append(textChars[i]);
            }
        }

        return text.toString();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() <= 0) {
            return;
        }

        int index = s.length() - 1;
        lock = true;

        if (mask.charAt(index) != maskedSymbol && mask.charAt(index) != s.charAt(index)) {
            s.insert(index, String.valueOf(mask.charAt(index)));
        }
        lock = false;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text.length() > 1) {
            setText("");
            for (char c : text.toString().toCharArray()) {
                append(String.valueOf(c));
            }
            return;
        }
        super.setText(text, type);
    }
}
