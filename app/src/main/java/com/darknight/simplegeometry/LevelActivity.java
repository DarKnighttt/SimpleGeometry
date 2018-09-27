package com.darknight.simplegeometry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LevelActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    Level lvl;
    Bundle extras;
    ImageView taskImage;
    TextView lvlNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        final EditText numberCount = findViewById(R.id.txt_number_count);
        final ImageButton confirm = findViewById(R.id.btn_confirm);
        lvlNumber = findViewById(R.id.txt_level_number);
        dbHandler = new DatabaseHandler(this);
        TextWatcher answerTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String answerInput = numberCount.getText().toString();
                if (!answerInput.isEmpty()) confirm.setVisibility(View.VISIBLE);
                else confirm.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        extras = getIntent().getExtras();
        lvl = dbHandler.getLevel(extras.getInt("id"));
        if (lvl.getId() == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setTitle(R.string.hello)
                    .setMessage(R.string.shirt_tutorial)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_clear_btn,null)
                    .create().show();
        }
        lvlNumber.setText("Level " + lvl.getId());
        int myImgId = getResources().getIdentifier("lvl" + lvl.getId(), "drawable", getPackageName());
        taskImage = findViewById(R.id.img_lvl_task);
        taskImage.setImageResource(myImgId);
        numberCount.addTextChangedListener(answerTextWatcher);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = Integer.valueOf(numberCount.getText().toString());
                if (answer != lvl.getAnswer()) {
                    numberCount.setText(null);
                    Toast.makeText(view.getContext(), "WRONG!!!", Toast.LENGTH_SHORT).show();
                } else {
                    numberCount.setText(null);
                    Toast.makeText(view.getContext(), "You God damn right!!!", Toast.LENGTH_SHORT).show();
                    dbHandler.updateLevel(lvl);
                    createDialog(lvl.getId()).show();
                }
            }
        });
    }


    protected Dialog createDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        if(id == 20){
            builder.setView(R.layout.alertdialog_image)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_dialog_btn_finish, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
        }else{
        builder.setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.next_lvl_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                        intent.putExtra("id", id + 1);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.choose_lvl_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        }
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ChooseLevelActivity.class));
    }
}
