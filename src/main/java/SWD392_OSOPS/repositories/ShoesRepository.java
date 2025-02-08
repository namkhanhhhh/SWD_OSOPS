package SWD392_OSOPS.repositories;

import SWD392_OSOPS.dtos.ShoesRevenueDTO;
import SWD392_OSOPS.entities.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Integer> {
    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM Shoes WHERE product_name LIKE %:name%", nativeQuery = true)
    List<Shoes> SearchProduct(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM Shoes WHERE product_name LIKE %:name% AND Shoes.status = true", nativeQuery = true)
    List<Shoes> SearchProductforShop(@Param("name") String name);



    @Query(value = "select * from Shoes where Shoes.status = true", nativeQuery = true)
    Page<Shoes> ViewProductforShop(Pageable pageable);


    @Query(value = "SELECT * FROM Shoes WHERE price >= :minPrice AND price <= :maxPrice AND status = true", nativeQuery = true)
    Page<Shoes> findByPriceRangeAndStatus(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "SELECT Shoes.Shoes_id\n" +
            "FROM Shoes join order_item on Shoes.Shoes_id = order_item.Shoes_id\n" +
            "join ordertb on order_item.order_id = ordertb.order_id\n" +
            "where ordertb.status = 'Completed' \n" +
            "group by Shoes.Shoes_id \n" +
            "order by sum(order_item.quantity) desc LIMIT 3;\n", nativeQuery = true)
    List<Integer> getBestSale();

    @Modifying
    @Transactional
    @Query(value = "select * from Shoes where Shoes.status = :status" , nativeQuery = true)
    List<Shoes> searchShoesByStatus(@Param("status") boolean status);


    @Query(value = "select sum(order_item.total) as TotalPrice from Shoes \n" +
            "join order_item on order_item.Shoes_id= Shoes.Shoes_id join ordertb on ordertb.order_id = order_item.order_id\n" +
            "where ordertb.status = 'Completed'", nativeQuery = true)
    String TotalRevenue();

    @Query(value = "select sum(order_item.total) as TotalPrice from Shoes \n" +
            "join order_item on order_item.Shoes_id= Shoes.Shoes_id join ordertb on ordertb.order_id = order_item.order_id\n" +
            "where ordertb.status = 'Completed'\n" +
            "and ordertb.order_date >= :start and order_date <= :end", nativeQuery = true)
    String TotalRevenueByDate(@Param("start") Date start, @Param("end") Date end);

    @Query("select new SWD392_OSOPS.dtos.ShoesRevenueDTO(p.productName as shoesName, count(oi.orderItemId) as total) " +
            "from Shoes p " +
            "join OrderItem oi on oi.shoes.shoesId = p.shoesId " +
            "join Order o on o.orderId = oi.order.orderId " +
            "where o.status = 'Completed' " +
            "group by p.shoesId, p.productName " +
            "order by total desc")
    List<ShoesRevenueDTO> TotalRevenueOfShoes();

    @Query("select new SWD392_OSOPS.dtos.ShoesRevenueDTO(" +
            "CONCAT(p.productName, ' - ', 'SomeText'), " +
            "count(oi.orderItemId)) " +
            "from Shoes p " +
            "join OrderItem oi on oi.shoes.shoesId = p.shoesId " +
            "join Order o on o.orderId = oi.order.orderId " +
            "where o.status = 'Completed' " +
            "and o.orderDate >= :start and o.orderDate <= :end " +
            "group by p.shoesId, p.productName " +
            "order by count(oi.orderItemId) desc")
    List<ShoesRevenueDTO> TotalRevenueOfShoesByList(@Param("start") LocalDate start, @Param("end") LocalDate end);


}
