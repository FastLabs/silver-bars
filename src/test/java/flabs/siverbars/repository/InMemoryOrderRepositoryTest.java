package flabs.siverbars.repository;

import flabs.siverbars.domain.OrderType;
import flabs.siverbars.domain.SalesUser;
import flabs.siverbars.domain.SilverOrder;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryOrderRepositoryTest {

    @Test
    public void testSaveOrderOk()  throws Exception {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();

        final SilverOrder batOrder = SilverOrder.newBuy()
                .placedBy(new SalesUser("batman"))
                .order(3.5, 300.0)
                .build();

        orderRepository.saveOrder(batOrder);

        final List<SilverOrder> allOrders = orderRepository.getAllOrders(OrderType.BUY);
        assertEquals(Collections.singletonList(batOrder), allOrders);
    }

    @Test(expected = OrderRepositoryException.class)
    public void testSaveOrderNotOk() throws OrderRepositoryException {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final SilverOrder batOrder = SilverOrder.newBuy()
                .placedBy(new SalesUser("batman"))
                .order(3.5, 300.0)
                .build(false);
        orderRepository.saveOrder(batOrder);
    }
}
