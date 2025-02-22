package spring.modern.data.repository.valkey;

import com.vmware.retail.domain.Product;
import com.vmware.retail.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("valkey")
public interface ProductValKeyRepository  extends ProductRepository, KeyValueRepository<Product,String> {
}
