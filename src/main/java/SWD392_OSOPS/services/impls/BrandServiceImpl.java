package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.Brand;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.repositories.BrandRepository;
import SWD392_OSOPS.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

}
