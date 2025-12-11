package model.dao.impl;

import model.dao.SellerDao;

public class DaoFactory {
    private DaoFactory() {}

    public static SellerDao createSellerDao() {
        return new SellerDaoImpl();
    }
}
