package com.sharetreats.domain.domain;

import com.sharetreats.exception.DuplicateDepartmentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void 최상의_생성_테스트() {
        Department department = new Department("ABC", 10, null);

        assertEquals("ABC", department.getName());
        assertEquals(10, department.getNumberOfEmployees());
        assertTrue(department.getChildren().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ㅁ", "1", "@"})
    void 영어가_아닌_특수문자로_생성시_실패한다(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Department(name, 1, null));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1001})
    void 직원수는_0명_이상_1000명_이하가_아니면_실패한다(int numberOfEmployees) {
        assertThrows(IllegalArgumentException.class, () -> new Department("ABC", numberOfEmployees, null));
    }

    @Test
    void 최상의_그룹과_바로_하위그룹이_있을때는_최상의와_하위그룹_전체의_직원_수를_카운팅을_한다() {
        Department child1 = new Department("CHILDI", 10, null);
        Department child2 = new Department("CHILDII", 10, null);
        Department child3 = new Department("CHILDIII", 10, null);

        Department department = new Department("ABC", 100, Arrays.asList(child1, child2, child3));

        assertEquals(130, department.getTotalOfEmployees());
    }

    @Test
    void 최상의_그룹과_바로_하위그룹이_있을때는_최상의와_하위그룹_전체의_직원_수를_카운팅을_한다2() {
        Department child1 = new Department("CHILDI", 10, null);
        Department child2 = new Department("CHILDII", 10, null);
        Department child3 = new Department("CHILDIII", 10, Arrays.asList(child1, child2));

        Department department = new Department("ABC", 100, List.of(child3));

        assertEquals(130, department.getTotalOfEmployees());
    }

    @Test
    void 최상의_그룹만_있을때는_최상의_직원_수만_카운팅을_한다() {
        Department department = new Department("ABC", 100, null);

        assertEquals(100, department.getTotalOfEmployees());
    }

    @Test
    void 자식_부모가_중복되면_안됨() {
        Department duplicationDepartment = new Department("PARENT", 10, null);
        Department child1 = new Department("CHILDI", 10, null);
        Department child2 = new Department("CHILDII", 10, null);

        Department department = new Department("ABC", 10, Arrays.asList(duplicationDepartment, child1, child2));

        assertThrows(DuplicateDepartmentException.class, () -> department.applyParent(duplicationDepartment));
    }

    @Test
    void 자식이_중복되면_안됨() {
        Department child = new Department("CHILD", 10, null);
        Department parent = new Department("PARENT", 10, List.of(child));

        assertThrows(DuplicateDepartmentException.class, () -> child.applyParent(parent));
    }

    @Test
    void 자식_삭제_정상작동() {
        Department child = new Department("CHILD", 10, null);
        Department parent = new Department("PARENT", 10, null);

        child.applyParent(parent);

        child.initParent();

        assertNull(child.getParent());
        assertFalse(parent.getChildren().stream()
                .anyMatch(department -> department.equals(child)));
    }
}