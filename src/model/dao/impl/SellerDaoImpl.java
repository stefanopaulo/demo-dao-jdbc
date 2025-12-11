package model.dao.impl;

import db.connection.DB;
import db.exceptions.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private Connection conn;
    SellerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

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
        try (
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setInt(1, id);
            try (ResultSet result = stmt.executeQuery()){
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
        return List.of();
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
