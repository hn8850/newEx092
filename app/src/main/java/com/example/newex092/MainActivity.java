package com.example.newex092;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author	Harel Navon <harelnavon2710@gmail.com>
 * @version 1.0
 * @since	22/11/2021
 *
 * The purpose of this project is to gather the information needed from the user in order to display
 * the series based on that information, while also having the option to view more information
 * about each item in the list.
 *
 * This is the Class for the main Activity of the project.
 * In this Activity,the user must enter the type of series they want (arithmetic or geometric),
 * the first term in the series, and the common difference.
 * After all variables are entered, the user may progress to the next Activity.
 */

public class MainActivity extends AppCompatActivity {

    TextView op1, op2;
    Button next;
    Switch sw;
    EditText first, jump;
    double a1, d;
    Intent si;
    boolean type; // if type == false, then the series type is Arithmetic, and if true, then Geometric
    boolean cont2 = false;
    boolean cont1 = false;


    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up:
     * create views, bind data to lists, etc.
     * This method also provides you with a Bundle containing
     * the activity's previously frozen state, if there was one.
     * Always followed by onStart().
     *
     * @param savedInstanceState: a reference to a Bundle object that is passed into the onCreate
     *                            method of the Activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        next = findViewById(R.id.next);
        first = findViewById(R.id.a1);
        jump = findViewById(R.id.d);
        sw = findViewById(R.id.sw);


    }


    /**
     * This is the OnClick method for the Button in the activity_main.xml.
     * The purpose of this method is sending over the information submitted by the user over to
     * the second Activity (for the series to be created and displayed),
     * and also switching between the Activities.
     * The method makes sure that the user can advance only if the information is valid.
     *
     * @param view: A reference to The widget that has been clicked.
     */
    public void go(View view)
    {
        try {
            a1 = Double.parseDouble(first.getText().toString());
            cont1 = true;
        } catch (Exception e) {
            cont1 = false;
            Toast.makeText(getApplicationContext(), "Enter valid number!", Toast.LENGTH_SHORT).show();
        }

        try {
            d = Double.parseDouble(jump.getText().toString());
            cont2 = true;
        } catch (Exception e) {
            cont2 = false;
            Toast.makeText(getApplicationContext(), "Enter valid number!", Toast.LENGTH_SHORT).show();
        }

        if (!sw.isChecked()) {
            type = false;
        } else type = true;


        if (cont1 && cont2) {
            si = new Intent(this, activity_second.class);
            si.putExtra("a1", a1);
            si.putExtra("d", d);
            si.putExtra("type", type);
            startActivityForResult(si, 100);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot go to next page without all variables", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * This is the program who receives the information that has been sent from the second
     * Activity back to this Activity.
     *
     * @param source: Where the Intent came from.
     * @param good: Did the Intent come back successfully.
     * @param data_back: The new Intent.
     */
    @Override
    protected void onActivityResult(int source, int good, @Nullable Intent data_back) {
        super.onActivityResult(source, good, data_back);
        if (data_back != null) {
            a1 = data_back.getDoubleExtra("first", 29329);
            d = data_back.getDoubleExtra("jump", 4282);
            type = data_back.getBooleanExtra("type", false);
            first.setText(String.valueOf(a1), TextView.BufferType.EDITABLE);
            jump.setText(String.valueOf(d));
            sw.setChecked(type);
        }
    }
}