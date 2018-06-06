package com.suke.czx.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.blankj.utilcode.util.Utils;
import com.suke.czx.demo.interceptor.DataInterceptor;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by czx on 2017/1/29.
 */

public class App extends Application {

    private static Context context;
    private static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Utils.init(this);

        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return  new Interceptor[]{new DataInterceptor()};
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void closeAllActivity(){
        for (Activity activity : activityList){
            if(activity != null){
                activity.finish();
            }
        }
        activityList.clear();
        AppSetting.Initial(context).removePreferences(AppConstant.USER_INFO);
    }

    public static List<Activity> getActivityList(){
        return activityList;
    }
}
