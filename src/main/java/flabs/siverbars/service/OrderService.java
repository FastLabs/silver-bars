package flabs.siverbars.service;

import flabs.siverbars.domain.OrderType;
import flabs.siverbars.domain.SalesUser;
import flabs.siverbars.domain.SilverOrder;
import flabs.siverbars.repository.OrderRepository;
import flabs.siverbars.repository.OrderRepositoryException;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Exposes available Silver Bars Order acceptable actions
 */

public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderActionResult cancelOrder(String orderId) {
        if (orderId == null) {
            return OrderActionResult.failCancelOrder("", "Please provide order id ");
        }
        try {
            orderRepository.deleteOrder(orderId);
            return OrderActionResult.orderCanceled(orderId);
        } catch (OrderRepositoryException ex) {
            System.err.println(String.format(" Error when canceling order %s", orderId));
            ex.printStackTrace(System.err);

            return OrderActionResult.failCancelOrder(orderId, "Error when trying to access order repository");
        }
    }

    public OrderActionResult registerOrder(OrderType orderType, Double quantity, Double priceKg, String userId) {
        if (quantity < 0) {
            final String reason = "Quantity should be a positive number";
            System.err.println(reason);
            return OrderActionResult.failPlaceOrder("", reason);
        }

        if (priceKg < 0) {
            final String reason = "Price kg should be a positive number";
            System.err.println(reason);
            return OrderActionResult.failPlaceOrder("", reason);
        }

        final SalesUser su = new SalesUser(userId);

        final SilverOrder order = SilverOrder.newOrder(orderType)
                .placedBy(su)
                .order(quantity, priceKg)
                .build();
        System.out.println(String.format("Register new order for %s", order.getOrderId()));
        try {
            orderRepository.saveOrder(order);
            return OrderActionResult.orderPlaced(order.getOrderId());
        } catch (OrderRepositoryException ex) {
            System.err.println(String.format(" Error when canceling order %s", order.getOrderId()));
            ex.printStackTrace(System.err);
            return OrderActionResult.failPlaceOrder(order.getOrderId(), "Error when accessing order repository");
        }

    }

    private Map<Double, Double> summarise(OrderType orderType) throws OrderRepositoryException {
        final Map<Double, Double> result = orderType == OrderType.BUY ? new TreeMap<>(Comparator.reverseOrder()) : new TreeMap<>(Comparator.naturalOrder());
        for (SilverOrder order : orderRepository.getAllOrders(OrderType.SELL)) {
            result.compute(order.getKgPrice(), (key, oldV) -> oldV == null ? order.getQuantity() : order.getQuantity() + oldV);
        }
        return result;
    }


    public OrderSummaryResult getOrderSummary() {
        try {
            Map<Double, Double> sellOrders = summarise(OrderType.SELL);
            Map<Double, Double> buyOrders = summarise(OrderType.BUY);
            return new OrderSummaryResult(sellOrders, buyOrders);
        } catch (OrderRepositoryException ex) {
            return OrderSummaryResult.error("Error when accessing ");
        }

    }
}
