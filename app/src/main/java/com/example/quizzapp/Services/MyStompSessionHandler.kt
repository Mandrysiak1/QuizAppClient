package com.example.quizzapp.Services



import com.example.quizzapp.objects.Message
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import java.lang.reflect.Type;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and
 * send a sample message to server.
 *
 * @author Kalyan
 *
 */
class MyStompSessionHandler : StompSessionHandlerAdapter() {

    var logger : Logger = LogManager.getLogger(MyStompSessionHandler::class)


    override fun afterConnected(session : StompSession, connectedHeaders : StompHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/123", this);
        logger.info("Subscribed to /topic/messages");
        session.send("/app/chat", getSampleMessage());
        logger.info("Message sent to websocket server");
    }


   override fun handleException(
       session: StompSession, @Nullable command: StompCommand?,
       headers: StompHeaders,
       payload: ByteArray,
       exception: Throwable
   ) {
        logger.error("Got an exception", exception);
    }


    override fun getPayloadType(headers : StompHeaders ) :Type{
        return Message::class.java
    }



    override fun handleFrame(headers: StompHeaders, @Nullable payload: Any?) {
         var msg:Message =  payload as Message;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private fun getSampleMessage() : Message {
         var msg  = Message();
        msg.from = "Nicky";
        msg.text = "Howdy!!";
        return msg;
    }
}