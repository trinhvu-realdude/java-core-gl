package greatlearning.miniproject.model;

import java.util.List;

public class Bill {
    private int id;
    private int orderId;
    private List<Integer> itemIdList;

    public Bill(int orderId, List<Integer> itemIdList) {
        this.orderId = orderId;
        this.itemIdList = itemIdList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Integer> getItemIdList() {
        return itemIdList;
    }

    public void setItemId(List<Integer> itemIdList) {
        this.itemIdList = itemIdList;
    }
}
