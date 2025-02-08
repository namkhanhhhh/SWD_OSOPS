package SWD392_OSOPS.services.impls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.dtos.BrandRevenueDTO;
import SWD392_OSOPS.entities.Brand;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.repositories.BrandRepository;
import SWD392_OSOPS.services.BrandService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand getBrand(int b) throws FileNotFoundException {
        if(brandRepository.findById(b).isEmpty()) return null;
        return brandRepository.findById(b).get();
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public void addBrand(Brand b) {
        brandRepository.save(b);
    }

    @Override
    public void editBrand(Brand b) {
      Brand existingBrand = brandRepository.getReferenceById(b.getBrandId());
      existingBrand.setBrandName(b.getBrandName());
      brandRepository.save(existingBrand);
    }

    @Override
    public List<BrandRevenueDTO> GetBrandRevenueByDate(Date start, Date end) {
        LocalDate startDate = convertToLocalDate(start);
        LocalDate endDate = convertToLocalDate(end);

        List<BrandRevenueDTO> results = brandRepository.ListRevenueOfBrandByDate(startDate, endDate);
        return results != null ? results : null;
    }

    @Override
    public List<BrandRevenueDTO> GetBrandRevenue() {

            List<BrandRevenueDTO> list = brandRepository.ListRevenueOfBrand();
            if(list==null) return null;
            return list;

    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
