package com.framgia.fsalon.screen.register;

import android.app.DatePickerDialog;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;

import com.framgia.fsalon.BR;
import com.framgia.fsalon.screen.home.HomeActivity;
import com.framgia.fsalon.screen.login.LoginActivity;
import com.framgia.fsalon.utils.Utils;
import com.framgia.fsalon.utils.navigator.Navigator;

import java.util.Calendar;
import java.util.Date;

/**
 * Exposes the data to be used in the Register screen.
 */
public class RegisterViewModel extends BaseObservable implements RegisterContract.ViewModel {
    private RegisterContract.Presenter mPresenter;
    private Navigator mNavigator;
    private String mName;
    private String mBirth;
    private String mEmail;
    private String mPhone;
    private String mPassword;
    private String mRePassword;
    private AppCompatActivity mActivity;

    public RegisterViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mNavigator = new Navigator(activity);
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getBirth() {
        return mBirth;
    }

    public void setBirth(String birth) {
        mBirth = birth;
        notifyPropertyChanged(BR.birth);
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.passWord);
    }

    @Bindable
    public String getRePassword() {
        return mRePassword;
    }

    public void setRePassword(String rePassword) {
        mRePassword = rePassword;
        notifyPropertyChanged(BR.rePassword);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRegistryClick() {
        mPresenter.registryAccount(mEmail, mPassword, mRePassword, mName, mPhone);
    }

    @Override
    public void onRegistrySuccess() {
        mNavigator.startActivity(HomeActivity.getInstance(mNavigator.getContext()));
        mNavigator.finishActivity();
    }

    @Override
    public void onRegistryFail(String msg) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
            .show();
    }

    @Override
    public void onLoginClick() {
        mNavigator.startActivity(LoginActivity.getInstance(mNavigator.getContext()));
        mNavigator.finishActivity();
    }

    public void onCalendarClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int day, year, month;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(),
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int dayOfMonth) {
                    Calendar result = Calendar.getInstance();
                    result.set(year, month, dayOfMonth);
                    Date date = result.getTime();
                    setBirth(Utils.dateToString(date));
                }
            }, year, month, day);
        dateDialog.show();
    }
}
