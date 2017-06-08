package financial.data.dao;

import financial.data.entities.CustomerDO;

public class CustomerDAO implements ICustomerDAO {

     private static final String SQL_INSERT = "INSERT INTO customers (customer_number, customer_name) VALUES (?, ?)";

    @Override
    public void create(CustomerDO customer) {
	// TODO Auto-generated method stub
    }

}
