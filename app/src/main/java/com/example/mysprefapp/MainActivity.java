package com.example.mysprefapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //There should be at most 7 users in the gym
    private final int gymAttendanceCount = 7;

    //Current members in/using gym
    private int currentUserCount = 0;

    //currentMembers Key for sharedPreferences
    private final String membersKey = "current_member_count";

    //Member key prefix
    private final String memberKeyPrefix = "member_id_";

    //SharedPreferences object we will use to read from sp
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    private Button checkInButton;

    private EditText memberIdEditText, memberNameEditText;

    private TextView informationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing sharedpreferences object
        sharedPreferences = getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);

//        currentUserCount = sharedPreferences.getInt(membersKey, 0);



        memberIdEditText = findViewById(R.id.membership_edittext);
        memberNameEditText = findViewById(R.id.name_edittext);

        informationText = findViewById(R.id.display_textfield);

        readUsers();
        //initializing the button
        checkInButton = findViewById(R.id.insert_button);

        //Button click listener to be used to check users in
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initializing sharedpreferences editor
                spEditor = sharedPreferences.edit();

                String member = memberIdEditText.getText() + "-" + memberNameEditText.getText();
                memberIdEditText.setText("");
                memberNameEditText.setText("");

                spEditor.putString(memberKeyPrefix+currentUserCount, member);
                currentUserCount++;
                spEditor.putInt(membersKey, currentUserCount);
                spEditor.apply();
                readUsers();


            }
        });
    }

    private void readUsers(){
        currentUserCount = sharedPreferences.getInt(membersKey, 0);

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < currentUserCount; i++){

            stringBuilder.append(sharedPreferences.getString(memberKeyPrefix+i, "Default_Name")+"\n");
        }

        informationText.setText(stringBuilder.toString());

    }

}
