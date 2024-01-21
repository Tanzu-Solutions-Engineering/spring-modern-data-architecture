package com.vmware.retail.source.transformation;

import com.vmware.retail.domain.Product;
import nyla.solutions.core.io.IO;
import nyla.solutions.core.io.csv.CsvWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CsvToProductsConverterTest {

    private CsvToProductsConverter subject;

    @BeforeEach
    void setUp() {
        subject = new CsvToProductsConverter();
    }

    @Test
    void convert() {

        var product1 = new Product("sku1","product1");
        var product2 = new Product("sku2","product2");

        var csv = new StringBuilder().append(CsvWriter.toCSV(product1.id(),product1.name()))
                .append(IO.newline())
                .append(CsvWriter.toCSV(product2.id(),product2.name()))
                .toString();

        System.out.printf(csv);

        var actual = subject.convert(csv);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        actual.get(0).equals(product1);
        actual.get(1).equals(product2);

    }

    @Test
    void defect() {
        String csv = "\"sku1\", \"Peanut butter\"\n" +
                "\"sku2\", \"Jelly\"\n" +
                "\"sku3\", \"Bread\"\n" +
                "\"sku4\", \"Milk\"";

        var actual = subject.convert(csv);
        assertThat(actual).isNotNull();

    }
}