package edu.uci.ics.crawler4j.crawler;

import edu.uci.ics.crawler4j.url.WebURL;

/**
 * A {@code CustomStack} object is a bounded stack
 * that contains information that the crawler set
 * for pages whose descendants it is still crawling,
 * if it is crawling in stack order.
 *
 * @author Yuan Kang
 */
public class CustomStack {
    /**
     * The stack space. Note that it is bounded by the crawling depth.
     */
    private CustomStackState[] stack;
    /**
     * The index of the top of the stack. The value is -1 if the stack is empty.
     */
    private int topPointer;

    /**
     * The mutex for controlling access to the stack
     */
    private final Object mutex = new Object();

    /**
     * Initialize the stack space and pointer.
     * @param depth the crawling depth, so the stack could have to keep
     *              the number of pages in the depth, and the first page
     */
    public CustomStack(int depth) {
        synchronized (mutex) {
            stack = new CustomStackState[depth + 1];
            topPointer = -1;
        }
    }

    /**
     * Get the top element in the stack,
     * and pop it if it was added by the given page's URL
     * @param curURL the URL of the current web page about which
     *               the crawler needs to decide whether to crawl,
     *               and is a descendant of all the URLs
     *               currently in the stack.
     *               If it is equal to the URL of the top state,
     *               the top state is popped.
     * @return the state currently on top of the stack
     */
    public CustomStackState peekOrPop(WebURL curURL) {
        synchronized (mutex) {
            if (topPointer < 0) {
                return null;
            } else {
                CustomStackState top = stack[topPointer];

                /* Pop if the URL matches. */
                if (top.wasAddedByURL(curURL)) {
                    stack[topPointer] = null;
                    topPointer--;
                }
                return top;
            }
        }
    }

    /**
     * Push a new state onto the stack if there is space.
     * @param newState the new state to push
     * @return {@literal true} iff there was enough space to push the state
     */
    public boolean push(CustomStackState newState) {
        synchronized (mutex) {
            if (topPointer < stack.length) {
                stack[++topPointer] = newState;
                return true;
            } else {
                return false;
            }
        }
    }
}
