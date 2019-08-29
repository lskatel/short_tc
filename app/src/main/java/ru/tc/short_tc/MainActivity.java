package ru.tc.short_tc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {
    DBHelper db_helper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_form);

        lv = findViewById(R.id.lv);

        db_helper = new DBHelper(this);
        load();
    }

    private void load() {
        /*ArrayAdapter<DSEModel> adapter = new ArrayAdapter<>(this,
                android.R.layout.activity_list_item, db_helper.getAllDSE());*/

        SimpleAdapter adapter = new SimpleAdapter(this, db_helper.getAllDSE(),
                android.R.layout.activity_list_item, new String[] {"dse_id", "dse_name"} , new int[] {R.id.dse_id, R.id.dse_name});

        lv.setAdapter(adapter);
    }
}
