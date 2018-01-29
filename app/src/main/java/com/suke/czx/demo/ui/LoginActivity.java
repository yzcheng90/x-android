package com.suke.czx.demo.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.google.gson.Gson;
import com.suke.czx.demo.AppConstant;
import com.suke.czx.demo.AppSetting;
import com.suke.czx.demo.R;
import com.suke.czx.demo.base.BaseActivity;
import com.suke.czx.demo.model.user.TokenEntity;
import com.suke.czx.demo.model.user.UserEntity;
import com.suke.czx.demo.present.user.LoginPresent;
import com.suke.czx.demo.widget.AlertView;
import com.suke.czx.demo.widget.DialogLoading;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;

public class LoginActivity extends BaseActivity<LoginPresent> {

    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;
    public DialogLoading loading;

    private static final int REQUEST_CODE_PERMISSION_SINGLE = 100;

    @Override
    public void initData(Bundle savedInstanceState) {
        loading = new DialogLoading(context);
        // 申请单个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_SINGLE)
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                )
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(LoginActivity.this, rationale).show();
                    }
                })
                .start();

    }

    @OnClick({R.id.submit})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.submit:
                setSubmit();
                break;
        }
    }

    public void setSubmit(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("mobile",mobile.getText().toString());
        hashMap.put("password",password.getText().toString());
        loading.show();
        getmPresenter().login(hashMap);
    }

    @Override
    public void showAlert(String msg) {
        super.showAlert(msg);
        if(loading.isShowing()){
            loading.dismiss();
        }
    }

    public void showError(NetError error){
        if(loading.isShowing()){
            loading.dismiss();
        }
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    AlertView.showTip(context, "数据解析异常！");
                    break;

                case NetError.NoConnectError:
                    AlertView.showTip(context, "网络无连接！");
                    break;

                case NetError.NoDataError:
                    AlertView.showTip(context, "添加失败！请重试！");
                    break;

                case NetError.OtherError:
                    AlertView.showTip(context, "服务器繁忙！");
                    break;
            }
        }
    }

    public void showSuccess(UserEntity userEntity){
        if(loading.isShowing()){
            loading.dismiss();
        }
        showAlert("登录成功！");
        XLog.d("------------",new Gson().toJson(userEntity));
        AppSetting.Initial(context).setStringPreferences(AppConstant.USER_INFO,new Gson().toJson(userEntity));
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(userEntity.getToken());
        tokenEntity.setExpire(userEntity.getExpire());
        AppSetting.Initial(context).setStringPreferences(AppConstant.USER_TOKEN,new Gson().toJson(tokenEntity));
    }


    @PermissionYes(REQUEST_CODE_PERMISSION_SINGLE)
    private void getSingleYes(@NonNull List<String> grantedPermissions) {
        XLog.d("权限获取成功！");
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_SINGLE)
    private void getSingleNo(@NonNull List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            //用自定义的提示语。
            AndPermission.defaultSettingDialog(this, 10)
                    .setTitle("权限申请失败")
                    .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
                    .setPositiveButton("好，去设置")
                    .show();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(LoginActivity.class)
                .data(new Bundle())
                .launch();
    }
}
