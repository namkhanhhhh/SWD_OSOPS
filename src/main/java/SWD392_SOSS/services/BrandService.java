package SWD392_SOSS.services;
import SWD392_SOSS.dtos.BrandRevenueDTO;
import SWD392_SOSS.entities.Brand;
import SWD392_SOSS.exceptions.FileNotFoundException;

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
