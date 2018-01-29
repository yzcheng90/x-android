package com.suke.czx.demo.base;

import android.content.Intent;

import com.google.gson.Gson;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.model.user.UserEntity;
import com.suke.czx.demo.widget.AlertView;

import java.util.HashMap;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by czx on 2017/5/13.
 */

public abstract class BaseActivity<P> extends XActivity {

    public UserEntity result = null;
    private P mPresenter;
    public HashMap<String,Object> staffInfo = null;

    @Override
    public P newP() {
        return mPresenter;
    }

    public P getmPresenter(){
        return (P) getP();
    }

    public UserEntity getUserInfo(){
        String user_info = AppSetting.Initial(context).getStringPreferences(AppConstant.USER_INFO);
        if(user_info!=null && !user_info.equals("")){
            Gson json = new Gson();
            result = json.fromJson(user_info,UserEntity.class);
        }
        return result;
    }

    public void showSuccessForResult(){
        Intent intent = new Intent();
        //通过Intent对象返回结果，调用setResult方法
        setResult(RESULT_OK,intent);
        finish();//结束当前的activity的生命周期
    }

    public void showAlert(String msg){
        AlertView.showTip(context,msg);
    }
}
