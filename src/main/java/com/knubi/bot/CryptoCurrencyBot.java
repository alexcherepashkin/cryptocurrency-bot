package com.knubi.bot;

import com.knubi.bot.service.UpdateReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
public class CryptoCurrencyBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Autowired
    private final UpdateReceiver updateReceiver;

    public CryptoCurrencyBot(@Value("${bot.token}") final String botToken, final UpdateReceiver updateReceiver) {
        super(botToken);
        this.updateReceiver = updateReceiver;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);

        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    private void executeWithExceptionCheck(final SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
