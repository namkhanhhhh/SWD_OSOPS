//package SWD392_OSOPS.repositories;
//
//import SWD392_OSOPS.dtos.ShoesRevenueDTO;
//import SWD392_OSOPS.entities.Shoes;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import SWD392_OSOPS.dtos.PhoneRevenueDTO;
//import SWD392_OSOPS.entities.Phone;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//@Repository
//public interface PhoneRepository extends JpaRepository<Shoes, Integer> {
//    @Modifying
//    @Transactional
//    @Query(value = "SELECT * FROM phone WHERE product_name LIKE %:name%", nativeQuery = true)
//    List<Shoes> SearchProduct(@Param("name") String name);
//
//    @Modifying
//    @Transactional
//    @Query(value = "SELECT * FROM phone WHERE product_name LIKE %:name% AND phone.status = true", nativeQuery = true)
//    List<Shoes> SearchProductforShop(@Param("name") String name);
//
//
//
//    @Query(value = "select * from phone where phone.status = true", nativeQuery = true)
//    Page<Shoes> ViewProductforShop(Pageable pageable);
//
//
//    @Query(value = "SELECT * FROM phone WHERE price >= :minPrice AND price <= :maxPrice AND status = true", nativeQuery = true)
//    Page<Shoes> findByPriceRangeAndStatus(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
//
//    @Modifying
//    @Transactional
//    @Query(value = "SELECT phone.phone_id\n" +
//            "FROM phone join order_item on phone.phone_id = order_item.phone_id\n" +
//            "join ordertb on order_item.order_id = ordertb.order_id\n" +
//            "where ordertb.status = 'Completed' \n" +
//            "group by phone.phone_id \n" +
//            "order by sum(order_item.quantity) desc LIMIT 3;\n", nativeQuery = true)
//    List<Integer> getBestSale();
//
//    @Modifying
//    @Transactional
//    @Query(value = "select * from phone where phone.status = :status" , nativeQuery = true)
//    List<Shoes> searchPhoneByStatus(@Param("status") boolean status);
//
//
//    @Query(value = "select sum(order_item.total) as TotalPrice from phone \n" +
//            "join order_item on order_item.phone_id= phone.phone_id join ordertb on ordertb.order_id = order_item.order_id\n" +
//            "where ordertb.status = 'Completed'", nativeQuery = true)
//    String TotalRevenue();
//
//    @Query(value = "select sum(order_item.total) as TotalPrice from phone \n" +
//            "join order_item on order_item.phone_id= phone.phone_id join ordertb on ordertb.order_id = order_item.order_id\n" +
//            "where ordertb.status = 'Completed'\n" +
//            "and ordertb.order_date >= :start and order_date <= :end", nativeQuery = true)
//    String TotalRevenueByDate(@Param("start") Date start, @Param("end") Date end);
//
//    @Query("select new SWD392_OSOPS.dtos.PhoneRevenueDTO(CONCAT(p.productName, ' - ', p.ram, 'GB/',p.memory,'GB',' - ',p.origin) as phoneName, count(oi.orderItemId) as total) from Phone p \n" +
//            "join OrderItem oi on oi.phone.phoneId= p.phoneId join Order o on o.orderId = oi.order.orderId\n" +
//            "where o.status = 'Completed'\n" +
//            "group by p.phoneId, p.productName\n" +
//            "order by  total desc")
//    List<ShoesRevenueDTO> TotalRevenueOfPhone();
//
//    @Query("select new SWD392_OSOPS.dtos.PhoneRevenueDTO(CONCAT(p.productName, ' - ', p.ram, 'GB/',p.memory,'GB',' - ',p.origin) as phoneName, count(oi.orderItemId) as total) from Phone p \n" +
//            "join OrderItem oi on oi.phone.phoneId= p.phoneId join Order o on o.orderId = oi.order.orderId\n" +
//            "where o.status = 'Completed'\n" +
//            "and o.orderDate >= :start and o.orderDate <= :end group by p.phoneId, p.productName\n" +
//            "order by  total desc")
//    List<ShoesRevenueDTO> TotalRevenueOfPhoneByList(@Param("start") LocalDate start, @Param("end") LocalDate end);
//
//}
