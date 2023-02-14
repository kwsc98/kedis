package pres.kedis.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import pers.kedis.core.handler.ComponentHandler;
import pers.kedis.core.handler.HandlerService;

/**
 * krpc
 * 2022/7/27 10:43
 *
 * @author wangsicheng
 * @since
 **/
public class KedisPostProcessor implements BeanPostProcessor {


    private final KedisApplicationContext krpcApplicationContext;


    KedisPostProcessor(KedisApplicationContext krpcApplicationContext) {
        this.krpcApplicationContext = krpcApplicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        assert beanName != null;
        assert bean != null;
        HandlerService handlerService = krpcApplicationContext.getHandlerService();
        if (bean instanceof ComponentHandler) {
            handlerService.registerChannelHandler(beanName, (ComponentHandler<?, ?>) bean);
        }
        return bean;
    }
}
