package com.example.gaomin.invokedemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaomin.invokedemo.R;
import com.example.gaomin.invokedemo.annotation.ContentView;
import com.example.gaomin.invokedemo.annotation.OnClick;
import com.example.gaomin.invokedemo.annotation.ViewInject;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.annotionTextView)
    private TextView annotionTextView;

    @ViewInject(R.id.invokeTextView)
    private TextView invokeTextView;

    @ViewInject(R.id.addTextView)
    private TextView addTextView;

    @ViewInject(R.id.myButton1)
    private Button myButton1;

    @ViewInject(R.id.myButton2)
    private Button myButton2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTextView.setText(invokeTextView.getText().toString()
                +annotionTextView.getText().toString());

    }

    @OnClick({R.id.myButton1,R.id.myButton2})
    public void toast(View view){
        switch (view.getId()){
            case R.id.myButton1:
                Toast.makeText(MainActivity.this,"111",Toast.LENGTH_SHORT).show();
                break;
            case R.id.myButton2:
                Toast.makeText(MainActivity.this,"222",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
