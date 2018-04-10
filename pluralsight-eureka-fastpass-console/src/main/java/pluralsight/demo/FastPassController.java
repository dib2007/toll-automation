package pluralsight.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class FastPassController {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallBackUserDetails")
	@RequestMapping(path = "/customerdetails", params = { "fastpassid" })
	public String getFastPassCustomerDetails(@RequestParam String fastpassid, Model m) {

		// RestTemplate rest = new RestTemplate();
		FastPassCustomer c = restTemplate.getForObject(
				"http://pluralsight-fastpass-service/fastpass?fastpassid=" + fastpassid, FastPassCustomer.class);
		System.out.println("retrieved customer details");
		m.addAttribute("customer", c);
		return "console";
	}

	public String getFallBackUserDetails(@RequestParam String fastpassid, Model m) {
		FastPassCustomer customer = new FastPassCustomer();
		customer.setFastPassId(fastpassid);
		m.addAttribute("customer", customer);
		return "console";
	}

}
