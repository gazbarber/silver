package test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SilverOrderTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldCreateOrder(){
        SilverOrder testOrder = new SilverOrder("user1", 3.5, 303, SilverOrder.OrderType.SELL);
        assertThat(testOrder.getUserId(), is("user1"));
        assertThat(testOrder.getWeight(), is(3.5));
        assertThat(testOrder.getCashValue(), is(303));
        assertThat(testOrder.getOrderType(), is(SilverOrder.OrderType.SELL));
    }


    /*
    Tests based on assumptions about expected behaviour (normally confirmed before implemented!)
     */
    @Test
    public void shouldNotAllowNullUserIdForOrder(){
        exceptionRule.expect(IllegalArgumentException.class);
        SilverOrder testOrder1 = new SilverOrder(null, 3.5, 303, SilverOrder.OrderType.SELL);
    }

    @Test
    public void shouldNotAllowEmptyUserIdForOrder(){
        exceptionRule.expect(IllegalArgumentException.class);
        SilverOrder testOrder2 = new SilverOrder("", 3.5, 303, SilverOrder.OrderType.SELL);
    }

    @Test
    public void shouldNotAllowNegativeOrderWeight(){
        exceptionRule.expect(IllegalArgumentException.class);
        SilverOrder testOrder = new SilverOrder("user1", -3.5, 303, SilverOrder.OrderType.SELL);
    }

    @Test
    public void shouldNotAllowNegativeOrderCashValue(){
        exceptionRule.expect(IllegalArgumentException.class);
        SilverOrder testOrder = new SilverOrder("user1", 3.5, -303, SilverOrder.OrderType.SELL);
    }

    @Test
    public void shouldNotAllowNullOrderType(){
        exceptionRule.expect(IllegalArgumentException.class);
        SilverOrder testOrder = new SilverOrder("user1", 3.5, 303, null);
    }
}