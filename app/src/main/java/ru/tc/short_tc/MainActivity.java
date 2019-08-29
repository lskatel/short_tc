package ru.tc.short_tc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DBHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_form);

        db_helper = new DBHelper(this);
        load();
    }

    private void load() {
        for (DSEModel dse : db_helper.getAllDSE()) {

        }
    }
}
