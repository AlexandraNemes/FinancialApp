package financial.file.parser.common.writer;

import java.util.List;

import financial.data.exception.FinancialDBException;
import financial.file.parser.common.ITransactionDTO;

/**
 * Interface used to define common behavior for the database writers of generic
 * transactions.
 * 
 * @author Alexandra Nemes
 *
 * @param <T extends TransactionDTO>
 */
public interface IDBWriter<T extends ITransactionDTO> extends IWriter<ITransactionDTO> {

    public void writeToDB(List<T> content) throws FinancialDBException;
}
