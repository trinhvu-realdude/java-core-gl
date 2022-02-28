package greatlearning.miniproject.model;

import java.util.List;

public class OrderDetails {
    private int id;
    private int orderId;
    private List<Integer> itemIdList;
    private List<Integer> numberOfPlatesList;

    public OrderDetails(int orderId, List<Integer> itemIdList, List<Integer> numberOfPlatesList) {
        this.orderId = orderId;
        this.itemIdList = itemIdList;
        this.numberOfPlatesList = numberOfPlatesList;
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

    public void setItemIdList(List<Integer> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<Integer> getNumberOfPlatesList() {
        return numberOfPlatesList;
    }

    public void setNumberOfPlatesList(List<Integer> numberOfPlatesList) {
        this.numberOfPlatesList = numberOfPlatesList;
    }
}
