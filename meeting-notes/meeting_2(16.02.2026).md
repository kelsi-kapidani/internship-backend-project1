# Pyetje

* Shume nga req bazohen tek spring security (token gen and auth). Ndoshta ta rishikojme qe ta perdorim?

* Price string?

* Menyra e duhur/standart per req 10? Si eshte raport i kursimit te hapesires ne api call me llogaritjet extra ne db/frontend?

* Cfare vendoset si param cfare vendoset si body? Ka ndonje rule of thumb rule?

* Per cfare tip repsonse, request krijohen dto? A ka ndonje rule of thumb rule?


# Shenime

* Enum per vlera te pandryshueshme dhe universale (stinet e vitet, statuse etj)

* Path variable per id, requestbody per objekt komplex jo 1,2, requestparam per tek element qe nuk eshte id 

* addpositive annotation per integer

* Emrit e klasave nuk duhen te kene verb

* Emri i records ne dto duhet te mbaroje me DTO

* Shiko annotations e controllerave

* Shiko dependency inject (ka dhe 2 menyra te tjera dhe shih diferencen)

* Tek PUT Mapping eshte kur useri con full body gjithmone me cfare do ndryshohet edhe kur nuk ka ndryshim. Kur Presim qe vetem ndoshta nje pjese mund te cohet nga useri per dnryshim perdorim PATCH

* Tek Service4 cdo lloj validimi

* Shih badRequestException dhe ValidationAdvice si krijohen custom bad request response

* Provo qe te shtosh filtra

* Custom queries te jparepository: JPQL dhe Native Queries

* Middle interface layer between controller and service.

* ToString annotation shih dhe njehere

* Shih Relations between entities

* Shih Fetch types per onetomany, manytoone