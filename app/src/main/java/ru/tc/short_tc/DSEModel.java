package ru.tc.short_tc;

import androidx.annotation.NonNull;

public class DSEModel {
    public int id;
    public String dse_id;
    public String dse_name;

    @NonNull
    @Override
    public String toString() {
        return dse_id + " " + dse_name;
    }
}
