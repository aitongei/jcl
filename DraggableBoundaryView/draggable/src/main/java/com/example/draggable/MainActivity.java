package com.example.draggable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          View      childButton = findViewById(R.id.button);

        childButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理点击事件
                Toast.makeText(MainActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();

            }
        });
    }
}