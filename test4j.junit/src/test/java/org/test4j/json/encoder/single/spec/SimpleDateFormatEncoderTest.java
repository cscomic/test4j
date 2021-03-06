package org.test4j.json.encoder.single.spec;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Test;
import org.test4j.json.helper.JSONFeature;
import org.test4j.junit.Test4J;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SimpleDateFormatEncoderTest extends Test4J {
    @Test
    public void testEncode() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormatEncoder encoder = SimpleDateFormatEncoder.instance;
        encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);

        StringWriter writer = new StringWriter();
        encoder.encode(df, writer, new ArrayList<String>());

        String json = writer.toString();
        want.string(json).isEqualTo("'yyyy-MM-dd'");
    }
}
