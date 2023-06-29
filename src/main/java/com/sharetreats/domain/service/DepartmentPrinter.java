package com.sharetreats.domain.service;

public interface DepartmentPrinter {
    /**
     * 모든 부서를 출력하는 인터페이스
     *
     * @return 저장된 모든 부서
     */
    String printByAll();

    /**
     * 모든 상위 부서를 출력하는 인터페이스
     * 구성원의 수는 하위 부서의 구성원 수의 모든 합.
     *
     * @return 상위 부서의 이름과 하위부서를 모두 포한함 구성원의 수
     */
    String printByTopAndAllEmployees();
}
