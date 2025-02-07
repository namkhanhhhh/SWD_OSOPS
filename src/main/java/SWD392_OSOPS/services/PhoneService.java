package SWD392_OSOPS.services;


import org.springframework.data.domain.Page;
import SWD392_OSOPS.dtos.ShoesRevenueDTO;
import SWD392_OSOPS.entities.Phone;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface PhoneService {
    List<Phone> findAllPhone();
    void addPhone(Phone phone);
    Phone getPhoneByID(int id) throws FileNotFoundException;
    Phone getPhoneByIdForManager(int id);
    List<Phone> getPhoneByBrand(int id) throws FileNotFoundException;
    void editPhone(Phone p);
    void changeStatus(Phone p);
    List<Phone> searchPhone(String name);
    Page<Phone> findPhonePage(int pageNo);
    Page<Phone> searchPhone(String name, int pageNo);
    List<Phone> getbestsale() throws FileNotFoundException;
    Page<Phone> viewphoneforshop(int pageno);
    Page<Phone> searchPhoneforShop(String name,int pageNo);
    Page<Phone> getPhoneBrandByPahination(int id,int pageNo) throws FileNotFoundException;
    Page<Phone> searchPhoneByStatus(boolean status,int pageNo);
    Page<Phone> searchByPrice(double min, double max,int PageNo);
    String GetTotalRevenue();
    List<PhoneRevenueDTO> BestSalePhone();
    List<PhoneRevenueDTO> BestSalePhoneByDate(Date start, Date end);
    String GetRevenueByDate(Date start, Date end);

}
