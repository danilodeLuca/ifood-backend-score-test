package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.dtos.ItemInfoDTO;
import ifood.score.entities.CategoryScore;
import ifood.score.entities.OrderJmsError;
import ifood.score.menu.Category;
import ifood.score.repositories.OrderJmsErrorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderJmsServiceTest extends BaseTest {

    @Autowired
    private OrderJmsService service;
    @Autowired
    private OrderJmsErrorRepository orderJmsErrorRepository;

    @Test
    public void testAddError() throws ClassNotFoundException {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        OrderJmsError myqueue = service.addError("myqueue", categoryScore);

        Assert.assertEquals(categoryScore, myqueue.getObject());
        Assert.assertFalse(myqueue.getCritical());
    }

    @Test
    public void testFindForRetry() throws ClassNotFoundException {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        OrderJmsError myqueue = service.addError("myqueue", categoryScore);
        OrderJmsError myqueue2 = service.addError("myqueue2", categoryScore);

        Assert.assertEquals(2, orderJmsErrorRepository.findByCriticalFalse().size());

        myqueue.setCritical(Boolean.TRUE);
        orderJmsErrorRepository.save(myqueue);
        Assert.assertEquals(1, orderJmsErrorRepository.findByCriticalFalse().size());
    }


}
