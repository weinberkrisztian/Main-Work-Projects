package eCommerceApp.dao;

import eCommerceApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface CustomerReposiroty extends JpaRepository<Customer, Long> {
}
