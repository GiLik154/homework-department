package com.sharetreats.domain.domain;

import com.sharetreats.exception.DuplicateDepartmentException;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class Department {
    private final String name;

    private final int numberOfEmployees;

    private Department parent;

    private List<Department> children;

    /**
     * Department 생성자
     *
     * @param name              회사의 이름
     * @param numberOfEmployees 회사 구성원의 수
     * @param departments       하위 부서들
     */
    public Department(String name, int numberOfEmployees, List<Department> departments) {
        this(name, numberOfEmployees);
        this.children = departments == null ? new ArrayList<>() : departments;
    }

    private Department(String name, int numberOfEmployees) {
        if (!validate(name)) throw new IllegalArgumentException();
        if (!validate(numberOfEmployees)) throw new IllegalArgumentException();

        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
    }

    /**
     * 이름을 검증하는 메소드
     *
     * @param name 검증할 메소드
     * @return 사용 가능 여부
     */
    private boolean validate(String name) {
        byte[] bytes = name.getBytes(StandardCharsets.UTF_8);

        for (byte b : bytes) {
            if (!(b >= 'A' && b <= 'Z')) {
                return false;
            }
        }

        return true;
    }

    /**
     * 구성원 수를 검증하는 메소드
     *
     * @param numberOfEmployees 검증할 구성원 수
     * @return 사용 가능 여부
     */
    private boolean validate(int numberOfEmployees) {
        return numberOfEmployees >= 0 && numberOfEmployees <= 1000;
    }

    /**
     * 부서가 최상위 부서인지 확인하는 메소드
     *
     * @return 부서의 최상위 여부
     */
    public boolean isTop() {
        return parent == null;
    }

    /**
     * 상위부서를 설정해주는 메소드
     * Exception 으로 막는 경우도 있으나,
     * 여기에서는 아예 구조상으로 2개의 부모를 상속하지 못하게 막음.
     *
     * @param newParent 새로운 상위 부서
     */
    public void applyParent(Department newParent) {
        validate(newParent);

        if (this.parent != null) {
            this.parent.deleteChild(this);
        }

        this.parent = newParent;
        this.parent.addDepartment(this);
    }

    /**
     * 하위 부서츨 추가하는 메소드
     *
     * @param newSonDepartment 추가할 하위 부서
     */
    public void addDepartment(Department newSonDepartment) {
        validate(newSonDepartment);

        this.children.add(newSonDepartment);
    }

    private void validate(Department department) {
        if (isDuplication(department)) throw new DuplicateDepartmentException("Duplicate child and parent");
    }

    private boolean isDuplication(Department newParent) {
        return children.stream().anyMatch(department -> department.equals(newParent));
    }

    /**
     * 상위 부서를 초기화하는 메소드
     */
    public void initParent() {
        if (this.parent == null) {
            throw new IllegalArgumentException();
        }

        this.parent.deleteChild(this);
        this.parent = null;
    }

    private void deleteChild(Department child) {
        children.remove(child);
    }

    /**
     * 하위 부서의 인원을 모두 더하는 메소드
     *
     * @return 하위 부서의 모든 인원을 합친 수
     */
    public int getTotalOfEmployees() {
        if (children.isEmpty()) {
            return numberOfEmployees;
        }

        return children.stream()
                .flatMapToInt(department -> IntStream.of(department.getTotalOfEmployees()))
                .sum() + numberOfEmployees;
    }
}
