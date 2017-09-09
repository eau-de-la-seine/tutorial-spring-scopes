package fr.ekinci.controllers;

import fr.ekinci.scopes.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. This controller is singleton scoped by default
 * 2. Cannot directly inject a request scoped bean and session scoped bean into singleton scoped bean, unless using {@link ObjectFactory}
 * 3. Tested with two different http clients simultaneously (Postman and Firefox). During the request in debug mode, {@link ObjectFactory#getObject()} returns :
 *     new instance if T is a `prototype` scoped component
 *     same instance <b>PER http client</b> if T is a `request` or `session` scoped component
 *
 * @author Gokan EKINCI
 */
@RestController
public class ScopeTestingController {
	private final AtomicInteger atomicInteger = new AtomicInteger(0);
	private final PrototypeScopedBean prototypeScopedBean;
	// private final RequestScopedBean requestScopedBean;
	// private final SessionScopedBean sessionScopedBean;
	private final ApplicationScopedBean applicationScopedBean;
	private final SingletonScopedBean singletonScopedBean;
	private final ObjectFactory<PrototypeScopedBean> op;
	private final ObjectFactory<RequestScopedBean> or;
	private final ObjectFactory<SessionScopedBean> os;

	@Autowired
	public ScopeTestingController(
		PrototypeScopedBean prototypeScopedBean,
		// RequestScopedBean requestScopedBean,
		// SessionScopedBean sessionScopedBean,
		ApplicationScopedBean applicationScopedBean,
		SingletonScopedBean singletonScopedBean,
		ObjectFactory<PrototypeScopedBean> op,
		ObjectFactory<RequestScopedBean> or,
		ObjectFactory<SessionScopedBean> os
	) {
		this.prototypeScopedBean = prototypeScopedBean;
		// this.requestScopedBean = requestScopedBean;
		// this.sessionScopedBean = sessionScopedBean;
		this.applicationScopedBean = applicationScopedBean;
		this.singletonScopedBean = singletonScopedBean;
		this.op = op;
		this.or = or;
		this.os = os;
	}

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request) {
		String previousValues = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
			"JSESSIONID: " + request.getRequestedSessionId(),
			showContent("prototypeScopedBean", prototypeScopedBean),
			// showContent("requestScopedBean", requestScopedBean),
			// showContent("sessionScopedBean", sessionScopedBean),
			showContent("applicationScopedBean", applicationScopedBean),
			showContent("singletonScopedBean", singletonScopedBean),
			showContent("op", op.getObject()),
			showContent("or", or.getObject()),
			showContent("os", os.getObject())
		);

		setNewValue(
			atomicInteger.incrementAndGet(),
			prototypeScopedBean,
			// requestScopedBean,
			// sessionScopedBean,
			applicationScopedBean,
			singletonScopedBean,
			op.getObject(),
			or.getObject(),
			os.getObject()
		);

		return previousValues;
	}

	private String showContent(String varName, SomeValue sm) {
		return (sm != null) ? sm.toString() : varName + " is null";
	}

	private void setNewValue(Integer value, SomeValue... someValues) {
		for (SomeValue someValue : someValues) {
			someValue.setValue("" + value);
		}
	}
}
