package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;


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
        JPAEdmExtension extension = new ShopJPAEdmExtension();

        this.setDetailErrors(true);
        ODataJPAContext context = this.getODataJPAContext();
        context.setEntityManagerFactory(factory.getObject());
        context.setPersistenceUnitName("default");
        context.setJPAEdmExtension(extension);
        return context;
    }
    
    @Override
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
        return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ? new ScenarioErrorCallback()
                               : super.getCallback(callbackInterface));
    }
    
    private class ScenarioErrorCallback implements ODataErrorCallback{
        @Override
        public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
            if (context.getHttpStatus() == HttpStatusCodes.INTERNAL_SERVER_ERROR) {
                context.getException().printStackTrace();
            }
            return EntityProvider.writeErrorDocument(context);
        }
    }

    public class ShopJPAEdmExtension implements JPAEdmExtension {

        @Override
        public void extendWithOperation(JPAEdmSchemaView jpaEdmSchemaView) {

        }

        @Override
        public void extendJPAEdmSchema(JPAEdmSchemaView jpaEdmSchemaView) {

        }

        @Override
        public InputStream getJPAEdmMappingModelStream() {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();

            return classloader.getResourceAsStream("mappingmodel.xml");
        }
    }
}