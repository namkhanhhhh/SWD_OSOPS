package SWD392_OSOPS.services;
import SWD392_OSOPS.dtos.BrandRevenueDTO;
import SWD392_OSOPS.entities.Brand;
import SWD392_OSOPS.exceptions.FileNotFoundException;

import java.util.Date;
import java.util.List;

public interface BrandService {
    Brand getBrand(int b) throws FileNotFoundException;

    List<Brand> findAllBrand();
    void addBrand (Brand b);
    void editBrand(Brand b);
    List<BrandRevenueDTO> GetBrandRevenueByDate(Date start, Date end);
    List<BrandRevenueDTO> GetBrandRevenue();
}
