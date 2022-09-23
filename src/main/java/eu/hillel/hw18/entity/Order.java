package eu.hillel.hw18.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private LocalDate date;
    private String number;
    private int id;
    private List<OrderNomenclature> orderNomenclatures = new ArrayList<>();

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<OrderNomenclature> getOrderNomenclatures() {
        return orderNomenclatures;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOrderNomenclatures(List<OrderNomenclature> orderNomenclatures) {
        this.orderNomenclatures = orderNomenclatures;
    }

    public Order(LocalDate date, String number, int id) {
        this.date = date;
        this.number = number;
        this.id = id;
    }

    public Order(LocalDate date, String number) {
        this.date = date;
        this.number = number;

    }

    public Order(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (OrderNomenclature orderNomenclature : orderNomenclatures) {
            stringBuilder.append(orderNomenclature + "\n");
        }
        return "Order Id=" + id + " Number=" + number + " Date=" + date + "\nNomenclature\n" + stringBuilder.toString();

    }
}
