package eCommerceApp.service;

import eCommerceApp.dto.Purchase;
import eCommerceApp.dto.PurchaseResponse;

public interface CheckoutServiceIF {

    PurchaseResponse placeOrder(Purchase purchase);
}
