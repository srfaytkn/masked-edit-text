package com.srfaytkn.android.maskededittextlib;


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
    private boolean locked;
    private String lastText = "";

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
        addText();
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

        setKeyListener(DigitsKeyListener.getInstance(String.valueOf(stringBuilder)));
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
            if (maskChars[i] == maskedSymbol && allowedChars.indexOf(textChars[i]) > -1) {
                text.append(textChars[i]);
            }
        }

        return text.toString();
    }

    private void addText() {
        Editable s = getEditableText();
        if (locked) {
            return;
        }

        locked = true;

        if (lastText.trim().equals(s.toString().trim()) && s.length() > 0) {
            int startIndex = s.length();

            for (; startIndex > 0; startIndex--) {
                if (mask.charAt(startIndex) == maskedSymbol) {
                    break;
                }
            }

            s.delete(startIndex, s.length());
        }

        String text = getUnMaskedText().trim();
        StringBuilder stringBuilder = new StringBuilder();
        int textIndex = 0;

        for (int index = 0; index < mask.length(); index++) {
            if (mask.charAt(index) != maskedSymbol) {
                stringBuilder.append(mask.charAt(index));
            } else {
                if (text.length() <= textIndex) {
                    break;
                }
                stringBuilder.append(text.charAt(textIndex));
                textIndex++;
            }
        }

        lastText = stringBuilder.toString();
        s.replace(0, s.length(), lastText);

        locked = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        addText();
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
