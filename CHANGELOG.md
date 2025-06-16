# [1.0.0-rc.2](https://github.com/Mobility-Data-Space/mds-logging-house-client/compare/v1.0.0-rc.1...v1.0.0-rc.2) (2025-06-16)


### Features

* creating in-memory store ([2608831](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/2608831f74a9223281e29c2dd4b31213c55967de))
* creating in-memory store ([0299636](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/0299636ed714aad4a723546527efbe8cd101b53e))

# 1.0.0-rc.1 (2025-06-16)


### Bug Fixes

* add more TransferStates and add field for LogMessage of TransferState ([e8d2e06](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/e8d2e062e33981bf46cbaa35afcfdf5c2c9c0284))
* add RequestMessage again to MultiContextJsonLdSerializer ([4d0e1b7](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/4d0e1b74ed8000441627293ec0cc672e030ac14d))
* checkstyle issues ([029eecb](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/029eecb30efc2b22e52f5bc6bdffd83520c4148c))
* checkstyle issues ([840faa2](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/840faa23735727d822da5c9d96e8d2e2fda609d1))
* checkstyle issues ([a76eee3](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/a76eee31923dc5f37ff72b1518b4df56b8e39bf0))
* checkstyle warnings ([d905084](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/d9050843fee3434388dc323700fe5d173b6d87d8))
* checkstyle warnings ([dd3dd10](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/dd3dd100c632a0c00f45b53ed35660a503c430ae))
* disabled state ([88ada14](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/88ada14334c37665367bf55403964114228fbfc6))
* **edc-extension:** remove unused maven repos ([#17](https://github.com/Mobility-Data-Space/mds-logging-house-client/issues/17)) ([7f6dea9](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/7f6dea9d6ce62b1fd7a3a12d3285c6ece218aed2))
* exclude docs and remove unused workflow ([f0c2ecc](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/f0c2ecc37442ea690a48414f88ac231192a9d325))
* fixing workers delay and period params ([1bafe35](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/1bafe358196606ec0abcf5116a839d3610cb7df6))
* flyway migrations conflict ([b66a38d](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/b66a38da47be67f372b6a6d5e236ecf27914ee94))
* handle more events for transfer ([5258ccb](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/5258ccb6e20f72b8068c52f3b3433a28a16b7e44))
* issue in insert statement, decode receipt ([2bbbc9a](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/2bbbc9a4d9b3745bf7639b8ed5b9ddf3d6c48026))
* logging house process id ([8ad5b4e](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/8ad5b4ea675745748fe2ef99e50a36fa1cdb9e78))
* message type for create process ([5dc8dda](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/5dc8dda55b3a2e83486b0462c31a3c3cbad610c8))
* ordering of pending statements ([810d7c1](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/810d7c173f9d10da8ea6cb23f43d894588c16289))
* registering event subscriber ([81740c9](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/81740c9d48e3766a94b3ca5ba0bfeef5baa785c4))
* remove not null constraint for TransferProcess ([e2e7187](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/e2e718706f2acac36dc7bddd9528dcb555e59d57))
* remove status parsing of not existent status column ([7650ed4](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/7650ed4a052690e10846496655ced698f7206ef3))
* restructure exception handling ([c0215f3](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/c0215f3230de38b369b0b889bc6e87056c22865b))
* serializer ([0f63780](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/0f63780c5b086ebf24310c02cf31c31613137fa6))
* special case of unordered messages, where logginghouse is shortly not available ([c49573f](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/c49573fbeec32c3f92fc4182a5f8fc173ecfd8bf))
* typo in release workflow ([19d9bba](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/19d9bba39bb365c1532235de15a8acd32dcfd7f9))
* urls for logging and process creation ([ac6665a](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/ac6665a80cd831398de1b9296b2a20607092ca78))
* use user token on release to trigger event workflows ([f15caa7](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/f15caa77cd9af588bfa0527b31454457c63a2dbc))


### Features

* add release pipline with build and publish trigger ([#18](https://github.com/Mobility-Data-Space/mds-logging-house-client/issues/18)) ([934a3ab](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/934a3ab3bacef488313d1816d7c1f61b16a7bb81))
* Added CustomLoggingHouseEvent with an implementation of ConnectorAvailableEvent ([8098a5a](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/8098a5ae1740afbfe3680576108caeb6a436e02b))
* flyway migrations ([7f86700](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/7f86700ee8c739c12fd1bcb99d48a373d4141bb2))
* improving error logs ([7755e27](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/7755e27263290fa6fcc7970841fca078f9f76b6a))
* logging house messages store ([cb9127c](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/cb9127cec0fb1185be812b5b762a51e39ac4c721))
* logging house messages store ([3df5a4b](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/3df5a4b700f1446ba7f5d9ad8d24a3fec804d6ea))
* logging house sender worker ([817ef84](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/817ef84dd2059e8c9dc4b936f3321978d9f51910))
* logging house sender worker ([2e3489e](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/2e3489e69b273f94192cb4991afc7150b4a897db))
* mds requested properties ([2c2d257](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/2c2d257d4fe851711b8caafce52b5f5cf24cd7b8))
* migrating to edc 0.12.0 ([0e7b84e](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/0e7b84eac41c26bb13908b6e21f77156e76a82ec))
* migrating to edc 0.13.0 ([3a98ab6](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/3a98ab62c707fa238f62862b6d4964ecef8f2bb9))
* receive and store receipt into database ([b12aa3d](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/b12aa3d020d217f25b2427a77fd0ecf35012cf2b))
* send retries ([701f1d9](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/701f1d92daab91a3744c9848b007d49db908f9e5))
* **server:** init logging-house-server with publish job ([fcce174](https://github.com/Mobility-Data-Space/mds-logging-house-client/commit/fcce174b431f7f92b0842a97d99e45f5caefa69d))
