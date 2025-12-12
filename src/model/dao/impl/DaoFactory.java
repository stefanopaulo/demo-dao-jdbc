package model.dao.impl;

import db.connection.DB;
import model.dao.DepartmentDao;
import model.dao.SellerDao;

import java.sql.Connection;

public class DaoFactory {
    private DaoFactory() {}

    public static SellerDao createSellerDao(Connection conn) {
        return new SellerDaoImpl(conn);
    }

    public static DepartmentDao createDepartmentDao(Connection conn) {
        return new DepartmentDaoImpl(conn);
    }
}
