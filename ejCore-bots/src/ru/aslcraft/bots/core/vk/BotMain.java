package ru.aslcraft.bots.core.vk;

import lombok.SneakyThrows;
import ru.aslcraft.bots.core.vk.api.GetLongPollHistory;
import ru.aslcraft.bots.core.vk.api.GetLongPollServer;

import org.json.JSONObject;

import java.util.Map;

public class BotMain implements Runnable{
    @SneakyThrows
    @Override
    public void run() {
        Map<String, Object> server = new GetLongPollServer().get();
        while (true) {
            JSONObject jsonObject = new JSONObject(new GetLongPollHistory().get((String) server.get("server"), (Long) server.get("ts"), (String) server.get("key")));
            server.put("ts", Long.parseLong(jsonObject.getString("ts")));
            new BotListener().parse(jsonObject);
        }

    }

}