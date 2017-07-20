package financial.data.dao;

import financial.data.entities.TransactionDO;
import financial.data.exception.FinancialDBException;

/**
 * Interface used to define common behavior for TransactionDAO objects.
 * 
 * @author Alexandra Nemes
 *
 */
public interface ITransactionDAO {

    public void create(TransactionDO transaction) throws FinancialDBException;
}
