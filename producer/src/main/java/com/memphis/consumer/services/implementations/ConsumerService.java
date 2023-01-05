package com.memphis.consumer.services.implementations;


import com.memphis.consumer.services.interfaces.IConsumerService;
import com.opencsv.CSVWriter;
import io.nats.client.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;


@Service
@Slf4j
public class ConsumerService implements IConsumerService {

    public static final String PLAYERS_FILE_DIR="/app";
    public static final String PLAYERS_FILE_PATH="/app/players.csv";

    @PostConstruct
    public void init() {
        consumeMsg();
    }

    @Override
    public void consumeMsg() {
        try {
            Options options = new Options.Builder()
                    .server("nats://demo.nats.io:4222")
                    .connectionListener((conn, type) -> {
                        System.out.println(type);
                    }).build();

            Connection natsConnection = Nats.connect(options);
            Subscription subscription = natsConnection.subscribe("players");
            Message msg;
            String msgData="";
            File fileDir = new File(PLAYERS_FILE_DIR);
            fileDir.mkdirs();
            File file = new File(PLAYERS_FILE_PATH);
            boolean isFileCreated=file.createNewFile();
            if (isFileCreated) {
                FileWriter outputfile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputfile);
                while ((msg = subscription.nextMessage(Duration.ofHours(1))) != null) {
                    msgData = new String(msg.getData());
                    String[] msgDataArr=msgData.split(",");
                    writer.writeNext(msgDataArr);
                    if (subscription.getPendingMessageCount() == 0) {
                        log.info(msgData);
                    }
                }
                writer.close();
                natsConnection.close();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

}
