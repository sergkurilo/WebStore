package by.epam.trainings.task6.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private List<Product> orderList;
    private Map<Integer, Integer> amountMap;
    private String date;

    public Order() {
    }

    public Order(int orderId, int userId, List<Product> orderList, Map<Integer, Integer> amountMap, String date) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderList = orderList;
        this.amountMap = amountMap;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Product> orderList) {
        this.orderList = orderList;
    }

    public Map<Integer, Integer> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<Integer, Integer> amountMap) {
        this.amountMap = amountMap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String  date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (userId != order.userId) return false;
        if (!date.equals(order.date)) return false;
        if (!orderList.equals(order.orderList)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + userId;
        result = 31 * result + orderList.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderList=" + orderList +
                ", date='" + date + '\'' +
                '}';
    }
}
