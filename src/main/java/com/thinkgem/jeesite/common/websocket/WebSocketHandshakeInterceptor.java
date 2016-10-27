package com.thinkgem.jeesite.common.websocket;

import java.util.Map;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
@Repository
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
		System.out.println("握手之前····");
		attributes.put("currentUser", UserUtils.getUser());
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("握手之后····");
		super.afterHandshake(request, response, wsHandler, exception);
	}
 
}