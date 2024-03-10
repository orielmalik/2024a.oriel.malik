package demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import demo.boundries.MiniAppCommandBoundary;
import demo.interfaces.CommandExec;
import demo.interfaces.ObjectCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component(value = "Offers")
public class Offers implements CommandExec {

	private final ObjectCrud objectCrud;

	public Offers(ObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}

	@Value("${helper.delimiter}")
	private String delimiter;

	@Override
	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input) {
		if (input.getCommandAttributes() == null) {
			input.setCommandAttributes(new HashMap<>());
		}

		if (input.getCommandAttributes().containsKey("offers")) {
			Object offers = input.getCommandAttributes().get("offers");

			// if there is no offers, return a message.
			if (offers == null) {
				input.getCommandAttributes().put("result", "You must add some offers.");
				return Flux.just(input);
			}
			Map<String, Object> counselorOffers = new HashMap<>();
			counselorOffers.put("counselorOffers", offers);
			// update dreamer
			udpateDreamerObject(counselorOffers, input.getTargetObject().getObjectId().getSuperapp(),
					input.getTargetObject().getObjectId().getId());
			// update command boundary
			input.getCommandAttributes().put("result", "offers sent to dreamer, waitting for response.");
			return Flux.just(input);
		}

		else if (input.getCommandAttributes().containsKey("offersResponse")) {
			Object offersResponse = input.getCommandAttributes().get("offersResponse");

			// check offersResponse
			if (offersResponse == null) {
				input.getCommandAttributes().put("result", "You must add some offers.");
				return Flux.just(input);
			}

			Map<String, Object> dreamerAcceptedOffers = new HashMap<>();
			dreamerAcceptedOffers.put("acceptedOffers", offersResponse);

			// update dreamer with the offers response
			udpateDreamerObject(dreamerAcceptedOffers, input.getTargetObject().getObjectId().getSuperapp(),
					input.getTargetObject().getObjectId().getId());

			// update command boundary
			input.getCommandAttributes().put("result", "offers accepted by dreamer.");
			return Flux.just(input);
		}

		input.getCommandAttributes().put("result",
				"There is no offers or offersResponse, we cant execute this command.");

		return Flux.just(input);
	}

	public Mono<Void> udpateDreamerObject(Map<String, Object> objectDetails, String targetObjectSuperapp,
			String targetObjectId) {
		String objectId = targetObjectSuperapp + delimiter + targetObjectId;
		return this.objectCrud.findById(objectId).map(object -> {
			object.setObjectDetails(objectDetails);
			return this.objectCrud.save(object);
		}).log().then();

	}

}
