package ru.aslcraft.bots.core.vk.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import ru.aslcraft.bots.core.EBT;
import ru.aslcraft.bots.core.vk.wclient;

public class GetLongPollServer {
	public Map<String, Object> get(){
		final wclient client = new wclient();
		final List<String> param = new ArrayList<>();
		param.add("group_id,"+EBT.getCfg().getString("vk.group-id", "replace here with group id", true));
		final JSONObject object = new JSONObject(client.body("groups.getLongPollServer", param, false)).getJSONObject("response");
		final HashMap<String, Object> result = new HashMap<>();
		result.put("server", object.getString("server"));
		result.put("key", object.getString("key"));
		result.put("ts", object.getLong("ts"));
		return result;
	}
}
