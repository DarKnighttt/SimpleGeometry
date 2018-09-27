package com.darknight.simplegeometry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class ChooseLevelActivity extends AppCompatActivity {

    public static DatabaseHandler dbHandler;
    List<Level> levels;
    DataGridAdapter levelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        dbHandler = new DatabaseHandler(this);
        GridView gridview = findViewById(R.id.lvl_grid);
/*        dbHandler.addLevel(new Level(1,0));
        dbHandler.addLevel(new Level(1,0));
        dbHandler.addLevel(new Level(1,0));
        dbHandler.addLevel(new Level(1,0));
        dbHandler.addLevel(new Level(1,0));
        dbHandler.addLevel(new Level(1,0));*/
        levels = dbHandler.getAllLevels();
        //Toast.makeText(this, levels.toString(), Toast.LENGTH_SHORT).show();
        levelAdapter = new DataGridAdapter(this, levels);
        gridview.setAdapter(levelAdapter);
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            Level lvl = levels.get(position);
            Intent i = new Intent(getApplicationContext(), LevelActivity.class);
            i.putExtra("id", lvl.getId());
            startActivity(i);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        levels.clear();
        levels.addAll(dbHandler.getAllLevels());
        levelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
