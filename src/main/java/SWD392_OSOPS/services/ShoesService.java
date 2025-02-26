package SWD392_OSOPS.services;


import SWD392_OSOPS.entities.Size;
import org.springframework.data.domain.Page;
import SWD392_OSOPS.dtos.ShoesRevenueDTO;
import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ShoesService {

    List<Shoes> findAllShoes();
    void addShoes(Shoes Shoes);
    Shoes getShoesByID(int id) throws FileNotFoundException;
    Shoes getShoesByIdForManager(int id);
    List<Shoes> getShoesByBrand(int id) throws FileNotFoundException;
    void editShoes(Shoes p,List<Integer> listSizeId);
    void changeStatus(Shoes p);
    List<Shoes> searchShoes(String name);
    Page<Shoes> findShoesPage(int pageNo);
    Page<Shoes> searchShoes(String name, int pageNo);
    List<Shoes> getbestsale() throws FileNotFoundException;
    Page<Shoes> viewShoesforshop(int pageno);
    Page<Shoes> searchShoesforShop(String name,int pageNo);
    Page<Shoes> getShoesBrandByPahination(int id,int pageNo) throws FileNotFoundException;
    Page<Shoes> searchShoesByStatus(boolean status,int pageNo);
    Page<Shoes> searchByPrice(double min, double max,int PageNo);
    String GetTotalRevenue();
    List<ShoesRevenueDTO> BestSaleShoes();
    List<ShoesRevenueDTO> BestSaleShoesByDate(Date start, Date end);
    String GetRevenueByDate(Date start, Date end);
    List<Size> getAllSizes() ;

}
