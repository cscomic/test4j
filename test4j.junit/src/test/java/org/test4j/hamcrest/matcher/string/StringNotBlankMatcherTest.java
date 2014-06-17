package org.test4j.hamcrest.matcher.string;

import java.util.Iterator;

import org.junit.Test;
import org.test4j.junit.Test4J;
import org.test4j.junit.annotations.DataFrom;

@SuppressWarnings("rawtypes")
public class StringNotBlankMatcherTest extends Test4J {

    @Test(expected = AssertionError.class)
    @DataFrom("dataForNotBlank")
    public void testNotBlank(String actual) {
        want.string(actual).notBlank();
    }

    public static Iterator dataForNotBlank() {
        return new DataIterator() {
            {
                data((String) null);
                data("");
                data(" ");
                data("\n");
            }
        };
    }

    @Test
    public void testNotBlank_Success() {
        want.string("tt").notBlank();
    }
}
