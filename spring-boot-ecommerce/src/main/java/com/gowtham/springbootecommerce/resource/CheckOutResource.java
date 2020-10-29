package com.gowtham.springbootecommerce.resource;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gowtham.springbootecommerce.model.User;
import com.gowtham.springbootecommerce.repository.CheckoutRespository;
import com.gowtham.springbootecommerce.repository.UserRepository;
import com.gowtham.springbootecommerce.utils.EcommerceException;

/**
 * The Class CheckOutResource.
 *
 * @author gowthamk
 * @version 1.0
 */
@RestController
@RequestMapping("/api/checkout")
public class CheckOutResource {
	Logger logger = LoggerFactory.getLogger(CheckOutResource.class);

	/** The CheckOut repository. */
	@Autowired
	private CheckoutRespository checkoutRespository;
	@Autowired
	private JavaMailSender javaMailSender;
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

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
	public List<CheckOut> put(@PathVariable String userName, @RequestBody Integer productId,
			@RequestBody Integer quantity,@RequestBody Boolean isIncrement) {
		List<CheckOut>  userCheckoutDataList = checkoutRespository.findByUsername(userName);
		List<CheckOut>  tempCheckoutDataList = new ArrayList<CheckOut>();
		if(userCheckoutDataList.isEmpty()) {
			CheckOut userCheckoutData = new CheckOut(userName, productId, quantity);
			tempCheckoutDataList.add(userCheckoutData);
		}else {
			for(CheckOut userCheckoutData : userCheckoutDataList) {
				CheckOut tempUserCheckoutData = new CheckOut(userCheckoutData.getUsername(),
						userCheckoutData.getProductId(), userCheckoutData.getQuantity());
				if(tempUserCheckoutData.getProductId() == productId) {
					int modifiedQuantity = isIncrement ?
							tempUserCheckoutData.getQuantity() + quantity 
							: tempUserCheckoutData.getQuantity() + quantity;
					tempUserCheckoutData.setQuantity(modifiedQuantity);
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
	 * @param userName the userName
	 * @param int  the productId
	 * @param int the quantity
	 * @return all categories
	 */
	@PostMapping(value = "/checkout/{userName}")
	public boolean checkout(@PathVariable String userName) throws EcommerceException {
		Thread thread = new Thread(){
			public void run(){
				List<CheckOut>  userCheckoutDataList = checkoutRespository.findByUsername(userName);
				if(!userCheckoutDataList.isEmpty()) {
					try {
						sendEmail(userCheckoutDataList);
						checkoutRespository.deleteByUsername(userName);
					} catch (MessagingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EcommerceException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();
		return true;
	}

	/**
	 * sendEmail is used to send the confimation mail to the user.
	 * @param List 		userCheckoutDataList
	 * @return all categories
	 * @throws EcommerceException 
	 */
	void sendEmail(List<CheckOut> userCheckoutDataList)throws MessagingException, IOException, EcommerceException {

		SimpleMailMessage msg = new SimpleMailMessage();
		if(userCheckoutDataList.isEmpty()) {
			logger.error("User not found");
			throw new EcommerceException("");
		}
		String userName = userCheckoutDataList.get(0).getUsername();
		User user = userRepository.findById(userName).get();
		msg.setTo(user.getEmail());
		msg.setSubject("Order conformation");
		StringBuilder mailBody = new StringBuilder();
		mailBody.append("Hello" + userName);
		mailBody.append("\n" + "Your orders has been confirmed");
		msg.setText(mailBody.toString());

		javaMailSender.send(msg);
	}
}
