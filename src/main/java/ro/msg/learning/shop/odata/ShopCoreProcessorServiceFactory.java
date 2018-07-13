package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ShopCoreProcessorServiceFactory extends ODataServiceFactory {

    @Override
    public ODataService createService(ODataContext oDataContext) throws ODataException {

      //  EdmProvider edmProvider = new ShopCoreProcessorEdmProvider();
        EdmProvider edmProvider = new ShopCreateDtoEdmProvider();
        ODataSingleProcessor singleProcessor = new ShopCoreProcessor();
        return createODataSingleProcessorService(edmProvider, singleProcessor);
    }

    @Override
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
        return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ? new ScenarioErrorCallback()
                : super.getCallback(callbackInterface));
    }

    private class ScenarioErrorCallback implements ODataErrorCallback {
        @Override
        public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
            if (context.getHttpStatus() == HttpStatusCodes.INTERNAL_SERVER_ERROR) {
                context.getException().printStackTrace();
            }
            return EntityProvider.writeErrorDocument(context);
        }
    }
}
