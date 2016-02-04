package controller;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import model.CustomerBean;
import model.CustomerService;

public class LoginAction extends ActionSupport implements SessionAware, ServletContextAware, Preparable {
	//接收資料
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

//	@Override
//	public void validate() {
//		if(username==null || username.trim().length()==0) {
//			this.addFieldError("username", this.getText("login.username.required"));
//		}
//		if(password==null || password.trim().length()==0) {
//			this.addFieldError("password", this.getText("login.password.required"));
//		}
//	}
	
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	private ServletContext application;
	@Override
	public void setServletContext(ServletContext context) {
		this.application = context;
	}

	private CustomerService customerService;
	@Override
	public void prepare() throws Exception {
		ApplicationContext context = 
				WebApplicationContextUtils.getWebApplicationContext(application);
		customerService = (CustomerService) context.getBean("customerService");
	}

	//呼叫model, 根據model執行結果顯示view
	@Override
	public String execute() throws Exception {
		CustomerBean bean = customerService.login(username, password);
		if(bean==null) {
			this.addFieldError("password", this.getText("login.failed"));
			return Action.INPUT;
		} else {
			session.put("user", bean);
			
			return Action.SUCCESS;
		}
	}
}
