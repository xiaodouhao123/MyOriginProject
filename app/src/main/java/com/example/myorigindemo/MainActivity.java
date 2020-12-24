package com.example.myorigindemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myorigindemo.myview.ProgressBar;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;

public class MainActivity extends AppCompatActivity {
    private Button bt_fetchVideos;
    private Button bt_qrcode;
    private ProgressBar pb;
    public static final int RESULT_REQUEST_CODE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_fetchVideos=findViewById(R.id.bt_fetchvideos);
        bt_qrcode=findViewById(R.id.bt_qrcode);
        pb=findViewById(R.id.pb);
        bt_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    goToQrcodeActivity();
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},1);
                }
            }
        });
        bt_fetchVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fetchVideos();
                        }
                    }).start();
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }*/
                Intent intent=new Intent(MainActivity.this, VerticalDragListViewActivity.class);
                startActivity(intent);
            }
        });
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int progress= (int) valueAnimator.getAnimatedValue();
                pb.setProgress(progress);
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    private void goToQrcodeActivity() {
        Intent intent = new Intent(this, ScannerActivity.class);
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。
          //设置扫码框的宽
          intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 800);
          //设置扫码框的高
          intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 800);
          //设置扫码框距顶部的位置
          intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 400);
          //Bundle bundle = new Bundle();
          //设置支持的扫码类型
          //bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);
          //intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void fetchVideos() {
        String str[] = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.DATA};
        Cursor cursor = MainActivity.this.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, str, null,
                null, null);
        while (cursor.moveToNext()) {
            String filePath=cursor.getString(3);
            MediaMetadataRetriever retriever=new MediaMetadataRetriever();
            retriever.setDataSource(filePath);
            String widthnew=retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //宽
            Log.w("xd","id=="+cursor.getString(0)+",DISPLAY_NAME=="+cursor.getString(1)+",width=="+widthnew);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == 0){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //用户同意，执行操作
                        fetchVideos();
                    }
                }).start();

            }else{
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
            }
        }
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                goToQrcodeActivity();
            }else{
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_REQUEST_CODE:
                    if (data == null) return;
                    String type = data.getStringExtra(Constant.EXTRA_RESULT_CODE_TYPE);
                    String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);
                    Toast.makeText(MainActivity.this,"codeType:" + type
                            + "-----content:" + content,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}