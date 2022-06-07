package me.fixca.barcord.backend.logger;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoggerBody {

    public LoggerBody(String key, String id, int timestamp, String room_id) {
        this.key = key;
        this.id = id;
        this.timestamp = timestamp;
        this.room_id = room_id;
    }

    @SerializedName("key")
    private String key;

    @SerializedName("id")
    private String id;

    @SerializedName("timestamp")
    private int timestamp;

    @SerializedName("room_id")
    private String room_id;

    @Override
    public String toString() {
        return "LoggerBody{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", room_id='" + room_id + '\'' +
                '}';
    }
}
