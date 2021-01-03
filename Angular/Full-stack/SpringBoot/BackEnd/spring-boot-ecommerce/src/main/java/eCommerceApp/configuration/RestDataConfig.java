package eCommerceApp.configuration;

import eCommerceApp.entity.Country;
import eCommerceApp.entity.Product;
import eCommerceApp.entity.ProductCategory;
import eCommerceApp.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    private EntityManager entityManger;

    @Autowired
    public RestDataConfig(EntityManager entityManger) {
    this.entityManger = entityManger;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod[] theUnsupportedMethods={HttpMethod.DELETE,HttpMethod.POST,HttpMethod.PUT};

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));

        config.getExposureConfiguration()
                .forDomainType(Country.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));

        config.getExposureConfiguration()
                .forDomainType(State.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));

        exposeIds(config);
       config.exposeIdsFor(ProductCategory.class);
        config.exposeIdsFor(Country.class);
        config.exposeIdsFor(State.class);
    }



    private void exposeIds(RepositoryRestConfiguration config) {

    Set<EntityType<?>> entityTypes=entityManger.getMetamodel().getEntities();

    List<Class> entityClasses= new ArrayList<>();

        for (EntityType tempEntity: entityTypes ) {
            entityClasses.add(tempEntity.getJavaType());
        }

        Class[] domainTypes=entityClasses.toArray(new Class[0]);

        config.exposeIdsFor(domainTypes);



    }
}
