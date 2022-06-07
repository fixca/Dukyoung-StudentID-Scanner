package me.fixca.barcord.backend.logger;

import com.google.gson.annotations.SerializedName;

public class LoggerResult {

    @SerializedName("success")
    public int success;

    @SerializedName("failure")
    public int failure;

    @Override
    public String toString() {
        return "LoggerResult{" +
                "success=" + success +
                ", failure=" + failure +
                '}';
    }
}
