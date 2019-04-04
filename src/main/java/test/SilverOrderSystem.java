package test;

import java.util.*;
import java.util.stream.Collectors;

public class SilverOrderSystem {
    /*
    Class contains a list of all BUY and SELL silver orders.
    Allow the adding and removing of orders
    Provides summary of all or just BUY/SELL orders
     */
    private final List<SilverOrder> orderStore = new ArrayList<>();

    public void submitOrder(final SilverOrder newOrder) {
        orderStore.add(newOrder);
    }

    public void submitOrders(final SilverOrder ... newOrders) {
        orderStore.addAll(Arrays.asList(newOrders));
    }

    public void removeOrder(final SilverOrder removeOrder) {
        orderStore.remove(removeOrder);
    }

    public List<CollatedSilverOrder> retrieveCollatedBuyOrders() {
        List<CollatedSilverOrder> orderedCollatedBuyOrders = new ArrayList<>();
        Map<Integer, Double> mergedWeights =
                orderStore.stream()
                .filter(o1 -> o1.getOrderType().equals(SilverOrder.OrderType.BUY))
                //Sum all weight for the same cash value
                .collect(Collectors.groupingBy(SilverOrder::getCashValue, Collectors.summingDouble(SilverOrder::getWeight)));

        mergedWeights.forEach(
                (value, weight) ->
                        orderedCollatedBuyOrders.add(new CollatedSilverOrder(weight,value, SilverOrder.OrderType.BUY)));

        //order by cash value descending
        orderedCollatedBuyOrders.sort(Comparator.comparing(CollatedSilverOrder::getCashValue).reversed());
        return orderedCollatedBuyOrders;
    }

    public List<CollatedSilverOrder> retrieveCollatedSellOrders() {
        List<CollatedSilverOrder> orderedCollatedSellOrders = new ArrayList<>();
        Map<Integer, Double> mergedWeights =
                orderStore.stream()
                .filter(o1 -> o1.getOrderType().equals(SilverOrder.OrderType.SELL))
                //Sum all weight for the same cash value
                .collect(Collectors.groupingBy(SilverOrder::getCashValue, Collectors.summingDouble(SilverOrder::getWeight)));

        mergedWeights.forEach(
                (value, weight) ->
                        orderedCollatedSellOrders.add(new CollatedSilverOrder(weight,value, SilverOrder.OrderType.SELL)));

        //order by cash value ascending
        orderedCollatedSellOrders.sort(Comparator.comparing(CollatedSilverOrder::getCashValue));

        return orderedCollatedSellOrders;
    }

    public List<CollatedSilverOrder> retrieveCollatedAllOrders() {
        List<CollatedSilverOrder> silverOrders = retrieveCollatedBuyOrders();
        silverOrders.addAll(retrieveCollatedSellOrders());
        return silverOrders;
    }
}
