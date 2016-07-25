package name.bvv.bot.attack.http;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by User on 22.07.2016.
 */
public class Bot
{
    private int id;
    private TreeMap<Integer, TreeMap<Integer, RequestResponse>> taskResult;
    private Storage storage;
    private boolean stop;

    public Bot(int id) {
        this.id = id;
        taskResult = new TreeMap<>();
        storage = new Storage();
    }

    public void addTask(Integer index, RequestResponse requestResponse)
    {
        TreeMap<Integer, RequestResponse> requestResponseList;
        if(taskResult.containsKey(index)){
            requestResponseList = taskResult.get(index);
            requestResponseList.put(requestResponseList.lastKey() + 1, requestResponse);
        } else {
            requestResponseList = new TreeMap<>();
            requestResponseList.put(0, requestResponse);
            taskResult.put(index, requestResponseList);
        }
    }

    public int getId() {
        return id;
    }

    public TreeMap<Integer, RequestResponse> getRequestResponse(Integer index)
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

    public TreeMap<Integer, TreeMap<Integer, RequestResponse>> getTaskResult() {
        return taskResult;
    }
}
