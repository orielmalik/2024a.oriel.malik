package demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

@Component(value = "GiveTips")
public class GiveTips implements CommandExec, MyUpdateMethod {
	@Autowired
	private ObjectCrud objectCrud;
	private String[] badWords = { "fuck", "shit", "damn", "asshole", "bitch", "bastard", "cock", "cunt", "dick", "piss",
			"slut", "whore", "motherfucker", "ass", "arse", "bollocks", "bullshit", "crap", "douchebag", "fanny",
			"goddamn", "jackass", "prick", "pussy", "wanker", "twat", "bastard", "bloody", "bugger", "censored",
			"choad", "cum", "effing", "frack", "fudge", "hell", "jesus", "penis", "sex", "sucker", "tits", "vagina",
			"blowjob", "boner", "boobs", "butt", "cocksucker", "dumbass", "fingerbang", "handjob", "shithead",
			"titfuck", "whorehouse", "wtf" };

	@Value("${helper.delimiter}")
	private String delimiter;

	@SuppressWarnings("unchecked")
	@Override
	public Flux<MiniAppCommandBoundary> execute(MiniAppCommandBoundary input) {
		String tipsContentString = null;

		if (input.getCommandAttributes() == null) {
			input.setCommandAttributes(new HashMap<>());
		}
		// get the value of key "tips".
		Object tips = input.getCommandAttributes().get("tips");
		// if there is no tips, return a message.
		if (tips == null) {
			input.getCommandAttributes().put("result", "You must add some tips.");
			return Flux.just(input);
		}

		// check if the tips sent as a Map.
		/*
		 * for example, commandAttributes:{ "tips" : { 
		 * 										"tip1" : "my tip1",
		 * 										"tip2" : "my tip2" 
		 * 										} 
		 * }
		 */
		if (tips instanceof Map<?, ?>) {
			// cast the tips to Map<String,String>
			Map<String, String> tipsMap = (Map<String, String>) tips;
			// get the number of tips.
			int tipsNum = tipsMap.size();
			// initialize ArrayList to store the tips content from the Map.
			List<String> tipsContentList = new ArrayList<>();
			// Store the tips content in the ArrayList.
			for (String value : tipsMap.values()) {
				tipsContentList.add(value);
			}
			// check if some tips do not have content.
			if (tipsContentList.size() != tipsNum) {
				input.getCommandAttributes().put("result", "Some tips do not have content.");
				return Flux.just(input);
			}
			// convert the ArrayList to string.
			tipsContentString = String.join(" ", tipsContentList);
		}

		// check if the tips sent as string
		/*
		 * for example, commandAttributes:{ "tips" : 
		 * 									"1. first tip 
		 * 									 2. second tip" 
		 * 
		 * }
		 */
		if (tips instanceof String) {
			tipsContentString = (String) tips;
		}
		// check if there are any bad words in the tips content.
		int validateContentResult = containsBadWords(tipsContentString, badWords);
		if (validateContentResult != 0) {
			input.getCommandAttributes().put("result",
					"There is " + validateContentResult + " bad words, please be respectful.");
			return Flux.just(input);
		}
		// add result message if every thing is valid.

		else {
			input.getCommandAttributes().put("result", "tips sent to target object.");
		}
		String targetObjectId = input.getTargetObject().getObjectId().getSuperapp() + delimiter
				+ input.getTargetObject().getObjectId().getId();
		return updateCounselorTargetObjects(targetObjectId, input.getCommandAttributes()).thenMany(Flux.just(input))
				.log();

	}

	public int containsBadWords(String inputString, String[] badWords) {
		// convert the badWords array to Set.
		Set<String> badWordSet = new HashSet<>(Arrays.asList(badWords));
		// split the input String by whitespace.
		String[] words = inputString.split("\\s");
		int counter = 0;
		// count how many bad words are in the string.
		for (String word : words) {
			if (badWordSet.contains(word.toLowerCase())) {
				counter++;
			}
		}

		return counter;
	}

	@Override
	public Mono<Void> updateCounselorTargetObjects(String targetObjectId, Map<String, Object> details) {
		Map<String, Object> temp = new HashMap<>(details);
		temp.remove("result");
		return this.objectCrud.findById(targetObjectId).map(object -> {
			temp.forEach((key, value) -> object.getObjectDetails().put(key, value));
			return object;
		}).flatMap(this.objectCrud::save).map(ObjectBoundary::new).then();
	}
}
