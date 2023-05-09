# Survey Submodule Source
This project represents the guts of the SurveySubmodule which will be included in the BroadPOS
Android project. This is not formatted as a valid submodule, but includes much of the logic
which must be present in that module. 

--- 
# Summary
This service provides Clients for two other REST API services: `TerminalRegister` developed by Pax Technology
and `Survey` developed by SoundPayments. Calls to these services are utilized by BroadPOS to register
a Terminal device with both API services. 

Further calls to TerminalRegister allow the device to search for registered stores, connect to stores, and get information
on the registration status. 

Further calls to Survey allow the device to fetch Questions and Questionnaires as defined in the SeamlessCommerce portal,
and upload responses.
 
# Components
## Authentication
The authentication process for the Survey submodule is broadly a two-step process:
1. Register Terminal with Store
  - Submodule will request registerData, which could determine that the terminal is already registered
  - If not already registered, display returned registerURL as QR Code
  - Client uses QR Code to access web form which will create a new Store
  - Terminal uses /ConnectStore endpoint to register terminal to the new Store
  - On successful connection to Store, terminal receives two Keys from the TerminalRegister API
2. Register Terminal with Survey
  - Submodule will use the registerRequestEncryptKey from Step1 to authenticate request to Survey/Register
  - Survey/Register response includes the RequestEncryptKey and Token which are required for authenticating further requests to Survey API 

After being registered with both TerminalRegister and Survey APIs, Survey Submodule should store the 
following authentication information on the local device:
- RequestEncryptKey (for Survey requests, from Survey/Register)
- Token (for Survey requests, from Survey/Register)
- DeviceID (for Survey requests, from Survey/Register)

### TerminalRegister Authentication Details
All TerminalRegister requests must include:
```java
class Example {
    public BaseRegisterRequest(String manufacturer, String model, String terminalSN, String certificate) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.terminalSN = terminalSN;
        this.certificate = certificate;
    }
}
```
These values should be accessible through the Neptune plugin/submodule

Requests also must include Headers for TimeStamp and Signature. Refer to TerminalRegister docs for details.

### Survey Authentication Details
Currently using these values to debug authentication process:

serial number: 0000002
request key: ce9d7c64b8dc3344
response key: c715cad6f997ba46

Issue:
receiving "parameter error"

Current Strategy:
Using requestEncryptKey provided by Seamless as Key to encode DeviceID + TimeStamp using SHA1 algorithm


