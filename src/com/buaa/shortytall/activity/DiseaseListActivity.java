package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.view.Menu;
import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.bean.Disease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiseaseListActivity extends DefaultActivity{

    private HashMap<Integer, ArrayList<Disease>> mockData;
    private ListView listView;
    
    public void initMockData(){
        mockData = new HashMap<Integer, ArrayList<Disease>>();
        
        ArrayList<Disease> list = new ArrayList<Disease>();
        list.add(new Disease("瓣膜增厚", "强心和利尿药物", "主要是由心脏瓣膜病引起的。心脏瓣膜病是指心脏瓣膜由于增厚、变形、粘连、钙化和破裂等而失去单向阀门作用的一种心脏病。心脏瓣膜病可以引起心脏瓣膜的狭窄或关闭不全，使血液不能通畅的流过或流过后倒流，血液不能通畅流过者称为瓣膜狭窄，血液流过后倒流者称为瓣膜关闭不全。最常见的是二尖瓣病变，其次是主动脉瓣病变。", "无论是心脏瓣膜成形还是置换手术，危险性都在逐渐减少，除部分重症患者外，成功率一般在96%以上。"));
        list.add(new Disease("不典型胸痛", "需根据不同的病因进行不同的治疗", "临床上胸痛，胸部不适是常见的症状，其临床表现各异。比如胃疼、脖子疼、牙疼、嗓子疼等等。", "需根据不同的病因进行不同的治疗"));
        list.add(new Disease("不典型胸痛", "回生第一丹胶囊", "生活中“岔气”现象时有发生，特别是在我们剧烈运动时更为常见，“岔气”虽然不能造成什么危险，但是，发生“岔气”时会伴随着呼吸而产生钻心的疼痛却让人难以忍耐。", "需根据不同的病因进行不同的治疗"));
        list.add(new Disease("发热伴寒战", "回生第一丹胶囊", "由于疾病导致机体病理性体温升高并伴有寒战的症状", "(1)注意预防上呼吸道感染，加强耐寒锻炼; 　　(2)避免淋雨受寒、醉酒、过劳等诱因; 　　(3)积极治疗原发病，如慢性心肺疾病、慢性肝炎、糖尿病和口腔疾病等，可以预防大叶性肺炎。"));
        list.add(new Disease("肺不张", "大剂量镇静剂，胸廓畸形", "全肺或部分肺呈收缩和无气状态。肺不张可能为急性或慢性，在慢性肺不张病变部位往往合并存在肺无气，感染，支气管扩张，组织破坏和纤维化", "避免使用长效麻醉剂，术后亦应少用止痛剂，因为此类药物抑制咳嗽反射"));
        list.add(new Disease("反向搏动", "暂无", "心脏收缩时，左心室前壁在收缩早期撞击心前区胸壁，使相应部位肋问组织向外搏动，称为心尖搏动。左心室心肌梗死后，心室壁心肌全层坏死。约10～38%的病例坏死的心肌逐渐被纤维疤痕组织所替代，形成室壁瘤。病变区薄层的心室壁向外膨出，心脏收缩时丧失活动能力或呈现反常运动。早在1881年对冠状动脉梗阻、心肌梗塞、心肌纤维化与左心室室壁瘤的演变过程已得到充分认识。", "临床上无明显症状者，一般可严密观察病情发展情况，无需急于施行外科手术治疗。"));
        mockData.put(MyHealth.Body_Area.BELLY, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("瓣膜增厚", "强心和利尿药物", "主要是由心脏瓣膜病引起的。心脏瓣膜病是指心脏瓣膜由于增厚、变形、粘连、钙化和破裂等而失去单向阀门作用的一种心脏病。心脏瓣膜病可以引起心脏瓣膜的狭窄或关闭不全，使血液不能通畅的流过或流过后倒流，血液不能通畅流过者称为瓣膜狭窄，血液流过后倒流者称为瓣膜关闭不全。最常见的是二尖瓣病变，其次是主动脉瓣病变。", "无论是心脏瓣膜成形还是置换手术，危险性都在逐渐减少，除部分重症患者外，成功率一般在96%以上。"));
        list.add(new Disease("不典型胸痛", "需根据不同的病因进行不同的治疗", "临床上胸痛，胸部不适是常见的症状，其临床表现各异。比如胃疼、脖子疼、牙疼、嗓子疼等等。", "需根据不同的病因进行不同的治疗"));
        list.add(new Disease("不典型胸痛", "回生第一丹胶囊", "生活中“岔气”现象时有发生，特别是在我们剧烈运动时更为常见，“岔气”虽然不能造成什么危险，但是，发生“岔气”时会伴随着呼吸而产生钻心的疼痛却让人难以忍耐。", "需根据不同的病因进行不同的治疗"));
        list.add(new Disease("发热伴寒战", "回生第一丹胶囊", "由于疾病导致机体病理性体温升高并伴有寒战的症状", "(1)注意预防上呼吸道感染，加强耐寒锻炼; 　　(2)避免淋雨受寒、醉酒、过劳等诱因; 　　(3)积极治疗原发病，如慢性心肺疾病、慢性肝炎、糖尿病和口腔疾病等，可以预防大叶性肺炎。"));
        list.add(new Disease("肺不张", "大剂量镇静剂，胸廓畸形", "全肺或部分肺呈收缩和无气状态。肺不张可能为急性或慢性，在慢性肺不张病变部位往往合并存在肺无气，感染，支气管扩张，组织破坏和纤维化", "避免使用长效麻醉剂，术后亦应少用止痛剂，因为此类药物抑制咳嗽反射"));
        list.add(new Disease("反向搏动", "暂无", "心脏收缩时，左心室前壁在收缩早期撞击心前区胸壁，使相应部位肋问组织向外搏动，称为心尖搏动。左心室心肌梗死后，心室壁心肌全层坏死。约10～38%的病例坏死的心肌逐渐被纤维疤痕组织所替代，形成室壁瘤。病变区薄层的心室壁向外膨出，心脏收缩时丧失活动能力或呈现反常运动。早在1881年对冠状动脉梗阻、心肌梗塞、心肌纤维化与左心室室壁瘤的演变过程已得到充分认识。", "临床上无明显症状者，一般可严密观察病情发展情况，无需急于施行外科手术治疗。"));
        mockData.put(MyHealth.Body_Area.BREAST, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.HEAD, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.LOWER_LIMB, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.NECK, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.REPRODUCTIVE, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.UPPER_LIMB, list);
    }
    
    @Override
    protected String getActionBarTitle() {
        return "Detail";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_list);
        initMockData();
        listView = (ListView)findViewById(R.id.disease_list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private List<String> getData(int type){
        List<String> data = new ArrayList<String>();
        for(Disease disease : mockData.get(type)){
            data.add(disease.getName());
        }
        return data;
    }
    
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1) == -1){
            finish();
        }
        final int type = intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1);
        assert(type >= 100 && type <= 106);
        
        switch(type){
        case MyHealth.Body_Area.HEAD:
        case MyHealth.Body_Area.BELLY:
        case MyHealth.Body_Area.BREAST:
        case MyHealth.Body_Area.LOWER_LIMB:
        case MyHealth.Body_Area.NECK:
        case MyHealth.Body_Area.REPRODUCTIVE:
        case MyHealth.Body_Area.UPPER_LIMB:
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData(type)));
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    Disease disease = mockData.get(type).get(arg2);
                    Intent intent = new Intent(DiseaseListActivity.this, DiseaseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_DESCRIPTION, disease.getDescription());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_DRUGS, disease.getDrugs());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_NAME, disease.getName());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_TIPS, disease.getTips());
                    
                    intent.putExtra(MyHealth.Bundle_keys.DISEASE, bundle);
                    startActivity(intent);
                }});
        default:
            break;
        }
    }

    @Override
    protected Context getContext() {
        return DiseaseListActivity.this;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        
        return super.onPrepareOptionsMenu(menu);
    }
    
}
