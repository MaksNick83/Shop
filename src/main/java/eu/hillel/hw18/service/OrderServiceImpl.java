package eu.hillel.hw18.service;

import eu.hillel.hw18.entity.Order;
import eu.hillel.hw18.entity.OrderNomenclature;
import eu.hillel.hw18.repository.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getByNumber(String number) {

        return orderRepository.getAllByNumber(number);

    }

    public List<Order> getAllWithSumLesserOrEqualAndNumberNomenclature(double cost, int countNomenclature) {
        return orderRepository.getAllWithSumLesserOrEqualAndNumberNomenclature(cost, countNomenclature);
    }

    public List<Order> getAllContainingGivenNomenclature(String nameNomenclature) {
        return orderRepository.getAllContainingGivenNomenclature(nameNomenclature);
    }

    public List<Order> getAllNotContainProductAndArrivedDuringCurrentDay(String nameNomenclature) {
        LocalDate localDate = LocalDate.now();
        return orderRepository.getAllNotContainProductAndArrivedDuringDay(nameNomenclature, localDate);
    }

    public void createOfItemsCurrentDay(String number) {
        LocalDate localDate = LocalDate.now();
        List<Order> orders = orderRepository.getAllByDate(localDate);
        if (orders.size() == 0) {
            return;
        }
        Order order = new Order(localDate, number);
        List<OrderNomenclature> orderNomenclatures = new ArrayList<>();

        for (Order order1 : orders) {
            for (OrderNomenclature orderNomenclature : order1.getOrderNomenclatures()) {
                orderNomenclatures.add(orderNomenclature);
            }
        }

        order.setOrderNomenclatures(orderNomenclatures);

        try {
            orderRepository.createOrder(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllOrdersSpecifiedQuantityAndSpecifiedProduct(String nameNomenclature, int quantity) {
        List<Order> orders = orderRepository.SpecifiedQuantitySpecifiedProduct(nameNomenclature, quantity);
        for (Order order : orders) {
            try {
                orderRepository.deleteOrder(order);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
