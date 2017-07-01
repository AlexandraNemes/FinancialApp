package financial.data.dao;

import financial.data.entities.TransactionDO;
import financial.data.exception.FinancialDBException;

public interface ITransactionDAO {

    public void create(TransactionDO transaction) throws FinancialDBException;
}
