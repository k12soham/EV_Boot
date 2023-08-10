package evprj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import evprj.entity.ChargingSession;
import evprj.entity.ChargingStation;
import evprj.entity.EVChargingStation;
import evprj.entity.OCPPMessage;
import evprj.service.ChargingService;

@RestController
@CrossOrigin
public class ChargingController {
	
	
	@Autowired
	ChargingService chargingService;

	
	
	@PostMapping("/saveChargingStation")
	public ChargingStation saveChargingStation(@RequestBody ChargingStation chargingStation) {
		return chargingService.chargingStation(chargingStation);
	}
	
	

	@PostMapping("/saveChargingSession")
	public ChargingSession saveChargingSession(@RequestBody ChargingSession chargingSession) {
		return chargingService.chargingSession(chargingSession);
	}
	
	@PostMapping("/remote-start-transaction")
    public ResponseEntity<?> remoteStartTransaction(@RequestBody OCPPMessage ocppMessage) {
		 String messageType = ocppMessage.getMessage_type();
	        String chargePointId = ocppMessage.getCharging_id();
	        // ... process other properties of the OCPP message
	        
	        // You can implement the logic to handle the OCPP message here
	        // For example, sending a response or performing some actions based on the message
	        
	        // Optionally, you can also send a response back to the sender
	        // For example, if you expect the sender to receive a confirmation
	        // after processing the OCPP message, you can send a response like this:
	      
	        		return ResponseEntity.status(HttpStatus.ACCEPTED).body("success");
	        // response.setStatus("success");
	        // return response;
	        
	        // Note: The return type is void in this example since we're not sending any response back.

    }

    @PostMapping("/remote-stop-transaction")
    public void remoteStopTransaction(@RequestBody OCPPMessage ocppMessage) {
        // Process the RemoteStopTransaction OCPP message sent by the simulator
        // You can deserialize the JSON payload into your OCPP message model and handle the request here
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handleHello(String message) {
    	  //	Thread.sleep(1000);
    	System.out.println("ssdsdsdyyyy");
        // You can put any logic here to generate the continuous response.
        // For simplicity, we'll just reply with "Hello, <message>!"
    	System.out.println(message);
    
        return "Hello, " + message + "!";
    }
    
    
    

  /*  @GetMapping("/batteryapi")
    @ResponseBody
    public String Battery() {
    	return "battery";
    }*/
    
    
    @GetMapping("/batteryapi")
    public ModelAndView index () {
    	
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("battery.html");
        return modelAndView;
    }
    
    
    
  //  @PostMapping("/aaaa")
    
    


    
    /***************************************************************************************************************/
    
    
    @PostMapping("/saveEVChargingStation")
	public ResponseEntity<?> saveEVChargingStation(@RequestBody EVChargingStation evChargingStation) {
    	System.out.println("ssyyy");
		return chargingService.saveEVchargingStation(evChargingStation);
	}

 
    
    @GetMapping("/getByStationId")
    public ResponseEntity<?> getByStationId(@RequestParam int charging_station_id){
    	return chargingService.getByStationId(charging_station_id);
    }
    
    

}
