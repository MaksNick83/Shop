package eu.hillel.hw18.repository;

import eu.hillel.hw18.connection.ConnectionProvider;
import eu.hillel.hw18.entity.Nomenclature;
import eu.hillel.hw18.entity.Order;
import eu.hillel.hw18.entity.OrderNomenclature;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    public List<Order> getAllByNumber(String number) {
        String queryByOrderNumber = """
                SELECT
                    orders.number,
                    orders.id order_id ,
                    orders.date,
                    ordernomenclatures.nomenclature_id,
                    ordernomenclatures.count,
                    nomenclatures.name,
                    nomenclatures.description,
                    nomenclatures.price
                FROM
                    shop.orders orders
                        left JOIN
                    shop.order_nomenclatures ordernomenclatures ON orders.id = ordernomenclatures.order_id
                        left JOIN
                    shop.nomenclatures nomenclatures ON nomenclatures.id = ordernomenclatures.nomenclature_id
                WHERE orders.number = ?
                order by order_id
                """;
        String[] parameters = {number};
        return getOrders(queryByOrderNumber, parameters);
    }

    public List<Order> getAllByDate(LocalDate localDate) {
        String queryByOrderNumber = """
                SELECT
                    orders.number,
                    orders.id order_id,
                    orders.date,
                    ordernomenclatures.nomenclature_id,
                    Sum(ordernomenclatures.count) count,
                    nomenclatures.name,
                    nomenclatures.description,
                    nomenclatures.price
                FROM
                    shop.orders orders
                        inner JOIN
                    shop.order_nomenclatures ordernomenclatures ON orders.id = ordernomenclatures.order_id
                        inner JOIN
                    shop.nomenclatures nomenclatures ON nomenclatures.id = ordernomenclatures.nomenclature_id
                WHERE
                    orders.date = ?
                group by
                nomenclature_id
                ORDER BY order_id
                """;
        String[] parameters = {localDate.toString()};
        return getOrders(queryByOrderNumber, parameters);
    }

    public List<Order> getAllWithSumLesserOrEqualAndNumberNomenclature(double cost, int countNomenclature) {
        String query = """
                  SELECT
                      filterOrder.number,
                      filterOrder.order_id,
                      filterOrder.date,
                      order_nomenclatures_.nomenclature_id,
                      order_nomenclatures_.count,
                      nomenclatures.name,
                      nomenclatures.description,
                      nomenclatures.price
                  FROM
                      (SELECT
                          allfor.order_id,
                              allfor.number,
                              allfor.date,
                              COUNT(allfor.nomenclature_id),
                              SUM(allfor.cost)
                      FROM
                          (SELECT
                          order_nomenclatures.order_id,
                              order_nomenclatures.nomenclature_id,
                              order_nomenclatures.count,
                              nomenclatures.price,
                              order_nomenclatures.count * nomenclatures.price cost,
                              orders.number,
                              orders.date
                      FROM
                          shop.order_nomenclatures order_nomenclatures
                      INNER JOIN shop.nomenclatures nomenclatures ON order_nomenclatures.nomenclature_id = nomenclatures.id
                      INNER JOIN shop.orders orders ON order_nomenclatures.order_id = orders.id) allfor
                      GROUP BY order_id
                      HAVING
                           SUM(allfor.cost) > ? AND COUNT(allfor.nomenclature_id) > ?) filterOrder
                          INNER JOIN
                      shop.order_nomenclatures order_nomenclatures_ ON order_nomenclatures_.order_id = filterOrder.order_id
                          INNER JOIN
                      shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures_.nomenclature_id
                 ORDER BY order_id               
                """;

        String[] parameters = {String.valueOf(cost), String.valueOf(countNomenclature)};
        return getOrders(query, parameters);
    }

    public List<Order> getAllContainingGivenNomenclature(String nameNomenclature) {
        String query = """
                 SELECT
                     filter_order.id order_id,
                     filter_order.date,
                     filter_order.number,
                     order_nomenclatures.nomenclature_id,
                     order_nomenclatures.count,
                     nomenclatures.name,
                     nomenclatures.description,
                     nomenclatures.price
                 FROM
                     (SELECT DISTINCT
                         orders.id, orders.date, orders.number
                     FROM
                         shop.orders orders
                     LEFT JOIN shop.order_nomenclatures order_nomenclatures
                     INNER JOIN shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id ON order_nomenclatures.order_id = orders.id
                         AND nomenclatures.name = ?
                     WHERE
                         NOT nomenclatures.name IS NULL) filter_order
                         INNER JOIN
                     shop.order_nomenclatures order_nomenclatures ON order_nomenclatures.order_id = filter_order.id
                         INNER JOIN
                     shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id
                ORDER BY order_id
                """;

        String[] parameters = {nameNomenclature};
        return getOrders(query, parameters);
    }

    public List<Order> getAllNotContainProductAndArrivedDuringDay(String nameNomenclature, LocalDate localDate) {
        String query = """
                 SELECT
                     filter_order.id order_id,
                     filter_order.date,
                     filter_order.number,
                     order_nomenclatures.nomenclature_id,
                     order_nomenclatures.count,
                     nomenclatures.name,
                     nomenclatures.description,
                     nomenclatures.price
                 FROM
                     (SELECT DISTINCT
                         orders.id, orders.date, orders.number
                     FROM
                         shop.orders orders
                     LEFT JOIN shop.order_nomenclatures order_nomenclatures
                     INNER JOIN shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id ON order_nomenclatures.order_id = orders.id
                         AND nomenclatures.name = ?
                     WHERE
                         date = ?
                             AND nomenclatures.name IS NULL) filter_order
                         INNER JOIN
                     shop.order_nomenclatures order_nomenclatures ON order_nomenclatures.order_id = filter_order.id
                         INNER JOIN
                     shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id
                ORDER BY order_id
                """;

        String[] parameters = {nameNomenclature, localDate.toString()};
        return getOrders(query, parameters);
    }

    public List<Order> SpecifiedQuantitySpecifiedProduct(String nameNomenclature, int quantity) {
        String query = """
                SELECT
                    filter_order.id order_id,
                    filter_order.date,
                    filter_order.number,
                    order_nomenclatures.nomenclature_id,
                    order_nomenclatures.count,
                    nomenclatures.name,
                    nomenclatures.description,
                    nomenclatures.price
                FROM
                    (SELECT DISTINCT
                        orders.id, orders.date, orders.number
                    FROM
                        shop.orders orders
                    LEFT JOIN shop.order_nomenclatures order_nomenclatures
                    INNER JOIN shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id ON order_nomenclatures.order_id = orders.id
                        AND nomenclatures.name = ?
                    WHERE
                        order_nomenclatures.count=?
                            AND not nomenclatures.name IS NULL) filter_order
                        INNER JOIN
                    shop.order_nomenclatures order_nomenclatures ON order_nomenclatures.order_id = filter_order.id
                        INNER JOIN
                    shop.nomenclatures nomenclatures ON nomenclatures.id = order_nomenclatures.nomenclature_id
                ORDER BY order_id
                """;

        String[] parameters = {nameNomenclature, String.valueOf(quantity)};
        return getOrders(query, parameters);
    }

    public void createOrder(Order order) throws Exception {
        Connection connection = ConnectionProvider.getConnection();
        try {
            connection.setAutoCommit(false);
            String addOrder = "INSERT INTO orders (date, number) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(addOrder, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getDate().toString());
            statement.setString(2, order.getNumber());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            order.setId(generatedKeys.getInt(1));
            String orderIdForAdd = String.valueOf(order.getId());
            addOrder = "INSERT INTO order_nomenclatures (order_id, nomenclature_id, count) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(addOrder);
            for (OrderNomenclature orderNomenclature : order.getOrderNomenclatures()) {
                statement.setString(1, orderIdForAdd);
                statement.setString(2, String.valueOf(orderNomenclature.getNomenclature().getId()));
                statement.setString(3, String.valueOf(orderNomenclature.getCount()));
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Can not create Order:" + e.getMessage());
            connection.rollback();
        } finally {
            closeConnection(connection);
        }
    }

    public void deleteOrder(Order order) throws Exception {
        Connection connection = ConnectionProvider.getConnection();
        try {
            String orderIdForDelete = String.valueOf(order.getId());

            connection.setAutoCommit(false);
            String deleteOrder = "DELETE FROM order_nomenclatures where order_id=?";
            PreparedStatement statement = connection.prepareStatement(deleteOrder);
            statement.setString(1, orderIdForDelete);
            statement.executeUpdate();
            deleteOrder = "DELETE FROM orders (id) VALUES (?)";
            statement = connection.prepareStatement(deleteOrder);
            statement.setString(1, orderIdForDelete);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Can not create Order:" + e.getMessage());
            connection.rollback();
        } finally {
            closeConnection(connection);
        }


    }

    private List<Order> getOrders(String queryByOrderNumber, String[] parameters) {
        Connection connection = ConnectionProvider.getConnection();
        ResultSet resultSet = executeQueryForDB(connection, queryByOrderNumber, parameters);
        return processResultSetCreateFullObjectOrder(connection, resultSet);
    }

    private ResultSet executeQueryForDB(Connection connection, String query, String[] parameters) {
        ResultSet resultSet = null;

        if (connection == null) {
            return null;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setString(i + 1, parameters[i]);
                }
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Can not execute query:" + e.getMessage());

        }
        return resultSet;
    }

    private List<Order> processResultSetCreateFullObjectOrder(Connection connection, ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        List<Order> orders = new ArrayList<>();
        List<OrderNomenclature> orderNomenclatures = null;
        Order order = null;
        int orderPrev = 0;
        int orderCurrent = 0;
        int nomenclatureId = 0;
        try {
            while (resultSet.next()) {
                orderCurrent = resultSet.getInt("order_id");
                nomenclatureId = resultSet.getInt("nomenclature_id");

                if (orderPrev != orderCurrent) {
                    if (orderPrev != 0) {
                        order.setOrderNomenclatures(orderNomenclatures);
                    }
                    LocalDate localDate = Date.valueOf(resultSet.getString("date")).toLocalDate();
                    order = new Order(localDate, resultSet.getString("number"), resultSet.getInt("order_id"));
                    orders.add(order);
                    orderNomenclatures = new ArrayList<>();
                    orderPrev = orderCurrent;
                }
                if (nomenclatureId == 0) {
                    continue;
                }
                Nomenclature nomenclature = new Nomenclature(nomenclatureId, resultSet.getString("name"), resultSet.getString("description"), resultSet.getDouble("price"));
                orderNomenclatures.add(new OrderNomenclature(nomenclature, resultSet.getDouble("count")));
            }
            if (orderCurrent != 0 && orderNomenclatures.size() != 0) {
                order.setOrderNomenclatures(orderNomenclatures);
            }

        } catch (SQLException e) {
            System.err.println("Can't read resultSet:" + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return orders;

    }

    private static void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can not close connection:" + e.getMessage());
        }
    }

}
