package flabs.siverbars.service;



public class OrderActionResult {
    public enum OrderAction {
        CANCEL_ORDER, PLACE_ORDER
    }

    public enum ActionStatus {
        ACTION_OK, ACTION_FAILED
    }


    public final String orderId;
    public final OrderAction orderAction;

    public final ActionStatus actionStatus;

    public final String message;

    public static OrderActionResult orderPlaced ( String orderId) {
     return new OrderActionResult(orderId, OrderAction.PLACE_ORDER, ActionStatus.ACTION_OK, null);
    }

    public static OrderActionResult failPlaceOrder ( String orderId, String reason) {
        return new OrderActionResult(orderId, OrderAction.PLACE_ORDER, ActionStatus.ACTION_FAILED, reason);
    }

    public static OrderActionResult orderCanceled ( String orderId) {
        return new OrderActionResult(orderId, OrderAction.CANCEL_ORDER, ActionStatus.ACTION_OK, null);
    }

    public static OrderActionResult failCancelOrder ( String orderId, String reason) {
        return new OrderActionResult(orderId, OrderAction.CANCEL_ORDER, ActionStatus.ACTION_FAILED, reason);
    }



    public OrderActionResult(String orderId, OrderAction orderAction, ActionStatus actionStatus, String message) {
        this.orderId = orderId;
        this.orderAction = orderAction;
        this.actionStatus = actionStatus;
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderActionResult{" +
                "orderId='" + orderId + '\'' +
                ", orderAction=" + orderAction +
                ", actionStatus=" + actionStatus +
                ", message='" + message + '\'' +
                '}';
    }
}
