package org.ing.grpc.interceptors;

import io.grpc.*;
import io.quarkus.grpc.GlobalInterceptor;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@GlobalInterceptor
public class HealthCheckInterceptor implements ServerInterceptor {

    static final Metadata.Key<String> SET_COOKIE_HEADER_KEY =
            Metadata.Key.of("Set-Cookie", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        return serverCallHandler.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {

            @Override
            public void sendHeaders(Metadata headers) {
                headers.put(SET_COOKIE_HEADER_KEY, "cookieName=quarkusCookie; Path=/; HttpOnly; Secure; SameSite=None");
                Log.info(SET_COOKIE_HEADER_KEY);
                Log.info(headers);
                super.sendHeaders(headers);
            }
            @Override
            public void sendMessage(RespT message) {
                Log.info("Sending message with Set-Cookie header");
                super.sendMessage(message);
            }


        }, metadata);
    }
}
