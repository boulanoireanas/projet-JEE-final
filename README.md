# I- Membre :

  Abdelouahad Imad Eddine
  
# II- SI VOUS SOUHAITEZ TESTER LE CODE 

## Il suffit de suivre les étapes suivantes :<br>

  - Installation keycloak-20.0.1<br>
  - creation realm / client [config ci dessous]/ user / attribution roles ( USER / ADMIN ) au user 

   ![keycloak](https://user-images.githubusercontent.com/77898496/214346530-2506bf61-3ee5-4ef5-8ea3-a09bf46ba51d.png)

  - run mvn package au niveau du directory keycloakx-custom-theme<br>
  - ajout du .jar au niveau du dossier providers de keycloak<br>
  - lignes de commandes : ```kc.bat config``` et puis ```kc.bat start-dev```<br>
  - creation du realm (ecom-control-realm) et du client (product-app)<br>
  - creation des utilisateurs et des roles<br>
  - lancement des serveurs : Eureka discovery -> gateway puis inventory, customer puis kafka-service pour cloturer avec le billing service <br>
  - D:\5IIR\J2EE\TPS\TP5\kafka_2.13-3.3.1\bin>``start windows\zookeeper-server-start.bat ../config/zookeeper.properties``
  - D:\5IIR\J2EE\TPS\TP5\kafka_2.13-3.3.1\bin>``start windows\kafka-server-start.bat ../config/server.properties``

# III- CAPTURES DE L'EXECUTION
<p align="center">
  
<img width="889" alt="1" src="https://user-images.githubusercontent.com/66911835/219956879-4d8da280-90d3-41f8-aa43-3a919d2098b8.PNG">

<img width="835" alt="2" src="https://user-images.githubusercontent.com/66911835/219956967-7f6feaf2-c2fb-4dcc-970f-f528bf9690e6.PNG">


<img width="832" alt="3" src="https://user-images.githubusercontent.com/66911835/219957038-664a8084-7c21-4614-bda0-5249233cf336.PNG">



### les données ont été ajouté apres avoir été consommé du topic R1 de kafka


<img width="904" alt="4" src="https://user-images.githubusercontent.com/66911835/219957162-3fb263d7-35c7-48a5-aad5-8617ad58f27b.PNG">

![5](https://user-images.githubusercontent.com/77898496/214628751-5825cb76-bfa9-42fa-a7ef-472ec6dcd7c5.png)
### visualisation des données des factures produites dans le topic R1 et qui ont été consommé par le billing service 
D:\5IIR\J2EE\TPS\TP5\kafka_2.13-3.3.1\bin>``start windows\kafka-console-producer.bat --broker-list localhost:9092 --topic R1``

<p>

### j'ai une erreur `` data-analytics-service `` : `` can't deserialize data [[105, 109, 97, 110, 97]] from topic [r1] `` <br>
# V. DEPLOIMENT SUR DOCKER :
  
![Capture d’écran 2023-01-25 190700](https://user-images.githubusercontent.com/77898496/214646695-870c1d58-2c78-4f15-9dbb-ca608e4073c8.png)
  
![Capture d’écran 2023-01-25 190938](https://user-images.githubusercontent.com/77898496/214647104-95bd16a1-8f0c-4851-8258-2fe9d886b49e.png)

# IV. BONUS  : (Personnalisation du theme de la page de login keycloak) <br>


<img width="765" alt="7" src="https://user-images.githubusercontent.com/66911835/219957446-4efe0318-11e3-4352-8eac-451c4722b876.PNG">

# V. Taches : 


- [x] Customer Service
- [x] Inventory Service
- [x] Gateway Service
- [x] Eureka Discovery
- [x] Billing Service![Uploading 6.png…]()

- [x] Angular Service
- [x] Securiser Services Avec Keycloak
- [x] Intégration du broker kafka
- [x] Micro-service producer facture
- [x] Consommer factures du Topic kafka - billing service
- [ ] Data-Analytics-Service [ erreur ]
- [ ] Affichage courbe
- [x] Deploiment Kafka
  
## BONNE RECEPTION 😊 :tada:
