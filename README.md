# Getting Started

![Enroll and Verify process](https://cloud.githubusercontent.com/assets/1618196/8716463/aa4fc09c-2b91-11e5-9d2e-2db257d139de.jpg)

## Glossary
*	UBM – biometric universal background model for a given phrase; the system can determine which voice parameters in a given phrase are unique and which are common for a group of speakers,
*	Password Group – groups the users using the same phrase within a given organisation; an appropriate UBM file will be required to create a new Password Group,
*	Voiceprint – a biometric statistic model of a user's voice represented by a matrix of parameters created on the basis of training recordings; a given Voiceprint can only be assigned to one Password Group only,
*	Voiceprint ID – a unique Voiceprint ID number used during API method callouts which indicates the biometric model of a given user, 
*	Playback Activity Detection (PAD) – a system used to detect the re-use of a voice sample which has already been successfully used during a verification or an enrollment of the specified user

## Creating the voiceprints

In order to carry out an operation with API VoicePIN for a given user, create his/her Voiceprint. To do so, call the POST method on the resource:
*/passwordGroups/{passwordGroupName}*

From now on the returned Voiceprint ID will indicate the Voiceprint of a given user in the database. Until the registration, the Voiceprint cannot however be used for verification of a given speaker.

Voiceprint is assigned to a given Password Group (passwordGroupName parameter) which means that it is linked to the phrase which will be used during the verification.


## Enrolling

Before a user can be verified, the system must "know" his/her voice. To do so, the existing Voiceprint must be registered (so called "enrollment") on the basis of at least 3 voice samples which contain the phrase that will be used for verification. If a recording is unclear or has a noise in the background, the system may request another recording to be provided. During the registration a unique voice model is created which will be assigned to a given Voiceprint ID. In addition some characteristic features of the recording are registered which will be used by the PAD system.

To carry out the registration, call 3 times the POST method on the */voiceprints/{voiceprintId}/enrollments* resource at least 3 times; thus the VoicePIN system will be provided with the phrase correctly pronounced by the same person. The person to be registered should speak naturally. 

**enrollStatus** – system response that determines whether the recording was accepted or not; possible values:
  * SAMPLE_ACCEPTED – the recording is accepted,
  * SAMPLE_REJECTED – the recording contains an incorrect phrase or too much noise.

**trained** – information from the system returned after a sample was sent; determines whether a given Voiceprint is "trained" by appropriate number of samples.
  *	false – more samples are required,
  *	true – registration is successful.

The figure below shows a simplified diagram of system component integration during registration:



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

Codec: linear PCM or PCM-A or PCM-U

Bit depth: 16 bit (linear PCM) or 8 bit (PCM-A/PCM-U) 

Channels: 1 (mono)

Length: ~2-­5s
