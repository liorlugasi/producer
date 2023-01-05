package com.memphis.producer.services.interfaces;

import io.nats.client.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface IProducerService {

    void readBooksFromCsv();

    void publishMsg(BufferedReader br, Connection natsConnection) throws IOException;
}
