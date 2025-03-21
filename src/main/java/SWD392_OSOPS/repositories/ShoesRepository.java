package SWD392_OSOPS.repositories;

import SWD392_OSOPS.entities.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Integer> {
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

    @Query(value = "select sum(order_item.total) as TotalPrice from Shoes \n" +
            "join order_item on order_item.Shoes_id= Shoes.Shoes_id join ordertb on ordertb.order_id = order_item.order_id\n" +
            "where ordertb.status = 'Completed'", nativeQuery = true)
    String TotalRevenue();
}
