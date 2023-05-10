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
### Flow
Authentication with the Survey Service includes two stages:
1. Authentication of Register request (See `Authenticator.addAuthToRegisterRequest`):
   - This request body must include the DeviceID (or DeviceSN) and current TimeStamp
   - This request body must include a Signature value, created by encrypting the original request body with the registerRequestEncryptKey and the SHA1 algorithm.

2. Authentication of Survey requests (See `Authenticator.addAuthToSurveyRequest`):
   1. This request body must include the DeviceID, current TimeStamp, and Token (returned in Register response)
   2. This request must include a Signature value, created by encrypting the original request body with the decrypted registerResponseKey and the SHA1 algorithm
```java
public class RegisterReq {

    @SerializedName("TimeStamp")
    public String timestamp;

    @SerializedName("SignatureData")
    public String signatureData;

    @SerializedName("DeviceID")
    public String deviceID;

    @Nullable
    @SerializedName("DeviceSN")
    public String deviceSN;

    @Nullable
    @SerializedName("AppkeyIdentity")
    public String appKeyIdentity;
}
```
## Relevant Resources
- [Survey API Reference](https://paxus-my.sharepoint.com/:b:/g/personal/ethan_cloin_pax_us/ER5wMOegXcxCu4dRbQs3kJ4ByrSN-ybEaUxSbrrEO5uIXQ?e=z3Tsuv)
- [TerminalRegister API Reference](https://paxus.sharepoint.com/:w:/r/sites/mpos_internal/Shared%20Documents/Draft/Terminal%20Register%20Specification%20V1.00.00.docx?d=w52c34a43e9ae47a79d5c464e2e38327b&csf=1&web=1&e=caTqgG)
