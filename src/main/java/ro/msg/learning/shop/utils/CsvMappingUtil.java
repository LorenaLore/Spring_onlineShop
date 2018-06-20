package ro.msg.learning.shop.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class CsvMappingUtil<T> {

    public List<T> fromCsv(Class<T> clazz, InputStream inputStream) throws IOException {

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        CsvSchema schema = mapper.schemaFor(clazz);
        MappingIterator<T> it = mapper.readerFor(clazz).with(schema).readValues(inputStream);

        return it.readAll();
    }

    public void toCsv(Class<T> clazz, List<T> pojoList, OutputStream outputStream) throws IOException {

        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        CsvSchema schema = mapper.schemaFor(clazz);
        ObjectWriter objectWriter = mapper.writer(schema);
        objectWriter.writeValue(outputStream, pojoList);
    }
}
