package spring.modern.data.repository.valkey;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("valkey")
public interface CustomerFavoriteValKeyRepository  extends CustomerFavoriteRepository, KeyValueRepository<CustomerFavorites,String> {
}
