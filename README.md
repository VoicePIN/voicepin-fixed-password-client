# Getting Started

## Glossary
*	**UBM** – biometric universal background model for a given phrase; the system can determine which voice parameters in a given phrase are unique and which are common for a group of speakers,
*	**Password Group** – groups the users using the same phrase within a given organisation; an appropriate UBM file will be required to create a new Password Group,
*	**Voiceprint** – a biometric statistic model of a user's voice represented by a matrix of parameters created on the basis of training recordings; a given Voiceprint can only be assigned to one Password Group only,
*	**Voiceprint ID** – a unique Voiceprint ID number used during API method callouts which indicates the biometric model of a given user, 
*	**Playback Activity Detection (PAD)** – a system used to detect the re-use of a voice sample which has already been successfully used during a verification or an enrollment of the specified user

## Creating the voiceprints

In order to carry out an operation with API VoicePIN for a given user, create his/her Voiceprint. To do so, call the **POST** method on the resource:
*/passwordGroups/{passwordGroupName}*

From now on the returned Voiceprint ID will indicate the Voiceprint of a given user in the database. Until the registration, the Voiceprint cannot however be used for verification of a given speaker.

Voiceprint is assigned to a given Password Group (**passwordGroupName** parameter) which means that it is linked to the phrase which will be used during the verification.


## Enrolling

Before a user can be verified, the system must "know" his/her voice. To do so, the existing Voiceprint must be registered (so called "enrollment") on the basis of at least 3 voice samples which contain the phrase that will be used for verification. If a recording is unclear or has a noise in the background, the system may request another recording to be provided. During the registration a unique voice model is created which will be assigned to a given Voiceprint ID. In addition some characteristic features of the recording are registered which will be used by the PAD system.

To carry out the registration, call 3 times the **POST** method on the */voiceprints/{voiceprintId}/enrollments* resource at least 3 times; thus the VoicePIN system will be provided with the phrase correctly pronounced by the same person. The person to be registered should speak naturally. 

**enrollStatus** – system response that determines whether the recording was accepted or not; possible values:
  * SAMPLE_ACCEPTED – the recording is accepted,
  * SAMPLE_REJECTED – the recording contains an incorrect phrase or too much noise.

**trained** – information from the system returned after a sample was sent; determines whether a given Voiceprint is "trained" by appropriate number of samples.
  *	false – more samples are required,
  *	true – registration is successful.

The figure below shows a simplified diagram of system component integration during registration:

![Enroll process](https://cloud.githubusercontent.com/assets/15819858/11125262/45b87e2c-8969-11e5-9a5e-6ebb1da0acbc.png)

## Verifying

If a Voiceprint is registered ("trained"), a biometric identification can be carried out; to do so, call the **POST** method on the */voiceprint/{voiceprintId}/verifications* resource. This operation produces two values: score and decision which are described later in this document. These parameters can help to make a decision whether a given user should be authenticated, blocked or whether the verification attempt should be ignored.

**score** – the value returned by the system during the user verification. It represents the probability that the sample being verified and the statistical biometric model (Voiceprint) come from the same person. Higher values indicate higher reliability of the verification. The range of values depends on the phrase and the quality of system "training" – the better passphrase/training the higher scores will target users achieve. As standard, the score value for an attempt not being a Playback type attack ranges from <-50 to 100>.

**decision** – the system's decision related to a given verification which is made on the basis of values obtained from the signal processing engine and threshold values for a given Password Group; possible values:
  *	MATCH – the identity is confirmed,
  *	MISMATCH – no biometric match,
  *	FRAUD – the re-use of a given sample was detected,
  *	WRONG_PHRASE_SPOKEN – the phrase is different from the one used during the registration.

The figure below shows a simplified diagram of system component integration during verification:
![Verification process](https://cloud.githubusercontent.com/assets/15819858/11124756/cae4b8de-8966-11e5-8be2-7ba87707276c.png)

## Resetting the Voiceprint

By calling out the **DELETE** method on the */voiceprints/{voiceprintId}/enrollments* resource, the system enables resetting a given Voiceprint. Resetting the Voiceprint can be required if a given user has problems with logging in due to noisy environment or a significant and permanent change of a transmission channel. After resetting, the Voiceprint must be registered again (enrollment).

Contrary to the Voiceprint removal, resetting does not cause the information required for PAD system operation to be removed from the database upon the re-registration. 


## Removing the Voiceprint

To completely remove the information about specified Voiceprint from database, a **DELETE** method on resource */voiceprints/{voiceprintId}* should be called.

Contrary to resetting the Voiceprint, the method described here removes the information required for correct operation of PAD system in case of re-registration.

## Input file format Type:

Only **WAVE** files are allowed

Sampling frequency: **8000 Hz**

Codec: linear **PCM** or **PCM-A** or **PCM-U**

Bit depth: **16 bit** (linear PCM) or **8 bit** (PCM-A/PCM-U) 

Channels: **1 (mono)**

Length: **~2-­5s**
