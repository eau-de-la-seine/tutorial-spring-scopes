package fr.ekinci.scopes;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Gokan EKINCI
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SingletonScopedBean extends SomeValue {

}
