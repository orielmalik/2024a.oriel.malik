package demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import demo.boundries.MiniAppCommandBoundary;
import demo.boundries.ObjectBoundary;
import demo.interfaces.CommandExec;
import demo.interfaces.MyUpdateMethod;
import demo.interfaces.ObjectCrud;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component(value = "Offers")
public class Offers implements CommandExec, MyUpdateMethod {

	@Autowired
	private ObjectCrud objectCrud;

	@Value("${helper.delimiter}")
	private String delimiter;

	@Override
	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input) {
		String targetObjectId = input.getTargetObject().getObjectId().getSuperapp() + delimiter
				+ input.getTargetObject().getObjectId().getId();
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

			// update command boundary
			input.getCommandAttributes().put("result", "offers sent to dreamer, waitting for response.");
			// update dreamer
			return updateCounselorTargetObjects(targetObjectId, counselorOffers).thenMany(Flux.just(input)).log();

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

			// update command boundary
			input.getCommandAttributes().put("result", "offers accepted by dreamer.");
			// update dreamer
			return updateCounselorTargetObjects(targetObjectId, dreamerAcceptedOffers).thenMany(Flux.just(input)).log();

		}

		input.getCommandAttributes().put("result",
				"There is no offers or offersResponse, we cant execute this command.");

		return Flux.just(input);
	}

	@Override
	public Mono<Void> updateCounselorTargetObjects(String targetObjectId, Map<String, Object> details) {
		return this.objectCrud.findById(targetObjectId).map(object -> {
			object.getObjectDetails().remove("counselorOffers");
			details.forEach((key, value) -> object.getObjectDetails().put(key, value));
			return object;
		}).flatMap(this.objectCrud::save).map(ObjectBoundary::new).then();
	}

}
