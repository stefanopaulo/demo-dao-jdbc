package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department department);
    boolean update(Department department);
    boolean deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
