package com.sys.blackcat.download.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.sys.blackcat.download.DownLoadManager;
import com.sys.blackcat.download.demo.databinding.LayoutAaBinding;

public class MainActivity extends AppCompatActivity {

    private LayoutAaBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_aa);
        binding.list.setAdapter(new MYAdapter());
        binding.btnShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().shutDown();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){

            DownLoadManager.getInstance().shutDown();
//            Process.killProcess((int) Thread.currentThread().getId());
//            System.exit(0);
//            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
