package com.dhemery.victor.view;

import com.dhemery.victor.By;

import java.util.List;

public interface IosViewAgent {
    /**
     * Instructs a set of views to perform an operation.
     * @param query identifies the views that will perform the operation.
     * @param operation the operation to perform.
     * @return a response that lists the results returned by each view that performed the operation.
     */
    List<String> perform(By query, Operation operation);
}
