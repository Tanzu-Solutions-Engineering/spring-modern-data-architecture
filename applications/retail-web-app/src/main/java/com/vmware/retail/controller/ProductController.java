package com.vmware.retail.controller;

import com.vmware.retail.domain.Product;
import com.vmware.retail.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

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
    public Product getProductById(@PathVariable Long id)
    {
        return repository.findById(id).orElse(null);
    }
}
