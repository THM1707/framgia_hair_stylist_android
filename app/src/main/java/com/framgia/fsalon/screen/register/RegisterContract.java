package com.framgia.fsalon.screen.register;

import com.framgia.fsalon.BasePresenter;
import com.framgia.fsalon.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RegisterContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onRegistryClick();
        void onRegistrySuccess();
        void onRegistryFail(String msg);
        void onLoginClick();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void registryAccount(String email, String password, String rePassword, String name,
                             String phone);
    }
}
