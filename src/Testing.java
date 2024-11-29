import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Testing {

    private TreeSet<FoodItem> menu;
    private Order currOrder;
    private Customer customer;
    private List<Customer> AllCustomers;

    @BeforeEach
    void setUp() {
        //String name, String address, String phone,int CustomerID,String email, String password,boolean isVIP
        customer = new Customer("Sample","Add","123",1,"test.com","test",false);
        AllCustomers=new ArrayList<>();
        AllCustomers.add(customer);
        menu = new TreeSet<>((a, b) -> Integer.compare(a.getId(), b.getId()));
        menu.add(new FoodItem(1, "Burger","snacks", 50.0, 10));
        menu.add(new FoodItem(2, "Pizza", "snacks",100.0, 5));
        currOrder = new Order(customer.getCustomerID(),(customer.getVIP()?"VIP":"REGULAR"));
    }
    @Test
    void InvalidQuantityItem() {
        String result = customer.getMyCart().addItem(1, 200,menu, currOrder, 101);
        assertEquals("Quantity exceeds current availability! Please reduce the quantity.", result);
    }
    @Test
    void ValidQuantityItem() {
        String result = customer.getMyCart().addItem(1, 5,menu, currOrder, 101);
        assertEquals("Item successfully added!",result);
    }

    @Test
    void checkInvalidLogin(){
        String email = customer.getEmail();
        String password = customer.getPassword() + "hello";
        assertThrows(InvalidLoginException.class, () -> CustomerLogin.checkCustomerLogin(email,password,AllCustomers));
    }

    @Test
    void checkValidLogin() throws InvalidLoginException {
        Customer result = CustomerLogin.checkCustomerLogin(customer.getEmail(),customer.getPassword(),AllCustomers);
        assertEquals(customer,result);
    }
}
