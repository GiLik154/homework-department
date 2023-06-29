package com.sharetreats.domain.service;

import com.sharetreats.domain.domain.Department;
import com.sharetreats.domain.domain.DepartmentRepository;
import com.sharetreats.exception.NotFoundDepartmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentHierarchyManagerTest {
    private final DepartmentRepository repository = DepartmentRepository.getInstance();

    private final DepartmentHierarchyManager manager = DepartmentService.getInstance();

    @BeforeEach
    public void init() {
        repository.deleteAll();
    }

    @Test
    void 부서_구성_정상_작동() {
        Department parent = repository.save(new Department("PARENT", 100, null));
        Department child = repository.save(new Department("CHILD", 100, null));

        manager.register("PARENT", "CHILD");

        assertEquals(child, parent.getChildren().get(0));
        assertEquals(200, parent.getTotalOfEmployees());
        assertEquals(parent, child.getParent());
    }

    @Test
    void 상위_부서가_없으면_등록_안됨() {
        repository.save(new Department("CHILD", 100, null));

        assertThrows(NotFoundDepartmentException.class, () ->
                manager.register("PARENT", "CHILD"));
    }

    @Test
    void 하위_부서가_없으면_등록_안됨() {
        repository.save(new Department("PARENT", 100, null));

        assertThrows(NotFoundDepartmentException.class, () ->
                manager.register("PARENT", "CHILD"));
    }

    @Test
    void 최상위_부서로_초기화() {
        Department parent = repository.save(new Department("PARENT", 100, null));
        Department child = repository.save(new Department("CHILD", 100, null));

        child.applyParent(parent);

        manager.register("*", "CHILD");

        assertNull(child.getParent());
        assertFalse(parent.getChildren().stream()
                .anyMatch(department -> department.equals(child)));
    }
}