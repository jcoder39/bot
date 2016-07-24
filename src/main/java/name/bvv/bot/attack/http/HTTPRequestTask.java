package name.bvv.bot.attack.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import name.bvv.bot.tasks.Task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * Created by User on 23.07.2016.
 */
public class HTTPRequestTask extends Task<HTTPResponse> // todo add ssl
{
    private String id;

    private Bot bot;

    @JsonProperty
    private String url; // todo change to StringBuffer

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
    public HTTPResponse call() throws Exception
    {
        HttpURLConnection connection = null;

        try {
            if(method.equals("GET")){
                if(required != null || !required.isEmpty()){
                    required.forEach((param, action) -> {
                        switch (action){
                            case "generate": {
                                url = url.replaceAll(":" + param, id.subSequence(0, 5).toString()); // todo add generator
                            } break;
                            case "storage": {
                                Object object = bot.getStorage().get(param);
                                url = url.replaceAll(":" + param, (String)object);
                            }
                        }
                    });
                }
            } else {
                // todo add RAW data to post body
            }
            sendTime = System.currentTimeMillis();
            URL requestURL = new URL(url);
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return new HTTPResponse(bot, id, url, response.toString(), storage);
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

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getRequired() {
        return required;
    }

    public Map<String, String> getStorage() {
        return storage;
    }

    public int getRepeat() {
        return repeat;
    }

    public int getTimeout() {
        return timeout;
    }

    public long getSendTime() {
        return sendTime;
    }
}
