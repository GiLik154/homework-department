package com.sharetreats.domain.service;

import com.sharetreats.domain.domain.Department;
import com.sharetreats.domain.domain.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService implements DepartmentCreator, DepartmentHierarchyManager, DepartmentPrinter {
    private static final DepartmentService service = new DepartmentService();

    private final DepartmentRepository repository = DepartmentRepository.getInstance();

    private DepartmentService() {
    }

    public static DepartmentService getInstance() {
        return service;
    }

    @Override
    public void crate(String name, int numberOfEmployees) {
        Department department = new Department(name, numberOfEmployees, null);

        repository.save(department);
    }

    @Override
    public void register(String parentName, String childName) {
        if (parentName.equals("*")) {
            initParent(childName);

            return;
        }

        Department parent = repository.findByName(parentName);
        Department child = repository.findByName(childName);

        child.applyParent(parent);
    }

    private void initParent(String name) {
        Department department = repository.findByName(name);

        department.initParent();
    }

    @Override
    public String printByAll() {
        List<Department> departments = repository.findByAll();

        return departments.stream()
                .map(department -> department.getName() + "," + department.getNumberOfEmployees())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String printByTopAndAllEmployees() {
        List<Department> departments = repository.findByTopDepartments();

        return departments.stream()
                .map(department -> department.getName() + "," + department.getTotalOfEmployees())
                .collect(Collectors.joining("\n"));
    }
}
