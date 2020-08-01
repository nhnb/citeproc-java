package de.undercouch.citeproc;

import java.io.IOException;

/**
 * <p>Builder for {@link CSL} objects.</p>
 *
 * <p>Please read the javadoc of the {@link CSL} class for more information
 * about its usage. In particular, take note of the 'Cleanup' section to
 * correctly release all resources when you're done with the citation
 * processor.</p>
 *
 * @author Michel Kraemer
 */
public class CSLBuilder {
    private ItemDataProvider itemDataProvider;
    private LocaleProvider localeProvider = new DefaultLocaleProvider();
    private AbbreviationProvider abbreviationProvider = new DefaultAbbreviationProvider();
    private VariableWrapper variableWrapper;
    private String style;
    private String lang = "en-US";
    private boolean forceLang = false;
    boolean experimentalMode = false;

    /**
     * Set the item data provider
     * @param itemDataProvider an object that provides citation item data
     * @return {@code this} builder
     */
    public CSLBuilder itemDataProvider(ItemDataProvider itemDataProvider) {
        this.itemDataProvider = itemDataProvider;
        return this;
    }

    /**
     * Set an optional locale provider
     * @param localeProvider an object that provides CSL locales
     * @return {@code this} builder
     */
    public CSLBuilder localeProvider(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
        return this;
    }

    /**
     * Set an optional abbreviation provider
     * @param abbreviationProvider an object that provides abbreviations
     * @return {@code this} builder
     */
    public CSLBuilder abbreviationProvider(AbbreviationProvider abbreviationProvider) {
        this.abbreviationProvider = abbreviationProvider;
        return this;
    }

    /**
     * Set an optional variable wrapper
     * @param variableWrapper an object that decorates rendered items
     * @return {@code this} builder
     */
    public CSLBuilder variableWrapper(VariableWrapper variableWrapper) {
        this.variableWrapper = variableWrapper;
        return this;
    }

    /**
     * Set the citation style to use. This may either be a serialized XML
     * representation of the style or a style's name such as <code>ieee</code>.
     * In the latter case, the processor loads the style from the classpath (e.g.
     * <code>/ieee.csl</code>).
     * @param style the style
     * @return {@code this} builder
     */
    public CSLBuilder style(String style) {
        this.style = style;
        return this;
    }

    /**
     * Set an RFC 4646 identifier for the citation locale (e.g. <code>en-US</code>)
     * @param lang the language identifier
     * @return {@code this} builder
     */
    public CSLBuilder lang(String lang) {
        this.lang = lang;
        return this;
    }

    /**
     * Specifies if the locale set with {@link #lang(String)} should overwrite
     * any default locale
     * @param forceLang {@code true} if the configured locale should overwrite
     * the default one
     * @return {@code this} builder
     */
    public CSLBuilder forceLang(boolean forceLang) {
        this.forceLang = forceLang;
        return this;
    }

    /**
     * Specifies if the new experimental pure Java CSL processor should be
     * used
     * @param experimentalMode {@code true} if the new experimental pure Java
     * CSL processor should be used
     * @return {@code this} builder
     */
    public CSLBuilder experimentalMode(boolean experimentalMode) {
        this.experimentalMode = experimentalMode;
        return this;
    }

    /**
     * Creates the {@code CSL} object with the configured parameters. Make sure
     * to call {@link CSL#close()} to release all resources associated with the
     * CSL processor when you're done with it.
     * @return the {@code CSL} object
     * @throws IOException @throws IOException if the underlying JavaScript
     * files or the CSL style could not be loaded
     */
    public CSL build() throws IOException {
        return new CSL(itemDataProvider, localeProvider, abbreviationProvider,
                variableWrapper, style, lang, forceLang, experimentalMode);
    }
}
