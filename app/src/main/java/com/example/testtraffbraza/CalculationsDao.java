package com.example.testtraffbraza;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CalculationsDao {

    @Query("SELECT balance FROM calculationData")
    Single<Integer> getBalance();

    @Query("SELECT rate FROM calculationData")
    Single<Integer> getRate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(CalculationData calculationData);
}
