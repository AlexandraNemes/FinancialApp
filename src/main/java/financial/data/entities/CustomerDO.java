package financial.data.entities;

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

}
