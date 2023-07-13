package com.example.testtraffbraza;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameActivityViewModel extends AndroidViewModel {
    private CalculatedDatabase calculatedDatabase;
    private CalculationData calculationData;

    private final int startBalance = 0;
    private final int startRate = 100;
    private int balance, rate = startRate;


    public GameActivityViewModel(@NonNull Application application) {
        super(application);
        calculatedDatabase = CalculatedDatabase.getInstance(application);
        calculationData = new CalculationData(1, startBalance, startRate);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                calculatedDatabase.calculationsDao().add(calculationData);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<Integer> getBalance() {
        return calculatedDatabase.calculationsDao().getBalance();
    }

    public void balanceCalculation(int index1, int index2, int index3) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                balance = calculatedDatabase.calculationsDao().getBalanceInt();
            }
        }));
        executorService.shutdown();

        if (index1 == index2 && index2 == index3) {
            balance += rate * 5;
        } else if (index1 == index2 || index2 == index3) {
            balance += rate * 2;
        } else {
            balance -= rate;
        }

        ExecutorService executorService2 = Executors.newFixedThreadPool(1);
        executorService2.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                calculationData.setBalance(balance);
                calculatedDatabase.calculationsDao().add(calculationData);
            }
        }));
        executorService2.shutdown();
    }
}