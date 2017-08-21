package financial.data.dao;

import financial.data.entities.CustomerDO;
import financial.data.exception.FinancialDBException;

/**
 * Interface used to define common behavior for CustomerDAO objects.
 * 
 * @author Alexandra Nemes
 *
 */
public interface ICustomerDAO {

    public void create(CustomerDO customer) throws FinancialDBException;
    public CustomerDO getCustomerByNumber(String customerNumber) throws FinancialDBException;
}
