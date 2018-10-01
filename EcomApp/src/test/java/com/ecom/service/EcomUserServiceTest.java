package com.ecom.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecom.dao.iface.EcomUserDao;
import com.ecom.hibernate.modal.EcomUser;

public class EcomUserServiceTest {

	@Mock
	EcomUserDao ecomUserDao;
	
	@InjectMocks
	EcomUserServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testForCreatingNewUsr() {
		/*EcomUserDao usermock = mock(EcomUserDaoImpl.class);
		EcomUser user = new EcomUser();
		user.setId(2);
		user.setFirstName("naresh");
		user.setLastName("kumar");
		user.setUsername("naresh1988");
		user.setPassword("naresh1988");
		
		Product product = new Product();
		product.setId(2);
		
		when(usermock.createOrder(1, 1)).thenReturn(1L);
		
		EcomUserServiceImpl service = new EcomUserServiceImpl(usermock);
		long result = service.createOrder(1, 1);
		assertEquals(1L, result);
		List<Order> resultorder = usermock.getAllOrders(1);
		assertEquals(1,resultorder.size());*/
		EcomUser user = new EcomUser();
		user.setId(1);
		user.setFirstName("naresh");
		user.setLastName("kumar");
		user.setUsername("naresh1988");
		user.setPassword("naresh1988");
		
		when(ecomUserDao.createUser((EcomUser) any(EcomUser.class))).thenReturn(true);
		//boolean isEnterd = service.createUser(user);
		//assertThat(service.createUser(user), is(notNullValue()));
		//System.out.println(service.createUser(user));
		assertEquals(true,service.createUser(user));
	}















}





