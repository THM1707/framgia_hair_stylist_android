package com.framgia.fsalon.screen.register;

import com.framgia.fsalon.data.model.UserRespone;
import com.framgia.fsalon.data.source.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link RegisterActivity}), retrieves the data and updates
 * the UI as required.
 */
final class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.ViewModel mViewModel;
    private UserRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public RegisterPresenter(RegisterContract.ViewModel viewModel, UserRepository repository) {
        mViewModel = viewModel;
        mRepository = repository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void registryAccount(String email, String password, String rePassword, String name,
                                String phone) {
        if (validateRePassword(password, rePassword)) {
            Disposable disposable = mRepository.registry(email, password, rePassword, name, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserRespone>() {
                    @Override
                    public void onNext(@NonNull UserRespone userRespone) {
                        mRepository.saveCurrentUser(userRespone).subscribe();
                        mViewModel.onRegistrySuccess();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mViewModel.onRegistryFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            mCompositeDisposable.add(disposable);
        }
    }

    public boolean validateRePassword(String password, String rePassword) {
        return (password.equals(rePassword));
    }
}
