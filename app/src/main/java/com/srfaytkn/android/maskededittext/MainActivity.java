package com.srfaytkn.android.maskededittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.srfaytkn.android.lib.MaskedEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaskedEditText cardNumberInput;
    private MaskedEditText phoneNumberInput;

    private TextView cardNumberText;
    private TextView cardNumberUnMaskedText;
    private TextView phoneNumberText;

    private Button showResultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setUp();
    }

    private void findViews() {
        cardNumberInput = findViewById(R.id.input_card_number);
        phoneNumberInput = findViewById(R.id.input_phone_number);

        cardNumberText = findViewById(R.id.text_card_number);
        cardNumberUnMaskedText = findViewById(R.id.text_card_number_unmasked);
        phoneNumberText = findViewById(R.id.text_phone_number);

        showResultButton = findViewById(R.id.button_show_result);
    }

    private void setUp() {
        showResultButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        cardNumberText.setText(cardNumberInput.getCompletedText());
        cardNumberUnMaskedText.setText(cardNumberInput.getUnMaskedText());

        phoneNumberText.setText(phoneNumberInput.getText().toString());
    }
}
