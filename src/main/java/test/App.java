package test;

public class App 
{
    //Example to demo the library, notice println's for console display only
    public static void main( String[] args )
    {
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        //add 2 buy and 2 sell orders, including £303 in both SELL and BUY
        SilverOrder sellOrder1 = new SilverOrder("user1", 3.5, 303, SilverOrder.OrderType.SELL);
        System.out.println("ADD "+sellOrder1);
        SilverOrder sellOrder2 = new SilverOrder("user2", 0.5, 307, SilverOrder.OrderType.SELL);
        System.out.println("ADD "+sellOrder2);
        SilverOrder buyOrder3 = new SilverOrder("user3", 2.1, 303, SilverOrder.OrderType.BUY);
        System.out.println("ADD "+buyOrder3);
        SilverOrder buyOrder4 = new SilverOrder("user4", 4.8, 289, SilverOrder.OrderType.BUY);
        System.out.println("ADD "+buyOrder4);

        silverOrderSystem.submitOrders(sellOrder1, buyOrder3, sellOrder2, buyOrder4);
        prettyPrintUpdatedLiveOrderBoard(silverOrderSystem);

        //add new SELL and BUY (increase SELL £307, BUY £289)
        SilverOrder sellOrder5 = new SilverOrder("user2", 1.7, 307, SilverOrder.OrderType.SELL);
        System.out.println("ADD "+sellOrder5);
        SilverOrder buyOrder6 = new SilverOrder("user4", 2.3, 289, SilverOrder.OrderType.BUY);
        System.out.println("ADD "+buyOrder6);

        silverOrderSystem.submitOrders(sellOrder5, buyOrder6);
        prettyPrintUpdatedLiveOrderBoard(silverOrderSystem);

        //remove 0.5kg, £307 (reduces SELL £307 amount). Remove 2.1kg, £303 (removes all BUY £303)
        silverOrderSystem.removeOrder(sellOrder5);
        System.out.println("REMOVE "+sellOrder5);
        silverOrderSystem.removeOrder(buyOrder3);
        System.out.println("REMOVE "+buyOrder3);

        prettyPrintUpdatedLiveOrderBoard(silverOrderSystem);
    }

    private static void prettyPrintUpdatedLiveOrderBoard(SilverOrderSystem silverOrderSystem) {
        System.out.println("\nLive Order Board");
        System.out.println("-----------------------");
        System.out.println("BUY ORDERS:");
        silverOrderSystem.retrieveCollatedBuyOrders().forEach(System.out::println);
        System.out.println("SELL ORDERS:");
        silverOrderSystem.retrieveCollatedSellOrders().forEach(System.out::println);
        System.out.println("-----------------------\n");
    }
}
