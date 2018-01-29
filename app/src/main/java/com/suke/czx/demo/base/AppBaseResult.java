package com.suke.czx.demo.base;

import com.google.gson.Gson;
import com.suke.czx.crypt.CDESCrypt;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by czx on 2017/9/8.
 */

public class AppBaseResult<T> implements IModel {

    private int code = 200;
    private String message;
    private T data;
    private String version = "1.0";
    private String mobile = "android";

    public final static int ERROR = 401;
    public final static int SUCCESS = 200;
    public final static int FAIL = 500;
    public final static int TOKENFAIL = 1000;
    public final static String KEY = "czx12345";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data =  data;
    }

    public void setEncryptData(T t){
        String mData = new Gson().toJson(t);
        try {
            if(mData != null && !mData.equals("")){
                this.data = (T) CDESCrypt.encryptString(mData, KEY);
            }else{
                this.data = t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return getCode() == TOKENFAIL;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
