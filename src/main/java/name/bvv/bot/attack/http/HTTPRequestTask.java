package name.bvv.bot.attack.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import name.bvv.bot.tasks.Task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by User on 23.07.2016.
 */
public class HTTPRequestTask extends Task<HTTPResponseTask>
{
    private String id;

    private Bot bot;

    @JsonProperty
    private String url;

    @JsonProperty
    private String method;

    @JsonProperty
    private Map<String, String> required;

    @JsonProperty
    private Map<String, String> storage;

    @JsonProperty
    private int repeat;

    @JsonProperty
    private int timeout;

    private long sendTime;

    public HTTPRequestTask(){}

    public HTTPRequestTask(Bot bot, HTTPRequestTask donor)
    {
        this.id = UUID.randomUUID().toString();
        this.bot = bot;
        this.url = donor.url;
        this.method = donor.method;
        this.required = donor.required;
        this.storage = donor.storage;
        this.repeat = donor.repeat;
        this.timeout = donor.timeout;
    }

    @Override
    public HTTPResponseTask call() throws Exception
    {
        sendTime = System.currentTimeMillis();
        HttpURLConnection connection = null;

        try {
            connection = new HTTPRequest(url, "application/json", method, required, bot.getStorage()).getConnection();
            url = connection.getURL().toString();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return new HTTPResponseTask(id, bot, url, response.toString(), storage);
        } catch (Exception e) {
            e.printStackTrace();
            bot.setStop(true);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }


    public int getRepeat() {
        return repeat;
    }


    public long getSendTime() {
        return sendTime;
    }
}
