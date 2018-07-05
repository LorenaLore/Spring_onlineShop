package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ShopJpaServiceFactory extends ODataJPAServiceFactory {

    private final LocalContainerEntityManagerFactoryBean factory;

    @Autowired
    public ShopJpaServiceFactory(LocalContainerEntityManagerFactoryBean factory) {
        this.factory = factory;
    }

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext context = this.getODataJPAContext();
        context.setEntityManagerFactory(factory.getObject());
        context.setPersistenceUnitName("default");
        //context.setJPAEdmMappingModel("mappingmodel.xml");
        return context;
    }
}