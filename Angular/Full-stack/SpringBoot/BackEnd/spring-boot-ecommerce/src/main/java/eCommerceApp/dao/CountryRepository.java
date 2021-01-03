package eCommerceApp.dao;

import eCommerceApp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(path = "countries" ,collectionResourceRel = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
