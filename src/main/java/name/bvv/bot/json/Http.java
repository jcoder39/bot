package name.bvv.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import name.bvv.bot.attack.http.HTTPRequestTask;

import java.util.List;

/**
 * Created by User on 23.07.2016.
 */
public class Http
{
    @JsonProperty
    private int bots;

    @JsonProperty
    private int timeout;

    @JsonProperty
    private List<HTTPRequestTask> tasks;

    public Http() {}

    public int getBots() {
        return bots;
    }

    public int getTimeout() {
        return timeout;
    }

    public List<HTTPRequestTask> getTasks() {
        return tasks;
    }
}
