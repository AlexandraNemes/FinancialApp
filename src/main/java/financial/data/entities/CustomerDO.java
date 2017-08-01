package financial.data.entities;

/**
 * Class used to create customer objects that will be written to the database.
 * 
 * @author Alexandra Nemes
 *
 */
public class CustomerDO {

    private Long id;
    private String customerNumber;
    private String customerName;

    public CustomerDO(String customerNumber, String customerName) {
	this.customerNumber = customerNumber;
	this.customerName = customerName;
    }

    public CustomerDO(Long id, String customerNumber, String customerName) {
	this.id = id;
	this.customerNumber = customerNumber;
	this.customerName = customerName;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getCustomerNumber() {
	return customerNumber;
    }

    public String getCustomerName() {
	return customerName;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("CustomerDO [id=");
	builder.append(id);
	builder.append(", customerNumber=");
	builder.append(customerNumber);
	builder.append(", customerName=");
	builder.append(customerName);
	builder.append("]");
	return builder.toString();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CustomerDO other = (CustomerDO) obj;
	if (customerNumber == null) {
	    if (other.customerNumber != null)
		return false;
	} else if (!customerNumber.equals(other.customerNumber))
	    return false;
	return true;
    }

    
}
