/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.io.csv.CsvReader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.function.Consumer;

/**
 *         var csv = """
 *                 "3","c1","pa","5"
 *                 "2","c2","pb","3"
 *                 "3","c1","pc","3"
 *                 """;
 */
@Component
@Slf4j
public class SplitCsvByCustomerOrder implements Consumer<String> {

    private final int customerIdCol;
    private final int orderIdCol;
    private final String exchange;
    private final CsvToCustomerOrder streamBridge;

    private final RabbitTemplate rabbitTemplate;

    public SplitCsvByCustomerOrder(CsvToCustomerOrder csvToCustomerOrder,
                                   @Value("${source.splitCsv.customer.orders.exchange:retail.customer.orders}")
                                   String exchange,
                                   @Value("${source.splitCsv.consumer.customerIdCol:1}")
                                   int customerIdCol,
                                   @Value("${source.splitCsv.consumer.orderIdCol:0}")
                                   int orderIdCol, RabbitTemplate rabbitTemplate) {
        this.exchange = exchange;
        this.streamBridge = csvToCustomerOrder;
        this.customerIdCol = customerIdCol;
        this.orderIdCol = orderIdCol;
        this.rabbitTemplate = rabbitTemplate;
    }

    @SneakyThrows
    @Override
    public void accept(String csv) {
        log.info("Csv\n{}", csv);

        if (csv == null || csv.trim().length() == 0) {
            log.warn("Ignore empty CSV\n{}", csv);
            return;
        }

        var csvReader = new CsvReader(new StringReader(csv));
        var csvList = csvReader
                .selectBuilder()
                .orderBy(customerIdCol)
                .groupBy(orderIdCol)
                .buildCsvText();

        String customerId = csvReader.get(0, customerIdCol, CsvReader.DataType.String);

        csvList.forEach(outCsv -> {
            rabbitTemplate.convertAndSend(exchange, customerId, this.streamBridge.apply(outCsv));
        });

    }
}

