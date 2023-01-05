package com.memphis.consumer.services.interfaces;

import com.opencsv.CSVWriter;
import io.nats.client.Connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public interface IConsumerService {
    void consumeMsg();
}
