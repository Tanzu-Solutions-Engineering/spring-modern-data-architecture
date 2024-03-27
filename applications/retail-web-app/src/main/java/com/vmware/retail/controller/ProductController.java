/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.controller;

import com.vmware.retail.domain.Product;
import com.vmware.retail.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * ProductController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("products")
public record ProductController(ProductRepository repository)
{
    @PostMapping("product")
    public void saveProduct(Product product)
    {
        repository.save(product);
    }

    @GetMapping("product/{id}")
    public Product getProductById(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("product/name/{name}")
    public List<Product> getProductsByNameContaining(@PathVariable String name) {
        if(name != null)
            name = name.toLowerCase(Locale.ROOT);
        return repository.findByNameContaining(name);
    }
}
