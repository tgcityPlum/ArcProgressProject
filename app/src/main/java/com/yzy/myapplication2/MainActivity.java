package com.yzy.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ArcProgressView homeArcProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeArcProgressView = findViewById(R.id.home_gradeProgressView);

        homeArcProgressView.setValues(80,"物理 / 化学 / 政治");
//        homeBestSubjectProgressView.setProgressWidthAnimation(40, 60, 80, 10);
//        homeBestSubjectProgressView.setProgressWidthAnimation(80 ,"物理 / 化学 / 政治");

    }

    public void onChangeRange(View view) {
        homeArcProgressView.setValues(60,"物理 / 化学 ");
    }
}
