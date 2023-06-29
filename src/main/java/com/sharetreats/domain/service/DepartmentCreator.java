package com.sharetreats.domain.service;

import com.sharetreats.exception.DuplicateDepartmentException;

public interface DepartmentCreator {
    /**
     * 부서를 생성하는 인터페이스.
     * 부서의 이름과, 인원수를 입력받아 생성한다.
     *
     * @param name              새로운 부서의 이름
     * @param numberOfEmployees 부서의 구성원 수
     * @throws DuplicateDepartmentException 이름이 중복될 경우 발생하는 익셉션.
     */
    void crate(String name, int numberOfEmployees);
}
