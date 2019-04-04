package test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import test.SilverOrder.OrderType;

import java.util.*;

public class SilverOrderSystemTest
{
    /**
     * Positive assertive Tests for requirements:
     We would like to have a 'Live Order Board' that can provide us with the following functionality:
     1) Register an order. Order must contain these fields:
     - user id
     - order quantity (e.g.: 3.5 kg)
     - price per kg (e.g.: £303)
     - order type: BUY or SELL
     2) Cancel a registered order - this will remove the order from 'Live Order Board'
     3) Get summary information of live orders (see explanation below)
     Imagine we have received the following orders:
     - a) SELL: 3.5 kg for £306 [user1]
     - b) SELL: 1.2 kg for £310 [user2]
     - c) SELL: 1.5 kg for £307 [user3]
     - d) SELL: 2.0 kg for £306 [user4]
     Our ‘Live Order Board’ should provide us the following summary information:
     - 5.5 kg for £306 // order a + order d
     - 1.5 kg for £307 // order c
     - 1.2 kg for £310 // order b
     */

    @Test
    public void shouldSubmitOrder(){

        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        silverOrderSystem.submitOrder(testOrder);

        List<CollatedSilverOrder> collatedOrderList = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList.size(), is(1));
        CollatedSilverOrder actualSilverOrder1 = collatedOrderList.get(0);

        assertThat(actualSilverOrder1.getOrderType(), is(equalTo(testOrder.getOrderType())));
        assertThat(actualSilverOrder1.getCashValue(), is(equalTo(testOrder.getCashValue())));
        assertThat(actualSilverOrder1.getWeight(), is(equalTo(testOrder.getWeight())));
    }

    @Test
    public void shouldRetrieveSellSilverOrdersInPreferredOrder(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder1 = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        SilverOrder testOrder2 = new SilverOrder("user2", 0.5, 307, OrderType.SELL);
        SilverOrder testOrder3 = new SilverOrder("user3", 2.1, 303, OrderType.SELL);
        SilverOrder testOrder4 = new SilverOrder("user4", 4.8, 289, OrderType.SELL);

        silverOrderSystem.submitOrders(testOrder1, testOrder2, testOrder3, testOrder4);

        CollatedSilverOrder expectedSilverOrder1 = new CollatedSilverOrder(4.8, 289, OrderType.SELL);
        CollatedSilverOrder expectedSilverOrder2 = new CollatedSilverOrder(5.6, 303, OrderType.SELL);
        CollatedSilverOrder expectedSilverOrder3 = new CollatedSilverOrder(0.5, 307, OrderType.SELL);


        List<CollatedSilverOrder> collatedOrderList = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList.size(), is(3));

        assertThat(collatedOrderList.get(0), is(equalTo(expectedSilverOrder1)));
        assertThat(collatedOrderList.get(1), is(equalTo(expectedSilverOrder2)));
        assertThat(collatedOrderList.get(2), is(equalTo(expectedSilverOrder3)));
        //BUY should have no values
        assertThat(silverOrderSystem.retrieveCollatedBuyOrders().size(), is(0));
    }

    @Test
    public void shouldRetrieveBuySilverOrdersInPreferredOrder(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder1 = new SilverOrder("user1", 3.5, 303, OrderType.BUY);
        SilverOrder testOrder2 = new SilverOrder("user2", 0.5, 307, OrderType.BUY);
        SilverOrder testOrder3 = new SilverOrder("user3", 2.1, 303, OrderType.BUY);
        SilverOrder testOrder4 = new SilverOrder("user4", 4.8, 289, OrderType.BUY);

        silverOrderSystem.submitOrders(testOrder1, testOrder2, testOrder3, testOrder4);

        CollatedSilverOrder expectedSilverOrder1 = new CollatedSilverOrder(0.5, 307, OrderType.BUY);
        CollatedSilverOrder expectedSilverOrder2 = new CollatedSilverOrder(5.6, 303, OrderType.BUY);
        CollatedSilverOrder expectedSilverOrder3 = new CollatedSilverOrder(4.8, 289, OrderType.BUY);


        List<CollatedSilverOrder> collatedOrderList = silverOrderSystem.retrieveCollatedBuyOrders();
        assertThat(collatedOrderList.size(), is(3));

        assertThat(collatedOrderList.get(0), is(equalTo(expectedSilverOrder1)));
        assertThat(collatedOrderList.get(1), is(equalTo(expectedSilverOrder2)));
        assertThat(collatedOrderList.get(2), is(equalTo(expectedSilverOrder3)));

        //SELL should have no values
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(0));
    }

    @Test
    public void shouldRemoveLiveSellOrderFromSystem(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder1 = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        SilverOrder testOrder2 = new SilverOrder("user2", 0.5, 307, OrderType.SELL);
        SilverOrder testOrder3 = new SilverOrder("user3", 2.1, 303, OrderType.SELL);
        SilverOrder testOrder4 = new SilverOrder("user4", 4.8, 289, OrderType.SELL);

        silverOrderSystem.submitOrders(testOrder1, testOrder2, testOrder3, testOrder4);

        silverOrderSystem.removeOrder(testOrder3);

        CollatedSilverOrder expectedSilverOrder01 = new CollatedSilverOrder(4.8, 289, OrderType.SELL);
        CollatedSilverOrder expectedSilverOrder02 = new CollatedSilverOrder(3.5, 303, OrderType.SELL);
        CollatedSilverOrder expectedSilverOrder03 = new CollatedSilverOrder(0.5, 307, OrderType.SELL);


        List<CollatedSilverOrder> collatedOrderList = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList.size(), is(3));

        assertThat(collatedOrderList.get(0), is(equalTo(expectedSilverOrder01)));
        assertThat(collatedOrderList.get(1), is(equalTo(expectedSilverOrder02)));
        assertThat(collatedOrderList.get(2), is(equalTo(expectedSilverOrder03)));

        //remove silverOrder and confirm item is removed and order maintained
        silverOrderSystem.removeOrder(new SilverOrder("user1", 3.5, 303, OrderType.SELL));
        List<CollatedSilverOrder> collatedOrderList2 = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList2.size(), is(2));
        CollatedSilverOrder actualSilverOrder11 = collatedOrderList2.get(0);
        CollatedSilverOrder actualSilverOrder12 = collatedOrderList2.get(1);

        assertThat(actualSilverOrder11, is(equalTo(expectedSilverOrder01)));
        assertThat(actualSilverOrder12, is(equalTo(expectedSilverOrder03)));
    }


    @Test//the can do it all test, add, remove orders, combine and subtract for collated values and remove collated values
    public void shouldBeAbleToAddAndRemoveToBuyAndSell(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();
        //assert that the system is empty
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(0));
        assertThat(silverOrderSystem.retrieveCollatedBuyOrders().size(), is(0));
        assertThat(silverOrderSystem.retrieveCollatedAllOrders().size(),is(0));

        //add 2 buy and 2 sell, £303 in both SELL and BUY
        SilverOrder sellOrder1 = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        SilverOrder sellOrder2 = new SilverOrder("user2", 0.5, 307, OrderType.SELL);
        SilverOrder buyOrder3 = new SilverOrder("user3", 2.1, 303, OrderType.BUY);
        SilverOrder buyOrder4 = new SilverOrder("user4", 4.8, 289, OrderType.BUY);

        silverOrderSystem.submitOrders(sellOrder1, buyOrder3, sellOrder2, buyOrder4);

        //assert sizes returned collated values
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(2));
        assertThat(silverOrderSystem.retrieveCollatedBuyOrders().size(), is(2));
        assertThat(silverOrderSystem.retrieveCollatedAllOrders().size(),is(4));

        //Assert values are in expected order, descending buy and ascending sells, as not specified supplied Buy first
        CollatedSilverOrder expectedSilverBuyOrder01 = new CollatedSilverOrder(2.1, 303, OrderType.BUY);
        CollatedSilverOrder expectedSilverBuyOrder02 = new CollatedSilverOrder(4.8, 289, OrderType.BUY);
        CollatedSilverOrder expectedSilverSellOrder01 = new CollatedSilverOrder(3.5, 303, OrderType.SELL);
        CollatedSilverOrder expectedSilverSellOrder02 = new CollatedSilverOrder(0.5, 307, OrderType.SELL);

        List<CollatedSilverOrder> collatedSilverOrders = silverOrderSystem.retrieveCollatedAllOrders();

        assertThat(collatedSilverOrders.get(0), is(equalTo(expectedSilverBuyOrder01)));
        assertThat(collatedSilverOrders.get(1), is(equalTo(expectedSilverBuyOrder02)));
        assertThat(collatedSilverOrders.get(2), is(equalTo(expectedSilverSellOrder01)));
        assertThat(collatedSilverOrders.get(3), is(equalTo(expectedSilverSellOrder02)));

        //add new SELL and BUY (increase SELL £307, BUY £289)
        SilverOrder sellOrder5 = new SilverOrder("user2", 1.7, 307, OrderType.SELL);
        SilverOrder buyOrder6 = new SilverOrder("user4", 2.3, 289, OrderType.BUY);

        silverOrderSystem.submitOrders(sellOrder5, buyOrder6);

        CollatedSilverOrder expectedSilverBuyOrder11 = new CollatedSilverOrder(2.1, 303, OrderType.BUY);
        CollatedSilverOrder expectedSilverBuyOrder12 = new CollatedSilverOrder(7.1, 289, OrderType.BUY);
        CollatedSilverOrder expectedSilverSellOrder11 = new CollatedSilverOrder(3.5, 303, OrderType.SELL);
        CollatedSilverOrder expectedSilverSellOrder12 = new CollatedSilverOrder(2.2, 307, OrderType.SELL);

        List<CollatedSilverOrder> collatedSilverOrders2 = silverOrderSystem.retrieveCollatedAllOrders();

        assertThat(collatedSilverOrders2.get(0), is(equalTo(expectedSilverBuyOrder11)));
        assertThat(collatedSilverOrders2.get(1), is(equalTo(expectedSilverBuyOrder12)));
        assertThat(collatedSilverOrders2.get(2), is(equalTo(expectedSilverSellOrder11)));
        assertThat(collatedSilverOrders2.get(3), is(equalTo(expectedSilverSellOrder12)));

        //remove 0.5kg, £307 (reduces SELL £307), 2.1kg, £303 (removes all BUY £303)
        silverOrderSystem.removeOrder(sellOrder2);
        silverOrderSystem.removeOrder(buyOrder3);

        CollatedSilverOrder expectedSilverBuyOrder21 = new CollatedSilverOrder(7.1, 289, OrderType.BUY);
        CollatedSilverOrder expectedSilverSellOrder21 = new CollatedSilverOrder(3.5, 303, OrderType.SELL);
        CollatedSilverOrder expectedSilverSellOrder22 = new CollatedSilverOrder(1.7, 307, OrderType.SELL);

        List<CollatedSilverOrder> collatedSilverOrders3 = silverOrderSystem.retrieveCollatedAllOrders();

        assertThat(collatedSilverOrders3.size(), is(3));

        assertThat(collatedSilverOrders3.get(0), is(equalTo(expectedSilverBuyOrder21)));
        assertThat(collatedSilverOrders3.get(1), is(equalTo(expectedSilverSellOrder21)));
        assertThat(collatedSilverOrders3.get(2), is(equalTo(expectedSilverSellOrder22)));
    }

    /**
     * Negative assertive Tests
    **/

    //cannot remove an order if exact order is not in the system
    @Test
    public void shouldNotAllowRemovalOfNonExistingOrder(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        silverOrderSystem.submitOrder(testOrder);

        List<CollatedSilverOrder> collatedOrderList = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList.size(), is(1));

        SilverOrder nonMatchingOrder1 = new SilverOrder("user2", 3.5, 303, OrderType.SELL);
        silverOrderSystem.removeOrder(nonMatchingOrder1);
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(1));
        SilverOrder nonMatchingOrder2 = new SilverOrder("user1", 3.8, 303, OrderType.SELL);
        silverOrderSystem.removeOrder(nonMatchingOrder2);
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(1));
        SilverOrder nonMatchingOrder3 = new SilverOrder("user1", 3.5, 304, OrderType.SELL);
        silverOrderSystem.removeOrder(nonMatchingOrder3);
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(1));
        SilverOrder nonMatchingOrder4 = new SilverOrder("user1", 3.5, 303, OrderType.BUY);
        silverOrderSystem.removeOrder(nonMatchingOrder4);
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(1));

        SilverOrder matchingOrder = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        silverOrderSystem.removeOrder(matchingOrder);
        assertThat(silverOrderSystem.retrieveCollatedSellOrders().size(), is(0));
    }

    @Test
    public void shouldAllowMultipleSameOrdersButOnlyRemoveOneAtATime(){
        SilverOrderSystem silverOrderSystem = new SilverOrderSystem();

        SilverOrder testOrder = new SilverOrder("user1", 3.5, 303, OrderType.SELL);
        silverOrderSystem.submitOrders(testOrder, testOrder); //add same order twice

        List<CollatedSilverOrder> collatedOrderList1 = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList1.size(), is(1));//one item with combined weight
        CollatedSilverOrder expectedSilverBuyOrder1 = new CollatedSilverOrder(7.0, 303, OrderType.SELL);
        assertThat(collatedOrderList1.get(0), is(expectedSilverBuyOrder1));

        silverOrderSystem.removeOrder(testOrder);//remove one
        List<CollatedSilverOrder> collatedOrderList2 = silverOrderSystem.retrieveCollatedSellOrders();
        assertThat(collatedOrderList2.size(), is(1));//one item, only one weight
        CollatedSilverOrder expectedSilverBuyOrder2 = new CollatedSilverOrder(3.5, 303, OrderType.SELL);
        assertThat(collatedOrderList2.get(0), is(expectedSilverBuyOrder2));

        silverOrderSystem.removeOrder(testOrder);//remove last item
        List<CollatedSilverOrder> collatedOrderList3 = silverOrderSystem.retrieveCollatedSellOrders();

        assertThat(collatedOrderList3.size(), is(0));
    }
}
