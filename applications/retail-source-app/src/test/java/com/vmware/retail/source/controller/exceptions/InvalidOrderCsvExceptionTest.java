/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InvalidOrderCsvExceptionTest {

    @Test
    void  setException() {

        var errorMsg = "Exception";
        assertThat(new InvalidOrderCsvException(
                new NumberFormatException(errorMsg)).getMessage()).contains(errorMsg);

    }
}