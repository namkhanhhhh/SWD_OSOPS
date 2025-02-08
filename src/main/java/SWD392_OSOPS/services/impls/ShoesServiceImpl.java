package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.dtos.ShoesRevenueDTO;
import SWD392_OSOPS.entities.Brand;
import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.repositories.BrandRepository;
import SWD392_OSOPS.repositories.ShoesRepository;
import SWD392_OSOPS.services.BrandService;
import SWD392_OSOPS.services.ShoesService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoesServiceImpl implements ShoesService {

    @Autowired
    private ShoesRepository ShoesRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;


    @Override
    public List<Shoes> findAllShoes() {
        return ShoesRepository.findAll();
    }

    @Override
    public void addShoes(Shoes Shoes) {

        ShoesRepository.save(Shoes);
    }

    @Override
    public Shoes getShoesByID(int id) throws FileNotFoundException {
      if(ShoesRepository.findById(id).isEmpty()){
          throw new FileNotFoundException("Not found!");
      }
       return ShoesRepository.findById(id).get();
    }

    @Override
    public Shoes getShoesByIdForManager(int id) {
        if(ShoesRepository.findById(id).isEmpty()){
            return null;
        }
        return ShoesRepository.findById(id).get();
    }

    @Override
    public List<Shoes> getShoesByBrand(int id) throws FileNotFoundException {
        List<Shoes> listShoes = findAllShoes();
        Brand brand = brandService.getBrand(id);
        if(brand ==null) return null;
        List<Shoes> l = new ArrayList<>();
        for (int i = 0; i < listShoes.size(); i++) {
            if(listShoes.get(i).getBrand().equals(brand) && listShoes.get(i).getStatus()) l.add(listShoes.get(i));
        }
        return l;
    }

    @Override
    public void editShoes(Shoes p) {

    }

    @Override
    public void changeStatus(Shoes p) {
        p.setStatus(!p.getStatus());
        ShoesRepository.save(p);
    }

    @Override
    public List<Shoes> searchShoes(String name) {

        return ShoesRepository.SearchProduct(name);
    }

    @Override
    public Page<Shoes> findShoesPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1,5);
        return this.ShoesRepository.findAll(pageable);

    }


    @Override
    public Page<Shoes> searchShoes(String name, int pageNo) {
        List<Shoes> list = ShoesRepository.SearchProduct(name);
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list, pageable, ShoesRepository.SearchProduct(name).size());
    }

    @Override
    public List<Shoes> getbestsale() throws FileNotFoundException {
        List<Integer> li = ShoesRepository.getBestSale();
        List<Shoes> lp = new ArrayList<>();
        for(Integer i : li){
            lp.add(getShoesByID(i));
        }
        return lp;
    }

    @Override
    public Page<Shoes> viewShoesforshop(int pageno) {
        Pageable pageable = PageRequest.of(pageno - 1, 9);
        return ShoesRepository.ViewProductforShop(pageable);
    }

    @Override
    public Page<Shoes> searchShoesforShop(String name, int pageNo) {
        List<Shoes> list = ShoesRepository.SearchProductforShop(name);
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list, pageable, ShoesRepository.SearchProductforShop(name).size());
    }


    @Override
    public Page<Shoes> getShoesBrandByPahination(int id, int pageNo) throws FileNotFoundException {
        List<Shoes> list = getShoesByBrand(id);
        if(list == null) return null;
        Pageable pageable = PageRequest.of(pageNo -1,6);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list,pageable,getShoesByBrand(id).size());
    }

    @Override
    public Page<Shoes> searchShoesByStatus(boolean status, int pageNo) {
        List<Shoes> list = ShoesRepository.searchShoesByStatus(status);
        Pageable pageable = PageRequest.of(pageNo-1,5);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<>(list,pageable, ShoesRepository.searchShoesByStatus(status).size());
    }

    @Override
    public Page<Shoes> searchByPrice(double min, double max, int PageNo) {
        Pageable pageable = PageRequest.of(PageNo-1,6);
        return ShoesRepository.findByPriceRangeAndStatus(min,max,pageable);
    }

    @Override
    public String GetTotalRevenue() {
        if(ShoesRepository.TotalRevenue()!= null) return ShoesRepository.TotalRevenue();
        return null;
    }

    @Override
    public List<ShoesRevenueDTO> BestSaleShoes() {
        List<ShoesRevenueDTO> list = ShoesRepository.TotalRevenueOfShoes();
        return list!=null? list : null;
    }

    @Override
    public List<ShoesRevenueDTO> BestSaleShoesByDate(Date start, Date end) {
        LocalDate startDate = convertToLocalDate(start);
        LocalDate endDate = convertToLocalDate(end);

        List<ShoesRevenueDTO> results = ShoesRepository.TotalRevenueOfShoesByList(startDate, endDate);
        int size = Math.min(5, results.size());
        return results != null ? results.subList(0,size) : null;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public String GetRevenueByDate(Date start, Date end) {
        if(ShoesRepository.TotalRevenueByDate(start,end)!= null) return ShoesRepository.TotalRevenueByDate(start,end);
        return null;
    }







}
