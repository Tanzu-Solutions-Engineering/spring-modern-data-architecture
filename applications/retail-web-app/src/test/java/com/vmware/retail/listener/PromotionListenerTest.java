package com.vmware.retail.listener;

import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.Promotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static nyla.solutions.core.util.Organizer.toList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PromotionListenerTest
{
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;


    @Test
    void given_promotion_When_accept_promotion_Then_send_promotion()
    {
        var subject = new PromotionListener(simpMessagingTemplate);
        String id = "id";
        Long productId = 3L;
        String productName = "pname";
        Promotion promotion = new Promotion(id,
                "new Stuff",
                toList(new Product(productId,productName)));
        subject.accept(promotion);

        verify(simpMessagingTemplate).convertAndSend(anyString(),any(Object.class));

    }
}