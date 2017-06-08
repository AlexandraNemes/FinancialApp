package financial.file.parser.common.writer;

import java.util.List;

import financial.file.parser.common.ITransactionDTO;

/**
 * Interface used to define common behavior for the database writers of generic
 * transactions.
 * 
 * @author Alexandra Nemes
 *
 * @param <T extends TransactionDTO>
 */
public interface ITransactionDBWriter<T extends ITransactionDTO> {

    public void writeToDB(List<T> content);
}
