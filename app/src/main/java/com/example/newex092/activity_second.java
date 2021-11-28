package com.example.newex092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Harel Navon <harelnavon2710@gmail.com>
 * @version 1.0
 * @since 22/11/2021
 * This is the Class for the secondary Activity of the project.
 * In this Activity, the first 20 terms of the series (calculated with the information from the
 * main Activity) will be shown.
 * The user is able to hold each term, which will open a context menu which allows them to choose
 * whether they want the sum of terms until the term they chose to be displayed, or if they want to
 * the position of the selected term to be displayed.
 */

public class activity_second extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnCreateContextMenuListener {
    TextView info;
    double x1, d;
    Intent gi;
    boolean type;
    ListView lv;
    String[] series = new String[20];
    double[] nums = new double[20];
    double[] sums = new double[20];
    String oper;
    int pos;


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
        setContentView(R.layout.activity_second);

        info = findViewById(R.id.info);
        lv = findViewById(R.id.series);

        gi = getIntent();
        x1 = gi.getDoubleExtra("a1", -42442);
        d = gi.getDoubleExtra("d", -17.5);
        type = gi.getBooleanExtra("type", false);

        nums[0] = x1;
        series[0] = String.valueOf(x1);
        sums[0] = x1;
        for (int i = 1; i < 20; i++) {
            if (!type) {
                nums[i] = nums[i - 1] + d;
            } else {
                nums[i] = nums[i - 1] * d;
            }
            series[i] = String.valueOf(nums[i]);
            sums[i] = sums[i - 1] + nums[i];
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, series);
        lv.setAdapter(adp);
        lv.setOnItemLongClickListener(this);
        lv.setOnCreateContextMenuListener(this);
    }

    /**
     * This is the OnClick method for the Button in the activity_second.xml.
     * The purpose of this method is sending over the information submitted by the user back to
     * the main Activity, and also switching between the Activities.
     *
     * @param view: The widget that has been clicked.
     */
    public void back(View view) {
        gi.putExtra("first", x1);
        gi.putExtra("jump", d);
        gi.putExtra("type", type);
        setResult(RESULT_OK, gi);
        finish();
    }


    /**
     * Callback method to be invoked when an item in this view has been clicked and held.
     * Implementers can call getItemAtPosition(position) if they need to access the data associated
     * with the selected item.
     *
     * @param parent:   The AbsListView where the click happened.
     * @param view:     The view within the AbsListView that was clicked.
     * @param position: The position of the view in the list.
     * @param id:       The row id of the item that was clicked
     * @return: true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position + 1;
        return false;
    }

    /**
     * Called when the context menu for this view is being built.
     * It is not safe to hold onto the menu after this method returns.
     *
     * @param menu:     The context menu that is being built.
     * @param v:        The view for which the context menu is being built
     * @param menuInfo: Extra information about the item for which the context menu should be shown.
     *                  This information will vary depending on the class of v.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Sum");
        menu.add("Position");
    }


    /**
     * This hook is called whenever an item in a context menu is selected.
     * The default implementation simply returns false to have the normal processing
     * happen (calling the item's Runnable or sending a message to its Handler as appropriate).
     * You can use this method for any items for which you would like to do processing
     * without those other facilities.
     *
     * @param item: The context menu item that was selected. This value cannot be null.
     * @return: Return false to allow normal context menu processing to proceed,
     * true to consume it here.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        oper = item.getTitle().toString();
        if (oper.equals("Sum")) info.setText("The sum is " + sums[pos - 1]);
        else info.setText("The position is " + pos);
        return super.onContextItemSelected(item);
    }


}