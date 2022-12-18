package IMS.Model;

/**
 * This is the InHouse class. Subclass of the abstract Part class.
 */
public class OutSourced extends Part{

    private String companyName;

    /**
     * Full constructor for creating Outsourced parts
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for company name
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }

    /**
     * Getter for company name
     * @return the company name
     */
    public String getCompanyName() {

        return companyName;
    }
}
