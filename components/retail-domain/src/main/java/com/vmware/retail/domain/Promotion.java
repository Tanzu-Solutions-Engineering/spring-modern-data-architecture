/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain;

import java.util.List;

public record Promotion(String id, String marketingMessage, List<Product> products)
{
}
