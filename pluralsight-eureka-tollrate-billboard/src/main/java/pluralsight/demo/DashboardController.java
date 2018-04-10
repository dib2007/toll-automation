package pluralsight.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class DashboardController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getDafaultRate")
	@RequestMapping("/dashboard")
	public String GetTollRate(@RequestParam int stationId, Model m) {
		
		//RestTemplate rest = new RestTemplate();
		TollRate tr = restTemplate.getForObject("http://pluralsight-tollrate-service/tollrate/" + stationId, TollRate.class);
		System.out.println("stationId: " + stationId);
		m.addAttribute("rate", tr.getCurrentRate());
		return "dashboard";
	}
	
	public String getDafaultRate(@RequestParam int stationId, Model m) {
		System.out.println("Dafault rate is retrieved");
		m.addAttribute("rate", 1.00);
		return "dashboard";
	}
}
