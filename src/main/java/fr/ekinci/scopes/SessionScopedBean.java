package fr.ekinci.scopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Gokan EKINCI
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SessionScopedBean extends SomeValue {

}
