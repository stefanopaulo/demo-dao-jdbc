package model.dao.impl;

import db.exceptions.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    private Connection conn;

    DepartmentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        String sql = """
                INSERT INTO department (Name)
                VALUES (?);
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, department.getName());

            if (stmt.executeUpdate() > 0) {
                try (ResultSet res = stmt.getGeneratedKeys()) {
                    if (res.next()) {
                        department.setId(res.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public boolean update(Department department) {
        String sql = """
                UPDATE department
                SET Name = ?
                WHERE id = ?
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT department.* FROM department WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    return instantiateDepartment(res);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT department.* FROM department";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet res = stmt.executeQuery()) {
                List<Department> list = new ArrayList<>();
                while (res.next()) {
                    Department dept = instantiateDepartment(res);
                    list.add(dept);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static Department instantiateDepartment(ResultSet res) throws SQLException {
        Department dept = new Department();
        dept.setId(res.getInt("Id"));
        dept.setName(res.getString("Name"));
        return dept;
    }
}
