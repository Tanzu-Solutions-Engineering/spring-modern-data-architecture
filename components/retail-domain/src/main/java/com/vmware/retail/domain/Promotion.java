package com.vmware.retail.domain;

import java.util.List;

public record Promotion(String id, String marketingMessage, List<Product> products)
{
}
