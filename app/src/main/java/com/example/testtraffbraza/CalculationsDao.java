package com.example.testtraffbraza;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CalculationsDao {

    @Query("SELECT balance FROM calculationData")
    LiveData<Integer> getBalance();

    @Query("SELECT balance FROM calculationData")
    int getBalanceInt();

    @Query("SELECT rate FROM calculationData")
    int getRate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(CalculationData calculationData);
}
