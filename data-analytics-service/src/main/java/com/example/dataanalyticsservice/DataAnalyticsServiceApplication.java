package com.example.dataanalyticsservice;

import com.example.dataanalyticsservice.models.Bill;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SpringBootApplication
public class DataAnalyticsServiceApplication {
	@Autowired
	private InteractiveQueryService interactiveQueryService;

	public static void main(String[] args) {
		SpringApplication.run(DataAnalyticsServiceApplication.class, args);
	}
	/*
	@GetMapping(path = "/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE) // produces pr dire je vais retouner ce type
	public Flux<Map<String, Long>>  analytics(){
		return Flux.interval(Duration.ofSeconds(1)) // genere moi un stream et un enregistrement dans ce stream chaque seconde
				.map(sequence->{
					Map<String,Long> stringLongMap=new HashMap<>();
					// interoger le store page-count | on doit specifier le type de store dans ce cas on a utilisé de type windows sinon keyValueStore()
					ReadOnlyWindowStore<String, Long> windowStore = interactiveQueryService.getQueryableStore("customer-count", QueryableStoreTypes.windowStore());
					Instant now=Instant.now();
					Instant from=now.minusMillis(5000);
					// fetchAll : comme un select le calcule des 5 derniers secondes | mn db - 5s l db
					KeyValueIterator<Windowed<String>, Long> fetchAll = windowStore.fetchAll(from, now);
					//WindowStoreIterator<Long> fetchAll = windowStore.fetch("P1", from, now); // si vous voulez par des criteres exp le res de la page P1
					while (fetchAll.hasNext()){
						KeyValue<Windowed<String>, Long> next = fetchAll.next();
						// l'enregistrement qu'on va return
						//stringLongMap.put(next.key.window().startTime(),next.value); // par exp d'autres params
						stringLongMap.put(next.key.key(),next.value);
					}
					return stringLongMap;
				}).share(); // le stream est partagé par plusieurs users sinon chaque user recoit son propre flux
	}
	*/
	@Bean
	public Function<KStream<String, Bill>, KStream<String,Long>> kStreamFunction(){
		return (input)->{
			// on va lire du topic R1 pcq le supplier ecrit dans ce topic
			// chaque enregistrement s'appelle un data record
			// il y'a 2 partie : key et value
			// pour le moment key = null pcq on envoie des json comme value
			// on va produire en sortie des statistiques
			// exp la PAGE a ete visité combien de fois
			// un ktable : est le resultat
			return input
					.map((k,v)->new KeyValue<>(v.getCustomer().getName(),0L))// la cle mtn c est le nom de la page et la val c'est 0
					.groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Long()))// la clé va etre deserialisé et serialisé en format String et la valeur long - groupBy return un stream
					.windowedBy(TimeWindows.of(Duration.ofSeconds(5))) // statistique sur les 5 derniers cas - windowedBy : produit un ktable c pour ca qu on va ajouter to stream et map pour organiser
					.count(Materialized.as("customer-count"))
					//vue materializé : store qui s appel page-cont : on peut recuperer ces donnees et les afficher dans une application web
					// page-cont est un store ou ktable ou table
					// grouper par rapport a la clé
					.toStream() // le res est un stream
					.map((k,v)->new KeyValue<>(k.key(),v)); // pour avoir cet affichage =>2023-01-24T19:32:45Z2023-01-24T19:32:50Z:P1
			// on voit l'évolution et non pas le cumul
		};
	}
}
