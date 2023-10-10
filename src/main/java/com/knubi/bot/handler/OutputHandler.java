package com.knubi.bot.handler;

import com.knubi.bot.model.TgUser;
import com.knubi.bot.service.ApiSource;
import com.knubi.bot.util.HandlerHelper;
import com.knubi.bot.util.TgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
public class OutputHandler implements Handler {

    private final ApiSource sourceServices;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TgUser user, String selectedSrc, Long chatId) {
        String data = sourceServices.getCurrencyData().orElse(EMPTY);

        SendMessage outMessage = HandlerHelper.createSendMessage(chatId, data);
        outMessage.setReplyMarkup(HandlerHelper.createKeyboardStartButton());

        return List.of(outMessage);
    }

    @Override
    public List<String> callBackQueries() {
        return List.of(TgUtil.API);
    }

}
