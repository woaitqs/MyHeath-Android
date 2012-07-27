package com.buaa.shortytall.activity;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DiseaseDetailActivity extends DefaultActivity{

    private TextView nameText;
    private TextView descText;
    private TextView drugText;
    private TextView tipsText;
    
    @Override
    protected String getActionBarTitle() {
        return "Disease Detail";
    }

    @Override
    protected Context getContext() {
        return DiseaseDetailActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseasedetail);
        nameText = (TextView)findViewById(R.id.disease_detail_name);
        descText = (TextView)findViewById(R.id.disease_detail_description);
        drugText = (TextView)findViewById(R.id.disease_detail_drugs);
        tipsText = (TextView)findViewById(R.id.disease_detail_tips);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent== null || intent.getBundleExtra(MyHealth.Bundle_keys.DISEASE) == null){
            finish();
        }
        Bundle bundle = intent.getBundleExtra(MyHealth.Bundle_keys.DISEASE);
        nameText.setText("名称:"+bundle.getString(MyHealth.Bundle_keys.DISEASE_NAME));
        descText.setText("症状:"+bundle.getString(MyHealth.Bundle_keys.DISEASE_DESCRIPTION));
        drugText.setText("用药:"+bundle.getString(MyHealth.Bundle_keys.DISEASE_DRUGS));
        tipsText.setText("注意事项:"+bundle.getString(MyHealth.Bundle_keys.DISEASE_TIPS));
    }
}
