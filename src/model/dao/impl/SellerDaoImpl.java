package model.dao.impl;

import db.exceptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoImpl implements SellerDao {
    private Connection conn;

    SellerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        String sql = """
                INSERT INTO seller
                (Name, Email, BirthDate, BaseSalary, DepartmentId)
                VALUES
                (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, Date.valueOf(seller.getBirthDate()));
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDepartment().getId());

            if (stmt.executeUpdate() > 0) {
                try (ResultSet result = stmt.getGeneratedKeys()) {
                    if (result.next()) {
                        int id = result.getInt(1);
                        seller.setId(id);
                    }
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        String sql = """
                SELECT seller.*,department.Name as DepName
                FROM seller INNER JOIN department
                ON seller.DepartmentId = department.Id
                WHERE seller.Id = ?
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Department department = instantiateDepartment(result);
                    return instantiateSeller(result, department);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Seller> findAll() {
        String sql = """
                SELECT seller.*,department.Name as DepName
                FROM seller INNER JOIN department
                ON seller.DepartmentId = department.Id
                ORDER BY Name
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet result = stmt.executeQuery()) {
                List<Seller> list = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();

                while (result.next()) {
                    Department dep = map.get(result.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(result);
                        map.put(result.getInt("DepartmentId"), dep);
                    }

                    Seller seller = instantiateSeller(result, dep);
                    list.add(seller);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        String sql = """
                SELECT seller.*,department.Name as DepName
                FROM seller INNER JOIN department
                ON seller.DepartmentId = department.Id
                WHERE DepartmentId = ?
                ORDER BY Name
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, department.getId());

            try (ResultSet result = stmt.executeQuery()) {
                List<Seller> list = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>();

                while (result.next()) {
                    Department dep = map.get(result.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(result);
                        map.put(result.getInt("DepartmentId"), dep);
                    }

                    Seller seller = instantiateSeller(result, dep);
                    list.add(seller);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static Seller instantiateSeller(ResultSet result, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(result.getInt("Id"));
        seller.setName(result.getString("Name"));
        seller.setEmail(result.getString("Email"));
        seller.setBirthDate(result.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(result.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    private static Department instantiateDepartment(ResultSet result) throws SQLException {
        return new Department(result.getInt("DepartmentId"), result.getString("DepName"));
    }
}
