# better-survey-lib
Project encapsulates API for TerminalRegister and Survey. Utilizes Retrofit + OkHttp libraries for HTTP requests. 
Unlike former survey-lib, this new project does not include overhead of extending FinancialApplication.

This project must fulfill a contract with BroadPOS modules and the Survey + TerminalRegister servers. 
BroadPOS modules will request information from this project, and this project will interact with the
Survey + TerminalRegister APIs to fulfill those requests

# BroadPOS Contract
| BroadPOS Requests | survey-lib Response |
| ----------------- | ------------------- |
| isTerminalRegistered? | `boolean`: true if registered |
| getRegistrationUrl | `string`: URL of web form to register terminal |

## Modules
## api
Contains Client and Interface definitions for TerminalRegister (and soon Survey)

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

## Flowchart
![Uptrillion_Flowchart drawio](https://user-images.githubusercontent.com/111765065/225148047-989260ba-de3f-4190-9dc3-13a7f59bab7d.png)

