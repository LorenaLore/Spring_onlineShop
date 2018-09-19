package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.processor.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.OrderService;

@Component
@RequiredArgsConstructor
@Scope("request")
public class ShopCoreProcessorServiceFactory extends ODataServiceFactory {

    private final OrderService orderService;

    @Override
    public ODataService createService(ODataContext oDataContext) {

        //  EdmProvider edmProvider = new ShopCoreProcessorEdmProvider();
        EdmProvider edmProvider = new ShopCreateDtoEdmProvider();
        ODataSingleProcessor singleProcessor = new ShopCoreProcessor(orderService);
        return createODataSingleProcessorService(edmProvider, singleProcessor);
    }

    @Override
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
        return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ? new ScenarioErrorCallback()
                : super.getCallback(callbackInterface));
    }

    private class ScenarioErrorCallback implements ODataErrorCallback {
        @Override
        public ODataResponse handleError(final ODataErrorContext context) {
            if (context.getHttpStatus() == HttpStatusCodes.INTERNAL_SERVER_ERROR) {
                context.getException().printStackTrace();
            }
            return EntityProvider.writeErrorDocument(context);
        }
    }
}
