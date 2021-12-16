package kr.example.electronicdisplay;

import android.graphics.drawable.Drawable;
import android.view.animation.Animation;

public class Define {

    public int color = 0;          // 전광판 텍스트 색상
    public Animation anim;         // 애니매이션을 나타내는 변수
    public int tempblink = 0;      // 깜빡임 상태 확인 변수
    public String Text = "";       // 전광판 텍스트
    public int tempspeed = 0;      // 속도 상태 확인 변수

    public static Define instance;
    public static Define ins(){
        if(instance == null){
            instance = new Define();
        }
        return instance;
    }
}
