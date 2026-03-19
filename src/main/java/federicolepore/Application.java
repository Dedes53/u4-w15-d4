package federicolepore;

import federicolepore.entities.Customer;
import federicolepore.entities.Order;
import federicolepore.entities.Product;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        //customer
        Customer Homer = new Customer(123456789, "homer", 1);
        Customer Marge = new Customer(123456710, "marge", 2);
        Customer Flanders = new Customer(1234567811, "flanders", 2);
        Customer Boe = new Customer(1234567812, "boe", 3);

        //products
        Product p1 = new Product(1L, "Harry Potter Saga", "Books", 120.00);
        Product p2 = new Product(2L, "Narnia", "Books", 45.00);
        Product p3 = new Product(3L, "Pannolini", "Baby", 25.00);
        Product p4 = new Product(4L, "Lego", "Baby", 25.00);
        Product p5 = new Product(5L, "Maglia bimbo", "Boys", 30.00);
        Product p6 = new Product(6L, "Scarpe bimbo", "Boys", 80.00);
        Product p7 = new Product(7L, "Mouse", "Tech", 20.00);

        List<Product> productsList = List.of(p1, p2, p3, p4, p5, p6, p7);

        //orders
        Order o1 = new Order(
                101,
                LocalDate.of(2025, 2, 10),
                LocalDate.of(2025, 2, 15),
                Marge,
                "DELIVERED",
                List.of(p1, p7)
        );

        Order o2 = new Order(
                102,
                LocalDate.of(2025, 3, 5),
                LocalDate.of(2025, 3, 9),
                Flanders,
                "PENDING",
                List.of(p3, p4)
        );

        Order o3 = new Order(
                103,
                LocalDate.of(2025, 1, 20),
                LocalDate.of(2025, 1, 25),
                Flanders,
                "DELIVERED",
                List.of(p5, p2)
        );

        Order o4 = new Order(
                104,
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 7),
                Homer,
                "DELIVERED",
                List.of(p6)
        );

        List<Order> ordersList = List.of(o1, o2, o3, o4);


        // esercizio 1: raggruppare ordini per cliente con mappa [cliete][lista ordini]
        System.out.println("ESERCIZIO 1");
        Map<Customer, List<Order>> ordersByCustomer = ordersList.stream() //avvio lo stream
                .collect(Collectors.groupingBy(Order::getCustomer)); //per ogni ordine prendo il cliente associato

        ordersByCustomer.forEach(((customer, orders) -> {
            System.out.println("Cliente: " + customer.getName());
            System.out.println("Ordini: " + orders);
            System.out.println();
        }));


        // esercizio 2: calcolare tot vendite per ogni cliente con mappa [cliente][importo tot ordini]
        System.out.println("ESERCIZIO 2");
        Map<Customer, Double> totalByCustomer = ordersList.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(Order::calulateTotal)));

        totalByCustomer.forEach((customer, total) -> {
            System.out.println("Cliente: " + customer.getName() + " -> totale acquisti: " + total);
            System.out.println();
        });


        // esercizio 3: trovare prodotti più costosi
        System.out.println("ESERCIZIO 3");
        List<Product> mostExpensive = productsList.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed()).toList();

        mostExpensive.forEach(
                Product -> System.out.println(Product.getName() + " " + Product.getPrice())
        );
        System.out.println();

        // esercizio 4: calcolare media importi degli ordini da lista ordini
        System.out.println("ESERCIZIO 4");

        List<Double> totalByOrders = ordersList.stream().map(Order -> Order.calulateTotal()).toList();
        System.out.println("Importi degli ordini: " + totalByOrders);

        double media = totalByOrders.stream().reduce(0.0, Double::sum) / totalByOrders.size();
        System.out.println("Media delgi ordini: " + media);
        System.out.println();


        // esercizio 5: raggruppare i prodotti per categoria e calcolare la somma per ognuna di queste
        System.out.println("ESERCZIO 5");
        Map<String, Double> productByCategory = productsList.stream().
                collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)
                ));

        productByCategory.forEach(
                (category, total) -> {
                    System.out.println("Categoria: " + category + "-> totale: " + total);
                    System.out.println();
                }
        );

    }
}

