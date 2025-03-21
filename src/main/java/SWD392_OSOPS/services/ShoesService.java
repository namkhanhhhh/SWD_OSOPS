package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoesService {

    Shoes getShoesByID(int id) throws FileNotFoundException;
    List<Shoes> getbestsale() throws FileNotFoundException;
    Page<Shoes> viewShoesforshop(int pageno);
    Page<Shoes> searchShoesforShop(String name,int pageNo);
    Page<Shoes> searchByPrice(double min, double max,int PageNo);
    String GetTotalRevenue();
}
