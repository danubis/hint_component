package danubis.yiluo.hintcomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import danubis.tony.hintcomponent.HintComponent;
import danubis.tony.hintcomponent.HintComponentListener;

public class MainActivity extends AppCompatActivity {

    private HintComponent hintComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hintComponent = new HintComponent.Builder(this)
                .rowHeight(dip2px(40))
                .numColumns(2)
                .heightPercentage(0.4f)
                .hintViewHeight(dip2px(25))
                .hintComponentListener(new HintComponentListener() {
                    @Override
                    public void onHintClicked(String keyword) {
                        Log.e("HintComponentSample", "result: " + keyword);
                    }
                })
                .build();

        findViewById(R.id.start_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintComponent.updateHintView(generateMap());
                    }
                });
    }


    private HashMap<String, String> generateMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("aaa", "AAA");
        map.put("bbb", "BBB");
        map.put("ccc", "CCC");
        map.put("ddd", "DDD");
        map.put("eee", "EEE");
        map.put("fff", "FFF");
        map.put("ggg", "GGG");
        map.put("hhh", "HHH");
        map.put("iii", "III");
        map.put("jjj", "JJJ");
        map.put("kkk", "KKK");
        map.put("lll", "LLL");
        map.put("mmm", "MMM");
        map.put("nnn", "NNN");
        map.put("ooo", "OOO");
        map.put("ppp", "PPP");
        map.put("qqq", "QQQ");
        map.put("rrr", "RRR");
        map.put("sss", "SSS");
        map.put("ttt", "TTT");
        map.put("uuu", "UUU");
        map.put("vvv", "VVV");
        map.put("www", "WWW");
        map.put("xxx", "XXX");
        map.put("yyy", "YYY");
        map.put("zzz", "ZZZ");
        map.put("aaa", "AAA");
        map.put("bbb", "BBB");
        map.put("ccc", "CCC");
        map.put("ddd", "DDD");
        map.put("eee", "EEE");
        map.put("fff", "FFF");
        map.put("ggg", "GGG");
        map.put("hhh", "HHH");
        map.put("iii", "III");
        map.put("jjj", "JJJ");
        map.put("kkk", "KKK");
        map.put("lll", "LLL");
        map.put("mmm", "MMM");
        map.put("nnn", "NNN");
        map.put("ooo", "OOO");
        map.put("ppp", "PPP");
        map.put("qqq", "QQQ");
        map.put("rrr", "RRR");
        map.put("sss", "SSS");
        map.put("ttt", "TTT");
        map.put("uuu", "UUU");
        map.put("vvv", "VVV");
        map.put("www", "WWW");
        map.put("xxx", "XXX");
        map.put("yyy", "YYY");
        map.put("zzz", "ZZZ");
        map.put("aaa", "AAA");
        map.put("bbb", "BBB");
        map.put("ccc", "CCC");
        map.put("ddd", "DDD");
        map.put("eee", "EEE");
        map.put("fff", "FFF");
        map.put("ggg", "GGG");
        map.put("hhh", "HHH");
        map.put("iii", "III");
        map.put("jjj", "JJJ");
        map.put("kkk", "KKK");
        map.put("lll", "LLL");
        map.put("mmm", "MMM");
        map.put("nnn", "NNN");
        map.put("ooo", "OOO");
        map.put("ppp", "PPP");
        map.put("qqq", "QQQ");
        map.put("rrr", "RRR");
        map.put("sss", "SSS");
        map.put("ttt", "TTT");
        map.put("uuu", "UUU");
        map.put("vvv", "VVV");
        map.put("www", "WWW");
        map.put("xxx", "XXX");
        map.put("yyy", "YYY");
        map.put("zzz", "ZZZ");

        return map;
    }


    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
