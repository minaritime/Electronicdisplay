package kr.example.electronicdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

// 메인화면에서 뜨는 전광판의 효과를 설정할 수 있는 액티비티
public class SettingMain extends AppCompatActivity {

    View v;
    Button mBtnPlay;
    Button mBtnColor;
    Button mBtnBlink;
    Button mBtnSpeed;
    EditText mEdtWord;
    TextView mTxtPlayWord;

    /**
     * 설정화면
     * - 메인화면에서 띄워줄 TextView의 애니메이션 효과, Text, 글자색상 등을 설정할 수 있는 화면입니다.
     *
     * 텍스트 설정
     * - 글자를 입력하면 싱글톤으로 저장하여 설정화면을 벗어나 메인화면을 시작할때 저장한 싱글톤을 불러와 setText를 합니다.
     *
     * 깜빡임 설정
     * - 버튼을 누를 시 OFF, ON을 번갈아 가며 setText를 하며 애니메이션을 저장한 싱글톤으로 저장합니다.
     *
     * 색상
     * - 버튼을 누를 시 Colorpicker가 다이얼로그로 팝업되며 원하는 색상을 선택할 수 있습니다.
     *   'OK' 버튼을 누를 시 버튼의 배경색상을 해당 색상으로 변경하고 싱글톤에 저장합니다.
     *   'CANCEL' 버튼을 누를 시 아무일도 하지않고 다이얼로그를 종료합니다.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        v = getLayoutInflater().inflate(R.layout.activity_main, null);

        // id 연결
        mBtnPlay = (Button) findViewById(R.id.start_button);
        mBtnColor = (Button) findViewById(R.id.color_button);
        mBtnBlink = (Button) findViewById(R.id.blink_button);
        mBtnSpeed = (Button) findViewById(R.id.speed_button);
        mEdtWord = (EditText) findViewById(R.id.editText);
        mTxtPlayWord = (TextView) v.findViewById(R.id.tv1);

        // 초기화(설정 창을 켰을때의 기본값 설정)
        if(Define.ins().tempblink == 0) {
            mBtnBlink.setText("off");
        }
        else if(Define.ins().tempblink == 1){
            mBtnBlink.setText("on");
        }
        if(Define.ins().tempspeed == 0){
            mBtnSpeed.setText("느림");
        }
        else if(Define.ins().tempspeed == 1){
            mBtnSpeed.setText("보통");
        }
        else if(Define.ins().tempspeed == 2){
            mBtnSpeed.setText("빠름");
        }
        mBtnColor.setBackgroundColor(Define.ins().color);

        // 색상 변경 버튼
        mBtnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        // 속도 버튼
        mBtnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 속도버튼 텍스트가 느림(tempspeed가 0인 상태일때) 일때
                if(Define.ins().tempspeed == 0){
                    mBtnSpeed.setText("보통");
                    // 깜빡임이 꺼져있을 때
                    if(Define.ins().tempblink == 0) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_normal);
                    }
                    // 깜빡임이 켜져있을 때
                    else {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_normal);
                    }
                    Define.ins().tempspeed = 1;
                }
                // 속도버튼 텍스트가 보통 일때
                else if(Define.ins().tempspeed == 1){
                    mBtnSpeed.setText("빠름");
                    // 깜빡임이 꺼져있을 때
                    if(Define.ins().tempblink == 0) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_fast);
                    }
                    // 깜빡임이 켜져있을 때
                    else {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_fast);
                    }
                    Define.ins().tempspeed = 2;
                }
                // 속도버튼 텍스트가 빠름 일때
                else {
                    mBtnSpeed.setText("느림");
                    // 깜빡임이 꺼져있을 때
                    if(Define.ins().tempblink == 0) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_slow);
                    }
                    // 깜빡임이 켜져있을 때
                    else {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_slow);
                    }
                    Define.ins().tempspeed = 0;
                }
            }
        });

        // 깜빡임 버튼
        mBtnBlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 깜빡임이 켜져있을 때
                if(Define.ins().tempblink == 1) {
                    // 속도가 느릴 때
                    if(Define.ins().tempspeed == 0) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_slow);        // 깜빡임 없음
                    }
                    // 속도가 보통일 때
                    else if(Define.ins().tempspeed == 1) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_normal);
                    }
                    // 속도가 빠를 때
                    else {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_noalpha_fast);
                    }
                    mBtnBlink.setText("off");
                    mTxtPlayWord.setAnimation(Define.ins().anim);
                    Define.ins().tempblink = 0;
                }
                // 깜빡임이 꺼져있을 때
                else {
                    // 속도가 느릴 때
                    if(Define.ins().tempspeed == 0) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_slow);          // 깜빡임 있음
                    }
                    // 속도가 보통일 때
                    else if(Define.ins().tempspeed == 1) {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_normal);
                    }
                    // 속도가 빠를 때
                    else {
                        Define.ins().anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.translate_alpha_fast);
                    }
                    mBtnBlink.setText("on");
                    mTxtPlayWord.setAnimation(Define.ins().anim);
                    Define.ins().tempblink = 1;
                }
            }
        });

        // 에딧텍스트 변경 이벤트
        mEdtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Define.ins().Text = s.toString();   // 입력된 내용을 싱글톤에 저장
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 시작 버튼
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인 화면으로 이동후
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                // 설정 화면을 종료한다
                finish();
            }
        });
    }

    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        // ArrayList에 색상코드 추가
        colors.add("#FF0000");
        colors.add("#FF6600");
        colors.add("#FFFF00");
        colors.add("#00FF00");
        colors.add("#006633");
        colors.add("#00FFCC");
        colors.add("#3399FF");
        colors.add("#0000FF");
        colors.add("#000099");
        colors.add("#9900FF");
        colors.add("#FF00FF");
        colors.add("#990033");
        colors.add("#000000");
        colors.add("#666666");
        colors.add("#FFFFFF");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        // OK 버튼 클릭 시 이벤트
                        mBtnColor.setBackgroundColor(color);
                        Define.ins().color = color;
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }

    // 뒤로가기 이벤트 (설정을 해주면 메인 화면이 2개 이상 뜨는것을 방지할 수 있음)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 메인 화면으로 이동후
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
        // 설정 화면을 종료한다
        finish();
    }
}
