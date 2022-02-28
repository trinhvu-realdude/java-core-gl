package greatlearning.miniproject.model;

import java.util.Date;

public class Bill {
    private int quantity;
    private double total;
    private Date date;

    public Bill(int quantity, double total, Date date) {
        this.quantity = quantity;
        this.total = total;
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
