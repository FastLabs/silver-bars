package flabs.siverbars.repository;

import flabs.siverbars.domain.OrderType;
import flabs.siverbars.domain.SilverOrder;

import java.util.List;

public interface OrderRepository {
    static OrderRepository inMemoryRepository() {
        return new InMemOrderRepository();
    }

    void saveOrder(SilverOrder silverOrder) throws OrderRepositoryException;
    void deleteOrder(String orderId) throws OrderRepositoryException;
    List<SilverOrder> getAllOrders(OrderType orderType) throws  OrderRepositoryException;




}
