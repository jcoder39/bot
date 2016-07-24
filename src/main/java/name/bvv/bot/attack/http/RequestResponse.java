package name.bvv.bot.attack.http;

import name.bvv.bot.attack.http.HTTPRequestTask;
import name.bvv.bot.attack.http.HTTPResponse;

/**
 * Created by User on 23.07.2016.
 */
public class RequestResponse
{
    private HTTPRequestTask requestTask;
    private HTTPResponse response;

    public RequestResponse(HTTPRequestTask requestTask, HTTPResponse response) {
        this.requestTask = requestTask;
        this.response = response;
    }

    public HTTPRequestTask getRequestTask() {
        return requestTask;
    }

    public void setRequestTask(HTTPRequestTask requestTask) {
        this.requestTask = requestTask;
    }

    public HTTPResponse getResponse() {
        return response;
    }

    public void setResponse(HTTPResponse response) {
        this.response = response;
    }
}
