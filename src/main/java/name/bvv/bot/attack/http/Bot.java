package name.bvv.bot.attack.http;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by User on 22.07.2016.
 */
public class Bot
{
    private int id;
    private Map<Integer, ArrayList<RequestResponse>> taskResult;
    private Storage storage;
    private boolean stop;

    public Bot(int id) {
        this.id = id;
        taskResult = new TreeMap<>();
        storage = new Storage();
    }

    public void addTask(Integer index, RequestResponse requestResponse)
    {
        ArrayList<RequestResponse> requestResponseArrayList;
        if(taskResult.containsKey(index)){
            requestResponseArrayList = taskResult.get(index);
            requestResponseArrayList.add(requestResponse);
        } else {
            requestResponseArrayList = new ArrayList<>(5);
            requestResponseArrayList.add(requestResponse);
            taskResult.put(index, requestResponseArrayList);
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<RequestResponse> getRequestResponse(Integer index)
    {
        return taskResult.getOrDefault(index, null);
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Map<Integer, ArrayList<RequestResponse>> getTaskResult() {
        return taskResult;
    }
}
