package com.vmware.retail.source.transformation;

import com.vmware.retail.domain.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.io.csv.CsvReader;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gregory green
 */
@Component
@Slf4j
public class CsvToProductsConverter implements Converter<String, List<Product>> {
    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;

    @SneakyThrows
    @Override
    public List<Product> convert(String csv) {

        log.info("CSV: {}",csv);
        var reader = new CsvReader(new StringReader(csv));
        var list = new ArrayList<Product>(reader.size());
        for(List<String> row : reader)
        {
            list.add(new Product(row.get(ID_COL),row.get(NAME_COL)));
        }
        return list;
    }
}
