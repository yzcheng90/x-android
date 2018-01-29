package com.suke.czx.demo.base;

import com.google.gson.Gson;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.model.user.UserEntity;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * Created by czx on 2017/11/13.
 */

public abstract class BaseLazyFragment<P extends IPresent> extends XLazyFragment<P> {

    public UserEntity result = null;

    public UserEntity getUserInfo(){
        String user_info = AppSetting.Initial(context).getStringPreferences(AppConstant.USER_INFO);
        if(user_info!=null && !user_info.equals("")){
            Gson json = new Gson();
            result = json.fromJson(user_info,UserEntity.class);
        }
        return result;
    }

}
