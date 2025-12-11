package application;

import db.connection.DB;
import db.exceptions.DbException;
import model.dao.SellerDao;
import model.dao.impl.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try (Connection conn = DB.getConnection()) {
            SellerDao dao = DaoFactory.createSellerDao(conn);
            Seller seller1 = dao.findById(3);
            System.out.println("=== Teste 1: seller findById ===");
            System.out.println(seller1);

            List<Seller> list = dao.findByDepartment(new Department(2, null));
            System.out.println("\n=== Teste 2: seller findByDepartment ===");
            for (Seller seller : list) {
                System.out.println(seller);
            }

            List<Seller> listAll = dao.findAll();
            System.out.println("\n=== Teste 3: seller findByAll ===");
            for (Seller seller : listAll) {
                System.out.println(seller);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}