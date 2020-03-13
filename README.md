
--- Introduction:
This Demo is a Spring Boot Application for building Restful Apis,
build from scratch here: https://start.spring.io/

--- How to use this Demo:
(1) mvn clean install
(2) run CenturyLinkDemoApplication
(3) Test api in postman or cURL, there are 3 apis (The local test results in under /Screenshots folder)
    here I use two github account for testing
       https://github.com/henrychen222 (This is my github account)
       https://github.com/996icu
    postman test example:
        http://localhost:8080/getFollowers/henrychen222
        http://localhost:8080/getStargazers/996icu/996.ICU
        http://localhost:8080/getRepoInfo/henrychen222
    curl test example:
        under /CurlApiTest folder, run each command, or run the whole file via sh githubTrackApi.sh (Linux/mac)


--- Improvement:
    (1) Use Gson/Jackson instead of org.json
    (2) Apply Google Guice @inject compared with @Autowired (DI)
    (2) Swagger UI with @ApiOperation https://github.com/swagger-api/swagger-core/wiki/Annotations
