package com.memphis.producer.services.implementations;


import com.google.gson.Gson;
import com.memphis.producer.services.interfaces.IProducerService;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProducerService implements IProducerService{


    public static final String PLAYERS_FILE_PATH="/app/players.csv";


    @PostConstruct
    public void init() {
        readBooksFromCsv();
    }

    @Override
    public void readBooksFromCsv() {
        Path pathToFile = Paths.get(PLAYERS_FILE_PATH);
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            Options options=new Options.Builder()
                    .server("nats://demo.nats.io:4222")
                    .connectionListener((conn, type) -> {
                        System.out.println(type);
                    }).build();
            Connection connection = Nats.connect(options);
            Thread t1= getThread(br, connection);
            Thread t2= getThread(br, connection);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private Thread getThread(BufferedReader br, Connection natsConnection) {
        return new Thread(() -> {
            try {
                publishMsg(br,natsConnection);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }

    @Override
    public void  publishMsg(BufferedReader br, Connection natsConnection) throws IOException {
        synchronized(this) {
            String line = "";
            while ((line = br.readLine()) != null) {
                Gson g = new Gson();
                String str = g.toJson(line);
                natsConnection.publish("players", str.getBytes());
            }
        }
    }
}
