package de.undercouch.citeproc.bibtex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the page parser
 * @author Michel Kraemer
 */
public class PageParserTest {
    /**
     * Tests a single page
     */
    @Test
    public void singlePage() {
        PageRange pr = PageParser.parse("10");
        assertEquals(Integer.valueOf(1), pr.getNumberOfPages());
    }

    /**
     * Tests page ranges
     */
    @Test
    public void simplePageRange() {
        PageRange pr = PageParser.parse("10-20");
        assertEquals("10-20", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(11), pr.getNumberOfPages());

        pr = PageParser.parse("10--20");
        assertEquals("10-20", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(11), pr.getNumberOfPages());

        pr = PageParser.parse("10---20");
        assertEquals("10-20", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(11), pr.getNumberOfPages());

        pr = PageParser.parse("10\u201320");
        assertEquals("10-20", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(11), pr.getNumberOfPages());
    }

    /**
     * Tests two page ranges separated by a comma
     */
    @Test
    public void twoPageRanges() {
        PageRange pr = PageParser.parse("10-20,30--40");
        assertEquals("10-20,30-40", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(22), pr.getNumberOfPages());
    }

    /**
     * Tests a complex page range
     */
    @Test
    public void complex() {
        PageRange pr = PageParser.parse("10-20,30--40,45,50\u201355,5");
        assertEquals("10-20,30-40,45,50-55,5", pr.getLiteral());
        assertEquals("5", pr.getPageFirst());
        assertEquals(Integer.valueOf(30), pr.getNumberOfPages());
    }

    /**
     * Tests page range containing an unknown page
     */
    @Test
    public void fromToUnknown() {
        PageRange pr = PageParser.parse("10-??");
        assertEquals("10-??", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertNull(pr.getNumberOfPages());
    }

    /**
     * Tests if a pseudo page range is rendered as a single page
     */
    @Test
    public void singlePageRange() {
        PageRange pr = PageParser.parse("10-10");
        assertEquals("10", pr.getLiteral());
        assertEquals("10", pr.getPageFirst());
        assertEquals(Integer.valueOf(1), pr.getNumberOfPages());
    }
}
