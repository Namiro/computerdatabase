package com.excilys.burleon.computerdatabase.webapp.expressionlanguage;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Paginator extends SimpleTagSupport {
    static final Logger LOGGER = LoggerFactory.getLogger(Paginator.class);

    private String uri;

    private int currPage;

    private int totalPages;

    private int maxLinks = 10;

    /**
     * Default constructor.
     */
    public Paginator() {

    }

    /**
     * Allow to construct a link.
     *
     * @param page
     *            The page
     * @return The link
     */
    private String constructLink(final int page) {
        return this.constructLink(page, String.valueOf(page), null);
    }

    /**
     * Allow to construct a link.
     *
     * @param page
     *            The page
     * @param text
     *            The text
     * @param className
     *            The className
     * @return The link
     */
    private String constructLink(final int page, final String text, final String className) {
        final StringBuilder link = new StringBuilder("<li");
        if (className != null) {
            link.append(" class=\"");
            link.append(className);
            link.append("\"");
        }
        if (this.currPage == page) {
            link.append(">").append("<a href=\"").append(this.uri.replace("##", String.valueOf(page)))
                    .append("\"><strong>").append(text).append("</strong></a></li>");
        } else {
            link.append(">").append("<a href=\"").append(this.uri.replace("##", String.valueOf(page)))
                    .append("\">").append(text).append("</a></li>");
        }
        return link.toString();
    }

    @Override
    public void doTag() {
        Paginator.LOGGER.debug("NB PAGE MAX : " + this.totalPages);
        Paginator.LOGGER.debug("MAX LINK : " + this.maxLinks);
        final boolean lastPage = this.currPage == this.totalPages;
        int pgStart = Math.max(this.currPage - this.maxLinks / 2, 1);
        int pgEnd = pgStart + this.maxLinks;
        if (pgEnd > this.totalPages + 2) {
            final int diff = pgEnd - this.totalPages;
            pgStart -= diff - 1;
            if (pgStart < 1) {
                pgStart = 1;
            }
            pgEnd = this.totalPages;
        }

        try {
            this.getJspContext().getOut().write("<ul class=\"pagination\">");

            if (this.currPage > 1) {
                this.getJspContext().getOut().write(this.constructLink(1, "<<", "paginatorPrev"));
                this.getJspContext().getOut().write(this.constructLink(this.currPage - 1, "<", "paginatorPrev"));

            }

            for (int i = pgStart; i < pgEnd; i++) {
                Paginator.LOGGER.debug("i " + i);
                if (i <= this.totalPages) {
                    this.getJspContext().getOut().write(this.constructLink(i));
                }
            }

            if (!lastPage) {
                this.getJspContext().getOut()
                        .write(this.constructLink(this.currPage + 1, ">", "paginatorNext paginatorLast"));
                this.getJspContext().getOut()
                        .write(this.constructLink(this.totalPages, ">>", "paginatorNext paginatorLast"));
            } else {
                this.getJspContext().getOut().write(this.constructLink(this.totalPages));
            }

            this.getJspContext().getOut().write("</ul>");

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public int getCurrPage() {
        return this.currPage;
    }

    public int getMaxLinks() {
        return this.maxLinks;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public String getUri() {
        return this.uri;
    }

    public void setCurrPage(final int currPage) {
        this.currPage = currPage;
    }

    public void setMaxLinks(final int maxLinks) {
        this.maxLinks = maxLinks;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }
}
