package name.bvv.bot.attack.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.bvv.bot.tasks.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by User on 23.07.2016.
 */
public class HTTPResponseTask extends Response
{
    private Bot bot;
    private String requestId;
    private String content;
    private Message message;
    private String query;

    private long responseTime;

    public HTTPResponseTask(String requestId, Bot bot, String query, String content, Map<String, String> toStorage)
            throws IOException, Storage.WrongTypeException
    {
        this.bot = bot;
        this.requestId = requestId;
        this.content = content;
        this.query = query;
        message = new ObjectMapper().readValue(content, Message.class);
        if(toStorage != null){
            for(Map.Entry<String, String> entry : toStorage.entrySet()){
                String key = Storage.getKeyWithoutType(entry.getKey());
                String saveAs = entry.getValue();
                Object object = message.getData().get(key);
                bot.getStorage().save(entry.getKey(), saveAs, object);
            }
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public String getContent() {
        return content;
    }

    public Bot getBot() {
        return bot;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
