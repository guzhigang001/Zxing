package com.example.ggxiaozhi.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggxiaozhi.zxing.zxing.app.CaptureActivity;
import com.example.ggxiaozhi.zxing.zxing.util.Util;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private ImageView mImageView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);
        mTextView = (TextView) findViewById(R.id.tv);
        mImageView = (ImageView) findViewById(R.id.image);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CaptureActivity.class), 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                String code = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, "onActivityResult:----> " + code);
                if (code.contains("http") || code.contains("https")) {
                    //二维码
                    mTextView.setText(code);
                } else if ((!code.contains("http") || !code.contains("https")) && code != null && TextUtils.isEmpty(code)) {
                    //条形码数字
                    mTextView.setText(code);
                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == 300) {
                //扫描图片
                String code = data.getStringExtra("result");
                Log.d(TAG, "onActivityResult:---->result " + code);
                mTextView.setText(code);
            }
            if (resultCode == 200) {
                //生成二维码回调
                Bitmap bitmap = Util.createQRCode(dip2px(this, 200), dip2px(this, 200), "https://www.baidu.com/");
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * px 和dip的转换
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }
}
