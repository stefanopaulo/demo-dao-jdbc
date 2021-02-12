package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Main2 {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== Test 1: department insert ===");
		
		Department dep = new Department(null, "Music");
		
		// departmentDao.insert(dep);
		
		// System.out.println("Inserted! New id = " + dep.getId());

	}

}
