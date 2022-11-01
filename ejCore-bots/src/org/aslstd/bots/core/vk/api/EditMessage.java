package org.aslstd.bots.core.vk.api;

import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.aslstd.bots.core.vk.wclient;

public class EditMessage {
    @SneakyThrows
    public void send(String text, long id, long messageID){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("peer_id,"+id);
        param.add("messsage_id,"+ (messageID - 1));
        param.add("message,"+ URLEncoder.encode(text, "UTF-8"));
        client.body("messages.edit", param, false);
    }
}