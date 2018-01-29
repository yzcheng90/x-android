package com.suke.czx.demo.net;

import com.suke.czx.demo.base.AppBaseResult;
import com.suke.czx.demo.model.user.UserEntity;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by czx on 2018/1/29.
 */

public interface Service {

    @POST("app/login")
    Flowable<AppBaseResult<UserEntity>> login(@Body AppBaseResult appBaseResult);
}
