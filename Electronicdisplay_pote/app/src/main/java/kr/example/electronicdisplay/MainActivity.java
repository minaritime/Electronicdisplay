package kr.example.electronicdisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

// 앱실행시 처음 띄워지며 설정된 전광판을 보여주는 액티비티
public class MainActivity extends AppCompatActivity {

    Button mBtnSetting;
    TextView mTxtPlayWord;

    /**
     * 전광판 화면
     * - 처음 앱을 실행 시 띄워주는 화면이며 설정된 TextView의 애니메이션을 보여줍니다.
     *
     * 설정버튼
     * - 버튼 클릭 시 TextView의 Text나 색상, 애니메이션 효과를 설정하는 화면으로 이동합니다.
     *   해당 버튼은 깔끔한 화면을 위해 2초 뒤에 자동으로 사라지며 TextView를 클릭 시 다시 버튼이 보입니다.
     *
     * 가로 고정
     * - 전광판 역할을 하기 위해 화면을 가로로 고정시켰습니다.
     *
     *
     * @param savedInstanceState
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // id 연결
        mBtnSetting = (Button) findViewById(R.id.setting_button);
        mTxtPlayWord = (TextView) findViewById(R.id.tv1);

        // 기본값 설정
        if(Define.ins().anim == null) {
            Define.ins().anim = AnimationUtils.loadAnimation(this, R.anim.translate_noalpha_normal);
        }
        mTxtPlayWord.setAnimation(Define.ins().anim);       // TextView에 애니메이션을 설정
        mTxtPlayWord.startAnimation(Define.ins().anim);     // TextView에 설정된 애니메이션을 시작

        // 앱 처음 실행시 기본값 설정, 설정화면에서 이동하여 왔다면 기존 값 변경
        if(Define.ins().color == 0) {
            mTxtPlayWord.setTextColor(Color.BLACK);
        }else {
            mTxtPlayWord.setTextColor(Define.ins().color);
        }
        if(!Define.ins().Text.equals("")){
            mTxtPlayWord.setText(Define.ins().Text);
        }else{
            mTxtPlayWord.setText("Welcome Moving display");
        }

        // 메인화면에서 2초 뒤에 버튼이 사라진다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtnSetting.setVisibility(View.GONE);
            }
        }, 2000);

        // 텍스트 클릭 시
        mTxtPlayWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 다시 생긴다.
                mBtnSetting.setVisibility(View.VISIBLE);
                // 2초 뒤 버튼이 다시 사라진다.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBtnSetting.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });

        // 설정 액티비티로 전환
        mBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingMain.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
