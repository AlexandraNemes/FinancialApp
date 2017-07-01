package financial.data.dao;

import financial.data.entities.CustomerDO;
import financial.data.exception.FinancialDBException;

public interface ICustomerDAO {

    public void create(CustomerDO customer) throws FinancialDBException;

}
