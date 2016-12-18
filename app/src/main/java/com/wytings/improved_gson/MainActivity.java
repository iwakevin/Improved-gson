package com.wytings.improved_gson;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wytings.improved_gson.gson.JsonUtils;
import com.wytings.improved_gson.module.ListModule;
import com.wytings.improved_gson.module.StringModule;

import static android.widget.LinearLayout.SHOW_DIVIDER_BEGINNING;
import static android.widget.LinearLayout.SHOW_DIVIDER_MIDDLE;

public class MainActivity extends Activity {

    LinearLayout buttonsContainer;
    String json = "{\"code\":9.9,\"data\":{},\"msg\":\"message\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonsContainer = createContainer();
        setContentView(buttonsContainer);
        initButtons();
    }


    private void testListJson() {
        try {
            ListModule module = JsonUtils.parseJson(json, ListModule.class);
            Toast.makeText(this, module.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void testObjectJson() {
        try {
            StringModule module = JsonUtils.parseJson(json, StringModule.class);
            Toast.makeText(this, module.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void testString() {
        try {
            String module = JsonUtils.parseJson(json, String.class);
            Toast.makeText(this, module, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initButtons() {
        addButton(json, android.R.color.holo_purple, null);
        addButton("test error list ", android.R.color.holo_green_light, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testListJson();
            }
        });
        addButton("test Object to String ", android.R.color.holo_red_light, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testObjectJson();
            }
        });
        addButton("test String ", android.R.color.holo_blue_light, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testString();
            }
        });
    }

    private LinearLayout createContainer() {
        LinearLayout buttonsContainer = new LinearLayout(this);
        buttonsContainer.setOrientation(LinearLayout.VERTICAL);
        buttonsContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        buttonsContainer.setShowDividers(SHOW_DIVIDER_MIDDLE | SHOW_DIVIDER_BEGINNING);
        return buttonsContainer;
    }


    private void addButton(String title, int backgroundColor, View.OnClickListener listener) {
        if (!TextUtils.isEmpty(title)) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            int margin = 30;
            params.setMargins(margin, margin, margin, 0);
            button.setLayoutParams(params);
            button.setText(title);
            button.setBackgroundColor(getResources().getColor(backgroundColor));
            button.setOnClickListener(listener);
            buttonsContainer.addView(button);
        }
    }
}
