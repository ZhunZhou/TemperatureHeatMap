package com.temperature_mapping.votage_reader;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
//import org.springframework.web.util.HtmlUtils;

import com.fazecast.jSerialComm.SerialPort;

@Controller
public class ArduinoDataController {
	static SerialPort chosenPort = SerialPort.getCommPort("tty.usbmodem143101");

	@Autowired
	private SimpMessagingTemplate template;

	@SendTo("/topic/arduino_data")
	public void sendData(String content) {
		this.template.convertAndSend("/topic/arduino_data", new ArduinoData(content));
	}

	public void run() {
		chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		chosenPort.openPort();
		Scanner scanner = new Scanner(chosenPort.getInputStream());
		while (scanner.hasNextLine()) {
			try {
				String line = scanner.nextLine();
				sendData(line);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		scanner.close();
	}

}
