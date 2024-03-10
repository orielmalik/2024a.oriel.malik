package demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import demo.boundries.MiniAppCommandBoundary;
import demo.interfaces.CommandExec;
import reactor.core.publisher.Flux;

@Component(value = "Offers")
public class Offers implements CommandExec {

	@Value("${helper.delimiter}")
	private String delimiter;

	@SuppressWarnings("unchecked")
	@Override
	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input) {
		String offersString = null;

		if (input.getCommandAttributes() == null) {
			input.setCommandAttributes(new HashMap<>());
		}
		// get the value of key "offers".
		Object offers = input.getCommandAttributes().get("offers");
		// if there is no offers, return a message.
		if (offers == null) {
			input.getCommandAttributes().put("result", "You must add some offers.");
			return Flux.just(input);
		}

		// check if the offers sent as a Map.
		/*
		 * for example, commandAttributes:{ "offers" : { "offer1" : "my offer1", "offer2" :
		 * "my offer2" } }
		 */
		if (offers instanceof Map<?, ?>) {
			// cast the offers to Map<String,String>
			Map<String, String> offersMap = (Map<String, String>) offers;

			// initialize ArrayList to store the offers from the Map.
			List<String> offersList = new ArrayList<>();
			// Store the offers in the ArrayList.
			for (String value : offersMap.values()) {
				offersList.add(value);
			}

			// convert the ArrayList to string.
			offersString = String.join(" ", offersList);
		}

		// check if the tips sent as string
		/*
		 * for example, commandAttributes:{ "offers" : "1. first offer 2. second offer" }
		 */
		if (offers instanceof String) {
			offersString = (String) offers;
		}

		return Flux.just(input);
	}
}
