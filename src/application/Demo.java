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

public class Demo {
    public static void main(String[] args) {
        try (Connection conn = DB.getConnection()){
            SellerDao dao = DaoFactory.createSellerDao(conn);
            Seller seller1 = dao.findById(3);
            System.out.println(seller1);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}