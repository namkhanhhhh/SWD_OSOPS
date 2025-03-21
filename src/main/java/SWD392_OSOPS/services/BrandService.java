package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Brand;
import SWD392_OSOPS.exceptions.FileNotFoundException;

import java.util.List;

public interface BrandService {

    List<Brand> findAllBrand();

}
