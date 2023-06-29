package com.sharetreats.factory;

import com.sharetreats.domain.service.DepartmentCreator;
import com.sharetreats.domain.service.DepartmentHierarchyManager;
import com.sharetreats.domain.service.DepartmentPrinter;
import com.sharetreats.domain.service.DepartmentService;

public class Factory {
    private static final Factory factory = new Factory();
    private final DepartmentCreator creator;
    private final DepartmentHierarchyManager manager;
    private final DepartmentPrinter printer;

    private Factory() {
        DepartmentService service = DepartmentService.getInstance();
        creator = service;
        manager = service;
        printer = service;
    }

    public static Factory getInstance() {
        return factory;
    }

    public DepartmentCreator getCreator() {
        return creator;
    }

    public DepartmentHierarchyManager getHierarchyManager() {
        return manager;
    }

    public DepartmentPrinter getPrinter() {
        return printer;
    }
}