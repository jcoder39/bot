package name.bvv.bot.attack.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.bvv.bot.attack.Attack;
import name.bvv.bot.json.Scenario;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by User on 22.07.2016.
 */
public class DoS extends Attack
{

    private Scenario scenario;
    private ExecutorService executor;
    private List<Bot> bots;

    private int success = 0;
    private int rejects = 0;

    public DoS()
    {
        loadScenario();
        bots = new ArrayList<>(scenario.getHttp().getBots());
        prepareHTTPRequestTasks();
    }

    private void loadScenario()
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            scenario = mapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/scenario.json"), Scenario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareHTTPRequestTasks()
    {
        List<HTTPRequestTask> tasks = scenario.getHttp().getTasks();

        int numBots = scenario.getHttp().getBots();

        for(int botIndex = 0; botIndex < numBots; botIndex++){
            Bot bot = new Bot(botIndex);
            for(int taskIndex = 0; taskIndex < tasks.size(); taskIndex++){
                HTTPRequestTask temp = tasks.get(taskIndex);
                int repeat = temp.getRepeat();
                for(int i = 0; i < repeat; i++) {
                    HTTPRequestTask task = new HTTPRequestTask(bot, temp);
                    bot.addTask(taskIndex, new RequestResponse(task, null));
                }
            }
            bots.add(bot);
        }
    }

    @Override
    public void kill()
    {
        executor = Executors.newFixedThreadPool(scenario.getHttp().getBots());

        List<HTTPRequestTask> tasks = scenario.getHttp().getTasks();

        for(int taskIndex = 0; taskIndex < tasks.size(); taskIndex++){
            HTTPRequestTask temp = tasks.get(taskIndex);
            int repeats = temp.getRepeat();
            for(int repeatIndex = 0; repeatIndex < repeats; repeatIndex++){

                List<Future<HTTPResponseTask>> answers = new ArrayList<>(scenario.getHttp().getBots());
                List<HTTPRequestTask> preparedTasks = new ArrayList<>(scenario.getHttp().getBots());

                for(Bot bot : bots){
                    if(bot.isStop()){
                        continue;
                    }
                    HTTPRequestTask requestTask = bot.getRequestResponse(taskIndex).get(repeatIndex).getRequestTask();
                    preparedTasks.add(requestTask);
                }

                try {
                    answers = executor.invokeAll(preparedTasks, scenario.getHttp().getTimeout(), TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Future<HTTPResponseTask> httpResponse : answers) {
                    try {
                        HTTPResponseTask response = httpResponse.get();
                        response.setResponseTime(System.currentTimeMillis());
                        response.getBot().getRequestResponse(taskIndex).get(repeatIndex).setResponse(response);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (CancellationException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                System.gc();
            }
        }

        executor.shutdown();

        PrintResult();
    }

    private void PrintResult()
    {
        bots.forEach((bot) -> {
            bot.getTaskResult().forEach((index, requestResponses) ->{
                requestResponses.forEach((index1, requestResponse) -> {
                    HTTPRequestTask requestTask = requestResponse.getRequestTask();
                    HTTPResponseTask response1 = requestResponse.getResponse();
                    System.out.println("request: " + requestTask.getUrl());
                    if(response1 != null) {
                        success++;
                        String responseContent = response1.getContent();
                        String time = String.valueOf(response1.getResponseTime() - requestTask.getSendTime());
                        System.out.println("response: " + responseContent);
                        System.out.println("time: " + time);
                    } else {
                        rejects++;
                        System.out.println("response: NULL");
                    }
                });
            });
        });

        System.out.println("SUCCESS: " + String.valueOf(success));
        System.out.println("REJECT: " + String.valueOf(rejects));
        System.out.println("ALL: " + String.valueOf(success + rejects));
    }
}
