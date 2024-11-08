package eello.notificationgeneration.service;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestMessage;

public interface NotificationGenerationWorker {

    String LISTENER_METHOD_NAME = "generate";

    void generate(NotificationGenerationRequestMessage message);
}
