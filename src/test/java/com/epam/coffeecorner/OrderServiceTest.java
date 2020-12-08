package com.epam.coffeecorner;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.ProductType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(BlockJUnit4ClassRunner.class)
public class OrderServiceTest {
    private static final Product PRODUCT_01 = new Product("TestProduct01", BigDecimal.valueOf(1.29), ProductType.COFFEE);
    private static final Product PRODUCT_02 = new Product("TestProduct02", BigDecimal.valueOf(1.29), ProductType.SOFT_DRINK);
    private static final Product EXTRA_01 = new Product("Extra 01", BigDecimal.valueOf(.49), ProductType.EXTRA);

    private OrderService out;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        out = new OrderService();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printReceipt_oneProduct_printsReceipt() {
        out.addProductToOrder(PRODUCT_01);
        out.printReceipt();
        assertThat(outContent.toString().trim()).isEqualToIgnoringWhitespace("Your receipt:TestProduct01---" +
                "1,29CHF---------------Total:1,29CHF");
    }

    @Test
    public void printReceipt_oneProduct_oneExtra_printsReceipt() {
        out.addProductToOrder(PRODUCT_01);
        out.addProductToOrder(EXTRA_01);
        out.printReceipt();
        assertThat(outContent.toString().trim()).isEqualToIgnoringWhitespace("Your receipt:TestProduct01---1,29 CHF" +
                "Extra 01---0,49CHF---------------Total:1,78 CHF");
    }

    @Test
    public void printReceipt_noProducts_throwsException() {
        try {
            out.printReceipt();
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage()).isEqualTo("Order should contain at least one product to print receipt");
            return;
        }
        fail();
    }

    @Test
    public void printReceipt_sixBeverages_fifthIsFree_printsReceipt() {
        out.addProductToOrder(PRODUCT_01);
        out.addProductToOrder(PRODUCT_02);
        out.addProductToOrder(PRODUCT_01);
        out.addProductToOrder(PRODUCT_01);
        out.addProductToOrder(PRODUCT_02);
        out.addProductToOrder(PRODUCT_01);
        out.printReceipt();
        assertThat(outContent.toString().trim()).isEqualToIgnoringWhitespace("Your receipt:TestProduct01--- 1,29 CHF" +
                "TestProduct02---1,29 CHF" +
                "TestProduct01---1,29 CHF" +
                "TestProduct01---1,29 CHF" +
                "TestProduct02---0,00 CHF" +
                "TestProduct01---1,29 CHF ---------------Total:6,45CHF");
    }


}