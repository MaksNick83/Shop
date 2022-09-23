package eu.hillel.hw18.repository;

import eu.hillel.hw18.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository {
    List<Order> getAllByNumber(String number);

    List<Order> getAllWithSumLesserOrEqualAndNumberNomenclature(double cost, int countNomenclature);

    List<Order> getAllContainingGivenNomenclature(String nameNomenclature);

    List<Order> getAllNotContainProductAndArrivedDuringDay(String nameNomenclature, LocalDate localDate);

    List<Order> getAllByDate(LocalDate localDate);

    void createOrder(Order order) throws Exception;

    List<Order> SpecifiedQuantitySpecifiedProduct(String nameNomenclature, int quantity);

    void deleteOrder(Order order) throws Exception;

}
