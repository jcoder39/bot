package name.bvv.bot.attack.http;

/**
 * Created by User on 23.07.2016.
 */
public class RequestResponse
{
    private HTTPRequestTask requestTask;
    private HTTPResponseTask response;

    public RequestResponse(HTTPRequestTask requestTask, HTTPResponseTask response) {
        this.requestTask = requestTask;
        this.response = response;
    }

    public HTTPRequestTask getRequestTask() {
        return requestTask;
    }

    public void setRequestTask(HTTPRequestTask requestTask) {
        this.requestTask = requestTask;
    }

    public HTTPResponseTask getResponse() {
        return response;
    }

    public void setResponse(HTTPResponseTask response) {
        this.response = response;
    }
}
