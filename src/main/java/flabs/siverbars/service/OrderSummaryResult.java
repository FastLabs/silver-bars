package flabs.siverbars.service;

import java.util.Map;

public class OrderSummaryResult {
    public final Map<Double, Double> sellOrders;
    public final Map<Double, Double> buyOrders;

    public final String message;

    public OrderSummaryResult(Map<Double, Double> sellOrders, Map<Double, Double> buyOrders, String message) {
        this.sellOrders = sellOrders;
        this.buyOrders = buyOrders;
        this.message = message;
    }


    public OrderSummaryResult(Map<Double, Double> sellOrders, Map<Double, Double> buyOrders) {
        this(sellOrders, buyOrders, null);
    }


    public static OrderSummaryResult error(String message) {
        return new OrderSummaryResult(null, null, message);
    }


}
