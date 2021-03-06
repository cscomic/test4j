package org.test4j.json.encoder.single.fixed;

import java.util.ArrayList;

import org.test4j.json.encoder.EncoderTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "test4j", "json" })
public class ByteEncoderTest extends EncoderTest {

    @Test(dataProvider = "byte_data")
    public void testEncodeSingleValue(Byte value, String expected) throws Exception {
        ByteEncoder encoder = ByteEncoder.instance;
        this.setUnmarkFeature(encoder);

        encoder.encode(value, writer, new ArrayList<String>());
        String result = writer.toString();
        want.string(result).isEqualTo(expected);
    }

    @DataProvider
    public Object[][] byte_data() {
        return new Object[][] { { Byte.valueOf("0110"), "110" }, // <br>
                { null, "null" } // <br>
        };
    }
}
