# Getting Started

![Enroll and Verify process](https://cloud.githubusercontent.com/assets/1618196/8716463/aa4fc09c-2b91-11e5-9d2e-2db257d139de.jpg)

## Glossary

* PasswordGroup - associates the speakers that use the same password inside the same organization identified with specific API Key. Organizations may have got more than one PasswordGroup
corresponding to the same password.
* PasswordGroupId - is an unique name of a PasswordGroup that will be used for speaker assignment. PasswordGroupId must be exclusive only inside one organization.
* VoicePrint - is a representation of the speaker inside VoicePIN system. It must correspond to only one passwordGroup.
* VoicePrintId - UUID of the VoicePrint. It is used for API calls associated to speaker enrollment and verification, as well as for resetting and removing the VoicePrint.
* Verification Score - Is an output of the verify method. Higher values mean more
reliable verification attempt.
* Verification Doubt Score - Is the one of the results of the verify method. Higher
values mean more possible fraud attempt.
* Verification Decision - Is an enumerated value returned by the verify method, that should be used to decide whether verifying person should be authenticated or not. Possible values are: **MATCH** - when the speaker is recognized, **MISMATCH** - when the speaker is not recognized and **FRAUD** - when the playback is detected.

## Creating the voiceprints

In order to use our biometric solution one has to register voiceprints first. Voiceprint is a set of biometric features that are representing the speaker based on his repeated utterances.

To register a voiceprint using our API, one has to call a **POST** method on resource: */passwordGroups/{passwordGroupId}*. Returned id now will be pointing on a Voiceprint in our database.

By choosing the **PasswordGroup** one basically decides which password will be used for upcoming verifications.

### externalId

It is not forbidden to add more than one representation of speaker to the same **PasswordGroup** but it is undesirable. If it is still necessary from whatever reasons (for example if we are aware that the same speaker will use different devices for biometric verification) then it is highly advised to make use of **externalId** while adding new voiceprints. It will increase the verification system usability for that speaker.

Additionally, all samples that are stored for the speaker identifying with
the same externalId will be used to detect possible fraud attempt, what is described
more particularly in section [Playback Detection](README.md#playback-detection).

## Enrolling

Next step is to perform the enrollment for specified voiceprint. To do so it will be necessary to call a **POST** method on resource */voiceprints/{voiceprintId}/enrollments* at least three times, delivering correctly spelled samples of the utterance
spoken by the same speaker and with the same content.
Content of the utterance depends on the chosen **PasswordGroup**.

Person that participate in the registration, should speak naturally, using possibly the same tone of voice during the whole process.

## Verifying

Now, when the voiceprint is trained, it is possible to perform the verifications. It can be done by calling a **POST** method on resource */voiceprint/{voiceprintId}/verifications*. Retrieved result consists of Verification Score, Verification Doubt Score and
Verification Decision, described briefly in [glossary](README.md#glossary) section. Those
parameters will help to decide whether user should be authenticated or not.

### Playback Detection

Provided samples are tested for possibility of fraud attempt during the verification
process. Thus, submitting the same audio sample more than once
will cause playback detection and **FRAUD** decision will be returned
as a verification operation result along with Verification Doubt Score, described in [glossary](README.md#glossary) section.  

## Resetting

To reset Voiceprint and allow speaker to perform enrollment once again, a **DELETE** method on resource */voiceprints/{voiceprintId}/enrollments* should be used.

Unlike the **Remove** operation, resetting the Voiceprint does not erase the
data used for [Playback Detection](README.md#playback-detection). That makes this operation safe.

## Removing

To completely remove the information about specified Voiceprint from database, a **DELETE** method on resource */voiceprints/{voiceprintId}* should be called.

Unlike the **Reset** operation, removing the Voiceprint also removes the
data used for [Playback Detection](README.md#playback-detection). That makes this operation unsafe.

## Input file format Type:

Wave Sampling frequency: 8000 Hz

Bit depth: 16 bit

Codec: PCM

Channels: 1 (mono)

Length: ~2-­5s
