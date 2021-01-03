package eCommerceApp.service;

import eCommerceApp.dao.CustomerReposiroty;
import eCommerceApp.dto.Purchase;
import eCommerceApp.dto.PurchaseResponse;
import eCommerceApp.entity.Customer;
import eCommerceApp.entity.Order;
import eCommerceApp.entity.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutServiceIF {

    private CustomerReposiroty customerRepository;

    public CheckoutServiceImpl(CustomerReposiroty customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order=purchase.getOrder();

        String orderTrackingNumber= generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems= purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer= purchase.getCustomer();
        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }


}
