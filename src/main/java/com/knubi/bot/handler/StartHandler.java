package com.knubi.bot.handler;

import com.knubi.bot.model.TgUser;
import com.knubi.bot.util.HandlerHelper;
import com.knubi.bot.util.TgUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

@Service
public class StartHandler implements Handler {

    private static final String WELCOME = "Welcome, *%s*!";

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TgUser user, String message, Long chatId) {
        final String welcome = String.format(WELCOME, user.getName());

        SendMessage welcomeMessage = HandlerHelper.createSendMessage(chatId, welcome);

        return List.of(welcomeMessage);
    }

    @Override
    public List<String> callBackQueries() {
        return List.of(TgUtil.START_COMMAND);
    }

}
