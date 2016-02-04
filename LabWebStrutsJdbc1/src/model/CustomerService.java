package model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component(value="customerService")
public class CustomerService {
	@Autowired
	private CustomerDAO customerDao;
	public void setCustomerDao(CustomerDAO customerDao) {
		this.customerDao = customerDao;
	}
	public static void main(String[] args) {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("beans.config.xml");
		CustomerService service = (CustomerService) context.getBean("customerService");
		
		CustomerBean bean = service.login("Alex", "A");
		System.out.println(bean);
		service.changePassword("Ellen", "EEE", "E");
		
		((ConfigurableApplicationContext) context).close();
	}
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if(newPassword!=null && newPassword.length()!=0) {
				byte[] temp = newPassword.getBytes();
				return customerDao.update(
						temp, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if(password!=null && password.trim().length()!=0) {
				byte[] pass = bean.getPassword();	//��Ʈw��X
				byte[] temp = password.getBytes();	//�ϥΪ̿�J
				if(Arrays.equals(pass, temp)) {
					return bean;
				}
			}
		}
		return null;
	}
}
