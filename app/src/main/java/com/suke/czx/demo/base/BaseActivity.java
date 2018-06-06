package com.suke.czx.demo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.suke.czx.demo.App;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.model.user.UserEntity;
import com.suke.czx.demo.widget.AlertView;

import java.util.HashMap;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by czx on 2017/5/13.
 */

public abstract class BaseActivity<P> extends XActivity {

    public UserEntity result = null;
    private P mPresenter;
    public HashMap<String,Object> staffInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.addActivity(this);
        super.onCreate(savedInstanceState);
    }

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

    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    AlertView.showTip(context,"数据解析异常！");
                    break;

                case NetError.AuthError:
                    AlertView.showTip(context,"用户验证过期或已在它端登录");
                    break;

                case NetError.NoConnectError:
                    AlertView.showTip(context,"网络无连接！");
                    break;

                case NetError.OtherError:
                    AlertView.showTip(context,"服务器繁忙！");
                    break;
            }
        }
    }
}
