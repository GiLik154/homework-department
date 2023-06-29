package com.sharetreats.domain.service;

import com.sharetreats.domain.domain.Department;
import com.sharetreats.domain.domain.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentPrinterTest {
    private final DepartmentRepository repository = DepartmentRepository.getInstance();

    private final DepartmentPrinter printer = DepartmentService.getInstance();

    @BeforeEach
    public void init() {
        repository.deleteAll();
    }

    @Test
    void 부서_모두_출력_정상작동_1() {
        repository.save(new Department("PARENT", 100, null));

        String print = printer.printByAll();

        assertEquals("PARENT,100", print);
    }

    @Test
    void 부서_모두_출력_정상작동_2() {
        repository.save(new Department("PARENT", 100, null));
        repository.save(new Department("CHILD", 200, null));

        String print = printer.printByAll();

        assertEquals("PARENT,100" + "\n" + "CHILD,200", print);
    }

    @Test
    void 부서_모두_출력_정상작동_3() {
        String print = printer.printByAll();

        assertEquals("", print);
    }

    @Test
    void 최상위부서_출력_정상작동() {
        repository.save(new Department("PARENT", 100, null));

        String print = printer.printByTopAndAllEmployees();

        assertEquals("PARENT,100", print);
    }


    @Test
    void 최상위부서_출력_정상작동_1() {
        Department child = repository.save(new Department("CHILD", 100, null));
        Department parent = repository.save(new Department("PARENT", 100, null));

        child.applyParent(parent);

        String print = printer.printByTopAndAllEmployees();

        assertEquals("PARENT,200", print);
    }

    @Test
    void 최상위부서_출력_정상작동_2() {
        Department child = repository.save(new Department("CHILD", 100, null));
        Department parent1 = repository.save(new Department("PARENTI", 100, null));
        Department parent2 = repository.save(new Department("PARENTII", 100, null));

        child.applyParent(parent1);

        String print = printer.printByTopAndAllEmployees();

        assertEquals("PARENTI,200" + "\n" + "PARENTII,100", print);
    }
}