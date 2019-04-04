package test;

public class SilverOrder {
    private String userId; //assume its just a string
    private double weight; //could be in grams and use int instead
    private int cashValue; //assumed whole pounds, e.g. same currency and int
    private OrderType orderType;

    //        - user id
    //        - order quantity (e.g.: 3.5 kg)
    //        - price per kg (e.g.: £303)
    //        - order type: BUY or SELL
    public SilverOrder(final String userId, final double weight, final int cashValue, final OrderType orderType) {
        if(weight<=0 || cashValue<=0 || null == userId || userId.trim().isEmpty() ||null == orderType ){
            throw new IllegalArgumentException();
        }

        this.userId = userId;
        this.weight = weight;
        this.cashValue = cashValue;
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public double getWeight() {
        return weight;
    }

    public int getCashValue() {
        return cashValue;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public String toString() {
        // SELL: 3.5 kg for £306 [user1]
        return orderType +": " + weight + " kg for £" + cashValue + " ["+userId+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SilverOrder that = (SilverOrder) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (cashValue != that.cashValue) return false;
        if (!userId.equals(that.userId)) return false;
        return orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cashValue;
        result = 31 * result + orderType.hashCode();
        return result;
    }

    public enum OrderType{
        SELL, BUY
    }
}
