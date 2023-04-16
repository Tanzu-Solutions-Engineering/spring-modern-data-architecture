/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain.customer;

/**
 Example JSON
 { "customerId" : "g01" }

 * @param customerId
 */
public record CustomerIdentifier(String customerId) {
}
