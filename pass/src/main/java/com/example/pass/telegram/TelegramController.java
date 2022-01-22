package com.example.pass.telegram;
import com.example.pass.entity.TelegramUser;
import com.example.pass.repository.DeliveryRepository;
import com.example.pass.repository.TelegramUsersRepository;
import com.example.pass.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RestController
public class  TelegramController extends TelegramLongPollingBot {

    @Autowired
    UsersRepository usersRepository;

//    @Autowired
//    DeliveryRepository deliveryRepository;

    @Autowired
    private  TelegramUsersRepository telegramUsersRepository;

    private TelegramUser telegramUser;

//    public TelegramController(TelegramUsersRepository telegramUsersRepository,TelegramUser telegramUser){
//        this.telegramUsersRepository = telegramUsersRepository;
//        this.telegramUser = telegramUser;
//    }

    public TelegramController() {

    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));


        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                if (!telegramUsersRepository.findByChatUserId(message.getChatId()).isPresent()) {
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setText("Здравствуйте! Если вы хотите получать наши сообщения," +
                            " вы обратились к нашему курьерскому боту. Пожалуйста, поделитесь своим контактным номером с кнопкой внизу!");
                    setButtons(sendMessage);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (message.getText().equals("/start")) {
                    System.out.println(message.getChatId());
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setText("Вы уже добавили. Если у нас есть новости, мы вышлем вам !!!");
                    setButtonsEnd(sendMessage);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

//            Пешком Лег. тран Груз. тран
                else if (message.getText().equals("Груз. тран")
                    || message.getText().equals("Пешком")
                    || message.getText().equals("Лег. тран")){
                System.out.println(" --------------------------- ");
                    System.out.println(message);
                    telegramUser = telegramUsersRepository.findByChatUserId(message.getChatId()).get();
                    String type = message.getText();
                    telegramUsersRepository.save(new TelegramUser(telegramUser.getId(),telegramUser.getChatUserId(),
                            telegramUser.getPhoneNumber(),telegramUser.getFirstName(),telegramUser.getUserName(), type));
                System.out.println(telegramUser);
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setText("Вы успешно добавили !!! Если у нас есть сообщения, мы отправим вам !!!");
                    setButtonsEnd(sendMessage);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                    else if (message.getText().equals("/помощь")){
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setText("Этот бот для получения заказа!!! Если у нас есть новости, мы вышлем вам !!!");
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }


                    } else {
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                sendMessage.setText("Вы отправили неверное сообщение! Пожалуйста, проверьте это!");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (message.hasContact()) {
            if (telegramUsersRepository.findByPhoneNumber(message.getContact().getPhoneNumber()).isPresent()) {
                System.out.println("bor yoki yoq :" + telegramUsersRepository.findByPhoneNumber(message.getContact().getPhoneNumber()));
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText("Вы уже добавили! Если у нас есть новости, мы вышлем вам !!!");
            setButtonsEnd(sendMessage);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else {
                sendMessage.setText("Выберите категорию");
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                System.out.println("message wu : " + message);
                setType(sendMessage);
                telegramUsersRepository.save(new TelegramUser(message.getChatId(),message.getContact().getPhoneNumber()
                        .replace("+",""),message.getFrom().getFirstName(),message.getFrom().getUserName()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                System.out.println("message has contactni oldi : " + message.hasContact());
            }
        }else{
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText("Вы отправили неверные данные! Пожалуйста, выберите верные данные!");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

        public void sendMessage(String phoneNumber,String message){
                SendMessage sendMessage = new SendMessage();
            System.out.println("telegram user repository : " + telegramUsersRepository.findByPhoneNumber(phoneNumber));
                telegramUser = telegramUsersRepository.findByPhoneNumber(phoneNumber).get();

                sendMessage.setChatId(String.valueOf(telegramUser.getChatUserId()));
                sendMessage.setText("You have new message: " + message);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
    List<TelegramUser> telegramUserList;
        public void sendMessagenotification(String type){

            System.out.println("void type : " + type);
                SendMessage sendMessage = new SendMessage();
//            System.out.println("repos : " + telegramUsersRepository.findByType(type));
//            System.out.println("Telegram user: " + telegramUser);
//            System.out.println("Telegram user repository: " + telegramUsersRepository);
                telegramUserList = telegramUsersRepository.findAllByType(type);
//            System.out.println("sendnotification: " + type);
//            telegramUser = telegramUsersRepository.findByType("Пешком");
//            System.out.println("test uchun :" +  telegramUser);

//            System.out.println("telegramUserOptional : = " + telegramUser);
            for (int i = 0; i < telegramUserList.size(); i++) {
                System.out.println(telegramUserList.get(i).getChatUserId());
                sendMessage.setChatId(String.valueOf(telegramUserList.get(i).getChatUserId()));
                sendMessage.setText("Есть новая доставка");
//                sendMessage.setText("Есть новая доставка type:=" + type);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
            }

    ReplyKeyboardMarkup replyKeyboardMarkup;
    KeyboardRow keyboardFirstRow;
    KeyboardButton keyboardButton;
    public synchronized void setButtonsEnd(SendMessage sendMessage) {

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

         keyboardFirstRow = new KeyboardRow();

         keyboardButton = new KeyboardButton();
        keyboardButton.setText("/помощь");
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public synchronized void setType(SendMessage sendMessage) {

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Пешком");
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText("Лег. тран");
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Груз. тран");
        keyboardFirstRow.add(keyboardButton);
        keyboardFirstRow.add(keyboardButton1);
        keyboardFirstRow.add(keyboardButton2);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public synchronized void setButtons(SendMessage sendMessage) {

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboardFirstRow = new KeyboardRow();

        keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText("Share my number ->");
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    @Override
    public String getBotUsername() {
        return "fortestingmyskillsbot";
    }

    @Override
    public String getBotToken() {
        return "1065941868:AAGh2qjjI6V8AAF8BQK3zsZyUMIjKyWaiwA";
    }
}