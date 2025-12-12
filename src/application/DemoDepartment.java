package application;

import db.connection.DB;
import db.exceptions.DbException;
import model.dao.DepartmentDao;
import model.dao.impl.DaoFactory;
import model.entities.Department;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DemoDepartment {
    public static void main(String[] args) {
        try (Connection conn = DB.getConnection()) {
            DepartmentDao dao = DaoFactory.createDepartmentDao(conn);
            Department dept = dao.findById(4);
            System.out.println("findById");
            System.out.println(dept);

            List<Department> list = dao.findAll();
            System.out.println("\nfindAll");
            for (Department department : list) {
                System.out.println(department);
            }

            System.out.println("\ndeleteById");
            boolean res = dao.deleteById(6);
            if (res) {
                System.out.println("Registro excluído.");
            } else {
                System.out.println("Nenhum registro encontrado.");
            }

            System.out.println("\nupdate");
            dept.setName("Clothes");
            res = dao.update(dept);
            if (res) {
                System.out.println("Registro atualizado.");
            } else {
                System.out.println("Nenhum registro encontrado.");
            }

            System.out.println("\ninsert");
            Department newDept = new Department(null, "Books");
            dao.insert(newDept);
            if (newDept.getId() != null) {
                System.out.println("Inserido com sucesso! Id: " + newDept.getId());
            } else {
                System.out.println("Erro ao incluir registro.");
            }
        } catch (DbException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}