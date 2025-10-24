package unitTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import appDomain.XmlParser;

import static org.junit.Assert.*;

public class XmlParserTest {
    private XmlParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new XmlParser();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testParseXmlFromSample1() {
        parser.parseXmlFromFile("sample1.xml");
        // Add assertions to verify the behavior
        assertTrue(parser.getIsWellFormed());
    }

    @Test
    public void testParseXmlFromSample2() {
        parser.parseXmlFromFile("sample2.xml");
        // Add assertions to verify the behavior
        assertFalse(parser.getIsWellFormed());
    }
}
