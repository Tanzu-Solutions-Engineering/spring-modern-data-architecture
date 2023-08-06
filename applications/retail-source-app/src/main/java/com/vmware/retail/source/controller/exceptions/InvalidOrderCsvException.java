/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason= """
        Format CSV:
        orderId,customer,productId,quantity
        
        Example:
        "5","nyla","sku4","1"
        """)
public class InvalidOrderCsvException extends RuntimeException{
    public InvalidOrderCsvException(NumberFormatException e) {
        super(e);
    }
}
