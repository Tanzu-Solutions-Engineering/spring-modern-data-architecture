package com.vmware.retail.domain;

import java.util.List;

public record Promotion(String id, List<Product> products)
{
}
