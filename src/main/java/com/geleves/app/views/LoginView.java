package com.geleves.app.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Connexion | Geleves")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();

	public LoginView(){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");  
		login.setI18n(createLoginI18n());

		add(new H1("Geleves"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation() 
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
	
	private LoginI18n createLoginI18n(){
		LoginI18n i18n = LoginI18n.createDefault();
		
		i18n.setHeader(new LoginI18n.Header());
		i18n.setForm(new LoginI18n.Form());
		i18n.setErrorMessage(new LoginI18n.ErrorMessage());
		
		
		// define all visible Strings to the values you want
		// this code is copied from above-linked example codes for Login
		// in a truly international application you would use i.e. `getTranslation(USERNAME)` instead of hardcoded string values. Make use of your I18nProvider
		i18n.getHeader().setTitle("Geleves");
	    i18n.getHeader().setDescription("Gestion des eleves");
	    i18n.getForm().setUsername("Utilisateur"); // this is the one you asked for.
	    i18n.getForm().setTitle("Connexion");
	    i18n.getForm().setSubmit("Se Connecter");
	    i18n.getForm().setPassword("Mot de Passe");
	    i18n.getForm().setForgotPassword("Restaurer mot de passe");
	    i18n.getErrorMessage().setTitle("Utilisateur/Mot de passe invalide");
	    i18n.getErrorMessage()
	        .setMessage("Verifier nom d'utilisateur et mot de passe puis reesayer.");
	    i18n.setAdditionalInformation(
	        "Veuillez saisir votre nom d'utilisateur et mot de passe pour vous connecter.");
	    return i18n;
	}
}