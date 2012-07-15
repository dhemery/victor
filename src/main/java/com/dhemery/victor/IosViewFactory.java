package com.dhemery.victor;

public interface IosViewFactory {
    /**
     * Create a view driver.
     * @param query a query that identifies a set of views.
     * @return a view driver that represents the views identified by the query.
     */
    IosView view(By query);
}
