package flabs.siverbars.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SilverOrderTest {

    @Test
    public void testNewInstance() {
        final SilverOrder batOrder = SilverOrder.newBuy()
                .order(3.5, 303.)
                .placedBy(new SalesUser("batman"))
                .build();

        assertEquals(OrderType.BUY, batOrder.getOrderType());
        assertEquals(new SalesUser("batman"), batOrder.getSalesUser());
        assertEquals(Double.valueOf(3.5), batOrder.getQuantity());
        assertEquals(Double.valueOf(303.), batOrder.getKgPrice());
        assertEquals("BUY-3.5-303.0-batman", batOrder.getOrderId());
    }

    @Test
    public void testEquality() {
        final SilverOrder batOrder = SilverOrder.newBuy()
                .order(3.5, 303.)
                .placedBy(new SalesUser("batman"))
                .build();

        final SilverOrder batOrder1 = SilverOrder.newBuy()
                .order(3.5, 303.)
                .placedBy(new SalesUser("batman"))
                .build();

        final SilverOrder robinOrder = SilverOrder.newBuy().order(3.5, 303.1)
                .placedBy(new SalesUser("robin"))
                .build();

        assertEquals(batOrder, batOrder1);
        assertEquals(batOrder.hashCode(), batOrder1.hashCode());

        assertNotEquals(batOrder, robinOrder);
    }
}
