package com.sharetreats.domain.service;

import com.sharetreats.domain.domain.Department;
import com.sharetreats.domain.domain.DepartmentRepository;
import com.sharetreats.exception.DuplicateDepartmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentCreatorTest {
    private final DepartmentRepository repository = DepartmentRepository.getInstance();

    private final DepartmentCreator departmentCreator = DepartmentService.getInstance();

    @BeforeEach
    public void init() {
        repository.deleteAll();
    }

    @Test
    void 그룹_생성_정상_작동() {
        departmentCreator.crate("TESTNAME", 100);

        Department department = repository.findByName("TESTNAME");
        assertEquals("TESTNAME", department.getName());
        assertEquals(100, department.getNumberOfEmployees());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ㅁ", "1", "@"})
    void 영어가_아닌_특수문자로_생성시_실패한다(String name) {
        assertThrows(IllegalArgumentException.class, () ->
                departmentCreator.crate(name, 1));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1001})
    void 직원수는_0명_이상_1000명_이하가_아니면_실패한다(int numberOfEmployees) {
        assertThrows(IllegalArgumentException.class, () ->
                departmentCreator.crate("ABC", numberOfEmployees));
    }

    @Test
    void 그룹은_중복되어_생성될_수_없다() {
        departmentCreator.crate("PARENT", 100);

        assertThrows(DuplicateDepartmentException.class, () ->
                departmentCreator.crate("PARENT", 100));

        Department department = repository.findByName("PARENT");
        assertEquals("PARENT", department.getName());
        assertEquals(100, department.getNumberOfEmployees());
    }
}