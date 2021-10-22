package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;

public class Object_TempFix
{
    private int money_id;
    private int work_credit;

    public Object_TempFix(int money_id, int work_credit) {
        this.money_id = money_id;
        this.work_credit = work_credit;
    }

    public int getMoney_id() {
        return money_id;
    }

    public void setMoney_id(int money_id) {
        this.money_id = money_id;
    }

    public int getWork_credit() {
        return work_credit;
    }

    public void setWork_credit(int work_credit) {
        this.work_credit = work_credit;
    }
}
