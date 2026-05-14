## Notes

* Ne skenare kur ka shume veprime brenda nje service function duhet bere @transactional per tu ruajtur konsistence ne database dhe per te bere handle rastin kur ndodh nje problem i jahstem gjate zhvillimit te kodit
* ndarje te funksioneve jo vetem per eficence ne riperdorim por dhe per lexueshmeri
* Transcational ben rollback cdo operation/command te bere gjate atij funksioni nese leshohet nje exception 
* Mundohu te valido para se te persistosh
* Ne fillim kontrollo nese transactional fshin order nese request eshte invalid me pas ndryshoje me krijimin e order ne fund
* Book with id + book.getId() is not present in this library exception message ne validmin e dyte te createOrder()
* getAllPendingOrders reformato me mapper e ri jo manuale
* Ndrysho var names