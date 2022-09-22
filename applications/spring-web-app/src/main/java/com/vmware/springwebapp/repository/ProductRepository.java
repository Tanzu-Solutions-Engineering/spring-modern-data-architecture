package com.vmware.springwebapp.repository;

import com.vmware.springwebapp.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * ProductRepository
 *
 * @author Gregory Green
 */
public interface ProductRepository extends CrudRepository<Product,String>
{
}
