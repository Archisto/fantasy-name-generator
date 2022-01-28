package com.lauri.example.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator extends AppCompatActivity {

    private final NameGenerator1 nameGenerator = new NameGenerator1();
    private final int maxNameCount = 12;
    private int nameCount = maxNameCount;
    private boolean showSeparators = false;
    List<String> poolAll;
    List<String> poolMain;
    List<String> poolMiddle;
    List<String> poolEnd;
    List<String> poolSpecial;
    List<List<String>> pools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_generator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initPools();
        final TextView textDisplay = findViewById(R.id.txtDisplay);
        final int maxNameLength = 26;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textDisplay.setText(getRandomFullNames(pools, nameCount, maxNameLength));
            }
        });

        // Name count button
        Button button = findViewById(R.id.btnNameCount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameCount >= maxNameCount) {
                    nameCount = 1;
                }
                else {
                    nameCount++;
                }

                updateNameCountText();
            }
        });
        updateNameCountText();

        // Name part separator switch
        final Switch sepSwitch = findViewById(R.id.separator_switch);
//        showSeparators = sepSwitch.isChecked();
        sepSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSeparators = sepSwitch.isChecked();
                // String message = getString(R.string.current_count) + " " + count;

//                TextView textView = findViewById(R.id.txtCountDisplay);
//                textView.setText(message);

//                Toast.makeText(getApplicationContext(), getString(R.string.well_done) +
//                    " (" + count + ")", Toast.LENGTH_SHORT).show();

                int stringId = R.string.separators_active;
                if (!showSeparators) {
                    stringId = R.string.separators_inactive;
                }
                Snackbar.make(v, getString(stringId), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView textView = findViewById(R.id.txtCountDisplay);
        textView.setText(String.format(getString(R.string.name_part_count), poolAll.size()));
  /*      String text = "";
        int partsInName = 0;
        for (String obj : poolAll) {
            Log.d("MyTag", obj);
            text += obj;
            partsInName++;
            if (partsInName >= 3) {
                text += "\n";
                partsInName = 0;
            }
        }*/

        textDisplay.setText(getRandomFullNames(pools, nameCount, maxNameLength));
    }

    private void initPools() {
        poolAll = new ArrayList<>();
        pools = new ArrayList<>();
        poolMain = new ArrayList<>();
        poolMiddle = new ArrayList<>();
        poolEnd = new ArrayList<>();
        poolSpecial = new ArrayList<>();
        pools.add(poolMain);
        pools.add(poolMiddle);
        pools.add(poolEnd);
        pools.add(poolSpecial);

        CustomXmlResourceParser.parseNameParts(getResources(), R.xml.name_parts, pools, poolAll);
    }

    private String getRandomFullNames(List<List<String>> pools,
                                      int nameCount,
                                      int maxFullNameLength) {
        StringBuilder names = new StringBuilder(nameCount * maxFullNameLength + nameCount);
        for (int i = 0; i < nameCount; i++) {
            String name = nameGenerator.generateFullName(pools, showSeparators);
            if (name.length() <= maxFullNameLength) {
                names.append(name).append("\n");
            }
            else {
                i--;
            }
        }
        return names.toString();
    }

    private void updateNameCountText() {
        String message = String.format(getString(R.string.name_count), nameCount);
        TextView textView = findViewById(R.id.txtNameCount);
        textView.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name_generator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
