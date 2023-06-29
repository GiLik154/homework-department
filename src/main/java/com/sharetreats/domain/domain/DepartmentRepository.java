package com.sharetreats.domain.domain;

import com.sharetreats.exception.DuplicateDepartmentException;
import com.sharetreats.exception.NotFoundDepartmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentRepository {
    private static final DepartmentRepository repository = new DepartmentRepository();

    private List<Department> departments = new ArrayList<>();

    private DepartmentRepository() {
    }

    public static DepartmentRepository getInstance() {
        return repository;
    }

    /**
     * 부서를 저장하는 메소드
     *
     * @param newDepartment 새로운 부서
     * @return 저장된 부서
     */
    public Department save(Department newDepartment) {
        if (isDuplication(newDepartment)) throw new DuplicateDepartmentException("duplicate new department.");

        departments.add(newDepartment);

        return newDepartment;
    }

    private boolean isDuplication(Department newDepartment) {
        return departments.stream().anyMatch(department -> department.getName().equals(newDepartment.getName()));
    }

    /**
     * 모든 부서를 검색하는 메소드
     *
     * @return 저장된 모든 부서
     */
    public List<Department> findByAll() {
        return departments;
    }

    /**
     * 이름으로 부서를 검색하는 메소드
     *
     * @param name 검색할 부서의 이름
     * @return 검색된 부서
     */
    public Department findByName(String name) {
        return departments.stream()
                .filter(department -> department.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundDepartmentException("Not found department"));
    }

    /**
     * 최상위 부서만 출력하는 메소드
     *
     * @return 최상위 부서
     */
    public List<Department> findByTopDepartments() {
        return departments.stream()
                .filter(Department::isTop)
                .collect(Collectors.toList());
    }

    /**
     * 부서 구성도를 초기화하는 메소드
     */
    public void deleteAll() {
        departments = new ArrayList<>();
    }
}