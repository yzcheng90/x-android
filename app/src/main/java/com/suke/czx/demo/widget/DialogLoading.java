package com.suke.czx.demo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.suke.czx.demo.R;


/**
 * Created by czx on 2017/4/10.
 */

public class DialogLoading extends Dialog{

    private Context context;
    private String text_value;

    public DialogLoading(Context context){
        super(context, R.style.record_voice_dialog_loading);
        this.context = context;
    }

    public DialogLoading(Context context, String text){
        super(context, R.style.record_voice_dialog_loading);
        this.context = context;
        this.text_value = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog_loading, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        TextView textView = (TextView) view.findViewById(R.id.text_value);
        if(text_value != null){
            textView.setText(text_value);
        }
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
    }

    public String getText_value() {
        return text_value;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }
}
