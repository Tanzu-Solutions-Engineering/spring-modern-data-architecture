package com.vmware.retail.listener;

import com.vmware.retail.domain.Promotion;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static java.lang.String.valueOf;

/**
 * PromotionListener
 *
 * @author Gregory Green
 */
@Component
public class PromotionListener implements Consumer<Promotion>
{
    public void accept(Promotion promotion)
    {
        String pattern = "";

        System.out.println("promotion:"+promotion+" pattern:"+pattern);

    }
}
