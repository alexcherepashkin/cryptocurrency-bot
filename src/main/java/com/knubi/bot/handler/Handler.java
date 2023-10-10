package com.knubi.bot.handler;

import java.io.Serializable;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import com.knubi.bot.model.TgUser;

public interface Handler {

    List<PartialBotApiMethod<? extends Serializable>> handle(TgUser user, String message, Long chatId);

    List<String> callBackQueries();
}
