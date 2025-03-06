package SWD392_OSOPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.BrandService;
import SWD392_OSOPS.services.OrderService;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class StatisticsController {
    @Autowired
    ShoesService shoesService;

    @Autowired
    BrandService brandService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/statistics")
    public String LoadStatistic(Model model) throws FileNotFoundException {
        model.addAttribute("listBestSalePhone",shoesService.getbestsale());
        if(shoesService.GetTotalRevenue() == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", shoesService.GetTotalRevenue() );
        model.addAttribute("listBrand",brandService.GetBrandRevenue());
        model.addAttribute("listUser", userService.TotalOderOfUser());
        model.addAttribute("listShoes",shoesService.BestSaleShoes());
        if(orderService.totalOrder() == 0) model.addAttribute("TotalOrder","0");
        else model.addAttribute("TotalOrder",orderService.totalOrder());
        return"Statistics";
    }
    @PostMapping("/staticsDate")
    public String SearchStaByDate(@RequestParam("start_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                  @RequestParam("end_date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                  Model model){

        if(shoesService.GetRevenueByDate(start,end) == null)  model.addAttribute("revenue","0");
        else model.addAttribute("revenue", shoesService.GetRevenueByDate(start,end));
        if(brandService.GetBrandRevenueByDate(start, end) != null) model.addAttribute("listBrand",brandService.GetBrandRevenueByDate(start, end));
        model.addAttribute("listUser",userService.TotalOrderOfUserByDate(start, end));
        model.addAttribute("listShoes",shoesService.BestSaleShoesByDate(start,end));
        if(orderService.totalOrderByDate(start, end) == 0) model.addAttribute("TotalOrder","0");
        else model.addAttribute("TotalOrder",orderService.totalOrderByDate(start, end));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        model.addAttribute("start_date", start);
        model.addAttribute("end_date", end);
        return "Statistics";
    }

}
