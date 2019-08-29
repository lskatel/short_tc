package ru.tc.short_tc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

public class Detal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal);

        ImageButton btn = findViewById(R.id.btn_prev2);
        btn.setOnClickListener(new PrevClick());

        ImageButton btnFind = findViewById(R.id.btn_prev);
        btnFind.setOnClickListener(new FindClick());

    }


    private class PrevClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Detal.this, Structure.class);
            startActivity(intent);
        }
    }

    private class FindClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Detal.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
