package com.example.gaomin.invokedemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gaomin.invokedemo.utils.InjectUtils;


/**
 * Created by gaomin on 2016/6/28.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }
}
