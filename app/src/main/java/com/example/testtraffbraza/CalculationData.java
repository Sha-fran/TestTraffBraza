package com.example.testtraffbraza;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "calculationData")
public class CalculationData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int balance;
    private int rate;


    public CalculationData(int id, int balance, int rate) {
        this.id = id;
        this.balance = balance;
        this.rate = rate;
    }

    @Ignore
    public CalculationData(int balance, int rate) {
        this.balance = balance;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public int getRate() {
        return rate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
