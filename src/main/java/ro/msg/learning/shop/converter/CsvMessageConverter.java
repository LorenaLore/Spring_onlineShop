package ro.msg.learning.shop.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.utils.CsvMappingUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    private CsvMappingUtil csvMappingUtility = new CsvMappingUtil();

    public CsvMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return super.canRead(mediaType);
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return mediaType != null && super.canWrite(mediaType);
    }

    @Override
    protected void writeInternal(List<T> object, Type type, HttpOutputMessage httpOutputMessage)
            throws IOException, HttpMessageNotWritableException {

        if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().getTypeName().equals(
                "java.util.List")) {
            csvMappingUtility.toCsv((Class) ((ParameterizedType) type).getActualTypeArguments()[0], object,
                    httpOutputMessage.getBody());
        } else {
            throw new HttpMessageNotReadableException("cannot write internal for type: " + type);
        }
    }

    @Override
    protected List<T> readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {

        return csvMappingUtility.fromCsv(aClass, httpInputMessage.getBody());
    }

    @Override
    public List<T> read(Type type, Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return readInternal(aClass, httpInputMessage);
    }
}
