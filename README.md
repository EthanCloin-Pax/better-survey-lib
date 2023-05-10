# better-survey-lib
Project encapsulates API for TerminalRegister and Survey. Utilizes Retrofit + OkHttp libraries for HTTP requests. 
Unlike former survey-lib, this new project does not include overhead of extending FinancialApplication.

This project must fulfill a contract with BroadPOS modules and the Survey + TerminalRegister servers. 
BroadPOS modules will request information from this project, and this project will interact with the
Survey + TerminalRegister APIs to fulfill those requests

## Modules
## api
Contains Client and Interface definitions for TerminalRegister and Survey. Also includes an Authenticator and SurveyGateway to accept and authenticate requests.

[Api Module README](app/src/main/java/com/example/bettersurveylib/api/README.md)

### Client
Provides access to a OkHttp client instance equipped with a LoggingInterceptor and GsonConverter to support
easy conversion of Java object parameters.

### Interface
Outlines endpoints which will be added to the Client instance. Includes annotations to outline expected Headers,
Body, and URL.

## requests
Contains POJO with Retrofit annotations to describe request body. One class for each supported API Request

## responses
Contains POJO with Retrofit annotations to describe response body. One class for each supported API Request
