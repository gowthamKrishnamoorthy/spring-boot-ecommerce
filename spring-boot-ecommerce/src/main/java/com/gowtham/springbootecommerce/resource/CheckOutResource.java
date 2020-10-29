package com.gowtham.springbootecommerce.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowtham.springbootecommerce.model.CheckOut;
import com.gowtham.springbootecommerce.repository.CheckoutRespository;

/**
 * The Class CheckOutResource.
 *
 * @author gowthamk
 * @version 1.0
 */
@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("*")
public class CheckOutResource {

	/** The CheckOut repository. */
	@Autowired
	private CheckoutRespository checkoutRespository;
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Gets all CheckOut.
	 *
	 * @return all CheckOut
	 */
	@GetMapping(value = "/all")
	@Transactional
	public List<CheckOut> getAll() {
		return checkoutRespository.findAll();
	}
	/**
	 * Gets CheckOut.
	 *
	 * @return CheckOut if exists
	 */
	@GetMapping(value = "/get/{userName}")
	public List<CheckOut> get(@PathVariable("userName") String userName) {
		return checkoutRespository.findByUsername(userName);
	}

	/**
	 * Delete CheckOut.
	 *
	 * @param id the id
	 * @return all categories
	 */
	@DeleteMapping(value = "/delete/{userName}")
	public boolean delete(@PathVariable("userName") String userName) {
		try {
			checkoutRespository.deleteByUsername(userName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * Put CheckOut.
	 *
	 * @param userName 		the userName
	 * @param int  the productId
	 * @param int the quantity
	 * @return all categories
	 */
	@PutMapping(value = "/put/{userName}")
	public List<CheckOut> put(@PathVariable String userName, @RequestBody Integer productId,@RequestBody Integer quantity) {
		List<CheckOut>  userCheckoutDataList = checkoutRespository.findByUsername(userName);
		List<CheckOut>  tempCheckoutDataList = new ArrayList<CheckOut>();
		if(userCheckoutDataList.isEmpty()) {
			CheckOut userCheckoutData = new CheckOut(userName, productId, quantity);
			tempCheckoutDataList.add(userCheckoutData);
		}else {
			for(CheckOut userCheckoutData : userCheckoutDataList) {
				CheckOut tempUserCheckoutData = new CheckOut(userCheckoutData.getUsername(), userCheckoutData.getProductId(), userCheckoutData.getQuantity());
				if(tempUserCheckoutData.getProductId() == productId) {
					tempUserCheckoutData.setQuantity(tempUserCheckoutData.getQuantity()+1);
				}
				tempCheckoutDataList.add(tempUserCheckoutData);
			}
		}

		checkoutRespository.deleteByUsername(userName);
		checkoutRespository.saveAll(tempCheckoutDataList);
		return checkoutRespository.findByUsername(userName);
	}

	/**
	 * post CheckOut.
	 *
	 * @param userName 		the userName
	 * @param int  the productId
	 * @param int the quantity
	 * @return all categories
	 */
	@PostMapping(value = "/checkout/{userName}")
	public boolean checkout(@PathVariable String userName) {
		List<CheckOut>  userCheckoutDataList = checkoutRespository.findByUsername(userName);
		if(userCheckoutDataList.isEmpty()) {
			return false;
		}else {
			try {
				sendEmail(userCheckoutDataList);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	void sendEmail(List<CheckOut> userCheckoutDataList)throws MessagingException, IOException {

		SimpleMailMessage msg = new SimpleMailMessage();
		String userName = userCheckoutDataList.get(0).getUsername();
		
		msg.setTo("abc@gmail.com");

		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello ${userName} \n Spring Boot Email");

		javaMailSender.send(msg);

	}
}
