package com.example.testtraffbraza;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameActivityViewModel extends AndroidViewModel {
    private CalculationsDao calculationsDao;
    private CalculationData calculationData;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final int startBalance = 0;
    private final int startRate = 100;
    private int rate = startRate;
    private int balance;

    private MutableLiveData<Integer> balanceFromDB = new MutableLiveData<>();


    public GameActivityViewModel(@NonNull Application application) {
        super(application);
        calculationsDao = CalculatedDatabase.getInstance(application).calculationsDao();
        calculationData = new CalculationData(1, startBalance, startRate);

        Disposable disposable = calculationsDao.add(calculationData).subscribeOn(Schedulers.io()).subscribe();
        compositeDisposable.add(disposable);
    }

    public LiveData<Integer> getBalanceForUI() {
        return balanceFromDB;
    }

    public void refreshBalance() {
        Disposable disposable = calculationsDao.getBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integerFromDB) throws Throwable {
                balanceFromDB.setValue(integerFromDB);
            }
        });
        compositeDisposable.add(disposable);
    }

    public void balanceCalculation(int index1, int index2, int index3) {
        Disposable disposable = calculationsDao.getBalance().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                balance = integer;

                if (index1 == index2 && index2 == index3) {
                    balance += rate * 5;
                } else if (index1 == index2 || index2 == index3) {
                    balance += rate * 2;
                } else {
                    balance -= rate;
                }
            }
        });
        compositeDisposable.add(disposable);



        calculationData.setBalance(balance);
        Disposable disposable2 = calculationsDao.add(calculationData).subscribeOn(Schedulers.io()).subscribe();
        compositeDisposable.add(disposable2);

        Disposable disposable3 = calculationsDao.getBalance().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                balanceFromDB.setValue(integer);
            }
        });
        compositeDisposable.add(disposable3);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
