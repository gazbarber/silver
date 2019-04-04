package test;

public class CollatedSilverOrder {

    private Double weight;
    private Integer cashValue;
    private SilverOrder.OrderType orderType;

    /*
        Class that contains the collated weights for a distinct cash value of silver.
        A summary object, compared to SilverOrder object does not have userID and individual order details
     */

    public CollatedSilverOrder(Double weight, Integer cashValue, SilverOrder.OrderType orderType) {
        this.weight = weight;
        this.cashValue = cashValue;
        this.orderType = orderType;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getCashValue() {
        return cashValue;
    }

    public SilverOrder.OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollatedSilverOrder that = (CollatedSilverOrder) o;

        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (cashValue != null ? !cashValue.equals(that.cashValue) : that.cashValue != null) return false;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        int result = weight != null ? weight.hashCode() : 0;
        result = 31 * result + (cashValue != null ? cashValue.hashCode() : 0);
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return weight +" kg for £" + cashValue ;
    }
}
