package com.suke.czx.demo.present.user;

import com.suke.czx.demo.base.AppBaseResult;
import com.suke.czx.demo.model.user.UserEntity;
import com.suke.czx.demo.net.Api;
import com.suke.czx.demo.ui.LoginActivity;
import java.util.HashMap;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by czx on 2018/1/12.
 */

public class LoginPresent extends XPresent<LoginActivity> {

    public void login(HashMap<String,Object> hashMap){
        AppBaseResult appBaseResult = new AppBaseResult<String>();
        appBaseResult.setEncryptData(hashMap);
        Api.getService().login(appBaseResult)
                .compose(XApi.<AppBaseResult<UserEntity>>getApiTransformer())
                .compose(XApi.<AppBaseResult<UserEntity>>getScheduler())
                .subscribe(new ApiSubscriber<AppBaseResult<UserEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(AppBaseResult<UserEntity> result) {
                        if(result.getCode() == AppBaseResult.SUCCESS){
                            getV().showSuccess(result.getData());
                        }else{
                            getV().showAlert(result.getMessage());
                        }
                    }
                });
    }

}
