package com.knubi.bot.service;

import com.knubi.bot.handler.Handler;
import com.knubi.bot.model.TgUser;
import com.knubi.bot.repository.TgUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateReceiver {

    private final List<Handler> handlers;
    private final TgUserRepository userRepository;

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isTextMessage(update)) {
                Message message = update.getMessage();
                Long userId = message.getFrom().getId();

                TgUser user = userRepository.findById(userId)
                        .orElseGet(() -> saveNewUser(message.getFrom()));

                return selectHandlerBy(message.getText())
                        .handle(user, message.getText(), message.getChatId());

            } else if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                Long userId = callbackQuery.getFrom().getId();

                TgUser user = userRepository.findById(userId)
                        .orElseGet(() -> saveNewUser(callbackQuery.getFrom()));

                String query = callbackQuery.getData();

                return selectHandlerBy(query)
                        .handle(user, query, update.getMessage().getChatId());
            }

        } catch (UnsupportedOperationException e) {
            log.error("No suitable handler found", e);
        }
        return Collections.emptyList();
    }

    private Handler selectHandlerBy(String query) {
        return handlers.stream()
                .filter(handler -> handler.callBackQueries().stream()
                        .anyMatch(q -> q.equalsIgnoreCase(query)))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private TgUser saveNewUser(User user) {
        log.info("Register new user with name: " + user.getFirstName());

        TgUser tgUser = new TgUser(user.getId(), user.getFirstName());
        return userRepository.save(tgUser);
    }

    private boolean isTextMessage(Update update) {
        return !update.hasCallbackQuery() &&
                update.hasMessage() &&
                update.getMessage().hasText();
    }
}
