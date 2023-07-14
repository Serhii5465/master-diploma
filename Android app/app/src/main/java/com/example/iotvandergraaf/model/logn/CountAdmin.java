package com.example.iotvandergraaf.model.logn;

import com.google.gson.annotations.SerializedName;

public class CountAdmin {
    @SerializedName("count_admins")
    private int count;

    public CountAdmin(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountAdmin{" +
                "count=" + count +
                '}';
    }
}
