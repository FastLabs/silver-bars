package flabs.siverbars.repository;

import flabs.siverbars.domain.OrderType;
import flabs.siverbars.domain.SilverOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class InMemOrderRepository implements OrderRepository {

    private List<SilverOrder> silverOrders = new ArrayList<>();


    public void saveOrder(SilverOrder silverOrder) throws OrderRepositoryException {

        if (silverOrder.getOrderId() == null) {
            throw new OrderRepositoryException();

        }
        silverOrders.add(silverOrder);

    }

    public void deleteOrder(String orderId) {
        this.silverOrders = silverOrders.stream().filter(o -> !o.getOrderId().equals(orderId)).collect(Collectors.toList());
    }

    @Override
    public List<SilverOrder> getAllOrders(OrderType orderType) {
        return silverOrders.stream().filter(o -> o.getOrderType() == orderType).collect(Collectors.toList());
    }
}
