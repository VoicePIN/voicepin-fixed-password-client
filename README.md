# Getting Started

## Creating the voiceprints
In order to use our biometric sollution one has to register voiceprints first. Voiceprint is a set of biometric features that are representing the speaker based on his repeated utterances.
To register a voiceprint using our API, one has to call a POST method on resource: /passwordGroups/{passwordGroupId}. Returned id now will be pointing on a Voiceprint in our database.
By choosing the PasswordGroup one basically decides which password will be used for upcoming verifications.
It is not forbidden to add more than one representation of speaker to the same PasswordGroup but it is undesirable. If it is still necessary from whatever reasons (for example if we are aware that the same speaker will use different devices for biometric verification) then it is highly advised to make use of externalId while adding new voiceprints. It will increase the verification system usability for that speaker.

## Enrolling
Next step is to perform the enrollment for specified voiceprint. To do so it will be necessary to call a POST method on resource /voiceprints/{voiceprintId}/enrollments at least three times, delivering correctly spelled samples of utterances of the same speaker.
Person that is enrolling should speak correct phrase according to chosen PasswordGroup, naturally, using possibly the same tone of voice during the process of enrollment

## Verifying/Resetting/Removing
Now, when the voiceprint is trained, it is possible to perform the verifications. It can be done by calling a POST method on resource /voiceprint/{voiceprintId}/verifications. Retrieved result will show whether the speaker's utterance matches to his Voiceprint or not.
To reset Voiceprint and allow speaker to perform enrollment once again, a DELETE method on resource /voiceprints/{voiceprintId}/enrollments should be used.
To completely remove the information about specified Voiceprint from database, a DELETE method on resource /voiceprints/{voiceprintId} shoud be called.

## Input file format Type:
Wave Sampling frequency: 8000 Hz
Bit depth: 16 bit
Codec: PCM
Channels: 1 (mono) Length: ~3­5 s
