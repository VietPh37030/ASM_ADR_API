package com.example.ass_adr_api.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_adr_api.R;

public class QandAActivity extends AppCompatActivity {
    private TextView mainContentTextView1, mainContentTextView2, mainContentTextView3, mainContentTextView4, mainContentTextView5;
    private TextView detailContentTextView1, detailContentTextView2, detailContentTextView3, detailContentTextView4, detailContentTextView5;

    private boolean isDetailVisible1 = false;
    private boolean isDetailVisible2 = false;
    private boolean isDetailVisible3 = false;
    private boolean isDetailVisible4 = false;
    private boolean isDetailVisible5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand_aactivity);

        mainContentTextView1 = findViewById(R.id.main_content_text_view);
        detailContentTextView1 = findViewById(R.id.detail_content_text_view);

        mainContentTextView2 = findViewById(R.id.main2_content_text_view);
        detailContentTextView2 = findViewById(R.id.detail2_content_text_view);

        mainContentTextView3 = findViewById(R.id.main3_content_text_view);
        detailContentTextView3 = findViewById(R.id.detail3_content_text_view);

        mainContentTextView4 = findViewById(R.id.main4_content_text_view);
        detailContentTextView4 = findViewById(R.id.detail4_content_text_view);

        mainContentTextView5 = findViewById(R.id.main5_content_text_view);
        detailContentTextView5 = findViewById(R.id.detail5_content_text_view);

        setToggleListener(mainContentTextView1, detailContentTextView1, isDetailVisible1);
        setToggleListener(mainContentTextView2, detailContentTextView2, isDetailVisible2);
        setToggleListener(mainContentTextView3, detailContentTextView3, isDetailVisible3);
        setToggleListener(mainContentTextView4, detailContentTextView4, isDetailVisible4);
        setToggleListener(mainContentTextView5, detailContentTextView5, isDetailVisible5);

        // Ẩn nội dung chi tiết ban đầu
        detailContentTextView1.setVisibility(View.GONE);
        detailContentTextView2.setVisibility(View.GONE);
        detailContentTextView3.setVisibility(View.GONE);
        detailContentTextView4.setVisibility(View.GONE);
        detailContentTextView5.setVisibility(View.GONE);
    }

    private void setToggleListener(TextView mainTextView, TextView detailTextView, boolean isDetailVisible) {
        mainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetailContent(detailTextView);
            }
        });
    }

    private void toggleDetailContent(TextView detailTextView) {
        // Lấy trạng thái hiện tại của detailTextView
        boolean isDetailVisible = detailTextView.getVisibility() == View.VISIBLE;
        // Đảo ngược trạng thái hiển thị của detailTextView
        detailTextView.setVisibility(isDetailVisible ? View.GONE : View.VISIBLE);
    }

}
