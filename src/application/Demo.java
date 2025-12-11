package application;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        System.out.println(department);

        Seller seller = new Seller(1, "Alex Gray", "alex@gmail.com", LocalDate.parse("1985-04-22"), 4000.0, department);
        System.out.println(seller);
    }
}