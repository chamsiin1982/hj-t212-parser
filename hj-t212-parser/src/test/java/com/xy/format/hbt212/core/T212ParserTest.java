package com.xy.format.hbt212.core;

import com.xy.format.hbt212.core.feature.ParserFeature;
import com.xy.format.hbt212.exception.T212FormatException;
import com.xy.format.hbt212.model.Pack;
import com.xy.format.hbt212.model.verify.PacketElement;
import com.xy.format.segment.base.cfger.Feature;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.PushbackReader;
import java.io.StringReader;

import static com.xy.format.hbt212.core.feature.ParserFeature.FOOTER_CONSTANT;
import static org.junit.Assert.*;

/**
 * Created by xiaoyao9184 on 2017/12/19.
 */
public class T212ParserTest {

    @Test
    public void parse() {
        String data = "##0139ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";
        StringReader reader = new StringReader(data);
        T212Parser parser = new T212Parser(reader);
        parser.setParserFeature(Feature.collectFeatureDefaults(ParserFeature.class));
        try {
            assertArrayEquals(parser.readHeader(),new char[]{'#','#'});
            assertArrayEquals(parser.readDataLen(),new char[]{'0','1','3','9'});
            assertEquals(parser.readData(139).length,139);
            assertArrayEquals(parser.readCrc(),new char[]{'4','9','8','0'});
            assertArrayEquals(parser.readFooter(),new char[]{'\r','\n'});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }

    @Test
    public void crc() {
        String data = "##0139ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817000;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n";
        StringReader reader = new StringReader(data);
        T212Parser parser = new T212Parser(reader);
        parser.setParserFeature(Feature.collectFeatureDefaults(ParserFeature.class));
        try {
            assertArrayEquals(parser.readHeader(),new char[]{'#','#'});
            assertArrayEquals(parser.readDataLen(),new char[]{'0','1','3','9'});
            assertEquals(parser.readDataAndCrc(139).length,139);
            assertArrayEquals(parser.readFooter(),new char[]{'\r','\n'});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }

}