package eCommerceApp.controller;

import eCommerceApp.dto.Purchase;
import eCommerceApp.dto.PurchaseResponse;
import eCommerceApp.service.CheckoutServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/checkout")
public class CheckoutController {

    private CheckoutServiceIF checkoutService;

    public CheckoutController(CheckoutServiceIF checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse checkout(@RequestBody Purchase purchase){
        System.out.println("hellooo");
        System.out.println(purchase);

        PurchaseResponse purchaseResponse=checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }
}
