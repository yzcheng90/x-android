package com.suke.czx.demo.base;

import android.content.Intent;

import com.google.gson.Gson;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.model.user.UserEntity;


import java.util.HashMap;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * Created by czx on 2017/11/13.
 */

public abstract class BaseFragment<P extends IPresent> extends XFragment<P> {

    public UserEntity result = null;
    public HashMap<String,Object> staffInfo = null;

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
        context.setResult(context.RESULT_OK,intent);
        context.finish();//结束当前的activity的生命周期
    }

}
