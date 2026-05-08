# IDity Android SDK

![Android](https://img.shields.io/badge/Platform-Android-lightgrey.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-11-orange.svg)
![Minimum Android](https://img.shields.io/badge/Android-21%2B-blue.svg)

**IDity** is an advanced identity verification SDK for Android. It helps you read and extract information from a user's ID card or Passport through a secure and seamless mobile experience.

---

## 🔑 Request a Client Key
To use the IDity library in your project, you must obtain a **Client Key**. 
Please contact **[idity@beinmobiles.com](mailto:idity@beinmobiles.com)** to get your credentials before starting your integration.

---

## 📦 Installation

### Swift Package Manager (SPM)
1. In Xcode, go to **File > Add Packages...**
2. Enter the following repository URL:
   `https://github.com/beinmobiles/IDity-iOS.git`
3. Set the dependency rule to the latest version or branch.

**Minimum Deployment Target:** Android 11

---

## ⚙️ Required Configuration

You must add the following permissions to your **AndroidManifest.xml** file. These are required for camera access to scan a document.

```xml
<!-- Required to capture document images for OCR -->
<uses-permission android:name="android.permission.CAMERA" />

<!-- Required for API-based recognition (if applicable) -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Ensures the app is only visible in the Play Store for devices with a camera -->
<uses-feature android:name="android.hardware.camera" android:required="true" />
```

---

## 🚀 Getting Started

### 1. Initialize the SDK
Initialize the SDK in your `Application`. You can pass a `tintColor` to match the SDK's UI elements (buttons and icons) to your app's branding. 

The `recognitionLanguage` parameter uses the `IDityLanguage` enum. This allows the SDK to prioritize specific character sets, ensuring higher recognition accuracy in multilingual regions.

```kotlin
com.beinmobiles.idity.*

override fun onCreate() {
    
    // Initialize the library
    IDitySDK.initialize(
        clientKey: "CLIENT_KEY",
        recognitionLanguage: IDityLanguage.LATIN,
        tintColor: Color.RED
    )
}
```

Using specific IDityLanguage cases improves OCR accuracy and performance by narrowing the character search space.

| Case | Language |
| :--- | :--- |
| `.ar` | `Arabic` |
| `.zh` | `Chinese` |
| `.en` | `English` |
| `.fr` | `French` |
| `.de` | `German` |
| `.it` | `Italian` |
| `.ja` | `Japanese` |
| `.ko` | `Korean` |
| `.pr` | `Portuguese` |
| `.ru` | `Russian` |
| `.es` | `Spanish` |
| `.uk` | `Ukrainian` |

**Optimize for Detection:** The order of the languages in the array is significant. The engine processes the list sequentially, so you should place the most likely language for your target users at the beginning of the array.

### 2. Start the Journey

```swift
// Start the verification process
IDitySDK.startJourney(
   this,
   this
)
```

---

## 📩 Handling Results (IDityJourneyCallbacks)

Conform to the IDityJourneyCallbacks protocol to handle the lifecycle and results of the verification process.

```kotlin
/// Called when the SDK successfully initializes and the UI appears
override fun onJourneyStarted() {
   print("IDity journey has started.")
}

/// Called when the document scanning and verification is successful
override fun onJourneyCompleted(infoObject: InfoObject) {
   // Access extracted data
   print("First Name: ${infoObject.firstName}")
   print("Last Name: ${infoObject.lastName}")
}

/// Called when the user manually closes the SDK before completion
override fun onJourneyCancelled() {
   print("User cancelled the verification process.")
}

/// Called if an error occurs during the journey
override fun onError(error: Exception) {
   print("IDity Error: ${error.message}")
}
```

---

## 📊 Data Models

The `IDityJourneyCallbacks` protocol returns an `InfoObject`. Use these models to map the verified data to your application's user profile.

### InfoObject
This object contains the personal details of the individual and the biometric face capture.

| Property | Type | Description |
| :--- | :--- | :--- |
| `firstName` | `String` | User's given name. |
| `lastName` | `String` | User's family name / Surname. |
| `fatherName` | `String` | Father's full name. |
| `motherFirstName` | `String` | Mother's first name. |
| `motherLastName` | `String` | Mother's maiden or last name. |
| `pob` | `String` | Place of Birth. |
| `dob` | `String` | Date of Birth. |
| `nationality` | `String` | User's nationality. |
| `gender` | `String` | Gender (M/F). |
| `faceImage` | `UIImage?` | The high-resolution selfie/face capture. |
| `document` | `DocumentObject` | Nested object containing document-specific data. |

### DocumentObject
This object contains the metadata and imagery of the scanned identification document.

| Property | Type | Description |
| :--- | :--- | :--- |
| `documentType` | `String` | Type of document (Passport, ID Card, etc.). |
| `idNumber` | `String` | The unique identification or passport number. |
| `issueDate` | `String` | Date the document was issued. |
| `expiryDate` | `String` | Date the document expires. |
| `issueCountry` | `String` | The country code or name that issued the document. |
| `documentImage` | `UIImage?` | High-quality image capture of the document front. |

---

## 🛡️ Privacy & Security

The IDity SDK is built with privacy in mind. Ensure that the PrivacyInfo.xcprivacy file included in the package is correctly bundled to comply with Apple's latest App Store requirements.

## 📧 Contact & Support

For technical support, feature requests, or to obtain your Client Key, please reach out:

Developer: beIN MOBILES

Email: idity@beinmobiles.com
