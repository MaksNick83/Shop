package eu.hillel.hw18.service;

import eu.hillel.hw18.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getByNumber(String number);

    List<Order> getAllWithSumLesserOrEqualAndNumberNomenclature(double cost, int countNomenclature);

    List<Order> getAllContainingGivenNomenclature(String nameNomenclature);

    List<Order> getAllNotContainProductAndArrivedDuringCurrentDay(String nameNomenclature);

    void createOfItemsCurrentDay(String number);

    void deleteAllOrdersSpecifiedQuantityAndSpecifiedProduct(String nameNomenclature, int quantity);
}
