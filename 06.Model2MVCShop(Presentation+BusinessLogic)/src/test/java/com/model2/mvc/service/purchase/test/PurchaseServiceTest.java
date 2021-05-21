package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		User user = new User();
		Product product = new Product();
		
		user.setUserId("user11");
		product.setProdNo(10001);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption("2");
		purchase.setReceiverName("리시버Name");
		purchase.setReceiverPhone("리시버Phone");
		purchase.setDivyAddr("배송주소");
		purchase.setDivyRequest("요청사항");
		purchase.setTranCode("3");
		purchase.setDivyDate("20210520");
		
		purchaseService.addPurchase(purchase);
		
//		Assert.assertEquals("user11", purchase.getBuyer());
//		Assert.assertEquals(10073, purchase.getPurchaseProd());
//		Assert.assertEquals("2", purchase.getPaymentOption());
//		Assert.assertEquals("3", purchase.getTranCode());
		
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
			
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10020);
			
		//==> console 확인
		System.out.println(purchase);
			
			//==> API 확인
		Assert.assertEquals(10020, purchase.getTranNo());
		Assert.assertEquals(10001, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("3", purchase.getTranCode().trim());
		Assert.assertEquals("2", purchase.getPaymentOption().trim());
		Assert.assertEquals("user11", purchase.getBuyer().getUserId());

	}
	
	//@Test
	public void testUpdateUser() throws Exception{
			 
		Purchase purchase = purchaseService.getPurchase(10020);
		Assert.assertNotNull(purchase);
		
//		Assert.assertEquals(10020, purchase.getTranNo());
//		Assert.assertEquals(10001, purchase.getPurchaseProd().getProdNo());
//		Assert.assertEquals("3", purchase.getTranCode().trim());
//		Assert.assertEquals("2", purchase.getPaymentOption().trim());
//		Assert.assertEquals("user11", purchase.getBuyer().getUserId());
		
		System.out.println(purchase);
		
		purchase.setReceiverName("Banwall");
		purchase.setPaymentOption("1");
		purchase.setTranCode("3");
		
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10020);
		Assert.assertNotNull(purchase);
		
		//==> console 확인
		System.out.println(purchase);
		
		//==> API 확인
		Assert.assertEquals("Banwall", purchase.getReceiverName());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("3", purchase.getTranCode().trim());
		
	}
	
	@Test
		 public void testGetPurchaseListAll() throws Exception{
			
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	
		 	Purchase purchase = new Purchase();
		 	String buyerId = "user11";
		 	Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);

		 }
	
	
	
}
