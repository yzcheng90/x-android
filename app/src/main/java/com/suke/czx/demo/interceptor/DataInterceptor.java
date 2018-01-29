package com.suke.czx.demo.interceptor;

import com.google.gson.Gson;
import com.suke.czx.crypt.CDESCrypt;
import com.suke.czx.demo.App;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.base.AppBaseResult;
import com.suke.czx.demo.model.user.TokenEntity;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by czx on 2017/4/27.
 */

public class DataInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestUrl = request.url().toString();
        String responseBody = "";
        Request.Builder requestBuilder = request.newBuilder();
        String userToken = AppSetting.Initial(App.getContext()).getStringPreferences(AppConstant.USER_TOKEN);
        if(userToken != null){
            TokenEntity tokenEntity = new Gson().fromJson(userToken,TokenEntity.class);
            if(tokenEntity != null){
                requestBuilder.addHeader("token", tokenEntity.getToken());
            }
        }

        Response response = chain.proceed(requestBuilder.build());
        String context = response.body().string();
        if(requestUrl.contains("?")){
            responseBody = context;
        }else{
            AppBaseResult appBaseResult = new Gson().fromJson(context,AppBaseResult.class);
            try {
                Object data = appBaseResult.getData();
                if(data != null && !data.toString().equals("")){
                    responseBody = "{\"code\":"+appBaseResult.getCode()+",\"message\":\""+appBaseResult.getMessage()+"\",\"data\":"+CDESCrypt.decryptString(new Gson().toJson(data),AppBaseResult.KEY)+"}";
                }else{
                    responseBody = "{\"code\":"+appBaseResult.getCode()+",\"message\":\""+appBaseResult.getMessage()+"\",\"data\":null}";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MediaType mediaType = response.body().contentType();
        return response.newBuilder().body(ResponseBody.create(mediaType, responseBody)).build();
    }
}
