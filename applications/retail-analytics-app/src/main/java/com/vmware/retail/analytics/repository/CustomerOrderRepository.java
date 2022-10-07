package com.vmware.retail.analytics.repository;

import com.vmware.retail.analytics.entity.CustomerOrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends CrudRepository<CustomerOrderEntity,Long> {

}
