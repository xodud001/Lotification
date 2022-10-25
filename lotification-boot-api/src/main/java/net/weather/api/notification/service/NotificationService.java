package net.weather.api.notification.service;

import io.github.jav.exposerversdk.*;
import lombok.RequiredArgsConstructor;
import net.weather.push_token.domain.PushToken;
import net.weather.push_token.service.PushTokenService;
import net.weather.user.domain.User;
import net.weather.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final PushTokenService pushTokenService;

    public void sendNotification(Long userId){
        User user = userService.findById(userId);
        PushToken pushToken = pushTokenService.findByUser(user);

        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(pushToken.getToken());
        expoPushMessage.setTitle("게임 시작 알림");
        expoPushMessage.setBody("소환사 롤로노아 김동영님께서 10분전에 게임을 시작하셨습니다");

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);

        PushClient client;
        try{
            client = new PushClient();
        }catch (PushClientException e) {
            throw new RuntimeException(e);
        }

        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);
        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
        }

        List<ExpoPushTicket> allTickets = new ArrayList<>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                allTickets.addAll(messageReplyFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client.zipMessagesTickets(expoPushMessages, allTickets);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client.filterAllSuccessfulMessages(zippedMessagesTickets);
        String okTicketMessagesString = okTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Received OK ticket for " +
                        okTicketMessages.size() +
                        " messages: " + okTicketMessagesString
        );

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client.filterAllMessagesWithError(zippedMessagesTickets);
        String errorTicketMessagesString = errorTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Received ERROR ticket for " +
                        errorTicketMessages.size() +
                        " messages: " +
                        errorTicketMessagesString
        );

        // Countdown 30s
        int wait = 30;
        for (int i = wait; i >= 0; i--) {
            System.out.print("Waiting for " + wait + " seconds. " + i + "s\r");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Fetching receipts...");

        List<String> ticketIds = (client.getTicketIdsFromPairs(okTicketMessages));
        CompletableFuture<List<ExpoPushReceipt>> receiptFutures = client.getPushNotificationReceiptsAsync(ticketIds);

        List<ExpoPushReceipt> receipts = new ArrayList<>();
        try {
            receipts = receiptFutures.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Received " + receipts.size() + " receipts:");

        for (ExpoPushReceipt receipt : receipts) {
            System.out.println(
                    "Receipt for id: " +
                            receipt.getId() +
                            " had status: " +
                            receipt.getStatus());

        }
    }
}
