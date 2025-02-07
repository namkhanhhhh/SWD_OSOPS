package SWD392_OSOPS.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import SWD392_OSOPS.dtos.BrandRevenueDTO;
import SWD392_OSOPS.entities.Brand;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("select new SWD392_OSOPS.dtos.BrandRevenueDTO( b.brandName as brandName, sum(oi.total) as total ) from Brand b join Phone p on b.brandId = p.brand.brandId join OrderItem oi on oi.phone.phoneId = p.phoneId join Order o on o.orderId = oi.order.orderId where o.status = 'Completed'  group by b.brandId, b.brandName")
    List<BrandRevenueDTO> ListRevenueOfBrand();

    @Query("select new SWD392_OSOPS.dtos.BrandRevenueDTO( b.brandName as brandName, sum(oi.total) as total ) from Brand b join Phone p on b.brandId = p.brand.brandId join OrderItem oi on oi.phone.phoneId = p.phoneId join Order o on o.orderId = oi.order.orderId where o.status = 'Completed' and o.orderDate >= :start and o.orderDate <= :end group by b.brandId, b.brandName")
    List<BrandRevenueDTO> ListRevenueOfBrandBuDate(@Param("start") LocalDate  start,@Param("end") LocalDate end);
}