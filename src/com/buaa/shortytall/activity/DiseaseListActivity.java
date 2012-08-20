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
        list.add(new Disease("板状腹", "强心和利尿药物", "触诊会摸到腹部很硬,象木版那么“硬”,故称为板状腹。", "无论是心脏瓣膜成形还是置换手术，危险性都在逐渐减少，除部分重症患者外，成功率一般在96%以上。"));
        list.add(new Disease("便秘", "排毒养颜胶囊", "便秘是一种很常见的临床症状，便秘是指便次太少，或排便不畅、费力、困难、粪便干结且最少。正常时，每日便次1-2次或2-3日排便一次，但粪便的量和便次常受食物种类以及环境的影响。许多患者的排便<3次/周，严重者长达2-4周才排便1次。有的每日排便可多次，但排便困难，排便时间每次可长达30分钟以上，粪便硬如羊粪，且数量极少。", ""));
        list.add(new Disease("胆囊结石", "胆石片", "胆囊结石是胆囊内发生结石的疾病，是我国的一种常见病，随年龄增长，发病率也升高，女性比男性发病率高。正常情况下胆囊是不会发生结石的，因为在正常胆汁中有一定比例的胆盐、卵磷脂使胆固醇保持溶解状态而不析出。胆囊结石在无感染时，一般无特殊体征或仅有右上腹轻度压痛。", "强调重点是初级预防，并针对初级预防进行了一系列预测胆石病高危人群的研究。"));
        list.add(new Disease("恶心与呕吐", "颈肩腰痛贴", "恶心与呕吐是临床常见症状。恶心常为呕吐的前驱感觉，也可单独出现。表现上腹部特殊不适感，常伴有头晕、流涎、脉缓、血压降低等发走神经兴奋症状。呕吐是指胃内容物或一部分小肠内容物，通过食管逆流出口腔的一种复杂的反射动作。呕吐可将有害物质从胃排出，人而起保护作用，但持久而剧烈的呕吐，可引起技水、电解质紊乱、代谢性碱中毒及营养不良，时甚至发生食管贲门黏膜撕裂伤山（MallmpWiess综合征）并发症。", "生姜可以预防术后的恶心和呕吐。"));
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
        list.add(new Disease("毛发脱落", "要保持心情舒畅，精神愉悦。患者要积极面对生活，主动适应快节奏的生活环境，正确对待生活、工作和学习压力。对所患的脱发病情不要耿耿于怀，要树立治愈疾病的信心。", "毛发脱落可以分成两种基本类型：由于毛囊受损造成的永久性脱发，和由于毛囊短时间受损造成的暂时性脱发。永久性脱发即常见的男性秃顶。在某些欧洲国家，男性的秃顶率高达40%。", "思乐康胶囊"));
        list.add(new Disease("头皮痒", "", "头皮痒是头皮屑多，头皮真菌感染或者头皮过敏的症状", "首先应养成良好的饮食习惯。平时应多摄取碱性食物，如牛奶、蔬菜、水果、海藻等，避免进食过多的酸性食物、油炸食品和甜食。还要忌吃辛辣和刺激性食物，如辣椒、芥末、生葱、生蒜、酒、咖啡和糖。其次，应养成经常洗发的习惯。第三，要尽量避免染发"));
        list.add(new Disease("眩晕", "山楂降压胶囊", "眩晕是主观症状，是一种运动纪觉或运动错觉。是患者对于空间关系的定向感觉障碍或平衡感觉障碍，患者感到外界环境或自身在旋转、移动或摇晃，是由前庭神经系统病变所引起。与头晕不同，一般来说头晕并无外界环境或自身旋转的运动觉，即患者主诉的头重脚轻、头脑不清楚等。", "不要太疲惫"));
        list.add(new Disease("头昏", "头昏是指头脑昏昏沉沉，头晕，脑子不清醒，丢三落四，自我感觉工作效率越来越低下。严重的话头昏眼花，眼冒金星，头昏脑胀，头重脚轻，常伴有烦躁，恶心，作呕。", "这是一种不常见的政治", "处方：天麻、茯苓各18克，白术、陈皮各12克，姜半夏9克，甘草6克。"));
        mockData.put(MyHealth.Body_Area.HEAD, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("扁平足", "无", "扁平足(骨科),平足症主要是由于某些原因使足骨形态异常、肌肉萎缩、韧带挛缩或慢性劳损造成足纵弓塌陷或弹性消失所引起的足痛，又称为扁平足。", "平足患者不宜穿有跟的鞋，包括中跟鞋和坡跟鞋。鞋跟具有力学功能，可以使重力线由脚跟向前移动，增加足弓和前脚的压力，高跟鞋所造成的足病多发就是这个原因，而中跟鞋的作用也是一样的，平足患者应特别注意。"));
        list.add(new Disease("大腿内侧红斑", "无", "大腿内侧红斑常见于股癣。股癣是指发生于人体阴囊和腹股部位的皮肤癣疾，这种由皮肤癣菌(Dermatomyces)感染引起的皮肤癣菌病(Dermatomycosis)主要发生在股部(大腿内根部两侧)，故名股癣。这种癣病可向会阴部、肛周、臀部发展，故又有“阴股癣”之名。", "无"));
        list.add(new Disease("脚臭", "脚气脚臭脚汗一扫光", "脚臭是由于脚心汗腺多，容易出汗，汗液里除含水分、盐分外，还含有乳酸及尿素。在多汗条件下，脚上的细菌大量繁殖并分解角质蛋白，再加上汗液中的尿素、乳酸，这样就会发出一种臭味。若鞋子不透气，空气不流通，臭味就会越积越浓，臭气异常强烈。", "脚气脚臭脚汗一扫光"));
        list.add(new Disease("老年人腿抽筋", "无", "一些老年人在夜间睡眠中或白天休息时会发生腿抽筋。引起老年人腿抽筋的原因很多，通常人们总是把腿抽筋和缺钙联系在一起，因为缺钙确实会引起腿抽筋。但老年人腿抽筋并非都是缺钙引起，有相当一部分老年人发生腿抽筋是与腿部血液循环不良有关。", "对于寒冷、疲劳或营养不良引起的小腿抽筋，睡前可用温水泡泡脚，或睡眠中多注意保暖，并适当做一些腿脚的按摩，注意合理膳食、均衡营养，科学锻炼，多晒太阳，适当补充维生素B1、维生素E等，都能起到良好的预防作用。但如果经常发作，且持续时间比较长，尤其是有其他疾病的中老年人，则应尽快去医院，千万不要自作主张，以免出事。"));
        mockData.put(MyHealth.Body_Area.LOWER_LIMB, list);
        list = new ArrayList<Disease>();
//        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
//        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
//        mockData.put(MyHealth.Body_Area.NECK, list);
//        list = new ArrayList<Disease>();
//        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
//        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
//        mockData.put(MyHealth.Body_Area.REPRODUCTIVE, list);
//        list = new ArrayList<Disease>();
//        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
//        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
//        mockData.put(MyHealth.Body_Area.UPPER_LIMB, list);
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
