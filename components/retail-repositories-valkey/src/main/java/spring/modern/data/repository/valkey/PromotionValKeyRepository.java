package spring.modern.data.repository.valkey;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("valkey")
public interface PromotionValKeyRepository extends PromotionRepository, KeyValueRepository<Promotion,String> {
}
