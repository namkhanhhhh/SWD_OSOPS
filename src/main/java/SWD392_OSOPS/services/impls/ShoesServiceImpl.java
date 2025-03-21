package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.repositories.ShoesRepository;
import SWD392_OSOPS.services.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoesServiceImpl implements ShoesService {

    @Autowired
    private ShoesRepository ShoesRepository;

    @Override
    public Shoes getShoesByID(int id) throws FileNotFoundException {
        if (ShoesRepository.findById(id).isEmpty()) {
            throw new FileNotFoundException("Not found!");
        }
        return ShoesRepository.findById(id).get();
    }

    @Override
    public List<Shoes> getbestsale() throws FileNotFoundException {
        List<Integer> li = ShoesRepository.getBestSale();
        List<Shoes> lp = new ArrayList<>();
        for (Integer i : li) {
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
    public Page<Shoes> searchByPrice(double min, double max, int PageNo) {
        Pageable pageable = PageRequest.of(PageNo - 1, 6);
        return ShoesRepository.findByPriceRangeAndStatus(min, max, pageable);
    }

    @Override
    public String GetTotalRevenue() {
        if (ShoesRepository.TotalRevenue() != null) return ShoesRepository.TotalRevenue();
        return null;
    }
}
