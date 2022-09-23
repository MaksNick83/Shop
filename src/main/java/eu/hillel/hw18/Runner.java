package eu.hillel.hw18;

import eu.hillel.hw18.entity.Order;
import eu.hillel.hw18.repository.OrderRepository;
import eu.hillel.hw18.repository.OrderRepositoryImpl;
import eu.hillel.hw18.service.OrderService;
import eu.hillel.hw18.service.OrderServiceImpl;

public class Runner {

    public static void main(String[] args) {
        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderService orderService = new OrderServiceImpl(orderRepository);
        System.out.println(orderService.getByNumber("104"));
        System.out.println("---AllWithSumLesserOrEqualAndNumberNomenclature-----");
        for (Order order : orderService.getAllWithSumLesserOrEqualAndNumberNomenclature(10.0, 2)) {
            System.out.println("Number order:" + order.getNumber() + "Id: " + order.getId());
        }
        System.out.println("---Containing Nomenclature----");
        for (Order order : orderService.getAllContainingGivenNomenclature("modem")) {
            System.out.println("Number order:" + order.getNumber() + "Id: " + order.getId());
        }
        System.out.println("---No Containing Nomenclature----");
        for (Order order : orderService.getAllNotContainProductAndArrivedDuringCurrentDay("modem")) {
            System.out.println("Number order:" + order.getNumber() + "Id: " + order.getId());
        }
        orderService.createOfItemsCurrentDay("104");
        orderService.deleteAllOrdersSpecifiedQuantityAndSpecifiedProduct("modem", 2);
    }
}
