package com.knubi.bot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@UtilityClass
public class HandlerHelper {

    public SendMessage createSendMessage(Long id, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(id)
                .text(text)
                .build();
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }


    public InlineKeyboardMarkup createKeyboardStartButton() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
                .text("-=START=-").callbackData(TgUtil.START_COMMAND).build();

        List<InlineKeyboardButton> startButton = List.of(inlineKeyboardButton);
        inlineKeyboard.setKeyboard(List.of(startButton));

        return inlineKeyboard;
    }

}
