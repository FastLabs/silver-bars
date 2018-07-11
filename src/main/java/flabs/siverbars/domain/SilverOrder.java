package flabs.siverbars.domain;

import java.util.Objects;

public final class SilverOrder {
    private String orderId;//TODO: maybe newOrder id should be a separate class
    private OrderType orderType;
    private Double quantity;
    private Double kgPrice;
    private SalesUser salesUser;


    public static Builder newSell() {
        return new Builder(OrderType.SELL);
    }

    public static Builder newBuy() {
        return new Builder(OrderType.BUY);
    }

    public static Builder newOrder(OrderType orderType) {
        return new Builder(orderType);
    }

    private SilverOrder(OrderType orderType) {
        this.orderType = orderType;
        this.orderId = null;
        this.quantity = null;
        this.kgPrice = null;
        this.salesUser = null;
    }

    private SilverOrder(SilverOrder template) {
        this.orderType = template.orderType;
        this.orderId = template.orderId;
        this.quantity = template.quantity;
        this.kgPrice = template.kgPrice;
        this.salesUser = template.salesUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getKgPrice() {
        return kgPrice;
    }

    public SalesUser getSalesUser() {
        return salesUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SilverOrder that = (SilverOrder) o;
        return Objects.equals(getOrderId(), that.getOrderId()) &&
                getOrderType() == that.getOrderType() &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getKgPrice(), that.getKgPrice()) &&
                Objects.equals(salesUser, that.salesUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getOrderId(), getOrderType(), getQuantity(), getKgPrice(), salesUser);
    }

    public static class Builder {
        private SilverOrder orderTemplate;

        private Builder(OrderType orderType) {
            this.orderTemplate = new SilverOrder(orderType);
        }

        public Builder placedBy(SalesUser user) {
            this.orderTemplate.salesUser = user;
            return this;
        }


        public Builder order(Double quantity, Double kgPrice) {
            this.orderTemplate.quantity = quantity;
            this.orderTemplate.kgPrice = kgPrice;
            return this;
        }

        public SilverOrder build() {
            return build(true);
        }

        public SilverOrder build(boolean genId) {
            if (genId) {
                orderTemplate.orderId = String.format("%s-%s-%s-%s"
                        , orderTemplate.orderType
                        , orderTemplate.quantity
                        , orderTemplate.kgPrice
                        , orderTemplate.salesUser.getUserId());
            }

            //TODO: add time-stamp
            return new SilverOrder(orderTemplate);
        }
    }
}
