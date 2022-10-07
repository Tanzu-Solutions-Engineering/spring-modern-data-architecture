package com.vmware.retail.controller;

import com.vmware.retail.domain.Product;
import com.vmware.retail.repository.ProductRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest
{

    @Mock
    private ProductRepository repository;

    @Test
    void given_product_When_saveProduct_Then_GetProduct_Equals_Saved()
    {
        var product = JavaBeanGeneratorCreator.of(Product.class).create();
        when(repository.findById(anyLong())).thenReturn(Optional.of(product));

        var subject = new ProductController(repository);

        subject.saveProduct(product);

        assertEquals(product,subject.getProductById(product.id()));
        verify(repository).save(any());
    }
}