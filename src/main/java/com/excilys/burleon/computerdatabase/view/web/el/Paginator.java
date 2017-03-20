package com.excilys.burleon.computerdatabase.view.web.el;

public final class Paginator {
    private String uri;
    private int currPage;
    private int totalPages;
    private int maxLinks = 10;

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
        link.append(">").append("<a href=\"").append(this.uri.replace("##", String.valueOf(page))).append("\">")
                .append(text).append("</a></li>");
        return link.toString();
    }

    /**
     *
     * @param uri
     *            Uri
     * @param currPage
     *            Current page
     * @param totalPages
     *            Total of page
     * @param maxLinks
     *            Max link to display
     * @return The string html to display
     */
    public String display(final String uri, final int currPage, final int totalPages, final int maxLinks) {
        this.uri = uri;
        this.currPage = currPage;
        this.totalPages = totalPages;
        this.maxLinks = maxLinks;

        final StringBuilder str = new StringBuilder();

        final boolean lastPage = this.currPage == this.totalPages;
        int pgStart = Math.max(this.currPage - this.maxLinks / 2, 1);
        int pgEnd = pgStart + this.maxLinks;
        if (pgEnd > this.totalPages + 1) {
            final int diff = pgEnd - this.totalPages;
            pgStart -= diff - 1;
            if (pgStart < 1) {
                pgStart = 1;
            }
            pgEnd = this.totalPages + 1;
        }

        str.append("<ul class=\"paginatorList\">");

        if (this.currPage > 1) {
            str.append(this.constructLink(this.currPage - 1, "Previous", "paginatorPrev"));
        }

        for (int i = pgStart; i < pgEnd; i++) {
            if (i == this.currPage) {
                str.append("<li class=\"paginatorCurr" + (lastPage && i == this.totalPages ? " paginatorLast" : "")
                        + "\">" + this.currPage + "</li>");
            } else {
                str.append(this.constructLink(i));
            }
        }

        if (!lastPage) {
            str.append(this.constructLink(this.currPage + 1, "Next", "paginatorNext paginatorLast"));
        }

        str.append("</ul>");

        return str.toString();
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
