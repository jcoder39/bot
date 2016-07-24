package name.bvv.bot.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Created by User on 23.07.2016.
 */
public class Scenario
{
    @JsonProperty
    private Http http;

    public Scenario(){}

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
