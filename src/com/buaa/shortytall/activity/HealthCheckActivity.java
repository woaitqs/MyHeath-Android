package com.buaa.shortytall.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.buaa.shortytall.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HealthCheckActivity extends DefaultActivity {

    private EditText heightET;
    private EditText weightET;
    private TextView resultTV;
    private Button calcBN;

    @Override
    protected String getActionBarTitle() {
        return "身高体重自测";
    }

    @Override
    protected Context getContext() {
        return HealthCheckActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthcheck);
        heightET = (EditText) findViewById(R.id.health_calc_height);
        weightET = (EditText) findViewById(R.id.health_calc_weight);
        calcBN = (Button) findViewById(R.id.health_check_calc);
        resultTV = (TextView) findViewById(R.id.health_check_result);
        initListeners();
    }

    private void initListeners() {
        calcBN.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(heightET.getText().toString()) || TextUtils.isEmpty(weightET.getText().toString())){
                    return;
                }
                String pattern_str = "[0-9]+(。[0-9]+)?";
                Pattern pattern = Pattern.compile(pattern_str);
                if (! pattern.matcher(heightET.getText().toString()).matches() || ! pattern.matcher(weightET.getText().toString()).matches()){
                    Toast.makeText(HealthCheckActivity.this, "pleast input the data in correct way", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(heightET.getText().toString()) > 0
                        && Double.parseDouble(weightET.getText().toString()) > 0) {
                    double height = Double.parseDouble(heightET.getText()
                            .toString());
                    double weight = Double.parseDouble(weightET.getText()
                            .toString());
                    double result = weight / (height * height);

                    if (result < 19) {
                        resultTV.setText("您的身高体重状态为 体重偏低");
                    } else if (result > 19 && result < 25) {
                        resultTV.setText("您的身高体重状态为 体重健康");
                    } else if (result > 25 && result < 30) {
                        resultTV.setText("您的身高体重状态为 超重");
                    } else if (result > 30 && result < 39) {
                        resultTV.setText("您的身高体重状态为 体重严重超重");
                    } else if (result > 40) {
                        resultTV.setText("您的身高体重状态为 极度超重");
                    }
                }

            }
        });
    }
}
