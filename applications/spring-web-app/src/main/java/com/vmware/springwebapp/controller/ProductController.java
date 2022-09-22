package com.vmware.springwebapp.controller;

import com.vmware.springwebapp.domain.Product;
import com.vmware.springwebapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
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
    public Product getProductById(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }
}
