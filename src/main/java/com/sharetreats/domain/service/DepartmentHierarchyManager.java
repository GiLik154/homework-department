package com.sharetreats.domain.service;

import com.sharetreats.exception.DuplicateDepartmentException;

public interface DepartmentHierarchyManager {
    /**
     * 부서의 구성을 정의하는 인터페이스
     * 상위 부서가 될 부서의 이름과
     * 하위 부서가 될 부서의 이름을 받아와서
     * 부서의 구성을 정의한다.
     *
     * @param parentName 상위 부서의 이름
     * @param childName  하위 부서의 이름
     * @throws DuplicateDepartmentException 하위 부서가 겹치는 경우 발생하는 익셉션.
     * @throws IllegalArgumentException     이미 최상위 부서인 부서를 다시 최상위로 설정하려고 하면 발생하는 익셉션.
     */
    void register(String parentName, String childName);
}