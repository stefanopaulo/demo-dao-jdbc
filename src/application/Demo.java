package application;

import db.connection.DB;
import db.exceptions.DbException;
import model.dao.SellerDao;
import model.dao.impl.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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

            System.out.println("\n=== Teste 4: seller insert ===");
            Seller seller2 = new Seller(null, "Greg", "greg@gmail.com", LocalDate.parse("1991-02-12"), 4000.0, new Department(3, null));
            dao.insert(seller2);
            System.out.println("Vendedor inserido com sucesso! Id: " + seller2.getId());

            System.out.println("\n=== Teste 5: seller update ===");
            seller2 = dao.findById(1);
            seller2.setName("Martha Waine");
            dao.update(seller2);
            System.out.println("Update concluído com sucesso!");

            System.out.println("\n=== Teste 6: seller delete ===");
            dao.deleteById(16);
            System.out.println("Delete concluído sem problemas.");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}