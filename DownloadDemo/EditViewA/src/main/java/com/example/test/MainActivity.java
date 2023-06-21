package com.example.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        String forbiddenChars = "abcde";
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = s.length() - 1; i >= 0; i--) {
                    if (forbiddenChars.contains(String.valueOf(s.charAt(i)))) {
                        s.delete(i, i + 1);
                    }
                }

                editText.setSelection(editText.getText().length());
            }
        });
        SpannableString s = new SpannableString("禁用字符为："+forbiddenChars);
        editText.setHint(s);
        String data = "Hello, World!";
        button.setText("提交："+data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Hello, World!";
                editText.setText(data);
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
            }
        });
    }
}
