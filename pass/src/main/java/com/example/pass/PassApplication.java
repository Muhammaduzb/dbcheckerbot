package com.example.pass;

import com.example.pass.config.SpringDBQueryObjectEx;
import com.example.pass.entity.GetOrder;
import com.example.pass.telegram.TelegramController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class PassApplication implements CommandLineRunner {
    SpringDBQueryObjectEx springDBQueryObjectEx;

    private static final Logger log = LoggerFactory.getLogger(SpringDBQueryObjectEx.class);

    TelegramController telegramController;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    PassApplication(TelegramController telegramController){
        this.telegramController = telegramController;
    }

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
//        springDBQueryObjectEx.main();
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(PassApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating tables");

        // Split up the array of whole names into an array of first/last names
        jdbcTemplate.query(
                "SELECT * from billing.orders WHERE instime >= current_timestamp - interval '30 seconds'",
//                , new Object[] { "Josh" },
                (rs, rowNum) -> new GetOrder(rs.getLong("orderno"),rs.getString("delivery_type")
                        ,rs.getTimestamp("instime"))
        ).forEach(getOrder ->

                telegramController.sendMessagenotification(getOrder.getDelivery_type())
//                        System.out.println(getOrder.getDelivery_type())
        );
    }
}
