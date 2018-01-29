package com.suke.czx.demo.net;

import com.suke.czx.demo.AppConstant;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by czx on 2018/1/29.
 */

public class Api {

    private static Service service;

    public static Service getService() {
        if (service == null) {
            synchronized (Api.class) {
                if (service == null) {
                    service = XApi.getInstance().getRetrofit(AppConstant.DEBUG_URL, true).create(Service.class);
                }
            }
        }
        return service;
    }
}
