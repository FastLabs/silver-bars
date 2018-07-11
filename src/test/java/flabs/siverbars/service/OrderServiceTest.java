package flabs.siverbars.service;

import flabs.siverbars.domain.OrderType;
import flabs.siverbars.domain.SalesUser;
import flabs.siverbars.domain.SilverOrder;
import flabs.siverbars.repository.OrderRepository;
import flabs.siverbars.service.OrderActionResult.ActionStatus;
import flabs.siverbars.service.OrderActionResult.OrderAction;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class OrderServiceTest {
    @Test
    public void testRegisterOrder() throws Exception {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final OrderService os = new OrderService(orderRepository);
        final OrderActionResult res = os.registerOrder(OrderType.BUY, 3.5, 306., "batman");
        assertEquals(ActionStatus.ACTION_OK, res.actionStatus);
        assertEquals(OrderAction.PLACE_ORDER, res.orderAction);

        final List<SilverOrder> buyOrders = orderRepository.getAllOrders(OrderType.BUY);
        assertEquals(singletonList(SilverOrder.newBuy().order(3.5, 306.).placedBy(new SalesUser("batman")).build()), buyOrders);
    }

    @Test
    public void testRegisterOrderNegativeQuantity() throws Exception {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final OrderService os = new OrderService(orderRepository);
        final OrderActionResult res = os.registerOrder(OrderType.BUY, -1., 306., "batman");

        assertEquals(ActionStatus.ACTION_FAILED, res.actionStatus);
        assertEquals("Quantity should be a positive number", res.message);
    }

    @Test
    public void testCancelOrder() throws Exception {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final OrderService os = new OrderService(orderRepository);
        os.registerOrder(OrderType.BUY, 3.5, 306., "batman");
        final SilverOrder deleted = SilverOrder.newBuy().order(3.5, 306.).placedBy(new SalesUser("batman")).build();
        final OrderActionResult res = os.cancelOrder(deleted.getOrderId());
        assertEquals(ActionStatus.ACTION_OK, res.actionStatus);
        assertEquals(OrderAction.CANCEL_ORDER, res.orderAction);
        assertEquals(0, orderRepository.getAllOrders(OrderType.BUY).size());
    }

    @Test
    public void testCancelNullOrder() throws Exception {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final OrderService os = new OrderService(orderRepository);
        os.registerOrder(OrderType.BUY, 3.5, 306., "batman");

        final OrderActionResult res = os.cancelOrder(null);

        assertEquals(ActionStatus.ACTION_FAILED, res.actionStatus);

    }

    @Test
    public void testSummary() {
        final OrderRepository orderRepository = OrderRepository.inMemoryRepository();
        final OrderService os = new OrderService(orderRepository);
        os.registerOrder(OrderType.SELL, 3.5, 306., "batman");
        os.registerOrder(OrderType.SELL, 1.2, 310., "robin");
        os.registerOrder(OrderType.SELL, 1.5, 307., "flash");
        os.registerOrder(OrderType.SELL, 2.0, 306., "joker");

        os.registerOrder(OrderType.BUY, 3.5, 306., "batman");
        os.registerOrder(OrderType.BUY, 1.2, 310., "robin");
        os.registerOrder(OrderType.BUY, 1.5, 307., "flash");
        os.registerOrder(OrderType.BUY, 2.0, 306., "joker");

        OrderSummaryResult orderSummary = os.getOrderSummary();

        final Map<Double, Double> sellResult = new TreeMap<>(Comparator.naturalOrder());
        sellResult.put(306., 5.5);
        sellResult.put(307., 1.5);
        sellResult.put(310., 1.2);

        assertEquals(sellResult, orderSummary.sellOrders);

        final Map<Double, Double> buyResult = new TreeMap<>(Comparator.naturalOrder());
        buyResult.put(310., 1.2);
        buyResult.put(307., 1.5);
        buyResult.put(306., 5.5);
        assertEquals(buyResult, orderSummary.buyOrders);
    }
}
