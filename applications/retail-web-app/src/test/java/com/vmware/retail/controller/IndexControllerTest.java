/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private Model model;
    private String customerIdAttribId ="customerId";
    private String customerId = "junit";

    @Test
    void given_model_when_index_then_setUserName() {

        var subject = new IndexController(customerIdAttribId, customerId);
        var actual = subject.homePage(request,model);

        assertEquals("index", actual);
        verify(model).addAttribute(anyString(),any());

    }
}