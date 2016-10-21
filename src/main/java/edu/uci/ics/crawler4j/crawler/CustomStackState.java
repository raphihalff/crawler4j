package edu.uci.ics.crawler4j.crawler;

import edu.uci.ics.crawler4j.url.WebURL;

/**
 * A {@code CustomStackState} object is a single element
 * in a {@code CustomStack} object,
 * and if the crawling schedule is in stack order,
 * it contains information that the WebCrawler set
 * while crawling an ancestor of the page that it is currently crawling.
 *
 * @author Yuan Kang
 */
public class CustomStackState {
    /**
     * The URL for which the crawler added the state.
     */
    private WebURL adderURL;

    /**
     * Construct the object with the basic URL field.
     * @param adderURL the URL for which the crawler added the state.
     */
    public CustomStackState(WebURL adderURL) {
        this.adderURL = adderURL;
    }

    /**
     * Check if the state was added by the given URL.
     * @param curURL the URL of the current web page about which
     *               the crawler needs to decide whether to crawl,
     *               and is a descendant of the URL
     *               for which the crawler pushed this state.
     * @return {@literal true} iff the URL of the page
     *         for which this state was added is equal to {@code curURL}
     */
    public boolean wasAddedByURL(WebURL curURL) {
        return adderURL.equals(curURL);
    }
}
