package financial.file.parser.tx.writer;

import java.util.List;

import financial.file.parser.common.writer.ITransactionDBWriter;
import financial.file.parser.tx.common.TXTransactionDTO;

/**
 * Class used to create the output to be written in the database for TX files.
 * 
 * @author Alexandra Nemes
 *
 */
public class TXDBWriter implements ITransactionDBWriter<TXTransactionDTO> {

    @Override
    public void writeToDB(List<TXTransactionDTO> transactionList) {
    }

}
