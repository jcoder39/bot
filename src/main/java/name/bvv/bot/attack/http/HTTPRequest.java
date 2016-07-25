package name.bvv.bot.attack.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * Created by User on 25.07.2016.
 */
public class HTTPRequest // todo add ssl
{
    private String url;
    private String method;
    private String contentType;
    private Map<String, String> required;
    private Storage storage;

    public HTTPRequest(String url, String contentType, String method, Map<String, String> required, Storage storage)
    {
        this.url = url;
        this.contentType = contentType;
        this.method = method;
        this.required = required;
        this.storage = storage;
    }

    public HttpURLConnection getConnection() throws IOException, MalformedURLException, ProtocolException
    {
        HttpURLConnection connection = null;

        if(method.equals("GET")){
            if(required != null || !required.isEmpty()){
                required.forEach((param, action) -> {
                    switch (action){
                        case "generate": {
                            String id = UUID.randomUUID().toString();
                            url = url.replaceAll(":" + param, id.subSequence(0, 5).toString()); // todo add generator
                        } break;
                        case "storage": {
                            Object object = storage.get(param);
                            url = url.replaceAll(":" + param, (String)object);
                        }
                    }
                });
            }
        } else {
            // todo add RAW data to post body
        }

        URL requestURL = new URL(url);
        connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod(method);

        connection.setRequestProperty("Content-Type", contentType);

        connection.setUseCaches(false);
        connection.setDoOutput(true);
        return connection;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }
}
