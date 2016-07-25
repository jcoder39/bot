package name.bvv.bot.attack.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by User on 23.07.2016.
 */
public class Message
{
    @JsonProperty
    private String id;

    @JsonProperty
    private Map<String, Object> data;

    public Message() {
    }

    public Message(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
