package com.enocholumide.services.schools;

import com.enocholumide.domain.school.Department;
import com.enocholumide.repositories.DepartmentRepository;
import com.enocholumide.repositories.ProgramsRepository;
import com.enocholumide.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolsServiceImpl implements SchoolsService{

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ProgramsRepository programsRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public ResponseEntity getSchools() {
        return ResponseEntity.ok(schoolRepository.findAll());
    }

    @Override
    public ResponseEntity getDepartments() {

        List<Department> departments = departmentRepository.findAll();

        departments.stream()
                .forEach(department ->
                        this.programsRepository.getLevelsByDepartment(department).stream()
                                .forEach(program ->
                                        department.getProgramsOffered().add(program.getLevel().toString() + "&" + program.getSemesters())
                                )
                );

        return ResponseEntity.ok().body(departments);
    }

    @Override
    public ResponseEntity getPrograms() {
        return ResponseEntity.ok().body(programsRepository.findAll());
    }
}
